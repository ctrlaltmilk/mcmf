package com.github.masongulu.uxn.devices;

public interface IDevice {
    void write(int address);
    void read(int address);
}
