package net.lipama.athens.events;

import java.util.ArrayList;

public class ShutdownEvent {
    public interface Event {
        void onShutdown();
    }
    private static final ArrayList<ShutdownEvent.Event> EVENTS = new ArrayList<>();
    public static void call() {
        for(Event event : EVENTS) {
            event.onShutdown();
        }
    }
    public static void subscribe(Event event) {
        EVENTS.add(event);
    }
}
