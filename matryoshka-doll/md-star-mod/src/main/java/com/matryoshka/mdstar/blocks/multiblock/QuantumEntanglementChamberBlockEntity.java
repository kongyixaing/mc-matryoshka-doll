package com.matryoshka.mdstar.blocks.multiblock;

import com.matryoshka.mdstar.registry.ModBlockEntities;
import com.matryoshka.mdstar.registry.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.registries.ForgeRegistries;

/**
 * 量子纠缠腔 BlockEntity。
 * 输入: 4× AE2 quantum_link_chamber → 1× 量子电路（QUANTUM_CIRCUIT）
 */
public class QuantumEntanglementChamberBlockEntity extends MultiblockControllerBlockEntity {

    public QuantumEntanglementChamberBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.QUANTUM_ENTANGLEMENT.get(), pos, state);
    }

    @Override public int getInventorySize() { return 5; } // 4 input + 1 output
    @Override public int getMaxEnergyStored() { return 20_000_000; }
    @Override public int getProcessingTime() { return 300 * 20; }
    @Override public String getMachineName() { return "量子纠缠腔"; }

    @Override
    public void serverTick() {
        if (!formed || level == null || level.isClientSide) return;
        if (energyStored < 10000) return; // 需要 10000 FE
        // 4 input slots must all be ae2:quantum_link_chamber
        ResourceLocation ae2ChamberId = new ResourceLocation("ae2", "quantum_link_chamber");
        boolean allSet = true;
        for (int i = 0; i < 4; i++) {
            ItemStack stack = inventory.getStackInSlot(i);
            if (stack.isEmpty()) { allSet = false; break; }
            ResourceLocation itemId = ForgeRegistries.ITEMS.getKey(stack.getItem());
            if (itemId == null || !itemId.equals(ae2ChamberId)) { allSet = false; break; }
        }
        if (!allSet) { progress = 0; return; }

        ItemStack output = inventory.getStackInSlot(4);
        if (output.isEmpty() || output.getItem() == ModItems.QUANTUM_CIRCUIT.get()) {
            energyStored -= 10000;
            progress++;
            if (progress >= maxProgress) {
                progress = 0;
                for (int i = 0; i < 4; i++) inventory.extractItem(i, 1, false);
                inventory.setStackInSlot(4, new ItemStack(ModItems.QUANTUM_CIRCUIT.get()));
            }
        }
        setChanged();
    }
}
