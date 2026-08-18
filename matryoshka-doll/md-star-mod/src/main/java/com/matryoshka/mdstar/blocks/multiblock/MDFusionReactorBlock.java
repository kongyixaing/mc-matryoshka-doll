package com.matryoshka.mdstar.blocks.multiblock;

import com.matryoshka.mdstar.multiblock.MultiblockPatterns;
import com.matryoshka.mdstar.registry.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

/**
 * MD 聚变反应堆 —— 能量核心。
 * 5×5×5 钛外壳立方体。提供大量 FE 能量供其他机器使用。
 */
public class MDFusionReactorBlock extends MultiblockControllerBlock {

    public MDFusionReactorBlock(Properties props) {
        super(props, MultiblockPatterns.MD_FUSION_REACTOR);
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return ModBlockEntities.MD_FUSION_REACTOR.get().create(pos, state);
    }

    @Override
    public String getMultiblockName() {
        return "MD 聚变反应堆";
    }
}
