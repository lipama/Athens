package net.lipama.athens.events;

import java.util.ArrayList;

public class FishingBobberCatchEvent extends TickEvent {
    public interface Event {
        void onBobberCatch();
    }
    private static final ArrayList<FishingBobberCatchEvent.Event> EVENTS = new ArrayList<>();
    public static void call() {
        for(FishingBobberCatchEvent.Event event : EVENTS) {
            event.onBobberCatch();
        }
    }
    public static void subscribe(FishingBobberCatchEvent.Event event) {
        EVENTS.add(event);
    }
}
