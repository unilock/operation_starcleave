package phanastrae.operation_starcleave.network.packet.s2c;

import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import phanastrae.operation_starcleave.OperationStarcleave;

public record StartFirmamentRegionSendS2CPacket() implements CustomPayload {
    public static final StartFirmamentRegionSendS2CPacket INSTANCE = new StartFirmamentRegionSendS2CPacket();

    public static final Id<StartFirmamentRegionSendS2CPacket> ID = new Id<>(OperationStarcleave.id("start_firmament_region_send_s2c"));
    public static final PacketCodec<RegistryByteBuf, StartFirmamentRegionSendS2CPacket> PACKET_CODEC = PacketCodec.unit(INSTANCE);

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}
