package net.lipama.athens.events;

import net.lipama.athens.Athens;

public class ShutdownEvent {
    private static void onCall() {
        Athens.onShutdown();
        Athens.MODULES.onShutdown();
    }
    public static void call() { onCall(); }
}