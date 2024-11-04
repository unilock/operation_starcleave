package phanastrae.operation_starcleave.item;

import com.google.common.base.Suppliers;
import net.minecraft.block.Block;
import net.minecraft.component.type.AttributeModifierSlot;
import net.minecraft.component.type.AttributeModifiersComponent;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.ArmorMaterials;
import net.minecraft.item.BlockItem;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;

import java.util.function.Supplier;

public class NetheritePumpkinItem extends BlockItem {

    private final Supplier<AttributeModifiersComponent> attributeModifiers;

    public NetheritePumpkinItem(Block block, Settings settings) {
        super(block, settings);

        RegistryEntry<ArmorMaterial> material = ArmorMaterials.NETHERITE;
        ArmorItem.Type type = ArmorItem.Type.HELMET;

        this.attributeModifiers = Suppliers.memoize(
                () -> {
                    int i = material.value().getProtection(type);
                    float f = material.value().toughness();
                    AttributeModifiersComponent.Builder builder = AttributeModifiersComponent.builder();
                    AttributeModifierSlot attributeModifierSlot = AttributeModifierSlot.forEquipmentSlot(type.getEquipmentSlot());
                    Identifier identifier = Identifier.ofVanilla("armor." + type.getName());
                    builder.add(
                            EntityAttributes.GENERIC_ARMOR, new EntityAttributeModifier(identifier, (double)i, EntityAttributeModifier.Operation.ADD_VALUE), attributeModifierSlot
                    );
                    builder.add(
                            EntityAttributes.GENERIC_ARMOR_TOUGHNESS,
                            new EntityAttributeModifier(identifier, (double)f, EntityAttributeModifier.Operation.ADD_VALUE),
                            attributeModifierSlot
                    );
                    float g = material.value().knockbackResistance();
                    if (g > 0.0F) {
                        builder.add(
                                EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE,
                                new EntityAttributeModifier(identifier, (double)g, EntityAttributeModifier.Operation.ADD_VALUE),
                                attributeModifierSlot
                        );
                    }

                    return builder.build();
                }
        );
    }

    @Override
    public AttributeModifiersComponent getAttributeModifiers() {
        return this.attributeModifiers.get();
    }
}
