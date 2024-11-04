package phanastrae.operation_starcleave.network.packet.s2c;

import net.minecraft.entity.Entity;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.Nullable;
import phanastrae.operation_starcleave.OperationStarcleave;

public record StarbleachedPearlLaunchPacketS2C(Vec3d pos, float radius, float maxAddedSpeed, boolean exceptExists, int exceptId) implements CustomPayload {
    public static final Id<StarbleachedPearlLaunchPacketS2C> ID = new Id<>(OperationStarcleave.id("starbleached_pearl_launch_s2c"));
    public static final PacketCodec<RegistryByteBuf, StarbleachedPearlLaunchPacketS2C> PACKET_CODEC = new PacketCodec<>() {
        @Override
        public StarbleachedPearlLaunchPacketS2C decode(RegistryByteBuf buf) {
            return new StarbleachedPearlLaunchPacketS2C(
                    buf.readVec3d(),
                    buf.readFloat(),
                    buf.readFloat(),
                    buf.readBoolean(),
                    buf.readInt()
            );
        }

        @Override
        public void encode(RegistryByteBuf buf, StarbleachedPearlLaunchPacketS2C value) {
            buf.writeVec3d(value.pos);
            buf.writeFloat(value.radius);
            buf.writeFloat(value.maxAddedSpeed);
            buf.writeBoolean(value.exceptExists);
            buf.writeInt(value.exceptId);
        }
    };

    public StarbleachedPearlLaunchPacketS2C(Vec3d pos, float radius, float maxAddedSpeed, @Nullable Entity except) {
        this(pos, radius, maxAddedSpeed, except != null, except == null ? 0 : except.getId());
    }

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}
