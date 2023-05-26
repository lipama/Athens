package net.lipama.athens.systems;

import net.lipama.athens.events.ShutdownEvent;

public interface System extends
        ShutdownEvent.Event
{
    default String name() {
        return this.getClass().getSimpleName();
    }
}
