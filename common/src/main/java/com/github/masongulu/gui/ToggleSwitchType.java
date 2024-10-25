package com.github.masongulu.gui;

public enum ToggleSwitchType {
    TOGGLE(0,21,0,17, 21);
    public final int switchTX;
    public final int switchH;
    public final int switchW;
    public final int switchHH;
    public final int switchHW;
    public final int switchOffTY;
    public final int switchOnTY;

    ToggleSwitchType(int switchTX, int switchOffTY, int switchOnTY, int switchW, int switchH) {
        this.switchTX = switchTX;
        this.switchH = switchH;
        this.switchW = switchW;
        this.switchHH = switchH / 2;
        this.switchHW = switchW / 2;
        this.switchOffTY = switchOffTY;
        this.switchOnTY = switchOnTY;
    }
}
