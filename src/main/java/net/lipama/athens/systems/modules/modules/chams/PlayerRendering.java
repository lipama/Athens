package net.lipama.athens.systems.modules.modules.chams;

import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.model.ModelPart;
import net.minecraft.util.Identifier;

import net.lipama.chams.api.Color;

public class PlayerRendering extends Rendering {
    public static final String TARGET = "Lnet/minecraft/client/model/ModelPart;render(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumer;II)V";
    public static void modifyRenderLayer(Args args, VertexConsumerProvider vertexConsumers, AbstractClientPlayerEntity player) {
        if (a() && p().enabled) {
            Identifier texture = p().texturePlayers ? player.getSkinTextures().texture() : ChamsModule.GOD_PLAYER;
            args.set(1, vertexConsumers.getBuffer(RenderLayer.getEntityTranslucent(texture)));
        }
    }
    public static void redirect(ModelPart modelPart, MatrixStack matrices, VertexConsumer vertices, int light, int overlay) {
        if (a() && p().enabled) {
            Color color = p().playerModelColor;
            modelPart.render(matrices, vertices, light, overlay, color.r/255f, color.g/255f, color.b/255f, color.a/255f);
        } else {
            modelPart.render(matrices, vertices, light, overlay);
        }
    }
}
