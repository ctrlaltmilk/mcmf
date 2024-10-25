package com.github.masongulu.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.resources.ResourceLocation;

import java.awt.*;

import static com.github.masongulu.ComputerMod.MOD_ID;

public class ToggleSwitchButton extends Button {
    static private final ResourceLocation texture = new ResourceLocation(MOD_ID, "textures/gui/switches.png");

    private ToggleSwitchType type;
    private String label;
    private Font font;
    private LabelPosition labelPosition;
    public ToggleSwitchButton(int x, int y, String label, OnPress onPress, Font font, ToggleSwitchType type, LabelPosition position) {
        super(x - type.switchHW, y - type.switchHH, type.switchW, type.switchW, new TextComponent(label), onPress);
        this.type = type;
        this.label = label;
        this.font = font;
        this.labelPosition = position;
    }
    public ToggleSwitchButton(int x, int y, String label, OnPress onPress, Font font, ToggleSwitchType type) {
        this(x, y, label, onPress, font, type, LabelPosition.ABOVE);
    }

    public void renderBg(PoseStack poseStack, int i, int j, boolean state) {
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, texture);

        int ty = state ? type.switchOnTY : type.switchOffTY;
        this.blit(poseStack, x, y, type.switchTX, ty, type.switchW, type.switchH);
    }

    @Override
    public void render(PoseStack poseStack, int i, int j, float f) {
        switch (labelPosition) {
            case ABOVE -> drawCenteredString(poseStack, font, label, x + type.switchHW, y - type.switchHH + 2, 16777215);
            case RIGHT -> drawString(poseStack, font, label, x + type.switchW, y + type.switchHH / 2, 16777215);
        }
    }

    public enum LabelPosition {
        ABOVE,
        RIGHT;
    }
}
