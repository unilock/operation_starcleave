package phanastrae.operation_starcleave.network.packet.c2s;

import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import phanastrae.operation_starcleave.OperationStarcleave;
import phanastrae.operation_starcleave.world.firmament.FirmamentTilePos;

public record AttackFirmamentTileC2SPacket(int tileX, int tileZ) implements CustomPayload {
    public static final Id<AttackFirmamentTileC2SPacket> ID = new Id<>(OperationStarcleave.id("attack_firmament_c2s"));
    public static final PacketCodec<RegistryByteBuf, AttackFirmamentTileC2SPacket> PACKET_CODEC = new PacketCodec<>() {
        @Override
        public AttackFirmamentTileC2SPacket decode(RegistryByteBuf buf) {
            return new AttackFirmamentTileC2SPacket(
                    buf.readInt(),
                    buf.readInt()
            );
        }

        @Override
        public void encode(RegistryByteBuf buf, AttackFirmamentTileC2SPacket value) {
            buf.writeInt(value.tileX);
            buf.writeInt(value.tileZ);
        }
    };

    public AttackFirmamentTileC2SPacket(FirmamentTilePos tilePos) {
        this(tilePos.tileX, tilePos.tileZ);
    }

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}
