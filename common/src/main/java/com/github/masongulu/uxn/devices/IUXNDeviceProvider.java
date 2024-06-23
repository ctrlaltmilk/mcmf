package com.github.masongulu.uxn.devices;

import com.github.masongulu.uxn.UXNDeviceManager;

public interface IUXNDeviceProvider {
    void attach(UXNDeviceManager manager);
    void detach(UXNDeviceManager manager);
}
