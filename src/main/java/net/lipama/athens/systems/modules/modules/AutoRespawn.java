package net.lipama.athens.systems.modules.modules;

import net.lipama.athens.systems.modules.Module;
import net.lipama.athens.events.*;
import net.lipama.athens.*;

import net.titanium.composer.EventHandler;

public class AutoRespawn extends Module {
    public AutoRespawn() {
        super("AutoRespawn");
        this.position = Position.Right(1);
    }
    @Override
    public void onEnable() {
        Athens.LOG.info("AutoRespawn Enabled");
    }
    @Override
    public void onDisable() {
        Athens.LOG.info("AutoRespawn Disabled");
    }
    @EventHandler
    public void onDeath(DeathEvent event) {
        if(!this.enabled) return;
        assert Athens.MC.player != null;
        Athens.MC.player.requestRespawn();
    }
}
