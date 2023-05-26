package net.lipama.athens.events;

public class DeathEvent {
    private static final DeathEvent INSTANCE = new DeathEvent();
    public static DeathEvent get() { return INSTANCE; }
}
