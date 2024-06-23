package com.github.masongulu;

import dev.architectury.event.events.common.CommandRegistrationEvent;
import net.minecraft.network.chat.TextComponent;

import static net.minecraft.commands.Commands.literal;

public class Commands {
    public static void register() {

        CommandRegistrationEvent.EVENT.register((dispatcher, dedicated) -> {dispatcher.register(
                literal("uxn_computers").executes(context -> {
                    context.getSource().sendSuccess(
                            new TextComponent(String.format("There are currently %d active computers.",
                                    ComputerMod.UXN_EXECUTOR.count())), false);
                    return 0;
                })
        );});
    }
}
