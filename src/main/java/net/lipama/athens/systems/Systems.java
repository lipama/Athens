package net.lipama.athens.systems;

import net.lipama.athens.systems.modules.Modules;
import net.lipama.athens.utils.HudUtils;
import net.lipama.athens.utils.RainbowColor;

public class Systems {
    public final HudUtils HUD = new HudUtils<>();
    public final Modules MODULES = new Modules();
    public RainbowColor COLOR = new RainbowColor();
    public static Systems init() {
        return new Systems();
    }
}
