package net.lipama.athens.mixin;

import net.lipama.athens.modules.interfaces.IClientPlayerInteractionManager;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.*;

import net.minecraft.client.MinecraftClient;

import net.lipama.athens.modules.interfaces.IMinecraftClient;
import net.lipama.athens.AthensClient;
import net.lipama.athens.events.*;


@Mixin(value = MinecraftClient.class, priority = 1001)
public abstract class MinecraftClientMixin implements IMinecraftClient {
    @Shadow
    private ClientPlayerInteractionManager interactionManager;

    @Inject(method = "<init>", at = @At("TAIL"))
    private void onInit(CallbackInfo info) {
        AthensClient.LOG.info("Minecraft Init");
    }

    @Inject(at = @At("HEAD"), method = "tick")
    private void onPreTick(CallbackInfo info) {
        TickEvent.Pre.call();
    }

    @Inject(at = @At("TAIL"), method = "tick")
    private void onTick(CallbackInfo info) {
        TickEvent.Post.call();
    }

    @ModifyArg(method = "updateWindowTitle", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/util/Window;setTitle(Ljava/lang/String;)V"))
    private String setTitle(String _original) {
        return "Athens Client";
    }

    @Override
    public IClientPlayerInteractionManager getInteractionManager() {
        return (IClientPlayerInteractionManager)interactionManager;
    }
}

