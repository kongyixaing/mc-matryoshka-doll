package com.matryoshka.mdstar.blocks.multiblock;

import com.matryoshka.mdstar.multiblock.MultiblockPatterns;
import com.matryoshka.mdstar.registry.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

/**
 * 戴森球核心 —— 终极能源。
 * 7×7×7 钛外壳。需要 等离子核心 + 太阳尘 才能形成。
 * 一旦形成会提供几乎无限的 FE 能量。
 */
public class DysonCoreBlock extends MultiblockControllerBlock {

    public DysonCoreBlock(Properties props) {
        super(props, MultiblockPatterns.DYSON_CORE);
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return ModBlockEntities.DYSON_CORE.get().create(pos, state);
    }

    @Override
    public String getMultiblockName() {
        return "戴森球核心";
    }
}
