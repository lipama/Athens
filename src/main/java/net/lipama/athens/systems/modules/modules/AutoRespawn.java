package net.lipama.athens.systems.modules.modules;

import net.lipama.athens.systems.modules.Module;
import net.lipama.athens.events.*;
import net.lipama.athens.*;

public class AutoRespawn extends Module implements DeathEvent.Event {
    public AutoRespawn() {
        super("AutoRespawn");
        DeathEvent.subscribe(this);
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
    @Override
    public void onDeath() {
        if(!this.enabled) return;
        assert Athens.MC.player != null;
        Athens.MC.player.requestRespawn();
        RespawnEvent.call();
    }
}
