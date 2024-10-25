package com.github.masongulu.item;

import com.github.masongulu.item.memory.RAMItem;
import com.github.masongulu.item.memory.SROMItem;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.Registry;
import net.minecraft.world.item.Item;

import static com.github.masongulu.ComputerMod.MOD_ID;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(MOD_ID, Registry.ITEM_REGISTRY);


    public static RegistrySupplier<Item> SROM_ITEM;
    public static RegistrySupplier<Item> RAM_ITEM;

    public static void register() {
        SROM_ITEM = ITEMS.register("srom", SROMItem::new);
        RAM_ITEM = ITEMS.register("ram", RAMItem::new);
        ITEMS.register();
    }
}
