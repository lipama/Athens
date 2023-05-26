package net.lipama.athens.events;

import net.lipama.athens.Athens;

import java.util.ArrayList;

@SuppressWarnings("unused")
public class TickEvent {
    public static class Pre {
        public interface Event {
            void onPreTick();
        }
        private static final ArrayList<TickEvent.Pre.Event> EVENTS = new ArrayList<>();
        public static void call() {
            for(TickEvent.Pre.Event event : EVENTS) {
                event.onPreTick();
            }
        }
        public static void subscribe(TickEvent.Pre.Event event) {
            EVENTS.add(event);
        }
    }

    public static class Post {

        public interface Event {
            void onPostTick();
        }
        private static final ArrayList<TickEvent.Post.Event> EVENTS = new ArrayList<>();
        public static void call() {
            Athens.onPostTick();
            for(TickEvent.Post.Event event : EVENTS) {
                event.onPostTick();
            }
        }
        public static void subscribe(TickEvent.Post.Event event) {
            EVENTS.add(event);
        }
    }
}
