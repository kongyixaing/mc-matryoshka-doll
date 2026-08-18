package com.matryoshka.mdstar.blocks.multiblock;

import com.matryoshka.mdstar.registry.ModBlockEntities;
import com.matryoshka.mdstar.registry.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;

/**
 * 戴森球核心 BlockEntity。
 * 输入: 戴森框架（DYSON_FRAME）×4 + 量子电路（QUANTUM_CIRCUIT）×4 → 反物质颗粒（ANTIMATTER_PELLET）
 * 形成后持续自动产出能量（终极能源）。
 */
public class DysonCoreBlockEntity extends MultiblockControllerBlockEntity {

    public DysonCoreBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.DYSON_CORE.get(), pos, state);
    }

    @Override public int getInventorySize() { return 9; }
    @Override public int getMaxEnergyStored() { return 1_000_000_000; } // 1B FE
    @Override public int getProcessingTime() { return 1000 * 20; } // 1000 秒
    @Override public String getMachineName() { return "戴森球核心"; }

    @Override
    public void serverTick() {
        if (!formed || level == null || level.isClientSide) return;

        // 持续产能
        energyStored = Math.min(maxEnergy, energyStored + 50000);

        // 加工：4 戴森框架 + 4 量子电路 → 1 反物质颗粒
        boolean inputsReady = true;
        for (int i = 0; i < 4; i++) {
            ItemStack s = inventory.getStackInSlot(i);
            if (s.isEmpty() || s.getItem() != ModItems.DYSON_FRAME.get()) { inputsReady = false; break; }
        }
        for (int i = 4; i < 8; i++) {
            ItemStack s = inventory.getStackInSlot(i);
            if (s.isEmpty() || s.getItem() != ModItems.QUANTUM_CIRCUIT.get()) { inputsReady = false; break; }
        }
        ItemStack output = inventory.getStackInSlot(8);

        if (inputsReady && (output.isEmpty() || output.getItem() == ModItems.ANTIMATTER_PELLET.get())) {
            progress++;
            if (progress >= maxProgress) {
                progress = 0;
                for (int i = 0; i < 8; i++) inventory.extractItem(i, 1, false);
                inventory.setStackInSlot(8, new ItemStack(ModItems.ANTIMATTER_PELLET.get()));
            }
        } else {
            progress = 0;
        }
        setChanged();
    }
}
