package phanastrae.operation_starcleave.network.packet.s2c;

import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import phanastrae.operation_starcleave.OperationStarcleave;

public record UnloadFirmamentRegionS2CPacket(long id) implements CustomPayload {
    public static final Id<UnloadFirmamentRegionS2CPacket> ID = new Id<>(OperationStarcleave.id("unload_firmament_region_s2c"));
    public static final PacketCodec<RegistryByteBuf, UnloadFirmamentRegionS2CPacket> PACKET_CODEC = PacketCodecs.VAR_LONG.xmap(UnloadFirmamentRegionS2CPacket::new, UnloadFirmamentRegionS2CPacket::id).cast();

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}
