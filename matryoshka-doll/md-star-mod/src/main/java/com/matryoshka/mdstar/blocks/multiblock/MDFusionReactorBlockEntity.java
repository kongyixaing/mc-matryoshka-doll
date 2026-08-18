package com.matryoshka.mdstar.blocks.multiblock;

import com.matryoshka.mdstar.registry.ModBlockEntities;
import com.matryoshka.mdstar.registry.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;

/**
 * MD 聚变反应堆 BlockEntity。
 * 形成后：把"等离子核心"+ "太阳尘" 转化为"戴森球框架"+ FE 能量。
 * 同时为其他机器供能（这里通过 maxEnergy 储能表现）。
 */
public class MDFusionReactorBlockEntity extends MultiblockControllerBlockEntity {

    public MDFusionReactorBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.MD_FUSION_REACTOR.get(), pos, state);
    }

    @Override public int getInventorySize() { return 4; }     // 2 input + 1 output + 1 fuel
    @Override public int getMaxEnergyStored() { return 100_000_000; } // 100M FE
    @Override public int getProcessingTime() { return 100 * 20; }
    @Override public String getMachineName() { return "MD 聚变反应堆"; }

    @Override
    public void serverTick() {
        if (!formed || level == null || level.isClientSide) return;

        // 输入: plasma_core + stellar_dust → dyson_frame + 能量
        ItemStack input1 = inventory.getStackInSlot(0);
        ItemStack input2 = inventory.getStackInSlot(1);
        ItemStack output = inventory.getStackInSlot(2);

        if (!input1.isEmpty() && input1.getItem() == ModItems.PLASMA_CORE.get()
                && !input2.isEmpty() && input2.getItem() == ModItems.STELLAR_DUST.get()) {
            energyStored = Math.min(maxEnergy, energyStored + 5000); // 每tick 5000 FE
            progress++;
            if (progress >= maxProgress) {
                progress = 0;
                inventory.extractItem(0, 1, false);
                inventory.extractItem(1, 1, false);
                if (output.isEmpty() || output.getItem() == ModItems.DYSON_FRAME.get()) {
                    inventory.setStackInSlot(2, new ItemStack(ModItems.DYSON_FRAME.get()));
                }
            }
        } else {
            // 仅输出能量
            if (energyStored > 0) energyStored = Math.min(maxEnergy, energyStored + 1000);
        }
        setChanged();
    }
}
