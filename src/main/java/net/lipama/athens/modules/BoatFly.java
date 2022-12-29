package net.lipama.athens.modules;

import net.lipama.athens.AthensClient;

import net.minecraft.util.math.Vec3d;
import net.minecraft.entity.Entity;

public class BoatFly extends Module {
    public BoatFly() {
        super("BoatFly");
        this.height = 5;
        this.hudHeight = 25;
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

    @Override
    public void onTick() {
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
                        double motionY = AthensClient.MC.options.jumpKey.isPressed() ? 0.5 : 0;
                        vehicle.setVelocity(new Vec3d(velocity.x, motionY, velocity.z));
                    }
                }
            }

            fallTime--;
        }
    }
}
