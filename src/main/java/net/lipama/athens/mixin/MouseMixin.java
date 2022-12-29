package net.lipama.athens.mixin;

import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.Mixin;

import net.lipama.athens.modules.Zoom;

import net.minecraft.client.Mouse;

@Mixin(Mouse.class)
@SuppressWarnings("unused")
public class MouseMixin {
    @Inject(at = {@At("RETURN")}, method = {"onMouseScroll(JDD)V"})
    private void onOnMouseScroll(long long_1, double double_1, double double_2, CallbackInfo ci) {
        Zoom.onMouseScroll(double_2);
    }
}
