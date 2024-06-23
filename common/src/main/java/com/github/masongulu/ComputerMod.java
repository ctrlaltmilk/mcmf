package com.github.masongulu;

import com.github.masongulu.block.ModBlocks;
import com.github.masongulu.block.entity.ModBlockEntities;
import com.github.masongulu.item.ModItems;
import com.github.masongulu.screen.ModMenus;
import com.github.masongulu.uxn.UXN;
import com.github.masongulu.uxn.UXNDeviceManager;
import com.github.masongulu.uxn.UXNExecutor;
import com.github.masongulu.uxn.devices.Console;
import dev.architectury.event.events.common.TickEvent;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public final class ComputerMod {
    public static final String MOD_ID = "mcmf";

    public static UXNExecutor UXN_EXECUTOR = new UXNExecutor();


    public static void test(UXNDeviceManager manager) {
        byte[] rom;
        try {
            System.out.println("Working Directory = " + System.getProperty("user.dir"));
            rom = Files.readAllBytes(Paths.get("rstest.rom"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Console console = new Console();
        console.attach(manager);
        System.arraycopy(rom, 0, manager.uxn.memory.getData(), 0x100, rom.length);
    }

    public static void init() {
        // Write common init code here.;
        ModBlocks.register();
        ModItems.register();
        ModBlockEntities.register();
        Commands.register();
        ModMenus.register();
        TickEvent.SERVER_POST.register((level) -> UXN_EXECUTOR.tick());
    }
}
