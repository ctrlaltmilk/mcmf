package com.github.masongulu.serial.block.entity;

import com.github.masongulu.serial.TerminalFont;
import com.github.masongulu.serial.screen.SerialTerminalMenu;
import com.github.masongulu.serial.screen.SerialTerminalScreen;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;

import java.util.ArrayList;

import static com.github.masongulu.ModBlockEntities.SERIAL_DEVICE_BLOCK_ENTITY;

public class SerialTerminalBlockEntity extends SerialPeerBlockEntity implements MenuProvider {
    // Terminal Width = 240
    // Terminal Height = 180
    public final static int TERM_WIDTH = 240;
    public final static int TERM_HEIGHT = 180;
    public int width;
    public int height;
    public int dataSize;
    public TerminalFont font;
    public boolean echo = true;
    public final ArrayList<String> text = new ArrayList<>();
    private final ContainerData data = new SerialTerminalContainerData(this);

    public void setFont(TerminalFont font) {
        this.font = font;
        width = TERM_WIDTH / (font.width + font.hpad);
        height = TERM_HEIGHT / font.height;
        dataSize = width * height;
        // make sure that the term contents fit
        while (text.size() >= height) {
            text.remove(0);
        }
    }
    public void nextFont() {
        setFont(font.next());
    }

    public SerialTerminalBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(SERIAL_DEVICE_BLOCK_ENTITY.get(), blockPos, blockState);
        setFont(TerminalFont.TIMES9K);
    }
    @Override
    protected Component getDefaultName() {
        return new TextComponent("Serial Terminal");
    }

    @Override
    protected AbstractContainerMenu createMenu(int i, Inventory inventory) {
        return new SerialTerminalMenu(i, inventory, this, this.data);
    }

    private void nextLine() {
        if (text.size() >= height) {
            text.remove(0); // remove oldest line
        }
        text.add("");
    }
    void putChar(char ch) {
        int lines = text.size();

        // Ensure there's at least one line
        if (lines == 0) {
            text.add("");
            lines = 1;
        }

        String last = text.get(lines - 1);

        // If the last line is full, move to the next line
        if (last.length() >= width) {
            nextLine();
            lines = text.size();
            last = text.get(lines - 1); // Get the new last line
        }

        // Append the character
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

    public void keyPress(char i) {
        if (echo) {
            write(i);
        }
        if (peer != null) {
            peer.write(i);
        }
    }
}
