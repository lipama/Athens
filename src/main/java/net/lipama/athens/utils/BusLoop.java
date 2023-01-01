package net.lipama.athens.utils;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;

import net.minecraft.client.MinecraftClient;

import java.util.function.Consumer;

public class BusLoop {
    private static boolean SHOULD_RUN = true;
    public static void onTick(Consumer<MinecraftClient> func) {
        ClientTickEvents.START_CLIENT_TICK.register(func::accept);
    }

    public static void onPacketReceive() {
//        ClientPlayNetworking.registerGlobalReceiver(TutorialNetworkingConstants.HIGHLIGHT_PACKET_ID, (client, handler, buf, responseSender) -> {
//
//        });
    }
}
