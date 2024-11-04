package phanastrae.operation_starcleave.mixin.client;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import phanastrae.operation_starcleave.block.OperationStarcleaveBlocks;

@Mixin(InGameHud.class)
public class InGameHudMixin {
    @WrapOperation(method = "renderMiscOverlays", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;isOf(Lnet/minecraft/item/Item;)Z"))
    private boolean operation_starcleave$netheritePumpkinOverlay(ItemStack instance, Item item, Operation<Boolean> original) {
        return original.call(instance, item) || original.call(instance, OperationStarcleaveBlocks.NETHERITE_PUMPKIN.asItem());
    }
}
