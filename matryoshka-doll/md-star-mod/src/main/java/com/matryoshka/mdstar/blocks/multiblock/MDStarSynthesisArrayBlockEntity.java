package com.matryoshka.mdstar.blocks.multiblock;

import com.matryoshka.mdstar.MDStarMod;
import com.matryoshka.mdstar.registry.ModBlockEntities;
import com.matryoshka.mdstar.registry.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;

/**
 * MD 之星合成阵列 BlockEntity —— 最终毕业。
 * 输入: MD 之星壳（MD_STAR_SHELL）×1 + 量子电路（QUANTUM_CIRCUIT）×8 + MD 之心（MD_HEART）×1
 * → 输出: MD 之星（MD_STAR）×1
 *
 * 完成后：玩家右键控制器触发校验 → 自动加工 → 输出毕业物。
 */
public class MDStarSynthesisArrayBlockEntity extends MultiblockControllerBlockEntity {

    public MDStarSynthesisArrayBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.MD_STAR_SYNTHESIS.get(), pos, state);
    }

    @Override public int getInventorySize() { return 11; } // 1 shell + 8 circuit + 1 heart + 1 output = 11
    @Override public int getMaxEnergyStored() { return 1_000_000_000; }
    @Override public int getProcessingTime() { return 3600 * 20; } // 1 小时
    @Override public String getMachineName() { return "MD 之星合成阵列"; }

    @Override
    public void serverTick() {
        if (!formed || level == null || level.isClientSide) return;
        if (energyStored < 1_000_000) return;

        ItemStack shell  = inventory.getStackInSlot(0);
        ItemStack heart   = inventory.getStackInSlot(10);

        if (shell.isEmpty() || shell.getItem() != ModItems.MD_STAR_SHELL.get()) { progress = 0; return; }
        if (heart.isEmpty() || heart.getItem() != ModItems.MD_HEART.get()) { progress = 0; return; }

        boolean allCircuits = true;
        for (int i = 1; i < 9; i++) {
            ItemStack s = inventory.getStackInSlot(i);
            if (s.isEmpty() || s.getItem() != ModItems.QUANTUM_CIRCUIT.get()) { allCircuits = false; break; }
        }
        if (!allCircuits) { progress = 0; return; }

        // 输出槽 9 必须为空或已是 MD_STAR
        ItemStack output = inventory.getStackInSlot(9);
        if (!output.isEmpty() && output.getItem() != ModItems.MD_STAR.get()) { progress = 0; return; }
        if (!output.isEmpty() && output.getCount() >= output.getMaxStackSize()) { progress = 0; return; }

        energyStored -= 1_000_000;
        progress++;
        if (progress >= maxProgress) {
            progress = 0;
            inventory.extractItem(0, 1, false);
            for (int i = 1; i < 9; i++) inventory.extractItem(i, 1, false);
            inventory.extractItem(10, 1, false);
            inventory.setStackInSlot(9, new ItemStack(ModItems.MD_STAR.get()));
            MDStarMod.LOGGER.info("§6★ MD 之星已合成！玩家毕业！★ at {}", getBlockPos());
        }
        setChanged();
    }
}
