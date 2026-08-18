package com.matryoshka.mdstar.blocks.multiblock;

import com.matryoshka.mdstar.multiblock.MultiblockPatterns;
import com.matryoshka.mdstar.registry.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

/**
 * 反物质催化炉 —— 反物质加工。
 * 5×5×5 钛外壳。把反物质颗粒加工为等离子核心（PLASMA_CORE）。
 */
public class AntimatterCatalystBlock extends MultiblockControllerBlock {

    public AntimatterCatalystBlock(Properties props) {
        super(props, MultiblockPatterns.ANTIMATTER_CATALYST);
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return ModBlockEntities.ANTIMATTER_CATALYST.get().create(pos, state);
    }

    @Override
    public String getMultiblockName() {
        return "反物质催化炉";
    }
}
