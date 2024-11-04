package phanastrae.operation_starcleave.block;

import net.minecraft.block.Block;
import net.minecraft.block.DispenserBlock;
import net.minecraft.block.dispenser.DispenserBehavior;
import net.minecraft.block.dispenser.FallibleItemDispenserBehavior;
import net.minecraft.block.dispenser.ProjectileDispenserBehavior;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPointer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import phanastrae.operation_starcleave.item.OperationStarcleaveItems;

public class OperationStarcleaveDispenserBehavior {

    public static void init() {
        register(OperationStarcleaveBlocks.NETHERITE_PUMPKIN, new FallibleItemDispenserBehavior() {
            @Override
            protected ItemStack dispenseSilently(BlockPointer pointer, ItemStack stack) {
                World world = pointer.world();
                BlockPos blockPos = pointer.pos().offset(pointer.state().get(DispenserBlock.FACING));
                NetheritePumpkinBlock netheritePumpkinBlock = (NetheritePumpkinBlock)OperationStarcleaveBlocks.NETHERITE_PUMPKIN;
                if (world.isAir(blockPos) && netheritePumpkinBlock.canDispense(world, blockPos)) {
                    if (!world.isClient) {
                        world.setBlockState(blockPos, netheritePumpkinBlock.getDefaultState(), Block.NOTIFY_ALL);
                        world.emitGameEvent(null, GameEvent.BLOCK_PLACE, blockPos);
                    }

                    stack.decrement(1);
                    this.setSuccess(true);
                } else {
                    this.setSuccess(ArmorItem.dispenseArmor(pointer, stack));
                }

                return stack;
            }
        });

        register(OperationStarcleaveItems.STARBLEACHED_PEARL, new ProjectileDispenserBehavior(OperationStarcleaveItems.STARBLEACHED_PEARL));

        register(OperationStarcleaveItems.SPLASH_STARBLEACH_BOTTLE, new ProjectileDispenserBehavior(OperationStarcleaveItems.SPLASH_STARBLEACH_BOTTLE));

        register(OperationStarcleaveItems.FIRMAMENT_REJUVENATOR, new ProjectileDispenserBehavior(OperationStarcleaveItems.FIRMAMENT_REJUVENATOR));
    }

    public static void register(ItemConvertible provider, DispenserBehavior behavior) {
        DispenserBlock.registerBehavior(provider, behavior);
    }
}
