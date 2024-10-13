package com.github.masongulu.uxn.devices;

import com.github.masongulu.uxn.UXN;
import com.github.masongulu.uxn.UXNBus;

public interface IDevice {
    void write(int address);
    void read(int address);
    void attach(UXNBus bus);
    void detach(UXNBus bus);
}
