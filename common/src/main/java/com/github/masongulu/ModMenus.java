package com.github.masongulu;

import com.github.masongulu.computer.screen.ComputerMenu;
import com.github.masongulu.computer.screen.ComputerScreen;
import com.github.masongulu.computer.screen.GenericDeviceMenu;
import com.github.masongulu.computer.screen.GenericDeviceScreen;
import com.github.masongulu.serial.screen.SerialTerminalMenu;
import com.github.masongulu.serial.screen.SerialTerminalScreen;
import dev.architectury.platform.Platform;
import dev.architectury.registry.menu.MenuRegistry;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.fabricmc.api.EnvType;
import net.minecraft.core.Registry;
import net.minecraft.world.inventory.MenuType;

import static com.github.masongulu.ComputerMod.MOD_ID;

public class ModMenus {
    public static final DeferredRegister<MenuType<?>> MENUS = DeferredRegister.create(MOD_ID, Registry.MENU_REGISTRY);
    public static RegistrySupplier<MenuType<ComputerMenu>> COMPUTER_MENU;
    public static RegistrySupplier<MenuType<GenericDeviceMenu>> GENERIC_DEVICE_MENU;
    public static RegistrySupplier<MenuType<SerialTerminalMenu>> SERIAL_TERMINAL_MENU;

    public static void register() {
        COMPUTER_MENU = MENUS.register("computer", () -> new MenuType<>(ComputerMenu::new));
        GENERIC_DEVICE_MENU = MENUS.register("device", () -> new MenuType<>(GenericDeviceMenu::new));
        SERIAL_TERMINAL_MENU = MENUS.register("serial_terminal", () -> new MenuType<>(SerialTerminalMenu::new));
        MENUS.register();
        if (Platform.getEnv() == EnvType.CLIENT) {
            MenuRegistry.registerScreenFactory(COMPUTER_MENU.get(), ComputerScreen::new);
            MenuRegistry.registerScreenFactory(GENERIC_DEVICE_MENU.get(), GenericDeviceScreen::new);
            MenuRegistry.registerScreenFactory(SERIAL_TERMINAL_MENU.get(), SerialTerminalScreen::new);
        }
    }
}
