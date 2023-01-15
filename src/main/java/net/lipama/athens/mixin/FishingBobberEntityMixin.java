package net.lipama.athens.mixin;

import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.*;

import net.minecraft.entity.projectile.FishingBobberEntity;
import net.minecraft.entity.data.TrackedData;

import net.lipama.athens.events.FishingBobberCatch;
import net.lipama.athens.AthensClient;

@SuppressWarnings("all")
@Mixin(FishingBobberEntity.class)
public abstract class FishingBobberEntityMixin {
    @Shadow private boolean caughtFish;

    @Inject(method = "onTrackedDataSet", at = @At("TAIL"))
    public void onTrackedDataSet(TrackedData<?> _data, CallbackInfo _cb) {
        if(caughtFish) AthensClient.EVENT_BUS.post(FishingBobberCatch.get());
    }
}

