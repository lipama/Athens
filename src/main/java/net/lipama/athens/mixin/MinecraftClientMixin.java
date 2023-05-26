package net.lipama.athens.mixin;

import net.lipama.athens.systems.modules.modules.OutLiner;
import org.spongepowered.asm.mixin.injection.callback.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.*;

import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;

import net.lipama.athens.systems.interfaces.*;
import net.lipama.athens.events.*;
import net.lipama.athens.*;


@Mixin(value = MinecraftClient.class, priority = 1001)
public abstract class MinecraftClientMixin implements IMinecraftClient {
    @Shadow
    public ClientPlayerInteractionManager interactionManager;

    @Inject(method = "<init>", at = @At("TAIL"))
    private void onInit(CallbackInfo info) {
        Athens.LOG.info("Minecraft Init");
    }

    @Inject(method = "tick", at = @At("HEAD"))
    private void onPreTick(CallbackInfo info) {
        TickEvent.Pre.call();
    }

    @Inject(method = "tick", at = @At("TAIL"))
    private void onTick(CallbackInfo info) {
        TickEvent.Post.call();
    }

    @ModifyArg(method = "updateWindowTitle", at = @At(
        value = "INVOKE", target = "Lnet/minecraft/client/util/Window;setTitle(Ljava/lang/String;)V"
    )) private String setTitle(String _original) {
        return "Athens Client";
    }

    @Override
    public IClientPlayerInteractionManager getInteractionManager() {
        return (IClientPlayerInteractionManager)interactionManager;
    }

    @Inject(method = "hasOutline", at = @At("HEAD"), cancellable = true)
    private void outlineEntities(Entity entity, CallbackInfoReturnable<Boolean> ci) {
        OutLiner.outline(entity, ci);
    }
}

