package net.lipama.athens.utils;

import net.lipama.athens.Athens;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

@SuppressWarnings("unused")
public enum RotationUtils {;

    public static Vec3d getEyesPos() {
        ClientPlayerEntity player = Athens.MC.player;

        assert player != null;
        return new Vec3d(player.getX(),
                player.getY() + player.getEyeHeight(player.getPose()),
                player.getZ());
    }

    public static Vec3d getClientLookVec() {
        ClientPlayerEntity player = Athens.MC.player;
        float f = 0.017453292F;
        float pi = (float)Math.PI;

        assert player != null;
        float f1 = MathHelper.cos(-player.getYaw() * f - pi);
        float f2 = MathHelper.sin(-player.getYaw() * f - pi);
        float f3 = -MathHelper.cos(-player.getPitch() * f);
        float f4 = MathHelper.sin(-player.getPitch() * f);

        return new Vec3d(f2 * f3, f4, f1 * f3);
    }

    public static Rotation getNeededRotations(Vec3d vec) {
        Vec3d eyesPos = getEyesPos();

        double diffX = vec.x - eyesPos.x;
        double diffY = vec.y - eyesPos.y;
        double diffZ = vec.z - eyesPos.z;

        double diffXZ = Math.sqrt(diffX * diffX + diffZ * diffZ);

        float yaw = (float)Math.toDegrees(Math.atan2(diffZ, diffX)) - 90F;
        float pitch = (float)-Math.toDegrees(Math.atan2(diffY, diffXZ));

        return new Rotation(yaw, pitch);
    }

    public static double getAngleToLookVec(Vec3d vec) {
        Rotation needed = getNeededRotations(vec);

        ClientPlayerEntity player = Athens.MC.player;
        assert player != null;
        float currentYaw = MathHelper.wrapDegrees(player.getYaw());
        float currentPitch = MathHelper.wrapDegrees(player.getPitch());

        float diffYaw = currentYaw - needed.yaw;
        float diffPitch = currentPitch - needed.pitch;

        return Math.sqrt(diffYaw * diffYaw + diffPitch * diffPitch);
    }

    public static float getHorizontalAngleToLookVec(Vec3d vec) {
        Rotation needed = getNeededRotations(vec);
        assert Athens.MC.player != null;
        return MathHelper.wrapDegrees(Athens.MC.player.getYaw()) - needed.yaw;
    }

    public record Rotation(float yaw, float pitch) {
            public Rotation(float yaw, float pitch) {
                this.yaw = MathHelper.wrapDegrees(yaw);
                this.pitch = MathHelper.wrapDegrees(pitch);
            }
        }
}
