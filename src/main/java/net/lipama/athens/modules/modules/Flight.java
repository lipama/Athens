package net.lipama.athens.modules.modules;

import net.lipama.athens.AthensClient;
import net.lipama.athens.modules.Module;

public class Flight extends Module {
    public Flight() {
        super("Flight");
        this.height = -20;
    }
    @Override
    public void onEnable() {
        AthensClient.LOG.info("Flight Enabled");
        if(AthensClient.MC.player != null) AthensClient.MC.player.getAbilities().allowFlying = true;
    }

    @Override
    public void onDisable() {
        AthensClient.LOG.info("Flight Disabled");
        if(AthensClient.MC.player != null) AthensClient.MC.player.getAbilities().allowFlying = false;
    }

    public void onPlayerRespawn() {
        AthensClient.MC.player.getAbilities().allowFlying = this.enabled;
    }
    public void onPlayerJoin() {
        AthensClient.MC.player.getAbilities().allowFlying = this.enabled;
    }
}