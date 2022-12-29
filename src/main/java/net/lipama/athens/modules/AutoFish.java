package net.lipama.athens.modules;

import net.minecraft.client.MinecraftClient;
import net.minecraft.util.Hand;

import net.lipama.athens.AthensClient;

public class AutoFish extends Module {
    private static int recastRod = -1;
    public static boolean active = true;

    public AutoFish() {
        super("AutoFish");
    }

    @Override
    public void onEnable() {
        AutoFish.active = true;
        AthensClient.LOG.info("AutoFish Enabled");
    }

    @Override
    public void onDisable() {
        AutoFish.active = false;
        AthensClient.LOG.info("AutoFish Disabled");
    }

    @Override
    public void onTick() {
        if(AutoFish.recastRod>0){
            recastRod--;
        }
        if(recastRod==0 && AutoFish.active) {
            MinecraftClient mc = MinecraftClient.getInstance();
            if(mc.interactionManager != null) mc.interactionManager.interactItem(mc.player, Hand.MAIN_HAND);
            recastRod = -1;
        }
    }

    public static void setRecastRod(int time) {
        AutoFish.recastRod = time;
    }
}
