package com.github.masongulu.serial;

import net.minecraft.resources.ResourceLocation;

import static com.github.masongulu.ComputerMod.MOD_ID;

public enum TerminalFont {
    COMPUTERCRAFT(8, 11, "term_font.png", -2),
    TIMES9K(5, 9, "times9k_font.png"),
    PICO8(5, 7, "pico8_font.png"),
    UNIFONT(8, 16, "unifont_font.png"),
    CGA(8,19,"cga_font.png"),
    TERMINUS(7,13,"terminus_font.png");
    public final int width;
    public final int height;
    public final int hpad;
    public final String path;
    public final ResourceLocation texture;

    TerminalFont(int width, int height, String path, int hpad) {
        this.width = width;
        this.height = height;
        this.path = path;
        this.hpad = hpad;
        this.texture = new ResourceLocation(MOD_ID, "textures/gui/fonts/" + path);
    }
    TerminalFont(int width, int height, String path) {
        this(width, height, path, 0);
    }


    public static final TerminalFont[] vals = values();

    public TerminalFont next() {
        return vals[(this.ordinal() + 1) % vals.length];
    }
}
