package com.matryoshka.mdstar.blocks.multiblock;

import com.matryoshka.mdstar.MDStarMod;
import com.matryoshka.mdstar.recipe.MDStarAssemblyRecipe;
import com.matryoshka.mdstar.registry.ModBlockEntities;
import com.matryoshka.mdstar.registry.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

/**
 * MD 组装塔 BlockEntity。
 * 完成后：消耗输入物 → 生成 MD 之心（md_heart）。
 * 实现要点：
 *   - 8 格输入栏（接收 KubeJS 注册的"精华"组件）
 *   - 服务端 tick 跑 MDStarAssemblyRecipe 类型配方
 *   - 消耗能量 + 时间
 */
public class MDAssemblyTowerBlockEntity extends MultiblockControllerBlockEntity {

    public static final int INPUT_SLOTS = 8;
    public static final int OUTPUT_SLOT = 8;
    public static final int SIZE = INPUT_SLOTS + 1;

    public MDAssemblyTowerBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.MD_ASSEMBLY_TOWER.get(), pos, state);
    }

    @Override public int getInventorySize() { return SIZE; }
    @Override public int getMaxEnergyStored() { return 10_000_000; }
    @Override public int getProcessingTime() { return 200 * 20; } // 200 秒
    @Override public String getMachineName() { return "MD 组装塔"; }

    @Override
    public void serverTick() {
        if (!formed || level == null || level.isClientSide) return;

        // 充能（实际中通过超导矩阵从聚变堆取电）
        if (energyStored < 100) energyStored += 50;

        // 尝试匹配 MDStarAssemblyRecipe 配方
        SimpleContainer input = new SimpleContainer(INPUT_SLOTS);
        for (int i = 0; i < INPUT_SLOTS; i++) {
            input.setItem(i, inventory.getStackInSlot(i));
        }

        Optional<MDStarAssemblyRecipe> match = level.getRecipeManager()
                .getRecipeFor((RecipeType<MDStarAssemblyRecipe>) (Object) MDStarAssemblyRecipe.Type.INSTANCE,
                        input, level);

        if (match.isPresent() && energyStored >= 1000) {
            energyStored -= 1000;
            progress++;
            if (progress >= maxProgress) {
                progress = 0;
                // 消耗输入
                for (int i = 0; i < INPUT_SLOTS; i++) {
                    inventory.extractItem(i, 1, false);
                }
                // 输出 MD 之心
                if (inventory.getStackInSlot(OUTPUT_SLOT).isEmpty()) {
                    inventory.setStackInSlot(OUTPUT_SLOT, new ItemStack(ModItems.MD_HEART.get()));
                } else if (inventory.getStackInSlot(OUTPUT_SLOT).getItem() == ModItems.MD_HEART.get()) {
                    inventory.getStackInSlot(OUTPUT_SLOT).grow(1);
                }
                MDStarMod.LOGGER.info("[MD Assembly Tower] Crafted MD Heart at {}", getBlockPos());
            }
        } else {
            progress = 0;
        }

        setChanged();
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int id, Inventory inv, Player player) {
        return new MDAssemblyTowerMenu(id, inv, this, ContainerLevelAccess.create(level, getBlockPos()));
    }
}
