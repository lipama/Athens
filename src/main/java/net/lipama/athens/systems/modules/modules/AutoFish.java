package net.lipama.athens.systems.modules.modules;

import net.minecraft.client.MinecraftClient;
import net.minecraft.util.Hand;

import net.lipama.athens.systems.modules.Module;
import net.lipama.athens.events.*;
import net.lipama.athens.*;

import net.titanium.composer.*;

public class AutoFish extends Module {
    private int recastRod = -1;

    public AutoFish() {
        super("AutoFish");
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
    @EventHandler
    public void onPostTick(TickEvent.Post event) {
        if(this.recastRod>0){
            recastRod--;
        }
        if(this.recastRod==0 && this.enabled) {
            MinecraftClient mc = MinecraftClient.getInstance();
            if(mc.interactionManager != null) mc.interactionManager.interactItem(mc.player, Hand.MAIN_HAND);
            recastRod = -1;
        }
    }
    @EventHandler
    public void onBobberCatch(FishingBobberCatchEvent event) {
        if(this.enabled) {
            this.recastRod = 20;
            Athens.MC.interactionManager.interactItem(Athens.MC.player, Hand.MAIN_HAND);
        }
    }
}
