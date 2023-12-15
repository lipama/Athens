package net.lipama.athens.mixin;

import net.minecraft.client.gui.DrawContext;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.Mixin;

import net.minecraft.client.gui.hud.InGameHud;

import net.lipama.athens.systems.screens.AthensHudOverlay;

@Mixin(InGameHud.class)
@SuppressWarnings("unused")
public class InGameHudMixin {
    @Inject(method = "render", at = @At("RETURN"))
    public void changeGamma(DrawContext context, float tickDelta, CallbackInfo _ci) {
        AthensHudOverlay.tickFullBright();
    }
    @Inject(method = "render", at = @At("RETURN"))
    public void onRender(DrawContext context, float tickDelta, CallbackInfo _ci) {
        AthensHudOverlay.render(context, tickDelta);
    }
}
