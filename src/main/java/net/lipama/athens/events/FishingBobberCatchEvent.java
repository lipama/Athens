package net.lipama.athens.events;

import net.lipama.athens.Athens;

public class FishingBobberCatchEvent extends TickEvent {
    private static void onCall() {
        Athens.MODULES.autoFish.onBobberCatch();
    }
    public static void call() { onCall(); }
}
