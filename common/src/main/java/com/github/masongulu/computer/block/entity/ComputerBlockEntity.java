package com.github.masongulu.computer.block.entity;

import com.github.masongulu.computer.screen.ComputerMenu;
import com.github.masongulu.core.uxn.UXNBus;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static com.github.masongulu.ModBlockEntities.COMPUTER_BLOCK_ENTITY;

public class ComputerBlockEntity extends BaseContainerBlockEntity implements MenuProvider {
    public static final int STRING_LENGTH = "WST 00 00 00 00 00 00 00 00 <".length(); // 29 characters
    public static final int DATA_START = STRING_LENGTH * 2;
    public static final int DATA_LENGTH = DATA_START + 5;
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
            } else if (i == DATA_START + 2) {
                return (bus.isPaused()) ? 1 : 0;
            } else if (i == DATA_START + 3) {
                return bus.getEventCount();
            } else if (i == DATA_START + 4) {
                return bus.isArgumentMode() ? 1 : 0;
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

    public static final int CONTAINER_SIZE = 1;
    private NonNullList<ItemStack> items;

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
            // this will register the bus on this computer
            bus.startup();
        }
    }

    public void shutdown() {
        if (bus.isExecuting()) {
            bus.shutdown();
        }
    }

    public void pause() {
        bus.pause();
    }

    public void togglePause() {
        bus.pause(!bus.isPaused());
    }

    public void togglePower() {
        if (bus.isExecuting()) shutdown();
        else startup();
    }

    public ComputerBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(COMPUTER_BLOCK_ENTITY.get(), blockPos, blockState);
        this.bus = new UXNBus(this);
        this.items = NonNullList.withSize(CONTAINER_SIZE, ItemStack.EMPTY);
    }

    @Override
    public @NotNull Component getDisplayName() {
        return new TextComponent("Computer");
    }

    @Override
    protected Component getDefaultName() {
        return new TextComponent("Computer");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {
        return new ComputerMenu(i, inventory, this, data, this);
    }

    @Override
    protected @NotNull AbstractContainerMenu createMenu(int i, Inventory inventory) {
        return new ComputerMenu(i, inventory, this, data, this);
    }

    private void tick(Level level, BlockPos pos, BlockState state) {
    }

    public static <T extends BlockEntity> void tick(Level level, BlockPos blockPos, BlockState blockState, T t) {
        ((ComputerBlockEntity)t).tick(level, blockPos, blockState);
    }

    public void step() {
        if (bus.uxn == null) return;
        bus.uxn.doStep = true;
    }

    @Override
    public int getContainerSize() {
        return CONTAINER_SIZE;
    }

    @Override
    public boolean isEmpty() {
        return this.items.isEmpty();
    }

    @Override
    public ItemStack getItem(int i) {
        return this.items.get(i);
    }

    @Override
    public ItemStack removeItem(int i, int j) {
        ItemStack itemStack = ContainerHelper.removeItem(this.items, i, j);
        if (!itemStack.isEmpty()) {
            this.setChanged();
        }

        return itemStack;
    }

    @Override
    public ItemStack removeItemNoUpdate(int i) {
        return ContainerHelper.takeItem(this.items, i);
    }

    @Override
    public void setItem(int i, ItemStack itemStack) {
        this.items.set(i, itemStack);
        if (itemStack.getCount() > this.getMaxStackSize()) {
            itemStack.setCount(this.getMaxStackSize());
        }

        this.setChanged();
    }

    @Override
    public boolean stillValid(Player player) {
        assert this.level != null;
        if (this.level.getBlockEntity(this.worldPosition) != this) {
            return false;
        } else {
            return !(player.distanceToSqr((double)this.worldPosition.getX() + 0.5, (double)this.worldPosition.getY() + 0.5, (double)this.worldPosition.getZ() + 0.5) > 64.0);
        }
    }

    @Override
    public void clearContent() {
        this.items.clear();
    }


    public void load(CompoundTag compoundTag) {
        super.load(compoundTag);
        this.items = NonNullList.withSize(CONTAINER_SIZE, ItemStack.EMPTY);
        ContainerHelper.loadAllItems(compoundTag, this.items);
    }

    protected void saveAdditional(CompoundTag compoundTag) {
        super.saveAdditional(compoundTag);
        ContainerHelper.saveAllItems(compoundTag, this.items);
    }

    public void toggleArgumentMode() {
        bus.setArgumentMode(!bus.isArgumentMode());
    }
}
