package net.lipama.athens.events.system;

public class PostInitEvent {
    private static final PostInitEvent INSTANCE = new PostInitEvent();
    public static PostInitEvent get() { return INSTANCE; }
}
