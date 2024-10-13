package com.github.masongulu.block;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import static com.github.masongulu.ComputerMod.MOD_ID;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(MOD_ID, Registry.BLOCK_REGISTRY);
    public static final DeferredRegister<Item> BLOCK_ITEMS = DeferredRegister.create(MOD_ID, Registry.ITEM_REGISTRY);
    public static RegistrySupplier<Block> COMPUTER_BLOCK;
    public static RegistrySupplier<Block> CABLE_BLOCK;
    public static RegistrySupplier<Block> REDSTONE_DEVICE_BLOCK;
    public static RegistrySupplier<BlockItem> COMPUTER_ITEM;
    public static RegistrySupplier<Item> CABLE_ITEM;
    public static RegistrySupplier<Item> REDSTONE_DEVICE_ITEM;

    public static TagKey<Block> DEVICE_CABLE;

    private static TagKey<Block> tag(String name) {
        return TagKey.create(Registry.BLOCK_REGISTRY, new ResourceLocation(MOD_ID, name));
    }

    public static void register() {
        DEVICE_CABLE = tag("device_cable");
        COMPUTER_BLOCK = BLOCKS.register("computer", ComputerBlock::new);
        COMPUTER_ITEM = BLOCK_ITEMS.register("computer", () -> new BlockItem(COMPUTER_BLOCK.get(), new Item.Properties()));
        CABLE_BLOCK = BLOCKS.register("cable", CableBlock::new);
        CABLE_ITEM = BLOCK_ITEMS.register("cable", () -> new BlockItem(CABLE_BLOCK.get(), new Item.Properties()));
        REDSTONE_DEVICE_BLOCK = BLOCKS.register("redstone_device", RedstoneDeviceBlock::new);
        REDSTONE_DEVICE_ITEM = BLOCK_ITEMS.register("redstone_device", () -> new BlockItem(REDSTONE_DEVICE_BLOCK.get(), new Item.Properties()));
        BLOCKS.register();
        BLOCK_ITEMS.register();
    }
}
