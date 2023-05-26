package net.lipama.athens.events.system;

public class PreInitEvent {
    private static final PreInitEvent INSTANCE = new PreInitEvent();
    public static PreInitEvent get() { return INSTANCE; }
}
