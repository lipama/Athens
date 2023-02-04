package net.lipama.athens.modules.modules;

import net.lipama.athens.events.RespawnEvent;
import net.lipama.athens.modules.Module;
import net.lipama.athens.AthensClient;

public class AutoRespawn extends Module {
    public AutoRespawn() {
        super("AutoRespawn");
        this.height = 130;
    }
    @Override
    public void onEnable() {
        AthensClient.LOG.info("AutoRespawn Enabled");
    }
    @Override
    public void onDisable() {
        AthensClient.LOG.info("AutoRespawn Disabled");
    }
    public void onDeath() {
        assert AthensClient.MC.player != null;
        AthensClient.MC.player.requestRespawn();
        RespawnEvent.call();
    }
}
