package com.matryoshka.mdstar.multiblock;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import java.util.ArrayList;
import java.util.List;

/**
 * 多方块结构形状定义。
 * 使用"切片"列表，每个切片包含 偏移坐标 与 期望方块的 predicate。
 *
 * 一个 controller 调用 matches() 时，会从 controller 自己的位置开始，
 * 按相对坐标检查所有切片是否满足。
 */
public class MultiblockPattern {

    public static class Slice {
        public final int dx, dy, dz;
        public final BlockPredicate predicate;
        public Slice(int dx, int dy, int dz, BlockPredicate predicate) {
            this.dx = dx; this.dy = dy; this.dz = dz;
            this.predicate = predicate;
        }
    }

    @FunctionalInterface
    public interface BlockPredicate {
        boolean test(BlockState state);
    }

    private final List<Slice> slices = new ArrayList<>();
    private final int widthX, heightY, lengthZ;

    public MultiblockPattern(int widthX, int heightY, int lengthZ) {
        this.widthX = widthX;
        this.heightY = heightY;
        this.lengthZ = lengthZ;
    }

    public MultiblockPattern add(int dx, int dy, int dz, BlockPredicate predicate) {
        slices.add(new Slice(dx, dy, dz, predicate));
        return this;
    }

    /**
     * 在指定 controller 位置开始校验多方块。
     * (0,0,0) 是 controller 自己——通常会被排除在外，因为 controller 自己就站在那里。
     */
    public boolean matches(Level level, BlockPos controllerPos) {
        for (Slice s : slices) {
            BlockPos target = controllerPos.offset(s.dx, s.dy, s.dz);
            BlockState state = level.getBlockState(target);
            if (!s.predicate.test(state)) {
                return false;
            }
        }
        return true;
    }

    public List<Slice> getSlices() {
        return slices;
    }
}
