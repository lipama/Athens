package net.lipama.athens.screens;

import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

import net.lipama.athens.Athens;

import java.util.ArrayList;

@SuppressWarnings({"FieldCanBeLocal", "unused"})
public class AthensOptionsScreen extends Screen {
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

    @SuppressWarnings("unchecked")
    protected void init() {
        AthensOptionsScreen.WIDTH = this.width;
        AthensOptionsScreen.HEIGHT = this.height;
        ArrayList<ButtonWidget> widgets = Athens.MODULES.buildButtonWidgets();
        for(ButtonWidget widget : widgets) {
            this.addDrawableChild(widget);
        }
    }
}
