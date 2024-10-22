package com.github.masongulu.serial;

import com.github.masongulu.serial.block.entity.SerialDeviceBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import static com.github.masongulu.block.ModBlocks.DEVICE_CABLE;
import static com.github.masongulu.block.ModBlocks.SERIAL_CABLE;

public class SerialBus {
    boolean conflicting = false;
    private final SerialDeviceBlockEntity hostEntity;
    private ISerialDevice device;

    public SerialBus(SerialDeviceBlockEntity hostEntity) {
        this.hostEntity = hostEntity;
    }

    /*
    This returns a list of all blocks around the starting position
    plus all blocks around any blocks tagged DEVICE_CABLE
     */
    public static ArrayList<BlockPos> traverse(Level level, BlockPos blockPos, BlockPos ignore) {
        java.util.Stack<BlockPos> toSearch = new java.util.Stack<>();
        toSearch.push(blockPos);
        ArrayList<BlockPos> visited = new ArrayList<>();
        while (!toSearch.isEmpty()) {
            BlockPos pos = toSearch.pop();
            // Add surrounding blocks to search
            for (Direction direction : Direction.values()) {
                BlockPos newPos = pos.relative(direction);
                if (visited.contains(newPos)) continue;
                if (newPos.equals(ignore)) continue;
                visited.add(newPos);
                BlockState state = level.getBlockState(newPos);
                if (state.is(SERIAL_CABLE)) {
                    toSearch.push(newPos);
                }
            }
        }
        return visited;
    }
    public static ArrayList<BlockPos> traverse(Level level, BlockPos blockPos) {
        return traverse(level, blockPos, null);
    }

    public static SerialBus findBus(Level level, BlockPos blockPos) {
        ArrayList<BlockPos> traversed = traverse(level, blockPos);
        for (BlockPos pos : traversed) {
            BlockEntity entity = level.getBlockEntity(pos);
            if (entity == null)
                continue;
            if (entity instanceof SerialDeviceBlockEntity computerBlockEntity) {
                return computerBlockEntity.getSbus();
            }
        }
        return null;
    }

    public void refresh(Level level, BlockPos blockPos, BlockPos ignore) {
        ArrayList<BlockPos> traversed = traverse(level, blockPos, ignore);
        ArrayList<ISerialDevice> foundDevices = new ArrayList<>();
        this.conflicting = false;
        System.out.println("Refreshing Serial Bus");
        for (BlockPos pos : traversed) {
            BlockEntity entity = level.getBlockEntity(pos);
            if (entity == null)
                continue;
            if (entity instanceof SerialDeviceBlockEntity serialDeviceBlockEntity) {
                if (hostEntity != serialDeviceBlockEntity) {
                    this.conflicting = true;
                    // TODO flesh out this conflicting thing more
                }
            } else if (entity instanceof ISerialDevice deviceProvider) {
                foundDevices.add(deviceProvider);
            }
        }
        if (foundDevices.size() > 1) {
            System.out.println("Found multiple serial devices");
            this.conflicting = true;
            return;
        }
        if (this.device != null) {
            this.device.detach(this);
            this.device = null;
        }
        if (foundDevices.size() == 1) {
            System.out.println("Found a single serial device");
            this.device = foundDevices.get(0);
            this.device.attach(this);
        }
    }

    public void write(ISerialDevice device, char ch) {
        if (device == this.hostEntity) {
            if (this.device != null) {
                this.device.write(ch);
            }
        } else {
            this.hostEntity.write(ch);
        }
    }

    public void refresh(Level level, BlockPos blockPos) {
        refresh(level, blockPos, null);
    }
    public void refresh() {
        refresh(this.hostEntity.getLevel(), this.hostEntity.getBlockPos());
    }
    public void refresh(BlockPos ignore) {
        refresh(this.hostEntity.getLevel(), this.hostEntity.getBlockPos(), ignore);
    }
}
