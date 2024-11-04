package phanastrae.operation_starcleave.network;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.network.ChunkBatchSizeCalculator;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import phanastrae.operation_starcleave.entity.projectile.StarbleachedPearlEntity;
import phanastrae.operation_starcleave.network.packet.OperationStarcleavePacketTypes;
import phanastrae.operation_starcleave.network.packet.c2s.AcknowledgeFirmamentRegionDataC2SPacket;
import phanastrae.operation_starcleave.network.packet.s2c.*;
import phanastrae.operation_starcleave.render.ScreenShakeManager;
import phanastrae.operation_starcleave.render.firmament.FirmamentTextureStorage;
import phanastrae.operation_starcleave.world.OperationStarcleaveWorld;
import phanastrae.operation_starcleave.world.firmament.*;

public class OperationStarcleaveClientPacketHandler {

    public static void init() {
        ClientPlayNetworking.registerGlobalReceiver(StartFirmamentRegionSendS2CPacket.ID, (pkt, ctx) -> OperationStarcleaveClientPacketHandler.startFirmamentRegionSend(pkt, ctx.player(), ctx.responseSender()));
        ClientPlayNetworking.registerGlobalReceiver(FirmamentRegionDataS2CPacket.ID, (pkt, ctx) -> OperationStarcleaveClientPacketHandler.receiveFirmamentRegionData(pkt, ctx.player(), ctx.responseSender()));
        ClientPlayNetworking.registerGlobalReceiver(FirmamentRegionSentS2CPacket.ID, (pkt, ctx) -> OperationStarcleaveClientPacketHandler.sentFirmamentRegion(pkt, ctx.player(), ctx.responseSender()));
        ClientPlayNetworking.registerGlobalReceiver(UpdateFirmamentSubRegionS2CPacket.ID, (pkt, ctx) -> OperationStarcleaveClientPacketHandler.updateFirmamentSubRegion(pkt, ctx.player(), ctx.responseSender()));
        ClientPlayNetworking.registerGlobalReceiver(UnloadFirmamentRegionS2CPacket.ID, (pkt, ctx) -> OperationStarcleaveClientPacketHandler.unloadFirmamentRegion(pkt, ctx.player(), ctx.responseSender()));

        ClientPlayNetworking.registerGlobalReceiver(FirmamentCleavedS2CPacket.ID, (pkt, ctx) -> OperationStarcleaveClientPacketHandler.onFirmamentCleaved(pkt, ctx.player(), ctx.responseSender()));

        ClientPlayNetworking.registerGlobalReceiver(StarbleachedPearlLaunchPacketS2C.ID, (pkt, ctx) -> OperationStarcleaveClientPacketHandler.onStarbleachedPearlLaunch(pkt, ctx.player(), ctx.responseSender()));
    }

    private static void startFirmamentRegionSend(StartFirmamentRegionSendS2CPacket packet, ClientPlayerEntity player, PacketSender responseSender) {
        ((OperationStarcleaveClientPlayNetworkHandler)player.networkHandler).operation_starcleave$getFirmamentRegionBatchSizeCalculator().onStartChunkSend();
    }

    private static void receiveFirmamentRegionData(FirmamentRegionDataS2CPacket packet, ClientPlayerEntity player, PacketSender responseSender) {
        World world = player.getWorld();
        Firmament firmament = Firmament.fromWorld(world);
        if(firmament != null) {
            FirmamentRegion firmamentRegion = firmament.getFirmamentRegion(packet.regionId());
            if(firmamentRegion == null) {
                if(firmament.getFirmamentRegionManager() instanceof ClientFirmamentRegionManager clientFirmamentRegionManager) {
                    FirmamentRegionHolder firmamentRegionHolder = clientFirmamentRegionManager.loadRegion(packet.regionId());
                    firmamentRegion = firmamentRegionHolder.getFirmamentRegion();
                }
            }

            if(firmamentRegion != null) {
                firmamentRegion.readFromData(packet.firmamentRegionData());
                FirmamentTextureStorage.getInstance().onRegionAdded(firmamentRegion, world);
            }
        }
    }

    private static void sentFirmamentRegion(FirmamentRegionSentS2CPacket packet, ClientPlayerEntity player, PacketSender responseSender) {
        ChunkBatchSizeCalculator firmamentRegionBatchSizeCalculator = ((OperationStarcleaveClientPlayNetworkHandler)player.networkHandler).operation_starcleave$getFirmamentRegionBatchSizeCalculator();
        firmamentRegionBatchSizeCalculator.onChunkSent(packet.batchSize());

        responseSender.sendPacket(new AcknowledgeFirmamentRegionDataC2SPacket(firmamentRegionBatchSizeCalculator.getDesiredChunksPerTick()));
    }

    private static void updateFirmamentSubRegion(UpdateFirmamentSubRegionS2CPacket packet, ClientPlayerEntity player, PacketSender responseSender) {
        World world = player.getWorld();
        Firmament firmament = Firmament.fromWorld(world);
        if(firmament != null) {
            FirmamentSubRegion firmamentSubRegion = firmament.getSubRegionFromId(packet.id());

            if(firmamentSubRegion != null) {
                firmamentSubRegion.readFromData(packet.subRegionData());

                FirmamentTextureStorage.getInstance().onSubRegionUpdated(firmamentSubRegion, world);
            }
        }
    }

    private static void unloadFirmamentRegion(UnloadFirmamentRegionS2CPacket packet, ClientPlayerEntity player, PacketSender responseSender) {
        Firmament firmament = Firmament.fromWorld(player.getWorld());
        if(firmament != null) {
            if(firmament.getFirmamentRegionManager() instanceof ClientFirmamentRegionManager clientFirmamentRegionManager) {
                clientFirmamentRegionManager.unloadRegion(packet.regionId());
            }
        }
    }

    private static void onFirmamentCleaved(FirmamentCleavedS2CPacket packet, ClientPlayerEntity player, PacketSender responseSender) {
        World world = player.getWorld();
        ((OperationStarcleaveWorld)world).operation_starcleave$setCleavingFlashTicksLeft(24);
        Vec3d pos = new Vec3d(packet.x(), world.getTopY() + 16, packet.z());
        world.playSound(
                pos.x,
                pos.y,
                pos.z,
                SoundEvents.ITEM_TRIDENT_THUNDER.value(),
                SoundCategory.BLOCKS,
                500.0F,
                1.6F + world.random.nextFloat() * 0.2F,
                false);

        ParticleEffect particleEffect = ParticleTypes.FLASH;
        world.addImportantParticle(particleEffect, pos.x, pos.y - 1, pos.z, 0, 0, 0);

        ScreenShakeManager.getInstance().setShakeAmount(3);
        Firmament firmament = Firmament.fromWorld(world);
        if(firmament != null) {
            firmament.addActor(new FirmamentDamageGlowActor(firmament, (int)pos.x, (int)pos.z));
        }
    }

    public static void onStarbleachedPearlLaunch(StarbleachedPearlLaunchPacketS2C packet, ClientPlayerEntity player, PacketSender responseSender) {
        Entity except = null;
        if(packet.exceptExists()) {
            Entity e = player.getWorld().getEntityById(packet.exceptId());
            if(e != null) {
                except = e;
            }
        }
        StarbleachedPearlEntity.doRepulsion(packet.pos(), packet.radius(), packet.maxAddedSpeed(), player.getWorld(), except);
    }
}
