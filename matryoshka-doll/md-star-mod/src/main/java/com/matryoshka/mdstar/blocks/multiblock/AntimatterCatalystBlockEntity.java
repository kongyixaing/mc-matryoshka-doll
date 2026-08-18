package com.matryoshka.mdstar.blocks.multiblock;

import com.matryoshka.mdstar.registry.ModBlockEntities;
import com.matryoshka.mdstar.registry.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;

/**
 * 反物质催化炉 BlockEntity。
 * 输入: 反物质颗粒（ANTIMATTER_PELLET）+ 太阳尘（STELLAR_DUST）×4 → 等离子核心（PLASMA_CORE）
 */
public class AntimatterCatalystBlockEntity extends MultiblockControllerBlockEntity {

    public AntimatterCatalystBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.ANTIMATTER_CATALYST.get(), pos, state);
    }

    @Override public int getInventorySize() { return 7; } // 6 input + 1 output
    @Override public int getMaxEnergyStored() { return 30_000_000; }
    @Override public int getProcessingTime() { return 500 * 20; }
    @Override public String getMachineName() { return "反物质催化炉"; }

    @Override
    public void serverTick() {
        if (!formed || level == null || level.isClientSide) return;
        if (energyStored < 50000) return;

        ItemStack pellet = inventory.getStackInSlot(0);
        if (pellet.isEmpty() || pellet.getItem() != ModItems.ANTIMATTER_PELLET.get()) {
            progress = 0; return;
        }

        boolean allDust = true;
        for (int i = 1; i < 5; i++) {
            ItemStack s = inventory.getStackInSlot(i);
            if (s.isEmpty() || s.getItem() != ModItems.STELLAR_DUST.get()) {
                allDust = false; break;
            }
        }
        if (!allDust) { progress = 0; return; }

        ItemStack output = inventory.getStackInSlot(5);
        if (output.isEmpty() || output.getItem() == ModItems.PLASMA_CORE.get()) {
            energyStored -= 50000;
            progress++;
            if (progress >= maxProgress) {
                progress = 0;
                inventory.extractItem(0, 1, false);
                for (int i = 1; i < 5; i++) inventory.extractItem(i, 1, false);
                inventory.setStackInSlot(5, new ItemStack(ModItems.PLASMA_CORE.get()));
            }
        }
        setChanged();
    }
}
