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

import static com.github.masongulu.ModBlockEntities.REDSTONE_DEVICE_BLOCK_ENTITY;

public class RedstoneDeviceBlockEntity extends GenericDeviceBlockEntity implements IDevice {
    private UXNBus bus;
    private int deviceNumber = 9;
    public final Map<Direction,Integer> redstoneOutputs = new HashMap<>();
    public Map<Direction,Integer> redstoneInputs = new HashMap<>();

    boolean redstoneUpdated = false;

    public RedstoneDeviceBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(REDSTONE_DEVICE_BLOCK_ENTITY.get(), blockPos, blockState);
        for (Direction direction : Direction.values()) {
            redstoneOutputs.put(direction, 0);
        }
    }

    private void tick(Level level, BlockPos pos, BlockState state) {
        if (redstoneUpdated) {
            redstoneUpdated = false;
            level.updateNeighborsAt(pos, state.getBlock());
        }
    }

    public static <T extends BlockEntity> void tick(Level level, BlockPos blockPos, BlockState blockState, T t) {
        ((RedstoneDeviceBlockEntity)t).tick(level, blockPos, blockState);
    }

    public void updateRedstone(Map<Direction,Integer> redstone) {
        if (bus == null) return;
        if (bus.uxn == null) return;
        redstoneInputs = redstone;
        bus.uxn.queueEvent(new RedstoneEvent(deviceNumber));
    }

    @Override
    public void attach(UXNBus manager) {
        bus = manager;
        manager.setDevice(deviceNumber, this);
    }

    @Override
    public void detach(UXNBus bus) {
        bus.deleteDevice(deviceNumber);
        this.bus = null;
    }

    @Override
    public void write(int address) {
        int port = address & 0x0F;
        switch(port) {
            case 2: // Perform rotation on the Block side, NORTH is FRONT
                redstoneUpdated = true;
                redstoneOutputs.put(Direction.NORTH, (int) bus.readDev(address));
                break;
            case 3: // RIGHT
                redstoneUpdated = true;
                redstoneOutputs.put(Direction.EAST, (int) bus.readDev(address));
                break;
            case 4: // LEFT
                redstoneUpdated = true;
                redstoneOutputs.put(Direction.WEST, (int) bus.readDev(address));
                break;
            case 5: // BACK
                redstoneUpdated = true;
                redstoneOutputs.put(Direction.SOUTH, (int) bus.readDev(address));
                break;
            case 6: // UP
                redstoneUpdated = true;
                redstoneOutputs.put(Direction.UP, (int) bus.readDev(address));
                break;
            case 7: // DOWN
                redstoneUpdated = true;
                redstoneOutputs.put(Direction.DOWN, (int) bus.readDev(address));
                break;
        }
    }

    @Override
    public void read(int address) {
        int port = address & 0x0F;
        switch(port) {
            case 2: // Perform rotation on the Block side, NORTH is FRONT
                bus.writeDev(address, (byte)(redstoneInputs.get(Direction.NORTH) & 0xFF));
                break;
            case 3: // RIGHT
                bus.writeDev(address, (byte)(redstoneInputs.get(Direction.EAST) & 0xFF));
                break;
            case 4: // LEFT
                bus.writeDev(address, (byte)(redstoneInputs.get(Direction.WEST) & 0xFF));
                break;
            case 5: // BACK
                bus.writeDev(address, (byte)(redstoneInputs.get(Direction.SOUTH) & 0xFF));
                break;
            case 6: // UP
                bus.writeDev(address, (byte)(redstoneInputs.get(Direction.UP) & 0xFF));
                break;
            case 7: // DOWN
                bus.writeDev(address, (byte)(redstoneInputs.get(Direction.DOWN) & 0xFF));
                break;
        }
    }

    @Override
    public IDevice getDevice(Direction attachSide) {
        return this;
    }

    @Override
    protected Component getDefaultName() {
        return new TextComponent("Redstone Device");
    }
}

class RedstoneEvent implements UXNEvent {
    int deviceNumber;
    RedstoneEvent(int deviceNumber) {
        this.deviceNumber = deviceNumber;
    }
    @Override
    public void handle(UXNBus bus) {
        int vectorAddress = deviceNumber << 4;
        int vector = (bus.readDev(vectorAddress) << 8) | bus.readDev(vectorAddress + 1);
        bus.uxn.pc = vector;
    }
}