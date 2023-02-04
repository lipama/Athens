package net.lipama.athens.mixin;

import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.Mixin;

import net.minecraft.resource.SynchronousResourceReloader;
import net.minecraft.client.render.*;

import net.lipama.athens.modules.modules.Zoom;

@Mixin(GameRenderer.class)
@SuppressWarnings({"unused", "SpellCheckingInspection"})
public abstract class GameRendererMixin implements AutoCloseable, SynchronousResourceReloader {
    @Inject(at = @At(value = "RETURN", ordinal = 1), method = {"getFov(Lnet/minecraft/client/render/Camera;FZ)D"}, cancellable = true)
    private void onGetFov(Camera _camera, float _tickDelta, boolean _changingFov, CallbackInfoReturnable<Double> cir) {
        cir.setReturnValue(
            Zoom.changeFovBasedOnZoom(cir.getReturnValueD())
        );
    }
}
