package com.github.masongulu.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerListener;
import net.minecraft.world.item.ItemStack;

import static com.github.masongulu.ComputerMod.MOD_ID;

public class ComputerScreen extends AbstractContainerScreen<ComputerMenu> {
    public Button powerButton;
    private final ResourceLocation texture = new ResourceLocation(MOD_ID, "textures/gui/computer.png");
    private final ComputerMenu computerMenu;
    private final Component onLabel = new TextComponent("On");
    private final Component offLabel = new TextComponent("Off");

    public ComputerScreen(ComputerMenu abstractContainerMenu, Inventory inventory, Component component) {
        super(abstractContainerMenu, inventory, component);
        computerMenu = abstractContainerMenu;
    }

    @Override
    protected void init() {
        super.init();
        int k = this.leftPos;
        int l = this.topPos;
        powerButton = new Button(k + 100, l + 10, 69, 20, new TextComponent("Power"), button -> {
            assert minecraft != null;
            assert minecraft.gameMode != null;
            minecraft.gameMode.handleInventoryButtonClick(this.menu.containerId, 0);
        });

        addWidget(powerButton);
    }

    @Override
    protected void renderBg(PoseStack poseStack, float f, int i, int j) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, this.texture);
        int k = this.leftPos;
        int l = this.topPos;
        this.blit(poseStack, k, l, 0, 0, this.imageWidth, this.imageHeight);
    }

    @Override
    public void render(PoseStack poseStack, int i, int j, float f) {
        renderBackground(poseStack);
        super.render(poseStack, i, j, f);
        powerButton.setMessage((computerMenu.isRunning() ? onLabel : offLabel));
        powerButton.render(poseStack, i, j, f);
        assert minecraft != null;
        drawString(poseStack, minecraft.font, computerMenu.getWst(), this.leftPos + 8, this.topPos + 45, 16777215);
        drawString(poseStack, minecraft.font, computerMenu.getRst(), this.leftPos + 8, this.topPos + 61, 16777215);
        drawString(poseStack, minecraft.font, String.format("PC %04x", computerMenu.getPc()),
                this.leftPos + 8, this.topPos + 29, 16777215);
        renderTooltip(poseStack, i, j);
    }
}
