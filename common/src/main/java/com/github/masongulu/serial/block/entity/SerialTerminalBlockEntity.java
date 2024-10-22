package com.github.masongulu.serial.block.entity;

import com.github.masongulu.serial.ISerialDevice;
import com.github.masongulu.serial.SerialBus;
import com.github.masongulu.serial.screen.SerialTerminalMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import java.util.ArrayList;

import static com.github.masongulu.block.entity.ModBlockEntities.SERIAL_DEVICE_BLOCK_ENTITY;

public class SerialTerminalBlockEntity extends BaseContainerBlockEntity implements MenuProvider, ISerialDevice {
    private SerialBus sbus;
    public static final int width = 30;
    public static final int height = 8;
    private final ArrayList<String> text = new ArrayList<>();
    private final ContainerData data = new ContainerData() {
        @Override
        public int get(int i) {
            int y = i / width;
            int x = i % width;
            if (y >= text.size()) {
                return ' ';
            }
            String line = text.get(y);
            if (x >= line.length()) {
                return ' ';
            }
            return line.charAt(x);
        }

        @Override
        public void set(int i, int j) {

        }

        @Override
        public int getCount() {
            return width * height;
        }
    };

    public SerialTerminalBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(SERIAL_DEVICE_BLOCK_ENTITY.get(), blockPos, blockState);
    }

    public SerialBus getSbus() {
        return sbus;
    }

    @Override
    protected Component getDefaultName() {
        return new TextComponent("Serial Device");
    }

    @Override
    protected AbstractContainerMenu createMenu(int i, Inventory inventory) {
        return new SerialTerminalMenu(i, inventory, this, this.data);
    }

    @Override
    public void attach(SerialBus bus) {
        sbus = bus;
    }

    @Override
    public void detach(SerialBus bus) {
        sbus = null;
    }

    private void nextLine() {
        if (text.size() == height) {
            text.remove(0); // remove oldest line
        }
        text.add("");
    }
    private void putChar(char ch) {
        String last = "";
        int lines = text.size();
        if (lines > 0) {
            last = text.get(lines - 1);
        } else {
            text.add("");
            lines = 1;
        }
        if (last.length() == width) {
            nextLine();
            last = "";
        }
        // this is horrendous
        last += ch;
        text.set(lines - 1, last);
    }

    @Override
    public void write(char ch) {
        if (ch == '\n') {
            nextLine();
        } else {
            putChar(ch);
        }
        System.out.write(ch);
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
