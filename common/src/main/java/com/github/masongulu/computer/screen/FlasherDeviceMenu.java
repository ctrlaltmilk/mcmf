package com.github.masongulu.computer.screen;

import com.github.masongulu.ModMenus;
import com.github.masongulu.computer.block.entity.ComputerBlockEntity;
import com.github.masongulu.computer.block.entity.FlasherDeviceBlockEntity;
import com.github.masongulu.computer.block.entity.GenericDeviceBlockEntity;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;

import static com.github.masongulu.ModMenus.FLASHER_DEVICE_MENU;

public class FlasherDeviceMenu extends GenericDeviceMenu {
    public FlasherDeviceMenu(int i, Inventory inventory) {
        this(i, inventory, null, new SimpleContainerData(1),
                new SimpleContainer(START_SLOT));
    }

    public FlasherDeviceMenu(int i, Inventory inventory, FlasherDeviceBlockEntity bentity, ContainerData data, Container container) {
        super(i, inventory, bentity, data, container, FLASHER_DEVICE_MENU.get());
        checkContainerSize(container, START_SLOT);
        addPlayerInventory(inventory);
        addPlayerHotbar(inventory);
        this.addSlot(new FlashableMemorySlot(container, 0, 116, 44));
    }


    private static final int START_SLOT = 1;
    private static final int INV_Y = 108;
    private static final int INV_X = 8;
    private void addPlayerInventory(Inventory playerInventory) {
        for (int i = 0; i < 3; ++i) {
            for (int l = 0; l < 9; ++l) {
                this.addSlot(new Slot(playerInventory, l + i * 9 + 9 + START_SLOT, INV_X + l * 18, INV_Y + i * 18));
            }
        }
    }

    private void addPlayerHotbar(Inventory playerInventory) {
        for (int i = 0; i < 9; ++i) {
            this.addSlot(new Slot(playerInventory, i + START_SLOT, INV_X + i * 18, INV_Y+58));
        }
    }
}