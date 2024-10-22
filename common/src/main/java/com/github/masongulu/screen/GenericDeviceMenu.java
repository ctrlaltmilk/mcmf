package com.github.masongulu.screen;

import com.github.masongulu.block.entity.ComputerBlockEntity;
import com.github.masongulu.block.entity.GenericDeviceBlockEntity;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;

public class GenericDeviceMenu extends AbstractContainerMenu {
    public GenericDeviceBlockEntity blockEntity;
    public ContainerData data;

    private String getString(int o) {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < ComputerBlockEntity.STRING_LENGTH; i++) {
            s.append((char)data.get(o + i));
        }
        return s.toString();
    }
    public GenericDeviceMenu(int i, Inventory inventory) {
        this(i, inventory, null, new SimpleContainerData(ComputerBlockEntity.DATA_LENGTH),
                new SimpleContainer(ComputerBlockEntity.CONTAINER_SIZE));
    }

    public GenericDeviceMenu(int i, Inventory inventory, GenericDeviceBlockEntity bentity, ContainerData data, Container container) {
        super(ModMenus.GENERIC_DEVICE_MENU.get(), i);
        blockEntity = bentity;
        this.data = data;
        addDataSlots(this.data);
    }

    protected void addDataSlots(ContainerData containerData) {
        for(int i = 0; i < containerData.getCount(); ++i) {
            this.addDataSlot(DataSlot.forContainer(containerData, i));
        }
    }

    public ItemStack quickMoveStack(Player player, int i) {
        ItemStack itemStack = ItemStack.EMPTY;
        return itemStack;
    }

    @Override
    public boolean stillValid(Player player) {
        return true;
    }

    public void setData(int i, int j) {
        super.setData(i, j);
        this.broadcastChanges();
    }

    @Override
    public boolean clickMenuButton(Player player, int i) {
        return true;
    }
}
