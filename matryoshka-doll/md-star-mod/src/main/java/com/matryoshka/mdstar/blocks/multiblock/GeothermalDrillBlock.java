package com.matryoshka.mdstar.blocks.multiblock;

import com.matryoshka.mdstar.multiblock.MultiblockPatterns;
import com.matryoshka.mdstar.registry.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

/**
 * 地壳能量开采站 —— 自动资源。
 * 5×5×5 钢外壳。形成后自动生成铁/铜/锡矿石，无需挖矿。
 */
public class GeothermalDrillBlock extends MultiblockControllerBlock {

    public GeothermalDrillBlock(Properties props) {
        super(props, MultiblockPatterns.GEOTHERMAL_DRILL);
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return ModBlockEntities.GEOTHERMAL_DRILL.get().create(pos, state);
    }

    @Override
    public String getMultiblockName() {
        return "地壳能量开采站";
    }
}
