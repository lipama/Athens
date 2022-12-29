package net.lipama.athens.screens;

import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;

import net.lipama.athens.utils.KeyBinds;
import net.lipama.athens.AthensClient;

import java.util.ArrayList;

@SuppressWarnings({"FieldCanBeLocal", "unchecked", "unused"})
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

    protected void init() {
        AthensOptionsScreen.WIDTH = this.width;
        AthensOptionsScreen.HEIGHT = this.height;
        ArrayList<ButtonWidget> widgets = AthensClient.MODULES.buildButtonWidgets();
        for(ButtonWidget widget : widgets) {
            this.addDrawableChild(widget);
        }
    }

    public static void handle(MinecraftClient client) {
        while(KeyBinds.screenKey.wasPressed()) {
            client.setScreenAndRender(new AthensOptionsScreen(client.currentScreen,client.options));
        }
    }
}
