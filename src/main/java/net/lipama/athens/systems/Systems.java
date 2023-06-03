package net.lipama.athens.systems;

import net.lipama.athens.systems.modules.*;
import net.lipama.athens.systems.screens.*;
import net.lipama.athens.utils.*;
import net.lipama.athens.*;

public class Systems {
    public final AthensHud HUD = new AthensHud();
    public final Modules MODULES = new Modules();
    public RainbowColor COLOR = new RainbowColor();
    public static Systems init() {
        Systems systems = new Systems();
        systems.HUD.init();
        Athens.COMPOSER.subscribe(systems.HUD);
        Athens.COMPOSER.subscribe(systems.MODULES);
        Athens.COMPOSER.subscribe(systems.COLOR);
        Athens.COMPOSER.subscribe(KeyBinds.class);
        Athens.COMPOSER.subscribe(AthensOptionsScreen.class);
        return systems;
    }
}
