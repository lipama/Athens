package net.lipama.athens.events;

import net.lipama.athens.Athens;

public class DeathEvent {
    private static void onCall() {
        Athens.MODULES.autoRespawn.onDeath();
    }
    public static void call() { onCall(); }
}
