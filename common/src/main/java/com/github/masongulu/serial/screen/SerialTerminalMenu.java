package com.github.masongulu.serial.screen;

import com.github.masongulu.screen.ModMenus;
import com.github.masongulu.serial.block.entity.SerialTerminalBlockEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;

public class SerialTerminalMenu extends AbstractContainerMenu {
    public SerialTerminalBlockEntity blockEntity;
    public ContainerData data;

    private String getString(int o, int length) {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < length; i++) {
            s.append((char)data.get(o + i));
        }
        return s.toString();
    }
    public SerialTerminalMenu(int i, Inventory inventory) {
        this(i, inventory, null, new SimpleContainerData(SerialTerminalBlockEntity.width*SerialTerminalBlockEntity.height));
    }

    public SerialTerminalMenu(int i, Inventory inventory, SerialTerminalBlockEntity termBlockEntity, ContainerData data) {
        super(ModMenus.SERIAL_TERMINAL_MENU.get(), i);
        blockEntity = termBlockEntity;
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
//        Slot slot = this.slots.get(i);
//        if (slot != null && slot.hasItem()) {
//            ItemStack itemStack2 = slot.getItem();
//            itemStack = itemStack2.copy();
//        }
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
