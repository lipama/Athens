package net.lipama.athens.events;

import net.minecraft.client.render.Camera;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.LightmapTextureManager;
import net.minecraft.client.util.math.MatrixStack;
import org.joml.Matrix4f;

public class WorldRenderEvent {

    public static class Pre {
        private static final WorldRenderEvent.Pre INSTANCE = new WorldRenderEvent.Pre();
        public MatrixStack matrices;
        public float tickDelta;
        public long limitTime;
        public boolean renderBlockOutline;
        public Camera camera;
        public GameRenderer gameRenderer;
        public LightmapTextureManager lightmapTextureManager;
        public Matrix4f positionMatrix;
        public static WorldRenderEvent.Pre get(MatrixStack matrices, float tickDelta, long limitTime,
            boolean renderBlockOutline, Camera camera, GameRenderer gameRenderer,
            LightmapTextureManager lightmapTextureManager, Matrix4f positionMatrix
        ) {
            INSTANCE.matrices = matrices;
            INSTANCE.tickDelta = tickDelta;
            INSTANCE.limitTime = limitTime;
            INSTANCE.renderBlockOutline = renderBlockOutline;
            INSTANCE.camera = camera;
            INSTANCE.gameRenderer = gameRenderer;
            INSTANCE.lightmapTextureManager = lightmapTextureManager;
            INSTANCE.positionMatrix = positionMatrix;
            return INSTANCE;
        }
    }
    public static class Post {
        private static final WorldRenderEvent.Post INSTANCE = new WorldRenderEvent.Post();
        public MatrixStack matrices;
        public float tickDelta;
        public long limitTime;
        public boolean renderBlockOutline;
        public Camera camera;
        public GameRenderer gameRenderer;
        public LightmapTextureManager lightmapTextureManager;
        public Matrix4f positionMatrix;
        public static WorldRenderEvent.Post get(MatrixStack matrices, float tickDelta, long limitTime,
            boolean renderBlockOutline, Camera camera, GameRenderer gameRenderer,
            LightmapTextureManager lightmapTextureManager, Matrix4f positionMatrix
        ) {
            INSTANCE.matrices = matrices;
            INSTANCE.tickDelta = tickDelta;
            INSTANCE.limitTime = limitTime;
            INSTANCE.renderBlockOutline = renderBlockOutline;
            INSTANCE.camera = camera;
            INSTANCE.gameRenderer = gameRenderer;
            INSTANCE.lightmapTextureManager = lightmapTextureManager;
            INSTANCE.positionMatrix = positionMatrix;
            return INSTANCE;
        }
    }
}
