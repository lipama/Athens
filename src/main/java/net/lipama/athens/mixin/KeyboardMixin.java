package net.lipama.athens.mixin;

import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.Mixin;

import net.minecraft.client.Keyboard;

import net.lipama.athens.events.*;
import net.lipama.athens.*;

@SuppressWarnings("all")
@Mixin(Keyboard.class)
public class KeyboardMixin  {
    @Inject(at = @At("HEAD"), method = "onKey(JIIII)V")
    private void onOnKey(long windowHandle, int keyCode, int scanCode, int action, int modifiers, CallbackInfo ci) {
        Athens.COMPOSER.post(KeyPressEvent.get(keyCode, scanCode, action, modifiers));
    }
}