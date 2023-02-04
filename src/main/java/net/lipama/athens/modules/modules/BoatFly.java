package net.lipama.athens.modules.modules;

import net.lipama.athens.modules.Module;
import net.minecraft.util.math.Vec3d;
import net.minecraft.entity.Entity;

import net.lipama.athens.AthensClient;

public class BoatFly extends Module {
    public BoatFly() {
        super("BoatFly");
        this.height = 5;
    }
    @Override
    public void onEnable() {
        AthensClient.LOG.info("BoatFly Enabled");
    }

    @Override
    public void onDisable() {
        AthensClient.LOG.info("BoatFly Disabled");
    }

    private int fallTime = 60;

    public void onPostTick() {
        if(enabled) {
            if(AthensClient.MC.player != null && AthensClient.MC.player.hasVehicle()) {
                Entity vehicle = AthensClient.MC.player.getVehicle();
                if(vehicle != null) {
                    Vec3d velocity = vehicle.getVelocity();
                    if(fallTime == 0) {
                        double motionY = -0.04;
                        vehicle.setVelocity(new Vec3d(velocity.x, motionY, velocity.z));
                        fallTime = 60;
                    } else {
                        double motionY = AthensClient.MC.options.jumpKey.isPressed() ? 0.5 : -0.5;
                        vehicle.setVelocity(new Vec3d(velocity.x, motionY, velocity.z));
                    }
                }
            }
            fallTime--;
        }
    }
}