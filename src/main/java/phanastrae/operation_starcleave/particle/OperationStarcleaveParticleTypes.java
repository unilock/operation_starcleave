package phanastrae.operation_starcleave.particle;

import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.particle.SimpleParticleType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import phanastrae.operation_starcleave.OperationStarcleave;

public class OperationStarcleaveParticleTypes {

    public static final SimpleParticleType FIRMAMENT_GLIMMER = FabricParticleTypes.simple();

    static {
        Registry.register(Registries.PARTICLE_TYPE, OperationStarcleave.id("firmament_glimmer"), FIRMAMENT_GLIMMER);
    }

    public static void init() {
    }
}
