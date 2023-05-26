package net.lipama.athens.events;

import java.util.ArrayList;

public class DeathEvent {
    public interface Event {
        void onDeath();
    }
    private static final ArrayList<DeathEvent.Event> EVENTS = new ArrayList<>();
    public static void call() {
        for(DeathEvent.Event event : EVENTS) {
            event.onDeath();
        }
    }
    public static void subscribe(DeathEvent.Event event) {
        EVENTS.add(event);
    }
}
