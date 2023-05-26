package net.lipama.athens.systems.modules.modules;

import net.lipama.athens.Athens;
import net.lipama.athens.systems.modules.Module;

public class FullBright extends Module {
    private static boolean active = false;
    public FullBright() {
        super("FullBright");
        this.position = Position.Right(2);
    }
    @Override
    public void onEnable() {
        Athens.LOG.info("FullBright Enabled");
        FullBright.active = true;
        FullBright.update();
    }

    @Override
    public void onDisable() {
        Athens.LOG.info("FullBright Disabled");
        FullBright.active = false;
        FullBright.update();
    }

    public static void update() {
        if(active) Athens.MC.options.getGamma().setValue(100.0);
        else Athens.MC.options.getGamma().setValue(1.0);
    }
}
