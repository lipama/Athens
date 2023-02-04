package net.lipama.athens.modules.settings;

import net.minecraft.client.gui.widget.ButtonWidget;
import org.jetbrains.annotations.NotNull;

public class ToggleableSetting extends Setting<Boolean, ButtonWidget> {
    public ToggleableSetting(@NotNull String name, @NotNull String description, boolean defaultValue) {
        super(name, description, defaultValue);
    }
}
