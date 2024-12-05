package com.github.masongulu;

import com.github.masongulu.item.ModItems;
import com.github.masongulu.network.ModPackets;
import com.github.masongulu.serial.ISerialPeer;
import com.github.masongulu.serial.block.entity.SerialPeerBlockEntity;
import com.github.masongulu.core.uxn.UXNBus;
import com.github.masongulu.core.uxn.UXNExecutor;
import com.github.masongulu.core.uxn.devices.Console;
import dev.architectury.event.EventResult;
import dev.architectury.event.events.common.BlockEvent;
import dev.architectury.event.events.common.TickEvent;
import dev.architectury.platform.Platform;
import net.fabricmc.api.EnvType;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static com.github.masongulu.ModBlocks.DEVICE_CABLE;
import static com.github.masongulu.ModBlocks.SERIAL_CABLE;

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
            } else if (state.is(SERIAL_CABLE)) {
                ISerialPeer sbus = SerialPeerBlockEntity.findSerialDevice(level, pos);
                if (sbus != null) {
                    SerialPeerBlockEntity.refresh(sbus, level, sbus.getPos(), pos);
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
            } else if (state.is(SERIAL_CABLE)) {
                ISerialPeer sbus = SerialPeerBlockEntity.findSerialDevice(level, pos);
                if (sbus != null) {
                    SerialPeerBlockEntity.refresh(sbus, level, pos);
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
        ModPackets.register();
        registerEvents();
    }
}
