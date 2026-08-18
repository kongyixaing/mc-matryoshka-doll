package com.matryoshka.mdstar.blocks.multiblock;

import com.matryoshka.mdstar.multiblock.MultiblockPatterns;
import com.matryoshka.mdstar.registry.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

/**
 * MD 组装塔 —— 终极装配多方块，合成 MD 之心。
 * 结构：5×5×5 钢外壳立方体，控制器居中。
 */
public class MDAssemblyTowerBlock extends MultiblockControllerBlock {

    public MDAssemblyTowerBlock(Properties props) {
        super(props, MultiblockPatterns.MD_ASSEMBLY_TOWER);
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return ModBlockEntities.MD_ASSEMBLY_TOWER.get().create(pos, state);
    }

    @Override
    public String getMultiblockName() {
        return "MD 组装塔";
    }
}
