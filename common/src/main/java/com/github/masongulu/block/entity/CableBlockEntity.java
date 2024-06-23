package com.github.masongulu.block.entity;

import com.github.masongulu.uxn.UXNDeviceManager;
import com.github.masongulu.uxn.devices.IUXNDeviceProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import static com.github.masongulu.block.entity.ModBlockEntities.CABLE_BLOCK_ENTITY;
import static com.github.masongulu.block.entity.ModBlockEntities.COMPUTER_BLOCK_ENTITY;

public class CableBlockEntity extends BlockEntity {
    // Only one computer can be connected to a device network
    private UXNDeviceManager uxnDeviceManager;

    public boolean attach(Direction sourceDir, UXNDeviceManager manager) {
        if (uxnDeviceManager != null) {
            // TODO
            return false;
        }
        System.out.println("Attaching CableBlockEntity @ " + worldPosition);
        for (Direction d : Direction.values()) {
            if (d == sourceDir) continue;
            assert level != null;
            BlockEntity e = level.getBlockEntity(worldPosition.relative(d));
            if (e instanceof CableBlockEntity) {
                ((CableBlockEntity) e).attach(d.getOpposite(), manager);
            } else if (e instanceof IUXNDeviceProvider) {
                ((IUXNDeviceProvider) e).attach(manager);
            }
        }
        uxnDeviceManager = manager;
        return true;
    }

    public void invalidate(@Nullable Direction sourceDir) {
        uxnDeviceManager.stop();
        for (Direction d : Direction.values()) {
            if (d == sourceDir) continue;
            assert level != null;
            BlockEntity e = level.getBlockEntity(worldPosition.relative(d));
            if (e instanceof CableBlockEntity) {
                ((CableBlockEntity) e).invalidate(d.getOpposite());
            }
        }
        uxnDeviceManager = null;
    }

    public CableBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(CABLE_BLOCK_ENTITY.get(), blockPos, blockState);
    }

    private void tick(Level level, BlockPos pos, BlockState state) {
    }

    public static <T extends BlockEntity> void tick(Level level, BlockPos blockPos, BlockState blockState, T t) {
        ((CableBlockEntity)t).tick(level, blockPos, blockState);
    }
}
