package com.github.masongulu.uxn;

public interface UXNEvent {
    /**
     * A UXN event. when this function is called implementations should set the PC of the uxn and relevant device bytes
     * @param bus the UXN bus handling this event
     */
    void handle(UXNBus bus);
}
