package com.matryoshka.mdstar.blocks.multiblock;

import com.matryoshka.mdstar.multiblock.MultiblockPattern;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

/**
 * 多方块结构控制器基类。
 *
 * 子类需要实现：
 *   - newBlockEntity()               提供 BlockEntity
 *   - getPattern()                   提供多方块结构定义
 *   - onFormed/onUnformed()          形成时的副作用
 *
 * 玩家用空手右键控制器会触发多方块校验。
 */
public abstract class MultiblockControllerBlock extends Block implements EntityBlock {

    public static final BooleanProperty FORMED = BlockStateProperties.ENABLED; // reuse 'enabled' bit

    private final MultiblockPattern pattern;

    public MultiblockControllerBlock(Properties props, MultiblockPattern pattern) {
        super(props);
        this.pattern = pattern;
        registerDefaultState(stateDefinition.any().setValue(FORMED, false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FORMED);
    }

    public MultiblockPattern getPattern() {
        return pattern;
    }

    @Override
    public abstract BlockEntity newBlockEntity(BlockPos pos, BlockState state);

    /**
     * 注册 BlockEntity ticker。仅服务端 tick。
     */
    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        if (level.isClientSide) return null;
        return (lvl, pos, st, be) -> {
            if (be instanceof MultiblockControllerBlockEntity controller) {
                controller.serverTick();
            }
        };
    }

    @SuppressWarnings("deprecation")
    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos,
                                 Player player, InteractionHand hand, BlockHitResult hit) {
        if (!level.isClientSide && hand == InteractionHand.MAIN_HAND) {
            BlockEntity be = level.getBlockEntity(pos);
            if (be instanceof MultiblockControllerBlockEntity controller) {
                boolean formed = controller.checkAndForm();
                if (formed) {
                    player.sendSystemMessage(net.minecraft.network.chat.Component.literal("§b[" + getMultiblockName() + "] §a多方块结构形成！"));
                } else {
                    player.sendSystemMessage(net.minecraft.network.chat.Component.literal("§c[" + getMultiblockName() + "] 多方块结构不完整！"));
                }
            }
        }
        return InteractionResult.sidedSuccess(level.isClientSide);
    }

    /**
     * 多方块的人类可读名称。
     */
    public abstract String getMultiblockName();
}
