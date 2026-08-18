package com.matryoshka.mdstar.blocks.multiblock;

import com.matryoshka.mdstar.multiblock.MultiblockPatterns;
import com.matryoshka.mdstar.registry.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

/**
 * 量子纠缠腔 —— 跨维度存储。
 * 5×5×5 钛外壳。配合 AE2 量子环使用，提供超大型 ME 网络存储。
 */
public class QuantumEntanglementChamberBlock extends MultiblockControllerBlock {

    public QuantumEntanglementChamberBlock(Properties props) {
        super(props, MultiblockPatterns.QUANTUM_ENTANGLEMENT);
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return ModBlockEntities.QUANTUM_ENTANGLEMENT.get().create(pos, state);
    }

    @Override
    public String getMultiblockName() {
        return "量子纠缠腔";
    }
}
