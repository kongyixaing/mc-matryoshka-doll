package com.matryoshka.mdstar.blocks.multiblock;

import com.matryoshka.mdstar.registry.ModBlockEntities;
import com.matryoshka.mdstar.registry.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;

/**
 * 超导线圈矩阵 BlockEntity。
 * 用作能量中继——把输入的"超导线圈"转化为能量输送能力，
 * 同时本机不直接发电，靠玩家从外部输入电力（FE）。
 */
public class SuperconductorMatrixBlockEntity extends MultiblockControllerBlockEntity {

    public SuperconductorMatrixBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.SUPERCONDUCTOR_MATRIX.get(), pos, state);
    }

    @Override public int getInventorySize() { return 2; } // 1 input (coil) + 1 output (charged coil)
    @Override public int getMaxEnergyStored() { return 50_000_000; } // 50M FE 缓冲
    @Override public int getProcessingTime() { return 20; }
    @Override public String getMachineName() { return "超导线圈矩阵"; }

    @Override
    public void serverTick() {
        if (!formed || level == null || level.isClientSide) return;
        ItemStack input = inventory.getStackInSlot(0);
        if (!input.isEmpty() && input.getItem() == ModItems.SUPERCONDUCTOR_COIL.get()) {
            // 每 tick 充 1000 FE
            energyStored = Math.min(maxEnergy, energyStored + 1000);
        }
        setChanged();
    }
}
