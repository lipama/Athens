package net.lipama.athens.mixin;

import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.*;

import net.minecraft.entity.projectile.FishingBobberEntity;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.util.Hand;

import net.lipama.athens.modules.AutoFish;
import net.lipama.athens.AthensClient;


@Mixin(FishingBobberEntity.class)
@SuppressWarnings("all")
public abstract class FishingBobberEntityMixin {
    @Shadow private boolean caughtFish;

    @Inject(method = "onTrackedDataSet", at = @At("TAIL"))
    public void onTrackedDataSet(TrackedData<?> _data, CallbackInfo _cb) {
        if(caughtFish && AutoFish.active) {
            AutoFish.setRecastRod(20);
            AthensClient.MC.interactionManager.interactItem(AthensClient.MC.player, Hand.MAIN_HAND);
        }
    }

}
