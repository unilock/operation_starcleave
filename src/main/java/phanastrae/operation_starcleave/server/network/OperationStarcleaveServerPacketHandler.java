package phanastrae.operation_starcleave.server.network;

import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import phanastrae.operation_starcleave.network.packet.c2s.AcknowledgeFirmamentRegionDataC2SPacket;
import phanastrae.operation_starcleave.network.packet.c2s.AttackFirmamentTileC2SPacket;
import phanastrae.operation_starcleave.world.firmament.Firmament;
import phanastrae.operation_starcleave.world.firmament.FirmamentTilePos;

public class OperationStarcleaveServerPacketHandler {
    
    public static void init() {
        ServerPlayNetworking.registerGlobalReceiver(AcknowledgeFirmamentRegionDataC2SPacket.ID, (payload, context) -> OperationStarcleaveServerPacketHandler.acknowledgeFirmamentRegionData(payload, context.player(), context.responseSender()));
        ServerPlayNetworking.registerGlobalReceiver(AttackFirmamentTileC2SPacket.ID, (payload, context) -> OperationStarcleaveServerPacketHandler.attackFirmamentTile(payload, context.player(), context.responseSender()));
    }

    public static void acknowledgeFirmamentRegionData(AcknowledgeFirmamentRegionDataC2SPacket packet, ServerPlayerEntity player, PacketSender responseSender) {
        FirmamentRegionDataSender.getFirmamentRegionDataSender(player.networkHandler).onAcknowledgeRegions(packet.desiredChunksPerTick());
    }

    public static void attackFirmamentTile(AttackFirmamentTileC2SPacket packet, ServerPlayerEntity player, PacketSender responseSender) {
        if(!player.getAbilities().creativeMode) return;

        World world = player.getWorld();
        if(world == null) return;
        Firmament firmament = Firmament.fromWorld(world);
        if(firmament == null) return;

        FirmamentTilePos tilePos = new FirmamentTilePos(packet.tileX(), packet.tileZ(), firmament);
        Vec3d tileCenter = tilePos.getCenter();
        if (!player.canInteractWithBlockAt(BlockPos.ofFloored(tileCenter), 3)) return;

        if(firmament.getDamage(tilePos.blockX, tilePos.blockZ) != 0) {
            firmament.setDamage(tilePos.blockX, tilePos.blockZ, 0);
            world.playSound(null, tileCenter.x, tileCenter.y, tileCenter.z, SoundEvents.BLOCK_GLASS_BREAK, SoundCategory.BLOCKS, 0.5f, 1);
        }
    }
}
