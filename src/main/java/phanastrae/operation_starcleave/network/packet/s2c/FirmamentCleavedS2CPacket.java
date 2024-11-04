package phanastrae.operation_starcleave.network.packet.s2c;

import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import phanastrae.operation_starcleave.OperationStarcleave;

public record FirmamentCleavedS2CPacket(int x, int z) implements CustomPayload {
    public static final Id<FirmamentCleavedS2CPacket> ID = new Id<>(OperationStarcleave.id("firmament_cleaved_s2c"));
    public static final PacketCodec<RegistryByteBuf, FirmamentCleavedS2CPacket> PACKET_CODEC = new PacketCodec<>() {
        @Override
        public FirmamentCleavedS2CPacket decode(RegistryByteBuf buf) {
            return new FirmamentCleavedS2CPacket(
                    buf.readInt(),
                    buf.readInt()
            );
        }

        @Override
        public void encode(RegistryByteBuf buf, FirmamentCleavedS2CPacket value) {
            buf.writeInt(value.x);
            buf.writeInt(value.z);
        }
    };

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}
