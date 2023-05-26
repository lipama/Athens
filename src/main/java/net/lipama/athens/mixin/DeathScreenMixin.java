package net.lipama.athens.mixin;

import org.spongepowered.asm.mixin.injection.callback.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.*;

import net.minecraft.client.gui.screen.*;
import net.minecraft.text.Text;

import net.lipama.athens.events.*;
import net.lipama.athens.*;

@Mixin(DeathScreen.class)
public class DeathScreenMixin extends Screen {
    private DeathScreenMixin(Text title) { super(title); }

    @Inject(at = @At(value = "TAIL"), method = "render")
    private void onTick(CallbackInfo ci) {
        Athens.COMPOSER.post(DeathEvent.get());
    }
}
