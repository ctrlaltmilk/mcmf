package com.github.masongulu.uxn.devices;

import net.minecraft.core.Direction;

public interface IDeviceProvider {
    IDevice getDevice(Direction attachSide);
}
