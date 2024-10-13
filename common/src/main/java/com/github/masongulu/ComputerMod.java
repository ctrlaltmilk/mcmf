package com.github.masongulu;

import com.github.masongulu.block.ModBlocks;
import com.github.masongulu.block.entity.ModBlockEntities;
import com.github.masongulu.item.ModItems;
import com.github.masongulu.screen.ModMenus;
import com.github.masongulu.uxn.UXN;
import com.github.masongulu.uxn.UXNBus;
import com.github.masongulu.uxn.UXNExecutor;
import com.github.masongulu.uxn.devices.Console;
import dev.architectury.event.EventResult;
import dev.architectury.event.events.common.BlockEvent;
import dev.architectury.event.events.common.TickEvent;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static com.github.masongulu.block.ModBlocks.CABLE_BLOCK;
import static com.github.masongulu.block.ModBlocks.DEVICE_CABLE;

public final class ComputerMod {
    public static final String MOD_ID = "mcmf";

    public static UXNExecutor UXN_EXECUTOR = new UXNExecutor();


    public static void test(UXNBus manager) {
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

    public static void registerEvents() {
        BlockEvent.BREAK.register(((level, pos, state, player, xp) -> {
            if (state.is(DEVICE_CABLE)) {
                UXNBus bus = UXNBus.findBus(level, pos);
                if (bus != null) {
                    bus.refresh(pos);
                }
            }
            return EventResult.pass();
        }));
        BlockEvent.PLACE.register(((level, pos, state, placer) -> {
            if (state.is(DEVICE_CABLE)) {
                UXNBus bus = UXNBus.findBus(level, pos);
                if (bus != null) {
                    bus.refresh(level, pos);
                }
            }
            return EventResult.pass();
        }));
        TickEvent.SERVER_POST.register((level) -> UXN_EXECUTOR.tick());
    }

    public static void init() {
        // Write common init code here.;
        ModBlocks.register();
        ModItems.register();
        ModBlockEntities.register();
        Commands.register();
        ModMenus.register();
        registerEvents();
    }
}
