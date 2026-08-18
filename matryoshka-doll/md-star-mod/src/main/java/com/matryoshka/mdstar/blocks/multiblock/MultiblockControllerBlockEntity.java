package com.matryoshka.mdstar.blocks.multiblock;

import com.matryoshka.mdstar.MDStarMod;
import com.matryoshka.mdstar.blocks.machinery.MachineCasingBlock;
import com.matryoshka.mdstar.multiblock.MultiblockPattern;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.Nullable;

/**
 * 多方块控制器 BlockEntity 基类。
 * 提供：
 *   - 物品栏 (ItemStackHandler)
 *   - 能量 (Forge Energy) 简易实现
 *   - 多方块校验方法 checkAndForm()
 *   - 服务端 tick 钩子 serverTick()
 */
public abstract class MultiblockControllerBlockEntity extends BlockEntity implements MenuProvider {

    protected final ItemStackHandler inventory = new ItemStackHandler(getInventorySize());
    protected boolean formed = false;
    protected int energyStored = 0;
    protected int maxEnergy = getMaxEnergyStored();
    protected int progress = 0;
    protected int maxProgress = getProcessingTime();

    public MultiblockControllerBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    // ---------------------------------------------------------------
    // 子类实现的配置项
    // ---------------------------------------------------------------
    public abstract int getInventorySize();
    public abstract int getMaxEnergyStored();
    public abstract int getProcessingTime();
    public abstract String getMachineName();

    // ---------------------------------------------------------------
    // 多方块校验
    // ---------------------------------------------------------------
    public boolean checkAndForm() {
        if (level == null) return false;
        MultiblockControllerBlock block = (MultiblockControllerBlock) getBlockState().getBlock();
        MultiblockPattern pattern = block.getPattern();
        // 这里简化为：检查 pattern 中的相对坐标是否都是 MachineCasing 或 Controller
        boolean ok = pattern.matches(level, getBlockPos());
        formed = ok;
        BlockState newState = getBlockState().setValue(MultiblockControllerBlock.FORMED, ok);
        level.setBlock(getBlockPos(), newState, 3);
        return ok;
    }

    public boolean isFormed() {
        return formed;
    }

    // ---------------------------------------------------------------
    // Tick（子类可重写）
    // ---------------------------------------------------------------
    public void serverTick() {
        if (!formed) return;
        // 默认空，子类在 tick 里实现加工逻辑
    }

    // ---------------------------------------------------------------
    // NBT
    // ---------------------------------------------------------------
    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.put("inventory", inventory.serializeNBT());
        tag.putBoolean("formed", formed);
        tag.putInt("energy", energyStored);
        tag.putInt("progress", progress);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        if (tag.contains("inventory")) inventory.deserializeNBT(tag.getCompound("inventory"));
        formed = tag.getBoolean("formed");
        energyStored = tag.getInt("energy");
        progress = tag.getInt("progress");
    }

    // ---------------------------------------------------------------
    // MenuProvider
    // ---------------------------------------------------------------
    @Override
    public Component getDisplayName() {
        return Component.literal(getMachineName());
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int containerId, Inventory inv, Player player) {
        return null; // 子类如有菜单，自行实现
    }

    public ItemStackHandler getInventory() {
        return inventory;
    }
}
