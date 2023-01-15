package net.lipama.athens.modules;

import com.linkrbot.projects.orbit.EventHandler;

import net.lipama.athens.events.FishingBobberCatch;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.Hand;

import net.lipama.athens.events.TickEvent;
import net.lipama.athens.AthensClient;

public class AutoFish extends Module {
    private int recastRod = -1;

    public AutoFish() {
        super("AutoFish");
        this.height = 30;
    }

    @Override
    public void onEnable() {
        AthensClient.LOG.info("AutoFish Enabled");
    }

    @Override
    public void onDisable() {
        AthensClient.LOG.info("AutoFish Disabled");
    }

    @EventHandler
    public void onTick(TickEvent.Post event) {
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
    public void onBobberCatch(FishingBobberCatch event) {
        if(this.enabled) {
            this.recastRod = 20;
            AthensClient.MC.interactionManager.interactItem(AthensClient.MC.player, Hand.MAIN_HAND);
        }
    }
}
