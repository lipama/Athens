package net.lipama.athens.mixin;

import net.lipama.athens.systems.modules.modules.chams.CrystalRendering;

import net.minecraft.client.render.entity.EndCrystalEntityRenderer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.entity.decoration.EndCrystalEntity;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.model.ModelPart;
import net.minecraft.util.Identifier;

import org.spongepowered.asm.mixin.injection.invoke.arg.Args;
import org.spongepowered.asm.mixin.injection.callback.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.*;

@SuppressWarnings("ShadowModifiers")
@Mixin(EndCrystalEntityRenderer.class)
public abstract class CrystalRenderMixin {
    @Mutable @Shadow @Final
    private static RenderLayer END_CRYSTAL;
    @Shadow @Final
    private static Identifier TEXTURE;
    @Shadow @Final
    public ModelPart core;
    @Shadow @Final
    public ModelPart frame;

    @Inject(method = CrystalRendering.METHOD, at = @At("HEAD"))
    private void render(EndCrystalEntity _e, float f, float g, MatrixStack _m, VertexConsumerProvider _v, int i, CallbackInfo ci) {
        END_CRYSTAL = CrystalRendering.renderLayer(TEXTURE);
    }

    @ModifyArgs(method = CrystalRendering.METHOD, at = @At(value = "INVOKE", target = CrystalRendering.SCALE, ordinal = 0))
    private void modifyScale(Args args) {
        CrystalRendering.scale(args);
    }

    @Redirect(method = CrystalRendering.METHOD, at = @At(value = "INVOKE", target = CrystalRendering.BOUNCE))
    private float modifyBounce(EndCrystalEntity crystal, float tickDelta) {
        return CrystalRendering.bounce(crystal, tickDelta);
    }

    @ModifyArgs(method = CrystalRendering.METHOD, at = @At(value = "INVOKE", target = CrystalRendering.ROTATION))
    private void modifyRotation(Args args) {
        CrystalRendering.rotation(args);
    }

    @Redirect(method = CrystalRendering.METHOD, at = @At(value = "INVOKE", target = CrystalRendering.FRAMES, ordinal = 3))
    private void modifyCore(ModelPart modelPart, MatrixStack matrices, VertexConsumer vertices, int light, int overlay) {
        CrystalRendering.modifyCore(core, matrices, vertices, light, overlay);
    }

    @Redirect(method = CrystalRendering.METHOD, at = @At(value = "INVOKE", target = CrystalRendering.FRAMES, ordinal = 1))
    private void modifyInnerFrame(ModelPart modelPart, MatrixStack matrices, VertexConsumer vertices, int light, int overlay) {
        CrystalRendering.modifyInnerFrame(frame, matrices, vertices, light, overlay);
    }

    @Redirect(method = CrystalRendering.METHOD, at = @At(value = "INVOKE", target = CrystalRendering.FRAMES, ordinal = 2))
    private void modifyOuterFrame(ModelPart modelPart, MatrixStack matrices, VertexConsumer vertices, int light, int overlay) {
        CrystalRendering.modifyOuterFrame(frame, matrices, vertices, light, overlay);
    }
}
