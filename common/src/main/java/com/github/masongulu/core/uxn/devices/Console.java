package com.github.masongulu.core.uxn.devices;


import com.github.masongulu.core.uxn.UXNBus;
import com.github.masongulu.core.uxn.UXNEvent;

import java.io.UnsupportedEncodingException;

public class Console extends Device {

    public Console() {
        this.deviceId = 1;
    }

    public void handleKey(char ch) {
        bus.uxn.queueEvent(new KeyEvent(ch));
    }

    public void queueArgs(String[] strs) {
        if (strs.length == 0) {return;}
        for (int i = 0; i < strs.length; i++) {
            String str = strs[i];
            try {
                byte[] bytes = str.getBytes("IBM437");
                for (byte byt : bytes) {
                    bus.uxn.queueEvent(new KeyEvent((char) byt, (byte) 0x02, (byte) 0x01));
                }
                if (i == strs.length - 1) {
                    bus.uxn.queueEvent(new KeyEvent(' ', (byte) 0x04, (byte) 0x01));
                } else {
                    bus.uxn.queueEvent(new KeyEvent(' ', (byte) 0x03, (byte) 0x01));
                }
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void write(int address) {
        byte data = bus.readDev(address);
        int port = address & 0x0F;
        switch (port) {
            case 0x08 -> {
                System.out.write((char) data);
            }
            case 0x09 -> {
                System.err.write((char) data);
            }
        }
    }

    @Override
    public void read(int address) {
        //all values should be set when the KeyEvent happens
    }
}

class KeyEvent implements UXNEvent {
    char ch;
    byte type;
    byte device;

    public KeyEvent(char ch, byte type, byte device) {
        this.ch = ch;
        this.type = type;
        this.device = device;
    }
    public KeyEvent(char ch) {
        this.ch = ch;
        this.type = 0x01; //stdin key
        this.device = 0x10; // this is where the device is on varvara
    }

    @Override
    public void handle(UXNBus bus) {
        bus.uxn.pc = (bus.readDev(0x10) << 8) | bus.readDev(0x11); //get the vector for PC at the time the event is handled
        bus.writeDev(device + 0x02, (byte) ch);
        bus.writeDev(device + 0x07, type);
    }

    @Override
    public String toString() {
        return "key: '%s' type: %02X".formatted(ch,type);
    }
}