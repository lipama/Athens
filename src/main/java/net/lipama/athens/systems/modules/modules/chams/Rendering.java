package net.lipama.athens.systems.modules.modules.chams;

import net.lipama.chams.api.ChamsSettings;

public class Rendering {
    public static boolean a() {
        return ChamsModule.isActive();
    }

    public static ChamsSettings.CrystalSettings cs() {
        return settings().crystalSettings;
    }

    public static ChamsSettings.PlayerSettings p() {
        return settings().playerSettings;
    }

    @SuppressWarnings("all")
    private static ChamsSettings settings() {
        return ChamsModule.getSettings().settings;
    }
}
