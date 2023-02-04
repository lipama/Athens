package net.lipama.athens.modules.settings;

import net.minecraft.client.gui.widget.ButtonWidget;
import org.jetbrains.annotations.NotNull;

public class SliderSetting extends Setting<Double, ButtonWidget> {
    public SliderSetting(@NotNull String name, @NotNull String description, Double min, Double max, Double defaultValue) {
        super(name, description, defaultValue);
    }
}
