package net.lipama.athens;

import com.linkrbot.projects.orbit.EventHandler;

import net.lipama.athens.baritone.BaritoneLoader;

import static net.lipama.athens.AthensClient.*;
import net.lipama.athens.modules.Modules;
import net.lipama.athens.events.*;
import net.lipama.athens.utils.*;

public class Athens {
    public static final HudUtils HUD = new HudUtils<>();
    public static final Modules MODULES = new Modules<>();
    public static RainbowColor COLOR = new RainbowColor();
    public static boolean useHud = true;
    public static boolean useBaritone = false;


    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static void preInit(){
        LOG.info("Initializing Athens");
        useHud = SaveUtils.loadState(SaveUtils.SavableData.GLOBAL,"Hud");
        useBaritone = SaveUtils.loadState(SaveUtils.SavableData.GLOBAL,"Baritone");
        if(useBaritone) BaritoneLoader.INSTANCE.load();
        KeyBinds.registerKeybinds();
        EVENT_BUS.subscribe(Athens.class);
        Utils.init();
    }

    public static void postInit() {
        COLOR.setSpeed(0.01);
        if(!FOLDER.exists()) {
            FOLDER.getParentFile().mkdirs();
            FOLDER.mkdir();
            LOG.info("Welcome, new user");
        } else {
            LOG.info("Welcome back, user");
        }
    }

    @EventHandler
    public static void onTick(TickEvent.Post event) {
        COLOR = COLOR.getNext();
    }

    @EventHandler
    private static void shutdown(ShutdownEvent event) {
        LOG.info("Shutting Down Athens");
        SaveUtils.SaveBuilder save = new SaveUtils.SaveBuilder();
        save.addLine("Hud", useHud);
        save.addLine("Baritone", useBaritone);

        SaveUtils.saveState(SaveUtils.SavableData.GLOBAL, save.build());
    }
}
