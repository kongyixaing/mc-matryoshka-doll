package com.matryoshka.mdstar.blocks.multiblock;

import com.matryoshka.mdstar.registry.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Random;

/**
 * 地壳能量开采站 BlockEntity。
 * 形成后：每 ~10 秒自动产出一个矿石（铁/铜/金/红石/钻石随机）。
 * 完全不需要输入——这是玩家"自动化采矿"阶段的关键设备。
 */
public class GeothermalDrillBlockEntity extends MultiblockControllerBlockEntity {

    private static final ItemStack[] ORES = new ItemStack[]{
            new ItemStack(Items.IRON_ORE),
            new ItemStack(Items.COPPER_ORE),
            new ItemStack(Items.GOLD_ORE),
            new ItemStack(Items.REDSTONE_ORE),
            new ItemStack(Items.DIAMOND_ORE),
            new ItemStack(Items.LAPIS_ORE),
            new ItemStack(Items.EMERALD_ORE),
            new ItemStack(Items.COAL)
    };

    public GeothermalDrillBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.GEOTHERMAL_DRILL.get(), pos, state);
    }

    @Override public int getInventorySize() { return 9; } // 9 个输出槽
    @Override public int getMaxEnergyStored() { return 5_000_000; }
    @Override public int getProcessingTime() { return 200; } // 每 10 秒产一个矿
    @Override public String getMachineName() { return "地壳能量开采站"; }

    @Override
    public void serverTick() {
        if (!formed || level == null || level.isClientSide) return;
        progress++;
        if (progress >= maxProgress) {
            progress = 0;
            ItemStack ore = ORES[new Random().nextInt(ORES.length)].copy();
            for (int i = 0; i < 9; i++) {
                ItemStack slot = inventory.getStackInSlot(i);
                if (slot.isEmpty() || (slot.getItem() == ore.getItem() && slot.getCount() < slot.getMaxStackSize())) {
                    inventory.insertItem(i, ore, false);
                    break;
                }
            }
        }
        setChanged();
    }
}
