package com.github.masongulu.gui;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarratableEntry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.github.masongulu.gui.ToggleSwitchType.PIANO;

public class PianoButtonGroup<T> implements Button.OnPress {
    private final int x;
    private final int y;
    private final int k;
    private final int l;
    private int selected = 0;
    private PianoButtonCallback<T> callback;
    private ArrayList<T> values;
    private ArrayList<String> labels;
    private int bWide = 4;
    private int bTall = 4;
    private int bWidth = PIANO.w + PIANO.padX;
    private int bHeight = PIANO.h + PIANO.padY;
    private boolean built = false;

    private final ArrayList<PianoButtonEntry<T>> buttons = new ArrayList<>();
    private final Map<Button,PianoButtonEntry<T>> buttonMap = new HashMap<>();

    public PianoButtonGroup(int x, int y, int k, int l) {
        this.x = x;
        this.y = y;
        this.k = k;
        this.l = l;
    }

    private void checkBuilt() {
        if (built) {
            throw new RuntimeException("Attempt to modify finalized PianoButtonGroup!");
        }
    }

    public PianoButtonGroup<T> setValues(ArrayList<T> values) {
        checkBuilt();
        this.values = values;
        return this;
    }

    public PianoButtonGroup<T> setLabels(ArrayList<String> labels) {
        checkBuilt();
        this.labels = labels;
        return this;
    }

    public PianoButtonGroup<T> setSize(int buttonWidth, int buttonHeight) {
        checkBuilt();
        this.bWidth = buttonWidth;
        this.bHeight = buttonHeight;
        return this;
    }

    public PianoButtonGroup<T> setDims(int buttonsWide, int buttonsTall) {
        checkBuilt();
        this.bWide = buttonsWide;
        this.bTall = buttonsTall;
        return this;
    }

    public PianoButtonGroup<T> setCallback(PianoButtonCallback<T> callback) {
        this.callback = callback;
        return this;
    }

    public PianoButtonGroup<T> setSelected(int i) {
        selected = i;
        return this;
    }

    private void generateLabels() {
        checkBuilt();
        if (this.labels == null) {
            labels = new ArrayList<>();
            for (int i = 0; i < bWide * bTall; i++) {
                labels.add(String.format("%s", values.get(i)));
            }
        }
    }

    public PianoButtonGroup<T> build(ModifiableScreen s, Minecraft minecraft) {
        generateLabels();
        built = true;
        for (int i = 0; i < bWide * bTall; i++ ) {
            int ix = i % bWide;
            int iy = i / bWide;
            int ax = k + x + bWidth * ix;
            int ay = l + y + bHeight * iy;
            ToggleSwitchButton b = new ToggleSwitchButton(ax, ay, labels.get(i), this,
                    minecraft.font, PIANO, ToggleSwitchButton.LabelPosition.ON);
            PianoButtonEntry<T> entry = new PianoButtonEntry<T>(b, values.get(i));
            buttons.add(entry);
            buttonMap.put(b, entry);
            s.addWidget(b);
        }
        return this;
    }

    public static PianoButtonGroup<Integer> deviceButtons(int x, int y, int k, int l) {
        ArrayList<Integer> values = new ArrayList<>();
        for (int i = 0; i < 16; i++) {
            values.add(i);
        }
        return new PianoButtonGroup<Integer>(x,y,k,l).setValues(values);
    }

    @Override
    public void onPress(Button button) {
        if (callback != null) {
            PianoButtonEntry<T> b = buttonMap.get(button);
            callback.callback(b.value);
        }
    }

    public void renderBg(PoseStack poseStack, int i, int j) {
        for (int d = 0; d < bWide * bTall; d++) {
            buttons.get(d).button.renderBg(poseStack, i, j, selected == d);
        }
    }

    public void render(PoseStack poseStack, int i, int j, float f) {
        for (int d = 0; d < bWide * bTall; d++) {
            buttons.get(d).button.render(poseStack, i, j, f, selected == d);
        }
    }

    static class PianoButtonEntry<T> {
        ToggleSwitchButton button;
        T value;
        public PianoButtonEntry(ToggleSwitchButton button, T v) {
            this.value = v;
            this.button = button;
        }
    }

    public interface PianoButtonCallback<T> {
        void callback(T value);
    }

    public interface ModifiableScreen {
        <T extends GuiEventListener & NarratableEntry> T addWidget(T guiEventListener);
    }

}
