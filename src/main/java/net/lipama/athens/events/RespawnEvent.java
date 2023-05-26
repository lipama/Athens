package net.lipama.athens.events;

public class RespawnEvent {
    private static final RespawnEvent INSTANCE = new RespawnEvent();
    public static RespawnEvent get() { return INSTANCE; }
}
