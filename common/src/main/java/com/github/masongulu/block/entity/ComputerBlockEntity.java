package com.github.masongulu.block.entity;

import com.github.masongulu.ComputerMod;
import com.github.masongulu.screen.ComputerMenu;
import com.github.masongulu.uxn.UXN;
import com.github.masongulu.uxn.UXNDeviceManager;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
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
    private UXNDeviceManager manager;
    private final ContainerData data = new ContainerData() {
        private String rst = "RST 00 00 00 00 00 00 00 00|<";
        private String wst = "WST 00 00 00 00 00 00 00 00|<";
        @Override
        public int get(int i) {
            if (manager != null) {
                if (i == 0) {
                    rst = manager.uxn.rst.toString();
                } else if (i == STRING_LENGTH) {
                    wst = manager.uxn.wst.toString();
                }
            }
            if (i < STRING_LENGTH) {
                return rst.charAt(i);
            } else if (i < DATA_START) {
                return wst.charAt(i - STRING_LENGTH);
            } else if (i == DATA_START) {
                return (manager != null) ? 1 : 0;
            } else if (i == DATA_START + 1) {
                if (manager != null) {
                    return manager.uxn.pc;
                }
                return 0;
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

    private void updateString(int o, String s) {
        for (int i = 0; i < STRING_LENGTH; i++) {
            data.set(o + i, s.charAt(i));
        }
    }

    public void startup() {
        if (manager == null) {
            System.out.println("Starting up...");
            manager = new UXNDeviceManager();
            for (Direction d : Direction.values()) {
                assert level != null;
                BlockEntity e = level.getBlockEntity(worldPosition.relative(d));
                if (e instanceof CableBlockEntity) {
                    ((CableBlockEntity) e).attach(d.getOpposite(), manager);
                    break;
                }
            }
            UXN uxn = new UXN(manager);
            manager.setUxn(uxn);
            ComputerMod.test(manager);
            manager.start();
        }
    }

    public void shutdown() {
        if (manager != null) {
            System.out.println("Shutting down...");
            manager.stop();
            manager = null;
        }
    }

    public void togglePower() {
        if (manager != null) shutdown();
        else startup();
    }

    public ComputerBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(COMPUTER_BLOCK_ENTITY.get(), blockPos, blockState);
        System.out.println("ComputerBlockEntity created");
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
