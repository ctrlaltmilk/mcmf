package com.github.masongulu.uxn;

import com.github.masongulu.ComputerMod;
import com.github.masongulu.uxn.devices.Device;
import com.github.masongulu.uxn.devices.IDevice;

public class UXNDeviceManager {
    public UXN uxn;
    private final IDevice[] devices = new IDevice[16];
    private final byte[] deviceMemory = new byte[256];
    private boolean executing = false;

    public void setUxn(UXN uxn) {
        this.uxn = uxn;
    }

    public void start() {
        if (executing) {
            return;
        }
        uxn.queueVector(0x100);
        ComputerMod.UXN_EXECUTOR.addUXN(uxn);
        executing = true;
    }

    public void stop() {
        ComputerMod.UXN_EXECUTOR.removeUXN(uxn);
        executing = false;
    }

    public void setDevice(int index, IDevice device) {
        devices[index] = device;
    }

    public void deo(int address, byte data) {
        deviceMemory[address] = data;
        int dev = (address & 0xF0) >> 4;
        IDevice device = this.devices[dev];
        if (device != null) {
            device.write(address);
        }
    }

    public void writeDev(int address, byte data) {
        address %= 256;
        deviceMemory[address] = data;
    }

    public byte readDev(int address) {
        address %= 256;
        return deviceMemory[address];
    }

    public byte dei(int address) {
        int dev = (address & 0xF0) >> 4;
        IDevice device = this.devices[dev];
        if (device != null) {
            device.read(address);
        }
        return deviceMemory[address];
    }
}
