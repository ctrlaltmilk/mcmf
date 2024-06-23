package com.github.masongulu.screen;

import com.github.masongulu.block.entity.ComputerBlockEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;

public class ComputerMenu extends AbstractContainerMenu {
    public ComputerBlockEntity blockEntity;
    public ContainerData data;

    private String getString(int o) {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < ComputerBlockEntity.STRING_LENGTH; i++) {
            s.append((char)data.get(o + i));
        }
        return s.toString();
    }
    public String getRst() {
        return getString(0);
    }
    public String getWst() {
        return getString(ComputerBlockEntity.STRING_LENGTH);
    }

    public boolean isRunning() {
        return data.get(ComputerBlockEntity.DATA_START) == 1;
    }

    public int getPc() {
        return data.get(ComputerBlockEntity.DATA_START + 1);
    }

    public ComputerMenu(int i, Inventory inventory) {
        this(i, inventory, null, new SimpleContainerData(ComputerBlockEntity.DATA_LENGTH));
    }
    public ComputerMenu(int i, Inventory inventory, ComputerBlockEntity computerBlockEntity, ContainerData data) {
        super(ModMenus.COMPUTER_MENU.get(), i);
        blockEntity = computerBlockEntity;
        this.data = data;
        addDataSlots(this.data);
        addPlayerInventory(inventory);
        addPlayerHotbar(inventory);
    }

    protected void addDataSlots(ContainerData containerData) {
        for(int i = 0; i < containerData.getCount(); ++i) {
            this.addDataSlot(DataSlot.forContainer(containerData, i));
        }
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
        blockEntity.togglePower();
        return true;
    }

    private void addPlayerInventory(Inventory playerInventory) {
        for (int i = 0; i < 3; ++i) {
            for (int l = 0; l < 9; ++l) {
                this.addSlot(new Slot(playerInventory, l + i * 9 + 9, 8 + l * 18, 84 + i * 18));
            }
        }
    }

    private void addPlayerHotbar(Inventory playerInventory) {
        for (int i = 0; i < 9; ++i) {
            this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 142));
        }
    }

}
