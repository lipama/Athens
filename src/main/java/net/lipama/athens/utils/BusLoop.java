package net.lipama.athens.utils;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;

import net.minecraft.client.MinecraftClient;

import java.util.function.Consumer;

public class BusLoop {
    private static boolean SHOULD_RUN = true;
    public static void subscribe(Consumer<MinecraftClient> func) {
        ClientTickEvents.START_CLIENT_TICK.register(func::accept);
    }
}
