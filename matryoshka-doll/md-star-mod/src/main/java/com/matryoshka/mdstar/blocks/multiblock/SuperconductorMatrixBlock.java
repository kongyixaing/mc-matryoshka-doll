package com.matryoshka.mdstar.blocks.multiblock;

import com.matryoshka.mdstar.multiblock.MultiblockPatterns;
import com.matryoshka.mdstar.registry.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

/**
 * 超导线圈矩阵 —— 高压输电。
 * 3×3×3 不锈钢外壳立方体。负责把聚变堆的 FE 输出给其他机器。
 */
public class SuperconductorMatrixBlock extends MultiblockControllerBlock {

    public SuperconductorMatrixBlock(Properties props) {
        super(props, MultiblockPatterns.SUPERCONDUCTOR_MATRIX);
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return ModBlockEntities.SUPERCONDUCTOR_MATRIX.get().create(pos, state);
    }

    @Override
    public String getMultiblockName() {
        return "超导线圈矩阵";
    }
}
