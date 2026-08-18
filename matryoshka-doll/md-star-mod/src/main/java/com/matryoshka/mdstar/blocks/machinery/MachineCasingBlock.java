package com.matryoshka.mdstar.blocks.machinery;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

/**
 * 机器外壳——多方块结构的"墙"方块。
 * 不会单独做任何机器逻辑，但 formed 属性可用于贴图变化。
 */
public class MachineCasingBlock extends Block {
    public static final BooleanProperty FORMED = BooleanProperty.create("formed");

    public MachineCasingBlock(Properties props) {
        super(props);
        registerDefaultState(stateDefinition.any().setValue(FORMED, false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FORMED);
    }
}
