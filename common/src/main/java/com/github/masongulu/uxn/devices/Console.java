package com.github.masongulu.uxn.devices;

import com.github.masongulu.uxn.UXN;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;

public class Console extends Device {
    private final Queue<Character> buffer = new LinkedList<>();

    public void handle(UXN u, char ch) {
        u.queueVector((deviceManager.readDev(0x10) << 8) | deviceManager.readDev(0x11));
        buffer.add(ch);
    }

    @Override
    public void write(int address) {
        byte data = deviceManager.readDev(address);
        int port = address & 0x0F;
        if (port == 0x08) { // write
            System.out.write((char) data);
        }
    }

    @Override
    public void read(int address) {
        int port = address & 0x0F;
        if (port == 0x02) {
            char ch = buffer.remove();
            deviceManager.writeDev(address, (byte) ch);
        }
    }
}
