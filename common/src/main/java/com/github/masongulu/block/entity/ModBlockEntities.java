package com.github.masongulu.block.entity;

import com.github.masongulu.block.RedstoneDeviceBlock;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.Registry;
import net.minecraft.world.level.block.entity.BlockEntityType;

import java.util.Set;

import static com.github.masongulu.ComputerMod.MOD_ID;
import static com.github.masongulu.block.ModBlocks.*;

public class ModBlockEntities {


    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(MOD_ID, Registry.BLOCK_ENTITY_TYPE_REGISTRY);
    public static RegistrySupplier<BlockEntityType<ComputerBlockEntity>> COMPUTER_BLOCK_ENTITY;
    public static RegistrySupplier<BlockEntityType<CableBlockEntity>> CABLE_BLOCK_ENTITY;
    public static RegistrySupplier<BlockEntityType<RedstoneDeviceBlockEntity>> REDSTONE_DEVICE_BLOCK_ENTITY;

    public static void register() {
        COMPUTER_BLOCK_ENTITY = BLOCK_ENTITIES.register("computer", () ->
                new BlockEntityType<>(ComputerBlockEntity::new, Set.of(COMPUTER_BLOCK.get()), null));
        CABLE_BLOCK_ENTITY = BLOCK_ENTITIES.register("cable", () ->
                new BlockEntityType<>(CableBlockEntity::new, Set.of(CABLE_BLOCK.get()), null));
        REDSTONE_DEVICE_BLOCK_ENTITY = BLOCK_ENTITIES.register("redstone_device", () ->
                new BlockEntityType<>(RedstoneDeviceBlockEntity::new, Set.of(REDSTONE_DEVICE_BLOCK.get()), null));
        BLOCK_ENTITIES.register();
    }
}
