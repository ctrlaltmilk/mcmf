package com.github.masongulu.network;

import dev.architectury.networking.NetworkManager;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;

import static com.github.masongulu.ComputerMod.MOD_ID;

public class ModPackets {
    public static final ResourceLocation KEY_PACKET_ID = new ResourceLocation(MOD_ID, "key_packet");

    public static void register() {
        NetworkManager.registerReceiver(NetworkManager.Side.C2S, KEY_PACKET_ID, (buf, context) -> {
            Player player = context.getPlayer();
            // Logic
        });
    }
}
