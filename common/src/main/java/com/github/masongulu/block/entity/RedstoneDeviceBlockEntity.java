package com.github.masongulu.block.entity;

import com.github.masongulu.uxn.UXNDeviceManager;
import com.github.masongulu.uxn.devices.IDevice;
import com.github.masongulu.uxn.devices.IUXNDeviceProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import java.util.HashMap;
import java.util.Map;

import static com.github.masongulu.block.entity.ModBlockEntities.COMPUTER_BLOCK_ENTITY;
import static com.github.masongulu.block.entity.ModBlockEntities.REDSTONE_DEVICE_BLOCK_ENTITY;

public class RedstoneDeviceBlockEntity extends BlockEntity implements IUXNDeviceProvider, IDevice {
    private UXNDeviceManager uxnDeviceManager;
    private int deviceNumber = 9;
    public final Map<Direction,Integer> redstoneOutputs = new HashMap<>();
    public Map<Direction,Integer> redstoneInputs = new HashMap<>();

    public RedstoneDeviceBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(REDSTONE_DEVICE_BLOCK_ENTITY.get(), blockPos, blockState);
        for (Direction direction : Direction.values()) {
            redstoneOutputs.put(direction, 0);
        }
    }

    private void tick(Level level, BlockPos pos, BlockState state) {
    }

    public static <T extends BlockEntity> void tick(Level level, BlockPos blockPos, BlockState blockState, T t) {
        ((RedstoneDeviceBlockEntity)t).tick(level, blockPos, blockState);
    }

    public void updateRedstone(Map<Direction,Integer> redstone) {
        if (uxnDeviceManager == null) return;
        System.out.println("Updating redstone");
        redstoneInputs = redstone;
        uxnDeviceManager.uxn.queueVector((uxnDeviceManager.readDev(deviceNumber << 4) << 8)
                | uxnDeviceManager.readDev(deviceNumber << 4 + 1));
    }

    @Override
    public void attach(UXNDeviceManager manager) {
        uxnDeviceManager = manager;
        manager.setDevice(deviceNumber, this);
        System.out.println("Redstone device attached!");
    }

    @Override
    public void detach(UXNDeviceManager manager) {
        uxnDeviceManager = null;
    }

    @Override
    public void write(int address) {
        int port = address & 0x0F;
        switch(port) {
            case 2: // Perform rotation on the Block side, NORTH is FRONT
                redstoneOutputs.put(Direction.NORTH, (int)uxnDeviceManager.readDev(address));
                break;
            case 3: // RIGHT
                redstoneOutputs.put(Direction.EAST, (int)uxnDeviceManager.readDev(address));
                break;
            case 4: // LEFT
                redstoneOutputs.put(Direction.WEST, (int)uxnDeviceManager.readDev(address));
                break;
            case 5: // BACK
                redstoneOutputs.put(Direction.SOUTH, (int)uxnDeviceManager.readDev(address));
                break;
            case 6: // UP
                redstoneOutputs.put(Direction.UP, (int)uxnDeviceManager.readDev(address));
                break;
            case 7: // DOWN
                redstoneOutputs.put(Direction.DOWN, (int)uxnDeviceManager.readDev(address));
                break;
        }
    }

    @Override
    public void read(int address) {
        int port = address & 0x0F;
        switch(port) {
            case 2: // Perform rotation on the Block side, NORTH is FRONT
                uxnDeviceManager.writeDev(address, (byte)(redstoneInputs.get(Direction.NORTH) & 0xFF));
                break;
            case 3: // RIGHT
                uxnDeviceManager.writeDev(address, (byte)(redstoneInputs.get(Direction.EAST) & 0xFF));
                break;
            case 4: // LEFT
                uxnDeviceManager.writeDev(address, (byte)(redstoneInputs.get(Direction.WEST) & 0xFF));
                break;
            case 5: // BACK
                uxnDeviceManager.writeDev(address, (byte)(redstoneInputs.get(Direction.SOUTH) & 0xFF));
                break;
            case 6: // UP
                uxnDeviceManager.writeDev(address, (byte)(redstoneInputs.get(Direction.UP) & 0xFF));
                break;
            case 7: // DOWN
                uxnDeviceManager.writeDev(address, (byte)(redstoneInputs.get(Direction.DOWN) & 0xFF));
                break;
        }

    }
}
