package com.matryoshka.mdstar.registry;

import com.matryoshka.mdstar.MDStarMod;
import com.matryoshka.mdstar.blocks.multiblock.MDAssemblyTowerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModMenuTypes {
    public static final DeferredRegister<MenuType<?>> MENU_TYPES =
            DeferredRegister.create(ForgeRegistries.MENU_TYPES, MDStarMod.MOD_ID);

    public static final RegistryObject<MenuType<MDAssemblyTowerMenu>> MD_ASSEMBLY_TOWER =
            MENU_TYPES.register("md_assembly_tower",
                    () -> IForgeMenuType.create((id, inv, buf) -> new MDAssemblyTowerMenu(id, inv, buf)));
}
