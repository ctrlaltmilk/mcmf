package com.github.masongulu.uxn.devices;

import com.github.masongulu.uxn.UXN;
import com.github.masongulu.uxn.UXNDeviceManager;

public abstract class Device implements IDevice, IUXNDeviceProvider {
    protected UXN uxn;
    protected int deviceNumber = 1;
    protected UXNDeviceManager deviceManager;
    public abstract void write(int address);
    public abstract void read(int address);
    public void attach(UXNDeviceManager manager) {
        deviceManager = manager;
        manager.setDevice(deviceNumber, this);
        uxn = deviceManager.uxn;
    }
    public void detach(UXNDeviceManager manager) {
        deviceManager = null;
    }
}
