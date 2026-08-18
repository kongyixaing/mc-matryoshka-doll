package com.matryoshka.mdstar.blocks.multiblock;

import com.matryoshka.mdstar.multiblock.MultiblockPatterns;
import com.matryoshka.mdstar.registry.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

/**
 * MD 之星合成阵列 —— 最终毕业多方块。
 * 7×7×7 钛外壳。输入 MD 之星壳 + 量子电路，合成最终毕业物 MD 之星。
 */
public class MDStarSynthesisArrayBlock extends MultiblockControllerBlock {

    public MDStarSynthesisArrayBlock(Properties props) {
        super(props, MultiblockPatterns.MD_STAR_SYNTHESIS);
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return ModBlockEntities.MD_STAR_SYNTHESIS.get().create(pos, state);
    }

    @Override
    public String getMultiblockName() {
        return "MD 之星合成阵列";
    }
}
