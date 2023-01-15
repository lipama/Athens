package net.lipama.athens.events;

public class FishingBobberCatch extends TickEvent {
    private static final FishingBobberCatch INSTANCE = new FishingBobberCatch();

    public static FishingBobberCatch get() {
        return INSTANCE;
    }
}
