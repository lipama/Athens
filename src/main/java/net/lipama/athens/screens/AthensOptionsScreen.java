package net.lipama.athens.screens;

import net.lipama.athens.AthensClient;
import net.lipama.athens.modules.settings.Setting;
import net.minecraft.client.gui.Drawable;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.Selectable;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

import net.lipama.athens.Athens;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class AthensOptionsScreen<T extends Element & Drawable & Selectable> extends Screen {
//    public static ArrayList<SettingButton> settingButtons = new ArrayList<>();
    public static ArrayList<ButtonWidget> settingButtons = new ArrayList<>();
    public static int WIDTH;
    public static int HEIGHT;
    private final Screen parent;
    private final GameOptions settings;

    public AthensOptionsScreen(Screen parent, GameOptions options) {
        super(Text.of("Athens Client"));
        this.parent = parent;
        this.settings = options;
        AthensOptionsScreen.WIDTH = this.width;
        AthensOptionsScreen.HEIGHT = this.height;
    }

    protected void init() {
        AthensOptionsScreen.WIDTH = this.width;
        AthensOptionsScreen.HEIGHT = this.height;
        ArrayList<ButtonWidget> widgets = Athens.MODULES.buildButtonWidgets();
        for(ButtonWidget widget : widgets) {
            this.addDrawableChild(widget);
            AthensClient.LOG.info("modules:" + widget.toString());
        }
        for(ButtonWidget widget : AthensOptionsScreen.settingButtons) {
            try {
                this.addDrawableChild(widget);
                AthensClient.LOG.info("Config:" + widget.toString());
            } catch (NullPointerException e){
                AthensClient.LOG.error("NULL PTR EXCEPTIONS");
            }
        }
    }

    public static class SettingButton {
        private final ButtonWidget button;
        private final int yLevel;
        public SettingButton(@NotNull ButtonWidget button, int yLevel) {
            this.button = button;
            this.yLevel = yLevel;
        }

        public ButtonWidget toButton() {
            this.button.setPos(
                AthensOptionsScreen.WIDTH / 2 - 100 + 205,
                AthensOptionsScreen.HEIGHT / 2 - this.yLevel
            );
            return this.button;
        }
    }
}
