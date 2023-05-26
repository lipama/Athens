package net.lipama.athens.mixin;

import net.lipama.athens.events.DeathEvent;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.injection.callback.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.*;

import net.minecraft.client.gui.screen.*;

@Mixin(DeathScreen.class)
public class DeathScreenMixin extends Screen {
    private DeathScreenMixin(Text title) { super(title); }

    @Inject(at = @At(value = "TAIL"), method = "render")
    private void onTick(CallbackInfo ci) {
        DeathEvent.call();
    }
}
