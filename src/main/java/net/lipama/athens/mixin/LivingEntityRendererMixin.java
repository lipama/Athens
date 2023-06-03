package net.lipama.athens.mixin;

import net.lipama.athens.systems.modules.modules.chams.LivingEntityRendering;

import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;

import org.spongepowered.asm.mixin.injection.invoke.arg.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.*;

@Mixin(LivingEntityRenderer.class)
public abstract class LivingEntityRendererMixin<T extends LivingEntity, M extends EntityModel<T>> {
    @Shadow
    protected abstract RenderLayer getRenderLayer(T entity, boolean showBody, boolean translucent, boolean showOutline);

    // Player chams

    @ModifyArgs(method = LivingEntityRendering.METHOD, at = @At(value = "INVOKE", target = LivingEntityRendering.SCALE))
    private void modifyScale(Args args, T livingEntity, float f, float g, MatrixStack m, VertexConsumerProvider v, int i) {
        LivingEntityRendering.modifyScale(args, livingEntity);
    }

    @ModifyArgs(method = LivingEntityRendering.METHOD, at = @At(value = "INVOKE", target = LivingEntityRendering.COLOR))
    private void modifyColor(Args args, T livingEntity, float f, float g, MatrixStack m, VertexConsumerProvider v, int i) {
        LivingEntityRendering.modifyColor(args, livingEntity);
    }

    @Redirect(method = LivingEntityRendering.METHOD, at = @At(value = "INVOKE", target = LivingEntityRendering.LAYER))
    private RenderLayer renderLayer(LivingEntityRenderer<T, M> livingEntityRenderer, T livingEntity, boolean showBody, boolean translucent, boolean showOutline) {
        return LivingEntityRendering.renderLayer(this::getRenderLayer, livingEntity, showBody, translucent, showOutline);
    }
}
