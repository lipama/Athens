package net.lipama.athens.systems.modules.modules;

import net.lipama.athens.systems.modules.Module;
import net.lipama.athens.*;

//import net.titanium.composer.*;

public class Flight extends Module {
    public Flight() {
        super("Flight");
        this.position = Position.Left(3);
    }
    @Override
    public void onEnable() {
        Athens.LOG.info("Flight Enabled");
        if(Athens.MC.player != null) Athens.MC.player.getAbilities().allowFlying = true;
    }
    @Override
    public void onDisable() {
        Athens.LOG.info("Flight Disabled");
        if(Athens.MC.player != null) Athens.MC.player.getAbilities().allowFlying = false;
    }
//    @EventHandler
//    public void onPlayerRespawn() {
//        assert Athens.MC.player != null;
//        Athens.MC.player.getAbilities().allowFlying = this.enabled;
//    }
//
//    public void onPlayerJoin() {
//        assert Athens.MC.player != null;
//        Athens.MC.player.getAbilities().allowFlying = this.enabled;
//    }
}
