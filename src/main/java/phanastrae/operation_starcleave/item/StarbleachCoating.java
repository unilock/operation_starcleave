package phanastrae.operation_starcleave.item;

import net.minecraft.component.DataComponentTypes;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.text.Text;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import phanastrae.operation_starcleave.entity.effect.OperationStarcleaveStatusEffects;

public class StarbleachCoating {
    public static void onEat(LivingEntity livingEntity, World world, ItemStack itemStack) {
        if(world.isClient) {
            return;
        }

        if(hasStarbleachCoating(itemStack)) {
            livingEntity.addStatusEffect(new StatusEffectInstance(OperationStarcleaveStatusEffects.STARBLEACHED_INSIDES, 200, 1));
        }
    }

    public static boolean hasStarbleachCoating(ItemStack itemStack) {
        return itemStack.getOrDefault(OperationStarcleaveComponents.STARBLEACHED, false);
    }

    public static void addStarbleach(ItemStack itemStack) {
        itemStack.set(OperationStarcleaveComponents.STARBLEACHED, true);
    }

    public static boolean canAddStarbleach(ItemStack itemStack) {
        if(!itemStack.contains(DataComponentTypes.FOOD)) {
            return false;
        }

        boolean hasStarbleach = itemStack.getOrDefault(OperationStarcleaveComponents.STARBLEACHED, false);
        if(hasStarbleach) {
            return false;
        }

        if(itemStack.isOf(OperationStarcleaveItems.STARBLEACH_BOTTLE)) {
            return false;
        }

        if(itemStack.isOf(OperationStarcleaveItems.STARFRUIT)) {
            return false;
        }

        if(itemStack.isOf(Items.CHORUS_FRUIT)) {
            return false;
        }

        return true;
    }

    public static Text getText() {
        return getText("operation_starcleave.tooltip.starbleached");
    }

    public static Text getText(String key) {
        float fl = (System.currentTimeMillis() % 4000) / 4000f;
        float twopi = 2 * MathHelper.PI;
        float red = MathHelper.sin(fl * twopi) * 0.2f + 0.8f;
        float green = MathHelper.sin((fl + 1/3f) * twopi) * 0.2f + 0.8f;
        float blue = MathHelper.sin((fl + 2/3f) * twopi) * 0.2f + 0.8f;

        int r = (int)(red * 255f) & 0xFF;
        int g = (int)(green * 255f) & 0xFF;
        int b = (int)(blue * 255f) & 0xFF;

        return Text.translatable(key).withColor(r | (g << 8) | (b << 16) | (0xFF << 24));
    }
}
