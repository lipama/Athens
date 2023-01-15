package net.lipama.athens.modules;

import net.lipama.athens.Athens;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;

import net.lipama.athens.screens.AthensOptionsScreen;
import net.lipama.athens.utils.*;

public abstract class Module implements HudUtils.Renderable {
    protected int height = 30;
    protected int hudHeight = 5;
    private final String NAME;
    protected boolean enabled;
    public Module(String name) {
        this.NAME = name;
    }
    public Text status() {
        if(this.enabled){
            return Text.of(NAME + " is enabled");
        } else {
            return Text.of(NAME + " is disabled");
        }
    }
    public String name() {
        return this.NAME;
    }
    public ButtonWidget buildButtonWidget() {
        ButtonWidget.Builder button = new ButtonWidget.Builder(this.status(), (btn) -> {
            this.toggle();
            btn.setMessage(this.status());
        });
        button.dimensions(
            AthensOptionsScreen.WIDTH / 2 - 100,
            AthensOptionsScreen.HEIGHT / 2 - this.height,
            200,
            20
        );
        return button.build();
    }
    public void enable() {
        this.enabled = true;
        this.onEnable();
    }
    public void disable() {
        this.enabled = false;
        this.onDisable();
    }
    public void toggle() {
        if(enabled) {
            this.disable();
        } else {
            this.enable();
        }
    }

    public abstract void onEnable();
    public abstract void onDisable();

    @Override
    public void render(MinecraftClient mc, MatrixStack matrices, float tickDelta, CallbackInfo info) {
        if(this.enabled) {
            mc.textRenderer.draw(matrices, this.NAME, 5, hudHeight, Athens.COLOR.getPacked());
        }
    }
}
