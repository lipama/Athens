package net.lipama.athens.systems.modules.modules;

import net.lipama.athens.systems.modules.Module;
import net.lipama.athens.events.*;
import net.lipama.athens.*;

import net.minecraft.client.MinecraftClient;
import net.minecraft.util.Hand;

public class AutoFish extends Module implements
    FishingBobberCatchEvent.Event,
    TickEvent.Post.Event
{
    private int recastRod = -1;

    public AutoFish() {
        super("AutoFish");
        FishingBobberCatchEvent.subscribe(this);
        TickEvent.Post.subscribe(this);
        this.position = Position.Left(2);
    }

    @Override
    public void onEnable() {
        Athens.LOG.info("AutoFish Enabled");
    }

    @Override
    public void onDisable() {
        Athens.LOG.info("AutoFish Disabled");
    }
    @Override
    public void onPostTick() {
        if(this.recastRod>0){
            recastRod--;
        }
        if(this.recastRod==0 && this.enabled) {
            MinecraftClient mc = MinecraftClient.getInstance();
            if(mc.interactionManager != null) mc.interactionManager.interactItem(mc.player, Hand.MAIN_HAND);
            recastRod = -1;
        }
    }
    @Override
    public void onBobberCatch() {
        if(this.enabled) {
            this.recastRod = 20;
            Athens.MC.interactionManager.interactItem(Athens.MC.player, Hand.MAIN_HAND);
        }
    }
}
