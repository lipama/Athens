package net.lipama.athens;

import net.lipama.athens.baritone.BaritoneLoader;
import static net.lipama.athens.AthensClient.*;
import net.lipama.athens.utils.*;

public class Athens {
    public static boolean useHud = true;
    public static boolean useBaritone = false;
    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static void preInit(){
        LOG.debug("preInit Athens");
        load();

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
        save.addLine("Baritone", useBaritone);

        SaveUtils.saveState(SaveUtils.SavableData.GLOBAL, save.build());
    }

    public static void load() {
        useHud = SaveUtils.loadState(SaveUtils.SavableData.GLOBAL,"Hud");
        useBaritone = SaveUtils.loadState(SaveUtils.SavableData.GLOBAL,"Baritone");
        if(useBaritone) BaritoneLoader.INSTANCE.load();

        KeyBinds.registerKeybinds();
    }

    public static void shutdown() {
        LOG.info("Shutting Down Athens");
        save();
        MODULES.shutdown();
    }
}
