package net.lipama.athens.systems.modules.modules.chams;

import net.lipama.chams.api.ChamsSettings;
import net.lipama.chams.api.Color;

import static net.minecraft.client.render.entity.EndCrystalEntityRenderer.getYOffset;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;
import net.minecraft.entity.decoration.EndCrystalEntity;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.model.ModelPart;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.Identifier;

public class CrystalRendering extends Rendering {
    public static final String METHOD = "render(Lnet/minecraft/entity/decoration/EndCrystalEntity;FFLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V";
    public static final String FRAMES = "Lnet/minecraft/client/model/ModelPart;render(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumer;II)V";
    public static final String SCALE = "Lnet/minecraft/client/util/math/MatrixStack;scale(FFF)V";
    public static final String BOUNCE = "Lnet/minecraft/client/render/entity/EndCrystalEntityRenderer;getYOffset(Lnet/minecraft/entity/decoration/EndCrystalEntity;F)F";
    public static final String ROTATION = "Lnet/minecraft/util/math/RotationAxis;rotationDegrees(F)Lorg/joml/Quaternionf;";

    public static RenderLayer renderLayer(Identifier originalTexture) {
        return RenderLayer.getEntityTranslucent(
            (a() && cs().enabled && !cs().textureCrystals) ? ChamsModule.BLANK : originalTexture
        );
    }

    public static void scale(Args args) {
        if (!a() || !cs().enabled) return;

        args.set(0, 2.0F * ((float) cs().crystalScale));
        args.set(1, 2.0F * ((float) cs().crystalScale));
        args.set(2, 2.0F * ((float) cs().crystalScale));
    }

    public static float bounce(EndCrystalEntity crystal, float tickDelta) {
        if (!a() || !cs().enabled) return getYOffset(crystal, tickDelta);

        float f = (float) crystal.endCrystalAge + tickDelta;
        float g = MathHelper.sin(f * 0.2F) / 2.0F + 0.5F;
        g = (g * g + g) * 0.4F * ((float) cs().crystalBounceHeight);
        return g - 1.4F;
    }

    public static void rotation(Args args) {
        if (!a() || !cs().enabled) return;
        args.set(0, ((float) args.get(0)) * ((float) cs().crystalRotateSpeed));
    }

    public static void modifyInnerFrame(ModelPart frame, MatrixStack matrices, VertexConsumer vertices, int light, int overlay) {
        modifyFrame(cs().renderInnerFrame, cs().innerFrameColor, frame, matrices, vertices, light, overlay);
    }

    public static void modifyOuterFrame(ModelPart frame, MatrixStack matrices, VertexConsumer vertices, int light, int overlay) {
        modifyFrame(cs().renderOuterFrame, cs().outerFrameColor, frame, matrices, vertices, light, overlay);
    }

    public static void modifyCore(ModelPart core, MatrixStack matrices, VertexConsumer vertices, int light, int overlay) {
        modifyFrame(cs().renderCrystalCore, cs().crystalCoreColor, core, matrices, vertices, light, overlay);
    }

    private static void modifyFrame(
        boolean shouldRender, Color color,
        ModelPart model, MatrixStack matrices, VertexConsumer vertices, int light, int overlay
    ) {
        if (!a() || !cs().enabled) {
            model.render(matrices, vertices, light, overlay);
            return;
        }
        if (shouldRender) {
            model.render(matrices, vertices, light, overlay, color.r / 255f, color.g / 255f, color.b / 255f, color.a / 255f);
        }
    }
}
