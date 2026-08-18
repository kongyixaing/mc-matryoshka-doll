package com.matryoshka.mdstar.registry;

import com.matryoshka.mdstar.MDStarMod;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

/**
 * 物品注册——包括所有 MD 之星相关中间物与终端物。
 * 一些中间物（如 create_essence）由 KubeJS 提供，这里只注册 mod 自己实现的。
 */
public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, MDStarMod.MOD_ID);

    // ---------------------------------------------------------------
    // 多方块控制器物品（BlockItem 由 ModBlocks 自动创建，这里仅注册额外 item）
    // ---------------------------------------------------------------

    // ---------------------------------------------------------------
    // 毕业中间物 / 终端物
    // ---------------------------------------------------------------
    public static final RegistryObject<Item> MD_FRAME = ITEMS.register("md_frame",
            () -> new Item(new Item.Properties().rarity(Rarity.EPIC).fireResistant()));

    public static final RegistryObject<Item> MD_HEART = ITEMS.register("md_heart",
            () -> new Item(new Item.Properties().rarity(Rarity.EPIC).fireResistant()));

    public static final RegistryObject<Item> MD_STAR_SHELL = ITEMS.register("md_star_shell",
            () -> new Item(new Item.Properties().rarity(Rarity.EPIC).fireResistant()));

    // 最终毕业物——MD 之星
    public static final RegistryObject<Item> MD_STAR = ITEMS.register("md_star",
            () -> new Item(new Item.Properties().rarity(Rarity.EPIC).fireResistant()
                    .food(new net.minecraft.world.food.FoodProperties.Builder()
                            .nutrition(20).saturationMod(2.0F).alwaysEat().build())));

    // ---------------------------------------------------------------
    // 各 mod 精华（部分亦可通过 KubeJS 创建，这里保留同名以兼容）
    // ---------------------------------------------------------------
    public static final RegistryObject<Item> CREATE_ESSENCE = ITEMS.register("create_essence",
            () -> new Item(new Item.Properties().rarity(Rarity.RARE)));
    public static final RegistryObject<Item> RAILS_ESSENCE = ITEMS.register("rails_essence",
            () -> new Item(new Item.Properties().rarity(Rarity.RARE)));
    public static final RegistryObject<Item> SLICE_ESSENCE = ITEMS.register("slice_essence",
            () -> new Item(new Item.Properties().rarity(Rarity.RARE)));
    public static final RegistryObject<Item> CNA_ESSENCE = ITEMS.register("cna_essence",
            () -> new Item(new Item.Properties().rarity(Rarity.RARE)));
    public static final RegistryObject<Item> GREGTECH_ESSENCE = ITEMS.register("gregtech_essence",
            () -> new Item(new Item.Properties().rarity(Rarity.RARE)));
    public static final RegistryObject<Item> AE2_ESSENCE = ITEMS.register("ae2_essence",
            () -> new Item(new Item.Properties().rarity(Rarity.RARE)));
    public static final RegistryObject<Item> AE2_THINGS_ESSENCE = ITEMS.register("ae2_things_essence",
            () -> new Item(new Item.Properties().rarity(Rarity.RARE)));
    public static final RegistryObject<Item> TWILIGHT_ESSENCE = ITEMS.register("twilight_essence",
            () -> new Item(new Item.Properties().rarity(Rarity.RARE)));
    public static final RegistryObject<Item> EXPLORE_ESSENCE = ITEMS.register("explore_essence",
            () -> new Item(new Item.Properties().rarity(Rarity.RARE)));

    // ---------------------------------------------------------------
    // 机械零件（多方块结构所需中间物）
    // ---------------------------------------------------------------
    public static final RegistryObject<Item> TITANIUM_CASING = ITEMS.register("titanium_casing",
            () -> new Item(new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> QUANTUM_CIRCUIT = ITEMS.register("quantum_circuit",
            () -> new Item(new Item.Properties().rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> SUPERCONDUCTOR_COIL = ITEMS.register("superconductor_coil",
            () -> new Item(new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> ANTIMATTER_PELLET = ITEMS.register("antimatter_pellet",
            () -> new Item(new Item.Properties().rarity(Rarity.EPIC).fireResistant()));
    public static final RegistryObject<Item> PLASMA_CORE = ITEMS.register("plasma_core",
            () -> new Item(new Item.Properties().rarity(Rarity.EPIC).fireResistant()));
    public static final RegistryObject<Item> STELLAR_DUST = ITEMS.register("stellar_dust",
            () -> new Item(new Item.Properties().rarity(Rarity.RARE)));
    public static final RegistryObject<Item> DYSON_FRAME = ITEMS.register("dyson_frame",
            () -> new Item(new Item.Properties().rarity(Rarity.EPIC).fireResistant()));
}
