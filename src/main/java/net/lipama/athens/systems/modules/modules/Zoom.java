package net.lipama.athens.systems.modules.modules;

import net.lipama.athens.Athens;
import net.minecraft.client.option.SimpleOption;
import net.minecraft.util.math.MathHelper;

import net.lipama.athens.utils.*;

public class Zoom {
    private static Double defaultMouseSensitivity;
    private static final double defaultLevel = 1;
    private static Double currentLevel;
    public static double changeFovBasedOnZoom(double fov) {
        SimpleOption<Double> mouseSensitivitySetting = Athens.MC.options.getMouseSensitivity();
        if(currentLevel == null) currentLevel = defaultLevel;
        if(!KeyBinds.zoomKey.isPressed()) {
            currentLevel = defaultLevel;
            if (defaultMouseSensitivity != null) {
                mouseSensitivitySetting.setValue(defaultMouseSensitivity);
                defaultMouseSensitivity = null;
            }
            return fov;
        }
        if(defaultMouseSensitivity == null) defaultMouseSensitivity = mouseSensitivitySetting.getValue();
        mouseSensitivitySetting.setValue(defaultMouseSensitivity * (1.0 / currentLevel));
        return fov / currentLevel;
    }

    public static void onMouseScroll(double amount) {
        if(!KeyBinds.zoomKey.isPressed()) return;
        if(currentLevel == null) currentLevel = defaultLevel;
        if(amount > 0) currentLevel *= 1.1;
        else if(amount < 0) currentLevel *= 0.9;
        currentLevel = MathHelper.clamp(currentLevel, 1, 50);
    }
}
