package com.github.masongulu.computer.block.entity;

import com.github.masongulu.core.uxn.UXNBus;
import com.github.masongulu.core.uxn.UXNEvent;
import com.github.masongulu.core.uxn.devices.IDevice;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import java.util.HashMap;
import java.util.Map;

import static com.github.masongulu.ModBlockEntities.FLASHER_DEVICE_BLOCK_ENTITY;
import static com.github.masongulu.ModBlockEntities.REDSTONE_DEVICE_BLOCK_ENTITY;

public class FlasherDeviceBlockEntity extends GenericDeviceBlockEntity {
    private UXNBus bus;

    public FlasherDeviceBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(FLASHER_DEVICE_BLOCK_ENTITY.get(), blockPos, blockState);
        deviceNumber = 9;
    }

    private void tick(Level level, BlockPos pos, BlockState state) {
    }

    public static <T extends BlockEntity> void tick(Level level, BlockPos blockPos, BlockState blockState, T t) {
        ((FlasherDeviceBlockEntity)t).tick(level, blockPos, blockState);
    }

    @Override
    public void write(int address) {
        int port = address & 0x0F;
        switch(port) {
        }
    }

    @Override
    public void read(int address) {
        int port = address & 0x0F;
        switch(port) {
        }
    }

    @Override
    public IDevice getDevice(Direction attachSide) {
        return this;
    }

    @Override
    protected Component getDefaultName() {
        return new TextComponent("Flasher Device");
    }
}