package com.github.masongulu.screen;

import dev.architectury.core.RegistryEntry;
import dev.architectury.registry.menu.MenuRegistry;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.Registry;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.Item;

import static com.github.masongulu.ComputerMod.MOD_ID;

public class ModMenus {
    public static final DeferredRegister<MenuType<?>> MENUS = DeferredRegister.create(MOD_ID, Registry.MENU_REGISTRY);
    public static RegistrySupplier<MenuType<ComputerMenu>> COMPUTER_MENU;

    public static void register() {
        COMPUTER_MENU = MENUS.register("computer", () -> new MenuType<>(ComputerMenu::new));
        MENUS.register();
        MenuRegistry.registerScreenFactory(COMPUTER_MENU.get(), ComputerScreen::new);
    }
}
