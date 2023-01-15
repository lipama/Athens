package net.lipama.athens.modules;

import net.lipama.athens.AthensClient;

public class FullBright extends Module {
    public static boolean active = false;

    public FullBright() {
        super("FullBright");
        this.height = 55;
    }
    @Override
    public void onEnable() {
        AthensClient.LOG.info("FullBright Enabled");
        FullBright.active = true;
    }

    @Override
    public void onDisable() {
        AthensClient.LOG.info("FullBright Disabled");
        FullBright.active = false;
    }
}
