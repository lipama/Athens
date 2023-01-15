package net.lipama.athens.events;

public class ShutdownEvent extends TickEvent {
    private static final ShutdownEvent INSTANCE = new ShutdownEvent();

    public static ShutdownEvent get() {
        return INSTANCE;
    }
}
