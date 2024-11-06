package phanastrae.operation_starcleave.item;

import com.mojang.serialization.Codec;
import net.minecraft.component.ComponentType;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import phanastrae.operation_starcleave.OperationStarcleave;

import java.util.function.UnaryOperator;

public class OperationStarcleaveComponentTypes {
    public static final ComponentType<Boolean> STARBLEACHED = register(builder -> builder.codec(Codec.BOOL).packetCodec(PacketCodecs.BOOL), "starbleached");

    public static void init() {
    }

    private static <T> ComponentType<T> register(UnaryOperator<ComponentType.Builder<T>> builderOperator, String name) {
        return Registry.register(Registries.DATA_COMPONENT_TYPE, OperationStarcleave.id(name), ((ComponentType.Builder)builderOperator.apply(ComponentType.builder())).build());
    }
}
