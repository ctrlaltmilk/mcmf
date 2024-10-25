package com.github.masongulu.serial.block.entity;

import com.github.masongulu.computer.block.entity.GenericDeviceBlockEntity;
import com.github.masongulu.serial.ISerialPeer;
import com.github.masongulu.core.uxn.UXNBus;
import com.github.masongulu.core.uxn.UXNEvent;
import com.github.masongulu.core.uxn.devices.IDevice;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.level.block.state.BlockState;

import static com.github.masongulu.ModBlockEntities.SERIAL_DEVICE_BLOCK_ENTITY;

public class SerialDeviceBlockEntity extends GenericDeviceBlockEntity implements IDevice, ISerialPeer {
    private ISerialPeer peer;
    private UXNBus bus;
    protected int deviceNumber = 1;
    private boolean conflicting = false;
    public SerialDeviceBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(SERIAL_DEVICE_BLOCK_ENTITY.get(), blockPos, blockState);
    }

    @Override
    public IDevice getDevice(Direction attachSide) {
        return this;
    }

    @Override
    protected Component getDefaultName() {
        return new TextComponent("Serial Device");
    }

    @Override
    public void write(int address) {
        int data = bus.readDev(address);
        int port = address & 0x0F;
        switch (port) {
            case 0x08 -> {
                if (this.peer != null)
                    this.peer.write((char) data);
            }
            case 0x09 -> {
                // TODO fix this
                System.err.write((char) data);
            }
        }
    }

    @Override
    public void read(int address) {

    }

    @Override
    public void attach(UXNBus bus) {
        this.bus = bus;
        this.bus.setDevice(deviceNumber, this);
        SerialPeerBlockEntity.refresh(this, this.getLevel(), this.getBlockPos());
    }

    @Override
    public void detach(UXNBus bus) {
        bus.deleteDevice(deviceNumber);
        this.bus = null;
    }

    @Override
    public void attach(ISerialPeer bus) {
        peer = bus;
    }

    @Override
    public void detach(ISerialPeer bus) {
        peer = null;
    }

    @Override
    public void write(char ch) {
        if (bus != null) {
            bus.queueEvent(new KeyEvent(ch, (byte) 0x01, (byte) (deviceNumber << 4)));
        }
    }

    @Override
    public void setConflicting(boolean b) {
        conflicting = b;
    }

    @Override
    public ISerialPeer getPeer() {
        return peer;
    }

    public BlockPos getPos() {
        return this.getBlockPos();
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
        bus.uxn.pc = (bus.readDev(device) << 8) | bus.readDev(device+1); //get the vector for PC at the time the event is handled
        bus.writeDev(device + 0x02, (byte) ch);
        bus.writeDev(device + 0x07, type);
    }

    @Override
    public String toString() {
        return "key: '%s' type: %02X".formatted(ch,type);
    }
}