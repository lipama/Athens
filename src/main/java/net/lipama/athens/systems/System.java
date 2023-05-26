package net.lipama.athens.systems;

public interface System {
    default String name() {
        return this.getClass().getSimpleName();
    }
}
