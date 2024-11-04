package phanastrae.operation_starcleave.network.packet.s2c;

import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import phanastrae.operation_starcleave.OperationStarcleave;
import phanastrae.operation_starcleave.world.firmament.FirmamentSubRegionData;

public record UpdateFirmamentSubRegionS2CPacket(long id, FirmamentSubRegionData subRegionData) implements CustomPayload {
    public static final Id<UpdateFirmamentSubRegionS2CPacket> ID = new Id<>(OperationStarcleave.id("update_firmament_subregion_s2c"));
    public static final PacketCodec<RegistryByteBuf, UpdateFirmamentSubRegionS2CPacket> PACKET_CODEC = new PacketCodec<>() {
        @Override
        public UpdateFirmamentSubRegionS2CPacket decode(RegistryByteBuf buf) {
            return new UpdateFirmamentSubRegionS2CPacket(
                    buf.readLong(),
                    new FirmamentSubRegionData(buf)
            );
        }

        @Override
        public void encode(RegistryByteBuf buf, UpdateFirmamentSubRegionS2CPacket value) {
            buf.writeLong(value.id);
            value.subRegionData.write(buf);
        }
    };

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}
