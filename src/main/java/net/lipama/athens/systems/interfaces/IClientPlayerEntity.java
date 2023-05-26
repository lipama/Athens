package net.lipama.athens.systems.interfaces;

import net.minecraft.util.math.Vec3d;

@SuppressWarnings("all")
public interface IClientPlayerEntity {
    public void setNoClip(boolean noClip);
    public float getLastYaw();
    public float getLastPitch();
    public void setMovementMultiplier(Vec3d movementMultiplier);
    public boolean isTouchingWaterBypass();
}
