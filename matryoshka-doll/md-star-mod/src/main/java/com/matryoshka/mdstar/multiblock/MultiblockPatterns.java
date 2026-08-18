package com.matryoshka.mdstar.multiblock;

import com.matryoshka.mdstar.blocks.machinery.MachineCasingBlock;
import com.matryoshka.mdstar.registry.ModBlocks;
import net.minecraft.world.level.block.state.BlockState;

import java.util.function.Predicate;

/**
 * 所有多方块结构的形状定义集中放在这里。
 * 每个 pattern 用相对坐标 (dx, dy, dz) 描述所需方块。
 *
 * 设计原则：
 *   - (0,0,0) 是 Controller
 *   - 外围一圈是 MachineCasing（钢/钛/不锈钢外壳）
 *   - 内部空腔为操作空间
 *
 * 现代大型机械结构示意（俯视）：
 *
 *     C C C C C
 *     C . . . C      (C = 外壳, . = 空腔/内部)
 *     C . [D] . C   (D = 控制器)
 *     C . . . C
 *     C C C C C
 *
 * MD Assembly Tower 是 5x5x5 的立方体，控制器在中间。
 */
public class MultiblockPatterns {

    private static Predicate<BlockState> isCasing() {
        return state -> state.getBlock() instanceof MachineCasingBlock;
    }

    private static Predicate<BlockState> isSteelCasing() {
        return state -> state.getBlock() == ModBlocks.STEEL_CASING.get();
    }

    private static Predicate<BlockState> isTitaniumCasing() {
        return state -> state.getBlock() == ModBlocks.TITANIUM_CASING_BLOCK.get();
    }

    private static Predicate<BlockState> isStainlessCasing() {
        return state -> state.getBlock() == ModBlocks.STAINLESS_CASING.get();
    }

    private static Predicate<BlockState> isAir() {
        return state -> state.isAir();
    }

    // ---------------------------------------------------------------
    // MD 组装塔 5x5x5
    //   外围钢外壳，内部空腔
    // ---------------------------------------------------------------
    public static final MultiblockPattern MD_ASSEMBLY_TOWER = buildHollowCube(2, isSteelCasing());

    // ---------------------------------------------------------------
    // MD 聚变反应堆 5x5x5 (钛外壳)
    // ---------------------------------------------------------------
    public static final MultiblockPattern MD_FUSION_REACTOR = buildHollowCube(2, isTitaniumCasing());

    // ---------------------------------------------------------------
    // 超导线圈矩阵 3x3x3 (不锈钢外壳)
    // ---------------------------------------------------------------
    public static final MultiblockPattern SUPERCONDUCTOR_MATRIX = buildHollowCube(1, isStainlessCasing());

    // ---------------------------------------------------------------
    // 量子纠缠腔 5x5x5 (钛外壳)
    // ---------------------------------------------------------------
    public static final MultiblockPattern QUANTUM_ENTANGLEMENT = buildHollowCube(2, isTitaniumCasing());

    // ---------------------------------------------------------------
    // 反物质催化炉 5x5x5 (钛外壳)
    // ---------------------------------------------------------------
    public static final MultiblockPattern ANTIMATTER_CATALYST = buildHollowCube(2, isTitaniumCasing());

    // ---------------------------------------------------------------
    // 戴森球核心 7x7x7 (钛外壳)
    // ---------------------------------------------------------------
    public static final MultiblockPattern DYSON_CORE = buildHollowCube(3, isTitaniumCasing());

    // ---------------------------------------------------------------
    // 地壳能量开采站 5x5x7 (钢外壳，垂直延伸)
    // ---------------------------------------------------------------
    public static final MultiblockPattern GEOTHERMAL_DRILL = buildHollowCube(2, isSteelCasing());

    // ---------------------------------------------------------------
    // MD 之星合成阵列 7x7x7 (钛外壳)
    // ---------------------------------------------------------------
    public static final MultiblockPattern MD_STAR_SYNTHESIS = buildHollowCube(3, isTitaniumCasing());

    // ---------------------------------------------------------------
    // 工具方法：构建一个空心的立方体（外壳 = casing，内部 = 空气）
    // 半径 r 表示从中心到边缘的距离（如 r=2 → 5x5x5）
    // ---------------------------------------------------------------
    private static MultiblockPattern buildHollowCube(int r, Predicate<BlockState> casingPredicate) {
        MultiblockPattern pattern = new MultiblockPattern(r * 2 + 1, r * 2 + 1, r * 2 + 1);
        for (int x = -r; x <= r; x++) {
            for (int y = -r; y <= r; y++) {
                for (int z = -r; z <= r; z++) {
                    if (x == 0 && y == 0 && z == 0) continue; // 跳过控制器自己
                    boolean isEdge = x == -r || x == r || y == -r || y == r || z == -r || z == r;
                    if (isEdge) {
                        pattern.add(x, y, z, casingPredicate::test);
                    } else {
                        pattern.add(x, y, z, s -> s.isAir()); // 内部空腔
                    }
                }
            }
        }
        return pattern;
    }
}
