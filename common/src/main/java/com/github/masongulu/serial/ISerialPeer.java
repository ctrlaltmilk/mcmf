package com.github.masongulu.serial;

import net.minecraft.core.BlockPos;

public interface ISerialPeer {
    void attach(ISerialPeer device);
    void detach(ISerialPeer device);

    void write(char ch);

    void setConflicting(boolean b);

    ISerialPeer getPeer();
    BlockPos getPos();
}
