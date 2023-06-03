package net.lipama.athens.systems.modules.modules.chams;

import net.lipama.athens.Athens;
import net.lipama.chams.api.Color;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

public class LivingEntityRendering extends Rendering {
    public static final String METHOD = "render(Lnet/minecraft/entity/LivingEntity;FFLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V";
    public static final String SCALE = "Lnet/minecraft/client/util/math/MatrixStack;scale(FFF)V";
    public static final String COLOR = "Lnet/minecraft/client/render/entity/model/EntityModel;render(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumer;IIFFFF)V";
    public static final String LAYER = "Lnet/minecraft/client/render/entity/LivingEntityRenderer;getRenderLayer(Lnet/minecraft/entity/LivingEntity;ZZZ)Lnet/minecraft/client/render/RenderLayer;";

    public static void modifyScale(Args args, LivingEntity livingEntity) {
        if (!a() || !p().enabled || !(livingEntity instanceof PlayerEntity)) return;
        if (p().ignoreSelf && livingEntity == Athens.MC.player) return;

        args.set(0, -((float) p().playerModelScale));
        args.set(1, -((float) p().playerModelScale));
        args.set(2, ((float) p().playerModelScale));
    }
    public static void modifyColor(Args args, LivingEntity livingEntity) {
        if (!a() || !p().enabled || !(livingEntity instanceof PlayerEntity)) return;
        if (p().ignoreSelf && livingEntity == Athens.MC.player) return;

        Color color = p().playerModelColor;
        args.set(4, color.r / 255f);
        args.set(5, color.g / 255f);
        args.set(6, color.b / 255f);
        args.set(7, color.a / 255f);
    }
    public static <T extends LivingEntity> RenderLayer renderLayer(
        GetRenderLayerFunc<T> f, T livingEntity, boolean showBody, boolean translucent, boolean showOutline
    ) {
        if (!a() || !p().enabled || !(livingEntity instanceof PlayerEntity) || p().texturePlayers)
            return f.get(livingEntity, showBody, translucent, showOutline);
        if (p().ignoreSelf && livingEntity == Athens.MC.player)
            return f.get(livingEntity, showBody, translucent, showOutline);
        return RenderLayer.getItemEntityTranslucentCull(ChamsModule.BLANK);
    }

    @FunctionalInterface
    public interface GetRenderLayerFunc<T extends LivingEntity> {
        RenderLayer get(T entity, boolean showBody, boolean translucent, boolean showOutline);
    }
}
