package net.lipama.athens.systems.screens;

import net.lipama.athens.Athens;
import net.lipama.athens.utils.KeyBinds;

import net.minecraft.client.gui.screen.option.GameOptionsScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.Selectable;
import net.minecraft.client.gui.Drawable;
import net.minecraft.client.gui.Element;

import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;

import java.util.ArrayList;

@SuppressWarnings({"unused", "FieldCanBeLocal"})
public class AthensOptionsScreen<T extends Element & Drawable & Selectable> extends GameOptionsScreen {
    private static final boolean RENDER_BACKGROUND = true;// TODO: Improve this
    public static void preInit() {
        KeyBinds.moduleKey.registerListener(keyPressEvent -> {
            if(KeyBinds.moduleKey.isWrong(keyPressEvent)) return;
            Athens.MC.setScreen(
                new AthensOptionsScreen<>(Athens.MC.currentScreen, Athens.MC.options)
            );
        });
    }
    public static int WIDTH;
    public static int HEIGHT;

    public AthensOptionsScreen(Screen parent, GameOptions options) {
        super(parent, options, Text.of("Athens Client"));
        AthensOptionsScreen.WIDTH = this.width;
        AthensOptionsScreen.HEIGHT = this.height;
    }
    @SuppressWarnings("unchecked")
    protected void init() {
        AthensOptionsScreen.WIDTH = this.width;
        AthensOptionsScreen.HEIGHT = this.height;
        ArrayList<ButtonWidget> widgets = Athens.SYSTEMS.MODULES.buildButtonWidgets();
        for(ButtonWidget widget : widgets) {
            this.addDrawableChild(widget);
        }
    }

    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        if(RENDER_BACKGROUND) this.renderBackground(matrices);// TODO: Improve this
        drawCenteredTextWithShadow(
            matrices, this.textRenderer, this.title, this.width / 2, 5,
            Athens.SYSTEMS.COLOR.getPacked()
        );
        super.render(matrices, mouseX, mouseY, delta);
    }
}
