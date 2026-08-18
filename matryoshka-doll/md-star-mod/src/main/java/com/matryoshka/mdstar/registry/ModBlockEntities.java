package com.matryoshka.mdstar.registry;

import com.matryoshka.mdstar.MDStarMod;
import com.matryoshka.mdstar.blocks.multiblock.AntimatterCatalystBlockEntity;
import com.matryoshka.mdstar.blocks.multiblock.DysonCoreBlockEntity;
import com.matryoshka.mdstar.blocks.multiblock.GeothermalDrillBlockEntity;
import com.matryoshka.mdstar.blocks.multiblock.MDAssemblyTowerBlockEntity;
import com.matryoshka.mdstar.blocks.multiblock.MDFusionReactorBlockEntity;
import com.matryoshka.mdstar.blocks.multiblock.MDStarSynthesisArrayBlockEntity;
import com.matryoshka.mdstar.blocks.multiblock.QuantumEntanglementChamberBlockEntity;
import com.matryoshka.mdstar.blocks.multiblock.SuperconductorMatrixBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, MDStarMod.MOD_ID);

    public static final RegistryObject<BlockEntityType<MDAssemblyTowerBlockEntity>> MD_ASSEMBLY_TOWER =
            BLOCK_ENTITIES.register("md_assembly_tower", () ->
                    BlockEntityType.Builder.of(MDAssemblyTowerBlockEntity::new,
                            ModBlocks.MD_ASSEMBLY_TOWER.get()).build(null));

    public static final RegistryObject<BlockEntityType<MDFusionReactorBlockEntity>> MD_FUSION_REACTOR =
            BLOCK_ENTITIES.register("md_fusion_reactor", () ->
                    BlockEntityType.Builder.of(MDFusionReactorBlockEntity::new,
                            ModBlocks.MD_FUSION_REACTOR.get()).build(null));

    public static final RegistryObject<BlockEntityType<SuperconductorMatrixBlockEntity>> SUPERCONDUCTOR_MATRIX =
            BLOCK_ENTITIES.register("superconductor_matrix", () ->
                    BlockEntityType.Builder.of(SuperconductorMatrixBlockEntity::new,
                            ModBlocks.SUPERCONDUCTOR_MATRIX.get()).build(null));

    public static final RegistryObject<BlockEntityType<QuantumEntanglementChamberBlockEntity>> QUANTUM_ENTANGLEMENT =
            BLOCK_ENTITIES.register("quantum_entanglement", () ->
                    BlockEntityType.Builder.of(QuantumEntanglementChamberBlockEntity::new,
                            ModBlocks.QUANTUM_ENTANGLEMENT.get()).build(null));

    public static final RegistryObject<BlockEntityType<AntimatterCatalystBlockEntity>> ANTIMATTER_CATALYST =
            BLOCK_ENTITIES.register("antimatter_catalyst", () ->
                    BlockEntityType.Builder.of(AntimatterCatalystBlockEntity::new,
                            ModBlocks.ANTIMATTER_CATALYST.get()).build(null));

    public static final RegistryObject<BlockEntityType<DysonCoreBlockEntity>> DYSON_CORE =
            BLOCK_ENTITIES.register("dyson_core", () ->
                    BlockEntityType.Builder.of(DysonCoreBlockEntity::new,
                            ModBlocks.DYSON_CORE.get()).build(null));

    public static final RegistryObject<BlockEntityType<GeothermalDrillBlockEntity>> GEOTHERMAL_DRILL =
            BLOCK_ENTITIES.register("geothermal_drill", () ->
                    BlockEntityType.Builder.of(GeothermalDrillBlockEntity::new,
                            ModBlocks.GEOTHERMAL_DRILL.get()).build(null));

    public static final RegistryObject<BlockEntityType<MDStarSynthesisArrayBlockEntity>> MD_STAR_SYNTHESIS =
            BLOCK_ENTITIES.register("md_star_synthesis_array", () ->
                    BlockEntityType.Builder.of(MDStarSynthesisArrayBlockEntity::new,
                            ModBlocks.MD_STAR_SYNTHESIS_ARRAY.get()).build(null));
}
