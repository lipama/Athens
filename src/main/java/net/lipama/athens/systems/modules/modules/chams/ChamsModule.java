package net.lipama.athens.systems.modules.modules.chams;

import net.minecraft.util.Identifier;

import net.lipama.athens.systems.modules.ModuleSettings;
import net.lipama.athens.systems.modules.Module;
import net.lipama.athens.utils.*;
import net.lipama.athens.*;

public class ChamsModule extends Module {
    public final ModuleSettings<ChamsModuleSettings> settings =
        ModuleSettings.initLoaded(id(), ChamsModuleSettings.class);

    public static final Identifier BLANK = AthensIdentifier.texture("blank.png");
    public static final Identifier GOD_PLAYER = AthensIdentifier.texture("god_player.png");

    public ChamsModule() {
        super("Chams");
        this.position = Position.Right(4);
    }

    @Override
    public void onEnable() {
        this.settings.load();
        Athens.LOG.info("Chams Enabled");
    }

    @Override
    public void onDisable() {
        Athens.LOG.warn(this.settings.get().settings.crystalSettings.innerFrameColor.toString());
        this.settings.save();
        Athens.LOG.info("Chams Disabled");
    }

    @SuppressWarnings("all")
    public static boolean isActive() {
        try {
            return Athens.SYSTEMS.MODULES.MODULES.get("Chams").get().getEnabled();
        } catch (Exception ignored) {
            return false;
        }
    }

    @SuppressWarnings("all")
    public static ChamsModuleSettings getSettings() {
        try {
            return ((ChamsModule) Athens.SYSTEMS.MODULES.MODULES.get("Chams").get()).settings.get();
        } catch (Exception e) {
            Athens.LOG.error("getSettings", e);
            throw new IllegalStateException(e);
        }
    }
}
