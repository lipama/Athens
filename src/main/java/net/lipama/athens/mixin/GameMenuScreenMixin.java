package net.lipama.athens.mixin;

import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.Mixin;

import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.screen.*;
import net.minecraft.text.Text;

import net.lipama.athens.screens.AthensOptionsScreen;

@Mixin(GameMenuScreen.class)
@SuppressWarnings({"unused", "ConstantConditions"})
public abstract class GameMenuScreenMixin extends Screen {
    protected GameMenuScreenMixin(Text text) { super(text); }

    @Inject(at = @At("HEAD"), method = "initWidgets")
    private void initWidgets(CallbackInfo _cb) {
        ButtonWidget.Builder button = new ButtonWidget.Builder(Text.of("Athens"), (btn) -> {
            this.client.setScreen(
                new AthensOptionsScreen(this, this.client.options)
            );
        });
        button.dimensions(10,10,90,20);
        this.addDrawableChild(button.build());
    }
}
