package com.matryoshka.mdstar.blocks.multiblock;

import com.matryoshka.mdstar.registry.ModMenuTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;

import javax.annotation.Nonnull;

/**
 * MD 组装塔 容器菜单。
 * 8 输入栏 + 1 输出栏 + 玩家背包（9x3）+ 玩家快捷栏（9）。
 */
public class MDAssemblyTowerMenu extends AbstractContainerMenu {

    private final MDAssemblyTowerBlockEntity blockEntity;
    private final ContainerLevelAccess access;

    @SuppressWarnings("unchecked")
    public MDAssemblyTowerMenu(int id, Inventory inv, MDAssemblyTowerBlockEntity be, ContainerLevelAccess access) {
        super((MenuType<MDAssemblyTowerMenu>) ModMenuTypes.MD_ASSEMBLY_TOWER.get(), id);
        this.blockEntity = be;
        this.access = access;

        IItemHandler handler = be.getInventory();
        // 8 输入槽（上方）
        for (int i = 0; i < 8; i++) {
            this.addSlot(new SlotItemHandler(handler, i, 26 + (i % 4) * 18, 18 + (i / 4) * 18));
        }
        // 1 输出槽
        this.addSlot(new SlotItemHandler(handler, MDAssemblyTowerBlockEntity.OUTPUT_SLOT, 116, 36));

        // 玩家背包 9x3
        for (int y = 0; y < 3; y++) {
            for (int x = 0; x < 9; x++) {
                this.addSlot(new Slot(inv, x + y * 9 + 9, 8 + x * 18, 84 + y * 18));
            }
        }
        // 玩家快捷栏 9
        for (int x = 0; x < 9; x++) {
            this.addSlot(new Slot(inv, x, 8 + x * 18, 142));
        }
    }

    public MDAssemblyTowerMenu(int id, Inventory inv, FriendlyByteBuf extraData) {
        this(id, inv, (MDAssemblyTowerBlockEntity) inv.player.level().getBlockEntity(extraData.readBlockPos()),
                ContainerLevelAccess.NULL);
    }

    @Override
    public @Nonnull ItemStack quickMoveStack(Player player, int index) {
        Slot slot = getSlot(index);
        if (!slot.hasItem()) return ItemStack.EMPTY;
        ItemStack original = slot.getItem().copy();
        if (index < 9) {
            // 从机器槽移到玩家背包
            if (!moveItemStackTo(slot.getItem(), 9, slots.size(), true)) return ItemStack.EMPTY;
        } else {
            // 从玩家背包移到机器槽
            if (!moveItemStackTo(slot.getItem(), 0, 8, false)) return ItemStack.EMPTY;
        }
        slot.setChanged();
        return original;
    }

    @Override
    public boolean stillValid(Player player) {
        return stillValid(access, player, blockEntity.getBlockState().getBlock());
    }
}
