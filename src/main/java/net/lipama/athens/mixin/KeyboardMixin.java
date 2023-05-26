package net.lipama.athens.mixin;

import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.Mixin;

import net.lipama.athens.events.KeyPressEvent;
import net.minecraft.client.Keyboard;

@SuppressWarnings("all")
@Mixin(Keyboard.class)
public class KeyboardMixin  {
    @Inject(at = @At("HEAD"), method = "onKey(JIIII)V")
    private void onOnKey(long windowHandle, int keyCode, int scanCode, int action, int modifiers, CallbackInfo ci) {
        KeyPressEvent.call(new KeyPressEvent(keyCode, scanCode, action, modifiers));
    }
}