package net.lipama.athens.baritone;

import net.lipama.athens.baritone.internal.*;

import java.util.Optional;

public class BaritoneCommunicator {
    public static Optional<Baritone> get() {
        if(!Baritone.BARITONE_LOADED) return Optional.empty();
        try {
            return Optional.of(BaritoneMain.self.baritone);
        } catch(NoClassDefFoundError e) {
            return Optional.empty();
        }
    }
}
