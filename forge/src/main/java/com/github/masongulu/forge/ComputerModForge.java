package com.github.masongulu.forge;

import dev.architectury.platform.forge.EventBuses;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import com.github.masongulu.ComputerMod;

@Mod(ComputerMod.MOD_ID)
public final class ComputerModForge {
    public ComputerModForge() {
        // Submit our event bus to let Architectury API register our content on the right time.
        EventBuses.registerModEventBus(ComputerMod.MOD_ID, FMLJavaModLoadingContext.get().getModEventBus());

        // Run our common setup.
        ComputerMod.init();
    }
}
