package phanastrae.operation_starcleave.entity.effect;

import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;
import phanastrae.operation_starcleave.OperationStarcleave;

public class OperationStarcleaveStatusEffects {

    public static final RegistryEntry<StatusEffect> STARBLEACHED_INSIDES = Registry.registerReference(Registries.STATUS_EFFECT, OperationStarcleave.id("starbleached_insides"), new StarbleachedInsidesStatusEffect());

    public static void init() {
    }
}
