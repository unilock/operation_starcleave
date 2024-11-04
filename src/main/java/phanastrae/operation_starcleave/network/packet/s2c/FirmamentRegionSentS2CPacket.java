package phanastrae.operation_starcleave.network.packet.s2c;

import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import phanastrae.operation_starcleave.OperationStarcleave;

public record FirmamentRegionSentS2CPacket(int batchSize) implements CustomPayload {
    public static final Id<FirmamentRegionSentS2CPacket> ID = new Id<>(OperationStarcleave.id("firmament_region_sent_s2c"));
    public static final PacketCodec<RegistryByteBuf, FirmamentRegionSentS2CPacket> PACKET_CODEC = PacketCodecs.INTEGER.xmap(FirmamentRegionSentS2CPacket::new, FirmamentRegionSentS2CPacket::batchSize).cast();

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}
