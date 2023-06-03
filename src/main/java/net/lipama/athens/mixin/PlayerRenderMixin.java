package net.lipama.athens.mixin;

import net.lipama.athens.systems.modules.modules.chams.PlayerRendering;

import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.model.ModelPart;

import org.spongepowered.asm.mixin.injection.invoke.arg.Args;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.*;

@Mixin(PlayerEntityRenderer.class)
public class PlayerRenderMixin {
    @ModifyArgs(method = "renderArm", at = @At(value = "INVOKE", target = PlayerRendering.TARGET, ordinal = 0))
    private void modifyRenderLayer(Args args, MatrixStack m, VertexConsumerProvider v, int l, AbstractClientPlayerEntity p, ModelPart a, ModelPart s) {
        PlayerRendering.modifyRenderLayer(args, v, p);
    }

    @Redirect(method = "renderArm", at = @At(value = "INVOKE", target = PlayerRendering.TARGET, ordinal = 0))
    private void redirectRenderMain(ModelPart modelPart, MatrixStack matrices, VertexConsumer vertices, int light, int overlay) {
        PlayerRendering.redirect(modelPart, matrices, vertices, light, overlay);
    }

    @Redirect(method = "renderArm", at = @At(value = "INVOKE", target = PlayerRendering.TARGET, ordinal = 1))
    private void redirectRenderSleeve(ModelPart modelPart, MatrixStack matrices, VertexConsumer vertices, int light, int overlay) {
        PlayerRendering.redirect(modelPart, matrices, vertices, light, overlay);
    }
}
