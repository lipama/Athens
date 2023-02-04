package net.lipama.athens.events;

import net.lipama.athens.Athens;

public class TickEvent {
    public static class Pre {
        private static void onCall() {

        }
        public static void call() { onCall(); }
    }

    public static class Post {
        private static void onCall() {
            Athens.onPostTick();
            Athens.MODULES.crystalAura.onPostTick();
            Athens.MODULES.autoFish.onPostTick();
            Athens.MODULES.boatFly.onPostTick();
        }
        public static void call() { onCall(); }
    }
}
