package com.matryoshka.mdstar.client;

import com.matryoshka.mdstar.blocks.multiblock.MDAssemblyTowerMenu;
import com.matryoshka.mdstar.MDStarMod;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

/**
 * MD 组装塔 GUI 屏幕。
 * 显示 8 输入槽 + 1 输出槽 + 玩家背包。
 */
public class MDAssemblyTowerScreen extends AbstractContainerScreen<MDAssemblyTowerMenu> {

    private static final ResourceLocation TEXTURE =
            new ResourceLocation(MDStarMod.MOD_ID, "textures/gui/md_assembly_tower.png");

    public MDAssemblyTowerScreen(MDAssemblyTowerMenu menu, Inventory inv, Component title) {
        super(menu, inv, title);
        this.imageWidth = 176;
        this.imageHeight = 166;
        this.inventoryLabelY = this.imageHeight - 94;
    }

    @Override
    protected void renderBg(GuiGraphics gfx, float partialTick, int mouseX, int mouseY) {
        // 默认贴图缺失时使用纯色背景
        gfx.fill(leftPos, topPos, leftPos + imageWidth, topPos + imageHeight, 0xFF1a1a2e);
        gfx.fill(leftPos + 7, topPos + 7, leftPos + imageWidth - 7, topPos + imageHeight - 7, 0xFF16213e);
        // 进度条
        int progress = menu.getSlot(0).hasItem() ? 24 : 0;
        if (progress > 0) {
            gfx.fill(leftPos + 80, topPos + 36, leftPos + 80 + 24, topPos + 42, 0xFF00ff88);
        }
    }

    @Override
    public void render(GuiGraphics gfx, int mouseX, int mouseY, float partialTick) {
        this.renderBackground(gfx);
        super.render(gfx, mouseX, mouseY, partialTick);
        this.renderTooltip(gfx, mouseX, mouseY);
    }
}
