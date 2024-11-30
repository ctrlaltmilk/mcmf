package com.github.masongulu.core.uxn.devices;

import com.github.masongulu.core.uxn.UXNBus;

public interface IDevice {
    void write(int address);
    void read(int address);
    void attach(UXNBus bus);
    void detach(UXNBus bus);
    String getLabel();
}
