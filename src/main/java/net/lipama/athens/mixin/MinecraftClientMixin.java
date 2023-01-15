package net.lipama.athens.mixin;

import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.Mixin;

import net.minecraft.client.MinecraftClient;

import net.lipama.athens.AthensClient;
import net.lipama.athens.events.*;


@Mixin(value = MinecraftClient.class, priority = 1001)
public abstract class MinecraftClientMixin {

    @Inject(method = "<init>", at = @At("TAIL"))
    private void onInit(CallbackInfo info) {
        AthensClient.LOG.info("Minecraft Init");
    }

    @Inject(at = @At("HEAD"), method = "tick")
    private void onPreTick(CallbackInfo info) {
        AthensClient.EVENT_BUS.post(TickEvent.Pre.get());
    }

    @Inject(at = @At("TAIL"), method = "tick")
    private void onTick(CallbackInfo info) {
        AthensClient.EVENT_BUS.post(TickEvent.Post.get());
    }

    @ModifyArg(method = "updateWindowTitle", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/util/Window;setTitle(Ljava/lang/String;)V"))
    private String setTitle(String _original) {
        return "Athens Client";
    }
}

