package com.github.masongulu.computer.block.entity;

import com.github.masongulu.computer.screen.FlasherDeviceMenu;
import com.github.masongulu.computer.screen.GenericDeviceMenu;
import com.github.masongulu.core.uxn.UXNBus;
import com.github.masongulu.core.uxn.UXNEvent;
import com.github.masongulu.core.uxn.devices.IDevice;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import java.util.HashMap;
import java.util.Map;

import static com.github.masongulu.ModBlockEntities.FLASHER_DEVICE_BLOCK_ENTITY;
import static com.github.masongulu.ModBlockEntities.REDSTONE_DEVICE_BLOCK_ENTITY;

public class FlasherDeviceBlockEntity extends GenericDeviceBlockEntity {
    private NonNullList<ItemStack> items;
    public static final int INVENTORY_SIZE = 1;

    public FlasherDeviceBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(FLASHER_DEVICE_BLOCK_ENTITY.get(), blockPos, blockState);
        deviceNumber = 13;
        this.items = NonNullList.withSize(INVENTORY_SIZE, ItemStack.EMPTY);
    }

    private void tick(Level level, BlockPos pos, BlockState state) {
    }

    public static <T extends BlockEntity> void tick(Level level, BlockPos blockPos, BlockState blockState, T t) {
        ((FlasherDeviceBlockEntity)t).tick(level, blockPos, blockState);
    }

    @Override
    protected AbstractContainerMenu createMenu(int i, Inventory inventory) {
        return new FlasherDeviceMenu(i, inventory, this, this.data, this);
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
    public String getLabel() {
        return "Flasher Device";
    }

    @Override
    public IDevice getDevice(Direction attachSide) {
        return this;
    }

    @Override
    protected Component getDefaultName() {
        return new TextComponent("Flasher Device");
    }

    @Override
    public int getContainerSize() {
        return INVENTORY_SIZE;
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
        this.items = NonNullList.withSize(INVENTORY_SIZE, ItemStack.EMPTY);
        ContainerHelper.loadAllItems(compoundTag, this.items);
    }

    protected void saveAdditional(CompoundTag compoundTag) {
        super.saveAdditional(compoundTag);
        ContainerHelper.saveAllItems(compoundTag, this.items);
    }
}