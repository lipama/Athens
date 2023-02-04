package net.lipama.athens.events;

import net.lipama.athens.Athens;

public class RespawnEvent {
    private static void onCall() {
        Athens.MODULES.flight.onPlayerRespawn();
    }
    public static void call() { onCall(); }
}
