package net.lipama.athens.mixin;

import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.Mixin;

import net.minecraft.entity.player.PlayerInventory;

import net.lipama.athens.modules.Zoom;

@Mixin(PlayerInventory.class)
@SuppressWarnings({"unused", "SpellCheckingInspection"})
public class PlayerInventoryMixin {
    @Inject(at = {@At("HEAD")}, method = {"scrollInHotbar(D)V"}, cancellable = true)
    private void onScrollInHotbar(double _scrollAmount, CallbackInfo ci) {
        if(Zoom.zoomKey.isPressed()) ci.cancel();
    }
}
