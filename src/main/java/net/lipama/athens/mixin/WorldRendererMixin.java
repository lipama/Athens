package net.lipama.athens.mixin;

import org.spongepowered.asm.mixin.injection.callback.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.*;

import net.minecraft.client.util.math.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.math.BlockPos;
import net.minecraft.block.BlockState;
import net.minecraft.client.render.*;
import net.minecraft.entity.Entity;
import org.lwjgl.opengl.GL11;
import org.joml.Matrix4f;

import net.lipama.athens.events.*;
import net.lipama.athens.*;

@Mixin(WorldRenderer.class)
public class WorldRendererMixin {

    @Shadow private void drawBlockOutline(MatrixStack matrices, VertexConsumer vertexConsumer, Entity entity,
        double d, double e, double f, BlockPos blockPos, BlockState blockState) {}

    @SuppressWarnings("CancellableInjectionUsage")
    @Inject(method = "render", at = @At("HEAD"), cancellable = true)
    private void render_head(MatrixStack matrices, float tickDelta, long limitTime, boolean renderBlockOutline,
        Camera camera, GameRenderer gameRenderer, LightmapTextureManager lightmapTextureManager,
        Matrix4f positionMatrix, CallbackInfo ci
    ) {
        Athens.COMPOSER.post(WorldRenderEvent.Pre.get(
            matrices, tickDelta, limitTime, renderBlockOutline, camera,
            gameRenderer, lightmapTextureManager, positionMatrix
        ));
    }

    @Inject(method = "render", at = @At("RETURN"))
    private void render_return(MatrixStack matrices, float tickDelta, long limitTime, boolean renderBlockOutline,
        Camera camera, GameRenderer gameRenderer, LightmapTextureManager lightmapTextureManager,
        Matrix4f positionMatrix, CallbackInfo callback
    ) {
        RenderSystem.clear(GL11.GL_DEPTH_BUFFER_BIT, MinecraftClient.IS_SYSTEM_MAC);
        Athens.COMPOSER.post(WorldRenderEvent.Post.get(
            matrices, tickDelta, limitTime, renderBlockOutline, camera,
            gameRenderer, lightmapTextureManager, positionMatrix
        ));
    }
}
