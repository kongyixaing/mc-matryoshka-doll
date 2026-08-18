package com.matryoshka.mdstar;

import com.matryoshka.mdstar.registry.ModBlockEntities;
import com.matryoshka.mdstar.registry.ModBlocks;
import com.matryoshka.mdstar.registry.ModCreativeTabs;
import com.matryoshka.mdstar.registry.ModItems;
import com.matryoshka.mdstar.registry.ModMenuTypes;
import com.matryoshka.mdstar.client.ClientEvents;
import com.matryoshka.mdstar.recipe.MDStarAssemblyRecipe;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * MD Star (mdstar) — Matryoshka Doll 整合包毕业 mod。
 * 提供大型现代机械多方块结构，用于合成最终毕业物 MD 之星。
 *
 * 阶段一：MD 组装塔（MD Assembly Tower）      — 终极组装
 * 阶段二：MD 聚变反应堆（MD Fusion Reactor）    — 能量核心
 * 阶段三：超导线圈矩阵（Superconductor Matrix） — 高压输电
 * 阶段四：量子纠缠腔（Quantum Entanglement）   — 跨维度存储
 * 阶段五：反物质催化炉（Antimatter Catalyst）   — 反物质加工
 * 阶段六：戴森球核心（Dyson Sphere Core）       — 终极能源
 * 阶段七：地壳能量开采站（Geothermal Drill）    — 自动资源
 * 阶段八：戴森云推进器（Dyson Swarm Thruster）  — 行星工程
 * 阶段九：MD 之星合成阵列（MD Star Synthesis Array） — 最终毕业
 */
@Mod(MDStarMod.MOD_ID)
public class MDStarMod {
    public static final String MOD_ID = "mdstar";
    public static final String MOD_NAME = "MD Star";
    public static final Logger LOGGER = LogManager.getLogger(MOD_NAME);

    public MDStarMod() {
        IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();

        // Register deferred registers
        ModItems.ITEMS.register(modBus);
        ModBlocks.BLOCKS.register(modBus);
        ModBlockEntities.BLOCK_ENTITIES.register(modBus);
        ModMenuTypes.MENU_TYPES.register(modBus);
        ModCreativeTabs.CREATIVE_MODE_TABS.register(modBus);
        MDStarAssemblyRecipe.Serializer.SERIALIZERS.register(modBus);

        // Common setup
        modBus.addListener(this::commonSetup);

        // Client setup
        modBus.addListener(ClientEvents::onClientSetup);
        modBus.addListener(ClientEvents::onRegisterRenderers);

        MinecraftForge.EVENT_BUS.register(this);
        LOGGER.info("[MD Star] Loaded. Build the MD Assembly Tower and craft the MD Star.");
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            LOGGER.info("[MD Star] Multiblock patterns registered.");
        });
    }
}
