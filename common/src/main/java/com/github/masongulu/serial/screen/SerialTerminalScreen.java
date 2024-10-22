package com.github.masongulu.serial.screen;

import com.github.masongulu.serial.block.entity.SerialTerminalBlockEntity;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ContainerData;

import static com.github.masongulu.ComputerMod.MOD_ID;

public class SerialTerminalScreen extends AbstractContainerScreen<SerialTerminalMenu> {
    private final ResourceLocation texture = new ResourceLocation(MOD_ID, "textures/gui/serial_terminal.png");
    private final SerialTerminalMenu termMenu;
    private final ResourceLocation fontTexture = new ResourceLocation(MOD_ID, "textures/gui/font.png");
    private final int fontWidth = 8;
    private final int fontHeight = 19;

    public SerialTerminalScreen(SerialTerminalMenu termMenu, Inventory inventory, Component component) {
        super(termMenu, inventory, component);
        this.termMenu = termMenu;
        this.imageHeight = 191;
        this.imageWidth = 251;
        this.inventoryLabelY = this.imageHeight - 94;
    }

    @Override
    protected void init() {
        super.init();
        int k = this.leftPos;
        int l = this.topPos;
    }

    @Override
    protected void renderBg(PoseStack poseStack, float f, int i, int j) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, this.texture);
        int k = this.leftPos;
        int l = this.topPos;
        assert minecraft != null;
        this.blit(poseStack, k, l, 0, 0, this.imageWidth, this.imageHeight);

        RenderSystem.setShaderTexture(0, this.fontTexture);
        for (int c = 0; c < SerialTerminalBlockEntity.width * SerialTerminalBlockEntity.height; c++ ) {
            int cx = c % SerialTerminalBlockEntity.width;
            int cy = c / SerialTerminalBlockEntity.width;
            int cpx = cx * fontWidth;
            int cpy = cy * fontHeight;
            int ch = termMenu.data.get(c);
            int charX = ch % 16;
            int charY = ch / 16;
            int charFromX = charX * fontWidth;
            int charFromY = charY * fontHeight;
            this.blit(poseStack, k + cpx + 5, l + cpy + 5, charFromX, charFromY, fontWidth, fontHeight);
        }
    }

    @Override
    public void render(PoseStack poseStack, int i, int j, float f) {
        renderBackground(poseStack);
        super.render(poseStack, i, j, f);
        int x = this.leftPos;
        int y = this.topPos;
        assert minecraft != null;

        // Switch labels
        renderTooltip(poseStack, i, j);
    }

    @Override
    protected void renderLabels(PoseStack poseStack, int i, int j) {
        this.font.draw(poseStack, this.title, (float)this.titleLabelX, (float)this.titleLabelY, 16777215);
    }
}
