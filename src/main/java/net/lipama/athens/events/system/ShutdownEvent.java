package net.lipama.athens.events.system;

public class ShutdownEvent {
    private static final ShutdownEvent INSTANCE = new ShutdownEvent();
    public static ShutdownEvent get() { return INSTANCE; }
}
