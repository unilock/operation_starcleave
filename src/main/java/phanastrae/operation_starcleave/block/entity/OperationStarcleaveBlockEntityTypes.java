package phanastrae.operation_starcleave.block.entity;

import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import phanastrae.operation_starcleave.OperationStarcleave;
import phanastrae.operation_starcleave.block.OperationStarcleaveBlocks;

public class OperationStarcleaveBlockEntityTypes {

    public static final BlockEntityType<BlessedBedBlockEntity> BLESSED_BED = Registry.register(
            Registries.BLOCK_ENTITY_TYPE,
            OperationStarcleave.id("blessed_bed"),
            BlockEntityType.Builder.create(BlessedBedBlockEntity::new, OperationStarcleaveBlocks.BLESSED_BED).build()
    );

    public static void init() {

    }
}
