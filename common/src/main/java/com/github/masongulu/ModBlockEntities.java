package com.github.masongulu;

import com.github.masongulu.computer.block.entity.ComputerBlockEntity;
import com.github.masongulu.computer.block.entity.RedstoneDeviceBlockEntity;
import com.github.masongulu.serial.block.entity.SerialDeviceBlockEntity;
import com.github.masongulu.serial.block.entity.SerialTerminalBlockEntity;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.Registry;
import net.minecraft.world.level.block.entity.BlockEntityType;

import java.util.Set;

import static com.github.masongulu.ComputerMod.MOD_ID;
import static com.github.masongulu.ModBlocks.*;

public class ModBlockEntities {


    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(MOD_ID, Registry.BLOCK_ENTITY_TYPE_REGISTRY);
    public static RegistrySupplier<BlockEntityType<ComputerBlockEntity>> COMPUTER_BLOCK_ENTITY;
    public static RegistrySupplier<BlockEntityType<RedstoneDeviceBlockEntity>> REDSTONE_DEVICE_BLOCK_ENTITY;
    public static RegistrySupplier<BlockEntityType<SerialDeviceBlockEntity>> SERIAL_DEVICE_BLOCK_ENTITY;
    public static RegistrySupplier<BlockEntityType<SerialTerminalBlockEntity>> SERIAL_TERMINAL_BLOCK_ENTITY;

    public static void register() {
        COMPUTER_BLOCK_ENTITY = BLOCK_ENTITIES.register("computer", () ->
                new BlockEntityType<>(ComputerBlockEntity::new, Set.of(COMPUTER_BLOCK.get()), null));
        REDSTONE_DEVICE_BLOCK_ENTITY = BLOCK_ENTITIES.register("redstone_device", () ->
                new BlockEntityType<>(RedstoneDeviceBlockEntity::new, Set.of(REDSTONE_DEVICE_BLOCK.get()), null));
        SERIAL_DEVICE_BLOCK_ENTITY = BLOCK_ENTITIES.register("serial_device", () ->
                new BlockEntityType<>(SerialDeviceBlockEntity::new, Set.of(SERIAL_DEVICE_BLOCK.get()), null));
        SERIAL_TERMINAL_BLOCK_ENTITY = BLOCK_ENTITIES.register("serial_terminal", () ->
                new BlockEntityType<>(SerialTerminalBlockEntity::new, Set.of(SERIAL_TERMINAL_BLOCK.get()), null));
        BLOCK_ENTITIES.register();
    }
}
