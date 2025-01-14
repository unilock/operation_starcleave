package phanastrae.operation_starcleave.data;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.block.BedBlock;
import net.minecraft.block.Block;
import net.minecraft.block.enums.BedPart;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.Items;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.condition.BlockStatePropertyLootCondition;
import net.minecraft.loot.condition.LootCondition;
import net.minecraft.loot.condition.RandomChanceLootCondition;
import net.minecraft.loot.condition.SurvivesExplosionLootCondition;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.entry.LeafEntry;
import net.minecraft.loot.entry.LootPoolEntry;
import net.minecraft.loot.function.ApplyBonusLootFunction;
import net.minecraft.loot.function.ExplosionDecayLootFunction;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;
import net.minecraft.predicate.StatePredicate;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import phanastrae.operation_starcleave.block.OperationStarcleaveBlocks;
import phanastrae.operation_starcleave.item.OperationStarcleaveItems;

import java.util.concurrent.CompletableFuture;

public class BlockLootTableProvider extends FabricBlockLootTableProvider {
    private final RegistryWrapper.Impl<Enchantment> impl = this.registryLookup.getWrapperOrThrow(RegistryKeys.ENCHANTMENT);

    protected BlockLootTableProvider(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        super(dataOutput, registryLookup);
    }

    @Override
    public void generate() {
        addDrop(OperationStarcleaveBlocks.NETHERITE_PUMPKIN);
        addDrop(OperationStarcleaveBlocks.STELLAR_SEDIMENT);
        addDrop(OperationStarcleaveBlocks.STARBLEACHED_LOG);
        addDrop(OperationStarcleaveBlocks.STARBLEACHED_WOOD);
        addDrop(OperationStarcleaveBlocks.STARBLEACHED_LEAVES);
        addDrop(OperationStarcleaveBlocks.STARBLEACHED_TILES);
        addDrop(OperationStarcleaveBlocks.STARBLEACHED_TILE_STAIRS);
        addDrop(OperationStarcleaveBlocks.STARBLEACHED_TILE_SLAB);
        addDrop(OperationStarcleaveBlocks.STARBLEACHED_TILE_WALL);
        addDrop(OperationStarcleaveBlocks.CHISELED_STARBLEACHED_TILES);
        addDrop(OperationStarcleaveBlocks.IMBUED_STARBLEACHED_TILES);
        addDrop(OperationStarcleaveBlocks.STARBLEACHED_PEARL_BLOCK);
        addDrop(OperationStarcleaveBlocks.STELLAR_TILES);
        addDrop(OperationStarcleaveBlocks.STELLAR_TILE_SLAB);
        addDrop(OperationStarcleaveBlocks.STELLAR_REPULSOR);
        addDrop(OperationStarcleaveBlocks.BLESSED_CLOTH_BLOCK);
        addDrop(OperationStarcleaveBlocks.BLESSED_CLOTH_CARPET);
        addDrop(OperationStarcleaveBlocks.BLESSED_CLOTH_CURTAIN);

        addDrop(OperationStarcleaveBlocks.STARBLEACH_CAULDRON, Items.CAULDRON);
        addDrop(OperationStarcleaveBlocks.STELLAR_FARMLAND, OperationStarcleaveItems.STELLAR_SEDIMENT);

        addRandomDrop(OperationStarcleaveBlocks.HOLY_MOSS, silkTouchDrop(OperationStarcleaveItems.HOLY_MOSS, OperationStarcleaveItems.STELLAR_SEDIMENT));

        addRandomDrop(OperationStarcleaveBlocks.STARDUST_BLOCK,
                silkTouchDrop(
                        item(OperationStarcleaveItems.STARDUST_BLOCK),
                        item(OperationStarcleaveItems.STARDUST_CLUSTER, 1, 4, false).apply(ExplosionDecayLootFunction.builder())));

        addRandomDrop(OperationStarcleaveBlocks.SHORT_HOLY_MOSS,
                conditionalDrop(
                        item(OperationStarcleaveItems.SHORT_HOLY_MOSS),
                        item(OperationStarcleaveItems.HOLY_STRANDS).apply(ApplyBonusLootFunction.uniformBonusCount(impl.getOrThrow(Enchantments.FORTUNE), 4)).apply(ExplosionDecayLootFunction.builder()).conditionally(RandomChanceLootCondition.builder(0.3F)),
                        WITH_SHEARS
        ));

        addDrop(OperationStarcleaveBlocks.BLESSED_BED, LootTable.builder().pool(
                LootPool.builder().rolls(ConstantLootNumberProvider.create(1))
                        .with(item(OperationStarcleaveItems.BLESSED_BED)
                                .conditionally(BlockStatePropertyLootCondition
                                        .builder(OperationStarcleaveBlocks.BLESSED_BED)
                                        .properties(StatePredicate.Builder.create().exactMatch(BedBlock.PART, BedPart.HEAD)))
                        )
                        .conditionally(SurvivesExplosionLootCondition.builder())
                )
        );
    }

    public void addRandomDrop(Block block, LootTable.Builder builder) {
        addDrop(block, builder.randomSequenceId(block.getLootTableKey().getValue()));
    }

    public LeafEntry.Builder<?> item(ItemConvertible itemConvertible) {
        return ItemEntry.builder(itemConvertible);
    }

    public LeafEntry.Builder<?> item(ItemConvertible itemConvertible, float min, float max, boolean add) {
        return item(itemConvertible).apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(min, max), add));
    }

    public LootTable.Builder conditionalDrop(LootPoolEntry.Builder<?> withCondition, LootPoolEntry.Builder<?> noCondition, LootCondition.Builder condition) {
        LootPoolEntry.Builder<?> noConditionEntry = noCondition.conditionally(SurvivesExplosionLootCondition.builder());
        LootPoolEntry.Builder<?> withConditionEntry = withCondition.conditionally(condition);

        return LootTable.builder().pool(
                LootPool.builder().rolls(ConstantLootNumberProvider.create(1))
                        .with(withConditionEntry.alternatively(noConditionEntry))
        );
    }

    public LootTable.Builder silkTouchDrop(LootPoolEntry.Builder<?> withSilkTouch, LootPoolEntry.Builder<?> noSilkTouch) {
        return conditionalDrop(withSilkTouch, noSilkTouch, createSilkTouchCondition());
    }

    public LootTable.Builder silkTouchDrop(ItemConvertible withSilkTouch, ItemConvertible noSilkTouch) {
        return silkTouchDrop(item(withSilkTouch), item(noSilkTouch));
    }
}
