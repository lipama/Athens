package net.lipama.athens;

import static net.lipama.athens.AthensClient.*;
import net.lipama.athens.utils.*;

public class Athens {
    public static boolean useHud = true;
    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static void preInit(){
        LOG.debug("preInit Athens");

        useHud = SaveUtils.loadState(SaveUtils.SavableData.GLOBAL,"Hud");
        KeyBinds.registerKeybinds();

        if(!FOLDER.exists()) {
            FOLDER.getParentFile().mkdirs();
            FOLDER.mkdir();
            LOG.info("Welcome, new user");
        } else {
            LOG.info("Welcome back, user");
        }
    }

    public static void init() {
        LOG.info("Initializing Athens");
    }

    private static void save() {
        SaveUtils.SaveBuilder save = new SaveUtils.SaveBuilder();
        save.addLine("Hud", useHud);

        SaveUtils.saveState(SaveUtils.SavableData.GLOBAL, save.build());
    }

    public static void shutdown() {
        LOG.info("Shutting Down Athens");
        save();
        MODULES.shutdown();
    }

    public static String getName() { return "Athens"; }
}
