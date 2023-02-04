package net.lipama.athens.modules.settings;

import org.jetbrains.annotations.NotNull;
import net.minecraft.client.gui.*;

public class Setting<SettingType, WidgetType> {
    protected final String name;
    private final String description;
    protected SettingType value;
    public Setting(@NotNull String name, @NotNull String description, @NotNull SettingType defaultValue) {
        this.description = description;
        this.value = defaultValue;
        this.name = name;
    }

    public SettingType getValue() { return this.value; }

    public void setValue(SettingType val) { this.value = val; }

    public <T extends Element & Drawable & Selectable> T buildWidget() { return null; }

}
