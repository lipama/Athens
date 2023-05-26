package net.lipama.athens.mixin;

import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.Mixin;

import net.lipama.athens.systems.modules.modules.Zoom;

import net.minecraft.client.Mouse;

@Mixin(Mouse.class)
@SuppressWarnings("unused")
public class MouseMixin {
    @Inject(at = @At("RETURN"), method = "onMouseScroll")
    private void onOnMouseScroll(long window, double horizontal, double vertical, CallbackInfo ci) {
        Zoom.onMouseScroll(vertical);
    }
}
