package net.lipama.athens.events;

import net.minecraft.client.util.math.MatrixStack;

public class WorldRenderEvent {

    public static class Pre {
        private static void onCall(float partialTicks, MatrixStack matrices) {

        }
        public static void call(float partialTicks, MatrixStack matrices) { onCall(partialTicks, matrices); }
    }

    public static class Post {
        private static void onCall(float partialTicks, MatrixStack matrices) {

        }
        public static void call(float partialTicks, MatrixStack matrices) { onCall(partialTicks, matrices); }
    }
}
