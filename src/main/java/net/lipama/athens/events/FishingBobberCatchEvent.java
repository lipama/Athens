package net.lipama.athens.events;

public class FishingBobberCatchEvent extends TickEvent {
    private static final FishingBobberCatchEvent INSTANCE = new FishingBobberCatchEvent();
    public static FishingBobberCatchEvent get() { return INSTANCE; }
}
