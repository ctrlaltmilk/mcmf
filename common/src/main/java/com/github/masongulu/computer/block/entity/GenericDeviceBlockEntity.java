package com.github.masongulu.computer.block.entity;

import com.github.masongulu.computer.screen.GenericDeviceMenu;
import com.github.masongulu.core.uxn.devices.IDeviceProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public abstract class GenericDeviceBlockEntity extends BaseContainerBlockEntity implements MenuProvider, IDeviceProvider {
    public int deviceNumber;
    private final ContainerData data = new ContainerData() {
        @Override
        public int get(int i) {
            return deviceNumber;
        }

        @Override
        public void set(int i, int j) {
            setDeviceNumber(j);
        }

        @Override
        public int getCount() {
            return 1;
        }
    };
    protected GenericDeviceBlockEntity(BlockEntityType<?> blockEntityType, BlockPos blockPos, BlockState blockState) {
        super(blockEntityType, blockPos, blockState);
    }

    public void setDeviceNumber(int i) {
        this.deviceNumber = i;
    }

    @Override
    protected AbstractContainerMenu createMenu(int i, Inventory inventory) {
        return new GenericDeviceMenu(i, inventory, this, this.data, this);
    }

    @Override
    public int getContainerSize() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public ItemStack getItem(int i) {
        return null;
    }

    @Override
    public ItemStack removeItem(int i, int j) {
        return null;
    }

    @Override
    public ItemStack removeItemNoUpdate(int i) {
        return null;
    }

    @Override
    public void setItem(int i, ItemStack itemStack) {

    }

    @Override
    public boolean stillValid(Player player) {
        return false;
    }

    @Override
    public void clearContent() {

    }
}
