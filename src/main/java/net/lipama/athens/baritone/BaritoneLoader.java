package net.lipama.athens.baritone;

import net.lipama.athens.baritone.internal.*;
import net.lipama.athens.*;

public enum BaritoneLoader {
    INSTANCE;

    public void load() {
        try {
            internalLoad();
            AthensClient.LOG.info("Baritone loaded");
        } catch(NoClassDefFoundError e) {
            AthensClient.LOG.error("BaritoneLoader.INSTANCE.load(); called when no baritone present");
            Athens.useBaritone = false;
        }
    }

    private void internalLoad() {
        Baritone baritone = new Baritone();
        Baritone.BARITONE_LOADED = true;
        BaritoneMain.Main(baritone);
    }
}
