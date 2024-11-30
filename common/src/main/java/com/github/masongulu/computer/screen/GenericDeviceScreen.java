package com.github.masongulu.computer.screen;

import com.github.masongulu.gui.ToggleSwitchButton;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

import static com.github.masongulu.ComputerMod.MOD_ID;
import static com.github.masongulu.gui.ToggleSwitchType.PIANO;

public class GenericDeviceScreen extends AbstractContainerScreen<GenericDeviceMenu> {
    private final ResourceLocation texture = new ResourceLocation(MOD_ID, "textures/gui/generic_device.png");
    private final GenericDeviceMenu deviceMenu;
    private final ToggleSwitchButton[] deviceSelectors = new ToggleSwitchButton[16];

    public GenericDeviceScreen(GenericDeviceMenu abstractContainerMenu, Inventory inventory, Component component) {
        super(abstractContainerMenu, inventory, component);
        deviceMenu = abstractContainerMenu;
        this.imageHeight = 107;
        this.inventoryLabelY = this.imageHeight - 94;
    }

    @Override
    protected void init() {
        super.init();
        int k = this.leftPos;
        int l = this.topPos;
        int bWidth = PIANO.w + PIANO.padX;
        int bHeight = PIANO.h + PIANO.padY;
        for (int i = 0; i < 16; i++ ) {
            assert minecraft != null;
            int finalI = i;
            int ix = i % 4;
            int iy = i / 4;
            int x = k + 20 + bWidth * ix;
            int y = l + 26 + bHeight * iy;
            deviceSelectors[i] = new ToggleSwitchButton(x, y, String.format("%d", i), button -> {
                assert minecraft.gameMode != null;
                minecraft.gameMode.handleInventoryButtonClick(this.menu.containerId, finalI);
            }, minecraft.font, PIANO, ToggleSwitchButton.LabelPosition.ON);
            addWidget(deviceSelectors[i]);
        }
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
        int deviceNumber = deviceMenu.data.get(0);
        for (int d = 0; d < 16; d++) {
            deviceSelectors[d].renderBg(poseStack, i, j, deviceNumber == d);
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
        int deviceNumber = deviceMenu.data.get(0);
        for (int d = 0; d < 16; d++) {
            deviceSelectors[d].render(poseStack, i, j, f, deviceNumber == d);
        }
        renderTooltip(poseStack, i, j);
    }

    @Override
    protected void renderLabels(PoseStack poseStack, int i, int j) {
        this.font.draw(poseStack, this.title, (float)this.titleLabelX, (float)this.titleLabelY, 16777215);
    }
}
