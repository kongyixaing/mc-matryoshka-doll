package com.matryoshka.mdstar.registry;

import com.matryoshka.mdstar.MDStarMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModCreativeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MDStarMod.MOD_ID);

    public static final RegistryObject<CreativeModeTab> MAIN_TAB = CREATIVE_MODE_TABS.register("main",
            () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.mdstar.main"))
                    .icon(() -> new ItemStack(ModItems.MD_STAR.get()))
                    .displayItems((params, output) -> {
                        output.accept(ModItems.MD_STAR.get());
                        output.accept(ModItems.MD_STAR_SHELL.get());
                        output.accept(ModItems.MD_HEART.get());
                        output.accept(ModItems.MD_FRAME.get());
                        output.accept(ModItems.QUANTUM_CIRCUIT.get());
                        output.accept(ModItems.PLASMA_CORE.get());
                        output.accept(ModItems.SUPERCONDUCTOR_COIL.get());
                        output.accept(ModItems.ANTIMATTER_PELLET.get());
                        output.accept(ModItems.STELLAR_DUST.get());
                        output.accept(ModItems.DYSON_FRAME.get());
                        output.accept(ModItems.TITANIUM_CASING.get());

                        output.accept(ModBlocks.MD_ASSEMBLY_TOWER.get());
                        output.accept(ModBlocks.MD_FUSION_REACTOR.get());
                        output.accept(ModBlocks.SUPERCONDUCTOR_MATRIX.get());
                        output.accept(ModBlocks.QUANTUM_ENTANGLEMENT.get());
                        output.accept(ModBlocks.ANTIMATTER_CATALYST.get());
                        output.accept(ModBlocks.DYSON_CORE.get());
                        output.accept(ModBlocks.GEOTHERMAL_DRILL.get());
                        output.accept(ModBlocks.MD_STAR_SYNTHESIS_ARRAY.get());

                        output.accept(ModBlocks.STEEL_CASING.get());
                        output.accept(ModBlocks.TITANIUM_CASING_BLOCK.get());
                        output.accept(ModBlocks.STAINLESS_CASING.get());
                    })
                    .build());
}
