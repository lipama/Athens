package net.lipama.athens.systems;

import net.lipama.athens.Athens;
import net.lipama.athens.systems.modules.Modules;
import net.lipama.athens.systems.screens.AthensOptionsScreen;
import net.lipama.athens.utils.HudUtils;
import net.lipama.athens.utils.KeyBinds;
import net.lipama.athens.utils.RainbowColor;

public class Systems {
    public final HudUtils HUD = new HudUtils<>();
    public final Modules MODULES = new Modules();
    public RainbowColor COLOR = new RainbowColor();
    public static Systems init() {
        Systems systems = new Systems();
        Athens.COMPOSER.subscribe(systems.HUD);
        Athens.COMPOSER.subscribe(systems.MODULES);
        Athens.COMPOSER.subscribe(systems.COLOR);
        Athens.COMPOSER.subscribe(KeyBinds.class);
        Athens.COMPOSER.subscribe(AthensOptionsScreen.class);
        return systems;
    }
}
