package com.github.masongulu.core.uxn;

import java.util.ArrayList;
import java.util.List;

public class UXNExecutor extends Thread {
    private final List<UXN> UXNs = new ArrayList<>();

    public int count() {
        return UXNs.size();
    }

    public void addUXN(UXN uxn) {
        UXNs.add(uxn);
    }

    public void removeUXN(UXN uxn) {
        UXNs.remove(uxn);
    }

    public void tick() {
        for (UXN uxn : UXNs) {
            uxn.runLimited(1024);
        }
    }

    @Override
    public void run() {
        do {
            tick();
        } while (!UXNs.isEmpty());
    }
}
