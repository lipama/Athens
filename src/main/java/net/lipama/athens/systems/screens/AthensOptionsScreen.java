package net.lipama.athens.systems.screens;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.option.GameOptionsScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.Selectable;
import net.minecraft.client.gui.Drawable;
import net.minecraft.client.gui.Element;
import net.minecraft.text.Text;

import net.lipama.athens.events.KeyPressEvent;
import net.lipama.athens.utils.KeyBinds;
import net.lipama.athens.Athens;

import net.titanium.composer.*;

import java.util.ArrayList;

@SuppressWarnings({"unused", "FieldCanBeLocal"})
public class AthensOptionsScreen<T extends Element & Drawable & Selectable> extends GameOptionsScreen {
    private static final boolean RENDER_BACKGROUND = true;// TODO: Improve this
    @EventHandler
    public static void onKey(KeyPressEvent event) {
        if(KeyBinds.moduleKey.isWrong(event)) return;
        Athens.MC.setScreen(
            new AthensOptionsScreen<>(Athens.MC.currentScreen, Athens.MC.options)
        );
    }
    public static int WIDTH;
    public static int HEIGHT;

    public AthensOptionsScreen(Screen parent, GameOptions options) {
        super(parent, options, Text.of(Athens.MOD_NAME + " Client"));
        AthensOptionsScreen.WIDTH = this.width;
        AthensOptionsScreen.HEIGHT = this.height;
    }
    protected void init() {
        AthensOptionsScreen.WIDTH = this.width;
        AthensOptionsScreen.HEIGHT = this.height;
        ArrayList<ButtonWidget> widgets = Athens.SYSTEMS.MODULES.buildButtonWidgets();
        for(ButtonWidget widget : widgets) {
            this.addDrawableChild(widget);
        }
    }
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        if(RENDER_BACKGROUND) this.renderBackground(context, mouseX, mouseY, delta);// TODO: Improve this
        context.drawCenteredTextWithShadow(
            this.textRenderer, this.title, 0, 5,
            Athens.SYSTEMS.COLOR.getPacked()
        );
        super.render(context, mouseX, mouseY, delta);
    }
}
