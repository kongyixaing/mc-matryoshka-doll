package com.matryoshka.mdstar.client;

import com.matryoshka.mdstar.client.MDAssemblyTowerScreen;
import com.matryoshka.mdstar.registry.ModMenuTypes;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

/**
 * 客户端事件：注册屏幕、BlockEntity 渲染器。
 */
public class ClientEvents {

    public static void onClientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            MenuScreens.register(ModMenuTypes.MD_ASSEMBLY_TOWER.get(), MDAssemblyTowerScreen::new);
        });
    }

    public static void onRegisterRenderers(EntityRenderersEvent.RegisterRenderers event) {
        // 多方块控制器暂时使用默认方块模型渲染，未来可加自定义 BER
    }
}
