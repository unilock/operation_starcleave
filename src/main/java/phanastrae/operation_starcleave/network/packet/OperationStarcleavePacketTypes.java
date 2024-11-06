package phanastrae.operation_starcleave.network.packet;

import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import phanastrae.operation_starcleave.network.packet.c2s.*;
import phanastrae.operation_starcleave.network.packet.s2c.*;

public class OperationStarcleavePacketTypes {
    public static void init() {
        PayloadTypeRegistry.playC2S().register(AcknowledgeFirmamentRegionDataC2SPacket.ID, AcknowledgeFirmamentRegionDataC2SPacket.PACKET_CODEC);
        PayloadTypeRegistry.playC2S().register(AttackFirmamentTileC2SPacket.ID, AttackFirmamentTileC2SPacket.PACKET_CODEC);

        PayloadTypeRegistry.playS2C().register(FirmamentCleavedS2CPacket.ID, FirmamentCleavedS2CPacket.PACKET_CODEC);
        PayloadTypeRegistry.playS2C().register(FirmamentRegionDataS2CPacket.ID, FirmamentRegionDataS2CPacket.PACKET_CODEC);
        PayloadTypeRegistry.playS2C().register(FirmamentRegionSentS2CPacket.ID, FirmamentRegionSentS2CPacket.PACKET_CODEC);
        PayloadTypeRegistry.playS2C().register(StarbleachedPearlLaunchPacketS2C.ID, StarbleachedPearlLaunchPacketS2C.PACKET_CODEC);
        PayloadTypeRegistry.playS2C().register(StartFirmamentRegionSendS2CPacket.ID, StartFirmamentRegionSendS2CPacket.PACKET_CODEC);
        PayloadTypeRegistry.playS2C().register(UnloadFirmamentRegionS2CPacket.ID, UnloadFirmamentRegionS2CPacket.PACKET_CODEC);
        PayloadTypeRegistry.playS2C().register(UpdateFirmamentSubRegionS2CPacket.ID, UpdateFirmamentSubRegionS2CPacket.PACKET_CODEC);
    }
}
