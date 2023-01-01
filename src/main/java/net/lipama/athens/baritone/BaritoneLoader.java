package net.lipama.athens.baritone;

import net.lipama.athens.*;

public enum BaritoneLoader {
    INSTANCE;

    public void load() {
        try {
            internal_load();
            AthensClient.LOG.info("Baritone loaded");
        } catch(NoClassDefFoundError e) {
            AthensClient.LOG.error("BaritoneLoader.INSTANCE.load(); called when no baritone present");
            Athens.useBaritone = false;
        }
    }

    private void internal_load() {
        Baritone baritone = new Baritone();
    }
}
