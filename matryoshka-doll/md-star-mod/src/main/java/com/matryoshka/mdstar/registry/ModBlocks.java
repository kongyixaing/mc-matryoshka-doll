package com.matryoshka.mdstar.registry;

import com.matryoshka.mdstar.MDStarMod;
import com.matryoshka.mdstar.blocks.multiblock.AntimatterCatalystBlock;
import com.matryoshka.mdstar.blocks.multiblock.DysonCoreBlock;
import com.matryoshka.mdstar.blocks.multiblock.GeothermalDrillBlock;
import com.matryoshka.mdstar.blocks.multiblock.MDAssemblyTowerBlock;
import com.matryoshka.mdstar.blocks.multiblock.MDFusionReactorBlock;
import com.matryoshka.mdstar.blocks.multiblock.MDStarSynthesisArrayBlock;
import com.matryoshka.mdstar.blocks.multiblock.QuantumEntanglementChamberBlock;
import com.matryoshka.mdstar.blocks.multiblock.SuperconductorMatrixBlock;
import com.matryoshka.mdstar.blocks.machinery.MachineCasingBlock;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

/**
 * 方块注册——包含所有大型现代机械多方块结构的控制器方块
 * 与"机器外壳"结构方块。
 *
 * 每个多方块由 1 个 Controller + 多个相同/不同 Casing 组成。
 */
public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, MDStarMod.MOD_ID);
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, MDStarMod.MOD_ID);

    // ---------------------------------------------------------------
    // 通用机器外壳
    // ---------------------------------------------------------------
    public static final RegistryObject<Block> STEEL_CASING = register("steel_casing",
            () -> new MachineCasingBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.METAL).strength(3.0F, 6.0F).requiresCorrectToolForDrops()));

    public static final RegistryObject<Block> TITANIUM_CASING_BLOCK = register("titanium_casing_block",
            () -> new MachineCasingBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.METAL).strength(5.0F, 9.0F).requiresCorrectToolForDrops()));

    public static final RegistryObject<Block> STAINLESS_CASING = register("stainless_casing",
            () -> new MachineCasingBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.METAL).strength(4.0F, 6.0F).requiresCorrectToolForDrops()));

    // ---------------------------------------------------------------
    // 多方块结构控制器
    // ---------------------------------------------------------------

    /** MD 组装塔 — 终极装配多方块，合成 MD 之心 */
    public static final RegistryObject<Block> MD_ASSEMBLY_TOWER = register("md_assembly_tower",
            () -> new MDAssemblyTowerBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_BLUE).strength(8.0F, 12.0F)
                    .requiresCorrectToolForDrops().lightLevel(s -> 15)));

    /** MD 聚变反应堆 — 能量核心 */
    public static final RegistryObject<Block> MD_FUSION_REACTOR = register("md_fusion_reactor",
            () -> new MDFusionReactorBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_RED).strength(10.0F, 14.0F)
                    .requiresCorrectToolForDrops().lightLevel(s -> 12)));

    /** 超导线圈矩阵 — 高压输电 */
    public static final RegistryObject<Block> SUPERCONDUCTOR_MATRIX = register("superconductor_matrix",
            () -> new SuperconductorMatrixBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_CYAN).strength(6.0F, 9.0F)
                    .requiresCorrectToolForDrops().lightLevel(s -> 8)));

    /** 量子纠缠腔 — 跨维度存储 */
    public static final RegistryObject<Block> QUANTUM_ENTANGLEMENT = register("quantum_entanglement",
            () -> new QuantumEntanglementChamberBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_PURPLE).strength(7.0F, 11.0F)
                    .requiresCorrectToolForDrops().lightLevel(s -> 14)));

    /** 反物质催化炉 — 反物质加工 */
    public static final RegistryObject<Block> ANTIMATTER_CATALYST = register("antimatter_catalyst",
            () -> new AntimatterCatalystBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_BLACK).strength(12.0F, 18.0F)
                    .requiresCorrectToolForDrops().lightLevel(s -> 7)));

    /** 戴森球核心 — 终极能源 */
    public static final RegistryObject<Block> DYSON_CORE = register("dyson_core",
            () -> new DysonCoreBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_YELLOW).strength(15.0F, 24.0F)
                    .requiresCorrectToolForDrops().lightLevel(s -> 15)));

    /** 地壳能量开采站 — 自动资源 */
    public static final RegistryObject<Block> GEOTHERMAL_DRILL = register("geothermal_drill",
            () -> new GeothermalDrillBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_GREEN).strength(7.0F, 9.0F)
                    .requiresCorrectToolForDrops()));

    /** MD 之星合成阵列 — 最终毕业（合成 MD之星） */
    public static final RegistryObject<Block> MD_STAR_SYNTHESIS_ARRAY = register("md_star_synthesis_array",
            () -> new MDStarSynthesisArrayBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.GOLD).strength(20.0F, 30.0F)
                    .requiresCorrectToolForDrops().lightLevel(s -> 18)));

    // ---------------------------------------------------------------
    // 通用注册辅助
    // ---------------------------------------------------------------
    private static <T extends Block> RegistryObject<T> register(String name, Supplier<T> block) {
        RegistryObject<T> reg = BLOCKS.register(name, block);
        ITEMS.register(name, () -> new BlockItem(reg.get(),
                new Item.Properties().rarity(Rarity.EPIC).fireResistant()));
        return reg;
    }
}
