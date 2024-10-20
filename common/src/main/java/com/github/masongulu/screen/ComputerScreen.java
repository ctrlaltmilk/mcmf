package com.github.masongulu.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

import static com.github.masongulu.ComputerMod.MOD_ID;

public class ComputerScreen extends AbstractContainerScreen<ComputerMenu> {
    public Button powerButton;
    public Button pauseButton;
    public Button stepButton;
    private final ResourceLocation texture = new ResourceLocation(MOD_ID, "textures/gui/computer.png");
    private final ComputerMenu computerMenu;
    private final int switchTX = 176;
    private final int powerSwitchX = 25;
    private final int pauseSwitchX = 60;
    private final int stepButtonX = 90;
    private final int switchY = 89;
    private final int labelY = 82;
    private final int switchOffTY = 30;
    private final int switchOnTY = 7;
    private final int switchH = 21;
    private final int switchW = 17;
    private final int switchHW = switchW / 2;

    public ComputerScreen(ComputerMenu abstractContainerMenu, Inventory inventory, Component component) {
        super(abstractContainerMenu, inventory, component);
        computerMenu = abstractContainerMenu;
        this.imageHeight = 206;
        this.inventoryLabelY = this.imageHeight - 94;
    }

    @Override
    protected void init() {
        super.init();
        int k = this.leftPos;
        int l = this.topPos;
        powerButton = new Button(k + powerSwitchX - switchHW, l + switchY, switchW, switchH, new TextComponent("Power"), button -> {
            assert minecraft != null;
            assert minecraft.gameMode != null;
            minecraft.gameMode.handleInventoryButtonClick(this.menu.containerId, 0);
        });
        pauseButton = new Button(k + pauseSwitchX - switchHW, l + switchY, switchW, switchH, new TextComponent("Pause"), button -> {
            assert minecraft != null;
            assert minecraft.gameMode != null;
            minecraft.gameMode.handleInventoryButtonClick(this.menu.containerId, 1);
        });
        stepButton = new Button(k + stepButtonX, l + switchY, switchW, switchH, new TextComponent("Step"), button -> {
            assert minecraft != null;
            assert minecraft.gameMode != null;
            minecraft.gameMode.handleInventoryButtonClick(this.menu.containerId, 2);
        });

        addWidget(powerButton);
        addWidget(pauseButton);
        addWidget(stepButton);
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
        int powerTextureY = computerMenu.isRunning() ? switchOnTY : switchOffTY;
        this.blit(poseStack, k + powerSwitchX - switchHW, l + switchY, switchTX, powerTextureY, switchW, switchH);
        int pauseTextureY = computerMenu.isPaused() ? switchOnTY : switchOffTY;
        this.blit(poseStack, k + pauseSwitchX - switchHW, l + switchY, switchTX, pauseTextureY, switchW, switchH);
    }

    @Override
    public void render(PoseStack poseStack, int i, int j, float f) {
        renderBackground(poseStack);
        super.render(poseStack, i, j, f);
        stepButton.render(poseStack, i, j, f);
        int x = this.leftPos;
        int y = this.topPos;
        assert minecraft != null;
        drawString(poseStack, minecraft.font, computerMenu.getWst(), x + 8, y + 43, 16777215);
        drawString(poseStack, minecraft.font, computerMenu.getRst(), x + 8, y + 61, 16777215);
        drawString(poseStack, minecraft.font, String.format("Vectors %d", computerMenu.getVectors()),
                x + 69, y + 25, 16777215);
        drawString(poseStack, minecraft.font, String.format("PC %04x", computerMenu.getPc()),
                x + 8, y + 26, 16777215);

        // Switch labels
        drawCenteredString(poseStack, minecraft.font, "Power", x + powerSwitchX, y + labelY, 16777215);
        drawCenteredString(poseStack, minecraft.font, "Pause", x + pauseSwitchX, y + labelY, 16777215);
        renderTooltip(poseStack, i, j);
    }

    @Override
    protected void renderLabels(PoseStack poseStack, int i, int j) {
        this.font.draw(poseStack, this.title, (float)this.titleLabelX, (float)this.titleLabelY, 16777215);
        this.font.draw(poseStack, this.playerInventoryTitle, (float)this.inventoryLabelX, (float)this.inventoryLabelY, 16777215);
    }
}
