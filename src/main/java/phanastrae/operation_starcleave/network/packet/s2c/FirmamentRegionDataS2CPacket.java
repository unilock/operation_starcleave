package phanastrae.operation_starcleave.network.packet.s2c;

import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import phanastrae.operation_starcleave.OperationStarcleave;
import phanastrae.operation_starcleave.world.firmament.FirmamentRegion;
import phanastrae.operation_starcleave.world.firmament.FirmamentRegionData;
import phanastrae.operation_starcleave.world.firmament.RegionPos;

public record FirmamentRegionDataS2CPacket(long regionId, FirmamentRegionData firmamentRegionData) implements CustomPayload {
    public static final Id<FirmamentRegionDataS2CPacket> ID = new Id<>(OperationStarcleave.id("firmament_region_data_s2c"));
    public static final PacketCodec<RegistryByteBuf, FirmamentRegionDataS2CPacket> PACKET_CODEC = new PacketCodec<>() {
        @Override
        public FirmamentRegionDataS2CPacket decode(RegistryByteBuf buf) {
            return new FirmamentRegionDataS2CPacket(
                    buf.readLong(),
                    new FirmamentRegionData(buf)
            );
        }

        @Override
        public void encode(RegistryByteBuf buf, FirmamentRegionDataS2CPacket value) {
            buf.writeLong(value.regionId);
            value.firmamentRegionData.write(buf);
        }
    };

    public FirmamentRegionDataS2CPacket(FirmamentRegion region) {
        this(RegionPos.fromWorldCoords(region.x, region.z).id,  new FirmamentRegionData(region));
    }

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}
