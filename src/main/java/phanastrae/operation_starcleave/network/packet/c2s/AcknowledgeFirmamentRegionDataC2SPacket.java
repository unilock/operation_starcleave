package phanastrae.operation_starcleave.network.packet.c2s;

import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import phanastrae.operation_starcleave.OperationStarcleave;

public record AcknowledgeFirmamentRegionDataC2SPacket(float desiredChunksPerTick) implements CustomPayload {
    public static final Id<AcknowledgeFirmamentRegionDataC2SPacket> ID = new Id<>(OperationStarcleave.id("start_firmament_region_send_s2c"));
    public static final PacketCodec<RegistryByteBuf, AcknowledgeFirmamentRegionDataC2SPacket> PACKET_CODEC = PacketCodecs.FLOAT.xmap(AcknowledgeFirmamentRegionDataC2SPacket::new, AcknowledgeFirmamentRegionDataC2SPacket::desiredChunksPerTick).cast();

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}
