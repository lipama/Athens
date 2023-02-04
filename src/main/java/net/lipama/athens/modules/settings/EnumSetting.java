package net.lipama.athens.modules.settings;

import net.minecraft.client.gui.Drawable;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.Selectable;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;
import org.jetbrains.annotations.NotNull;

public class EnumSetting<T extends EnumSetting.EnumSettingType> extends Setting<T, ButtonWidget> {
    public EnumSetting(@NotNull String name, @NotNull String description, @NotNull T defaultValue) {
        super(name, description, defaultValue);
    }

    @Override
    public <Gui extends Element & Drawable & Selectable> Gui buildWidget() {
        ButtonWidget.Builder element = new ButtonWidget.Builder(Text.of(this.name),(btn)->{
            btn.setMessage(Text.of(this.getValue().getNext()));
        });


        return (Gui) element.build();
    }

    public interface EnumSettingType {
        String getNext();
    }
}
