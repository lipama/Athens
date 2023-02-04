package net.lipama.athens.modules.modules;

import net.lipama.athens.AthensClient;
import net.lipama.athens.modules.Module;

public class FullBright extends Module {
    private static boolean active = false;
    public FullBright() {
        super("FullBright");
        this.height = 55;
    }
    @Override
    public void onEnable() {
        AthensClient.LOG.info("FullBright Enabled");
        FullBright.active = true;
        FullBright.update();
    }

    @Override
    public void onDisable() {
        AthensClient.LOG.info("FullBright Disabled");
        FullBright.active = false;
        FullBright.update();
    }

    private static void update(){
        if(active) AthensClient.MC.options.getGamma().setValue(69420.0);
        else AthensClient.MC.options.getGamma().setValue(1.0);
    }

    public static void tickFullBright() { update(); }
}
