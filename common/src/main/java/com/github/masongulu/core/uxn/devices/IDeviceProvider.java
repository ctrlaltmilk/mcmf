package com.github.masongulu.core.uxn.devices;

import net.minecraft.core.Direction;

public interface IDeviceProvider {
    IDevice getDevice(Direction attachSide);
}
