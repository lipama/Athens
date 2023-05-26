package net.lipama.athens.events;

import java.util.ArrayList;

public class RespawnEvent {
    public interface Event {
        void onPlayerRespawn();
    }
    private static final ArrayList<RespawnEvent.Event> EVENTS = new ArrayList<>();
    public static void call() {
        for(RespawnEvent.Event event : EVENTS) {
            event.onPlayerRespawn();
        }
    }
    public static void subscribe(RespawnEvent.Event event) {
        EVENTS.add(event);
    }

}
