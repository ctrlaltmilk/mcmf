package com.github.masongulu.block.entity;

import com.github.masongulu.ComputerMod;
import com.github.masongulu.screen.ComputerMenu;
import com.github.masongulu.uxn.UXNBus;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static com.github.masongulu.block.entity.ModBlockEntities.COMPUTER_BLOCK_ENTITY;

public class ComputerBlockEntity extends BlockEntity implements MenuProvider {
    public static final int STRING_LENGTH = "WST 00 00 00 00 00 00 00 00 <".length(); // 29 characters
    public static final int DATA_START = STRING_LENGTH * 2;
    public static final int DATA_LENGTH = DATA_START + 2;
    private final UXNBus bus;
    private final ContainerData data = new ContainerData() {
        private String rst = "RST 00 00 00 00 00 00 00 00|<";
        private String wst = "WST 00 00 00 00 00 00 00 00|<";
        @Override
        public int get(int i) {
            if (bus.uxn != null) {
                if (i == 0) {
                    rst = bus.uxn.rst.toString();
                } else if (i == STRING_LENGTH) {
                    wst = bus.uxn.wst.toString();
                }
            }
            if (i < STRING_LENGTH) {
                return rst.charAt(i);
            } else if (i < DATA_START) {
                return wst.charAt(i - STRING_LENGTH);
            } else if (i == DATA_START) {
                return (bus.isExecuting()) ? 1 : 0;
            } else if (i == DATA_START + 1) {
                if (bus.uxn != null) {
                    return bus.uxn.pc;
                }
            }
            return 0;
        }

        @Override
        public void set(int i, int j) {
        }

        @Override
        public int getCount() {
            return DATA_LENGTH;
        }
    };

    public UXNBus getBus() {
        return bus;
    }

    private void updateString(int o, String s) {
        for (int i = 0; i < STRING_LENGTH; i++) {
            data.set(o + i, s.charAt(i));
        }
    }

    public void startup() {
        if (bus.uxn == null) {
            System.out.println("Starting up...");
            // this will register the bus on this computer
//            bus.floodFillNetwork(level, getBlockPos());
            bus.startup();
        }
    }

    public void shutdown() {
        if (bus.isExecuting()) {
            System.out.println("Shutting down...");
            bus.shutdown();
        }
    }

    public void pause() {
        bus.pause();
    }

    public void togglePower() {
        if (bus.isExecuting()) shutdown();
        else startup();
    }

    public ComputerBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(COMPUTER_BLOCK_ENTITY.get(), blockPos, blockState);
        this.bus = new UXNBus(this);
    }

    @Override
    public @NotNull Component getDisplayName() {
        return new TextComponent("Computer Block");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {
        return new ComputerMenu(i, inventory, this, data);
    }

    private void tick(Level level, BlockPos pos, BlockState state) {
    }

    public static <T extends BlockEntity> void tick(Level level, BlockPos blockPos, BlockState blockState, T t) {
        ((ComputerBlockEntity)t).tick(level, blockPos, blockState);
    }

}
