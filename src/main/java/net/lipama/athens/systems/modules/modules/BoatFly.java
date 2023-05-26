package net.lipama.athens.systems.modules.modules;

import net.lipama.athens.Athens;
import net.lipama.athens.events.TickEvent;
import net.lipama.athens.systems.modules.Module;
import net.minecraft.util.math.Vec3d;
import net.minecraft.entity.Entity;

public class BoatFly extends Module implements TickEvent.Post.Event {
    public BoatFly() {
        super("BoatFly");
        TickEvent.Post.subscribe(this);
        this.position = Position.Right(3);
    }
    @Override
    public void onEnable() {
        Athens.LOG.info("BoatFly Enabled");
    }

    @Override
    public void onDisable() {
        Athens.LOG.info("BoatFly Disabled");
    }

    private int fallTime = 60;

    @Override
    public void onPostTick() {
        if(enabled) {
            if(Athens.MC.player != null && Athens.MC.player.hasVehicle()) {
                Entity vehicle = Athens.MC.player.getVehicle();
                if(vehicle != null) {
                    Vec3d velocity = vehicle.getVelocity();
                    if(fallTime == 0) {
                        double motionY = -0.04;
                        vehicle.setVelocity(new Vec3d(velocity.x, motionY, velocity.z));
                        fallTime = 60;
                    } else {
                        double motionY = Athens.MC.options.jumpKey.isPressed() ? 0.5 : -0.5;
                        vehicle.setVelocity(new Vec3d(velocity.x, motionY, velocity.z));
                    }
                }
            }
            fallTime--;
        }
    }
}
