package phanastrae.operation_starcleave.item;

import com.mojang.serialization.Codec;
import net.minecraft.component.ComponentType;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import phanastrae.operation_starcleave.OperationStarcleave;

import java.util.function.UnaryOperator;

public class OperationStarcleaveComponents {
    public static final ComponentType<Boolean> STARBLEACHED = register("starbleached", builder -> builder.codec(Codec.BOOL).packetCodec(PacketCodecs.BOOL));

    public static void init() {
    }

    private static <T> ComponentType<T> register(String path, UnaryOperator<ComponentType.Builder<T>> builderOperator) {
        return Registry.register(Registries.DATA_COMPONENT_TYPE, OperationStarcleave.id(path), ((ComponentType.Builder)builderOperator.apply(ComponentType.builder())).build());
    }
}
