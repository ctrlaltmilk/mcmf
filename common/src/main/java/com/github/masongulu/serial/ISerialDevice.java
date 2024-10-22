package com.github.masongulu.serial;

public interface ISerialDevice {
    void attach(SerialBus bus);
    void detach(SerialBus bus);

    /**
     * When a device writes to the SerialBus, SerialBus will call this on the other connected device
     */
    void write(char ch);
}
