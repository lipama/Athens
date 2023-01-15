package net.lipama.athens.utils;

import com.linkrbot.projects.orbit.EventHandler;

import static net.lipama.athens.AthensClient.*;

import net.lipama.athens.modules.Modules;
import net.lipama.athens.screens.*;
import net.lipama.athens.events.*;

public class Utils {
    public static void init() {
        EVENT_BUS.subscribe(Utils.class);
        EVENT_BUS.subscribe(Modules.class);
        EVENT_BUS.subscribe(AthensOptionsScreen.class);
    }

    @EventHandler
    private void tick(TickEvent.Pre event) {
        // Tick Code
    }
}
