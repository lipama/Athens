package net.lipama.athens.mixin;

import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.Mixin;

import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.gui.screen.*;
import net.minecraft.text.Text;

import net.lipama.athens.*;

@Mixin(TitleScreen.class)
@SuppressWarnings({"unused", "SpellCheckingInspection"})
public class TitleScreenMixin extends Screen {
    private static boolean firstTimeTitleScreen = true;

    public TitleScreenMixin(Text title) {
        super(title);
    }

    @Inject(
        method = "render",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/gui/screen/TitleScreen;drawStringWithShadow(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/font/TextRenderer;Ljava/lang/String;III)V",
            ordinal = 0
        )
    )
    private void onFullLoad(MatrixStack _matrices, int _mouseX, int _mouseY, float _delta, CallbackInfo _info) {
        if(firstTimeTitleScreen) {
            Athens.postInit();
            firstTimeTitleScreen = false;
        }
    }

    @Inject(method = "render", at = @At("TAIL"))
    private void onRender(MatrixStack matrices, int mouseX, int mouseY, float delta, CallbackInfo info) {
        if(mouseX <=50 && mouseY <= 10){
            AthensClient.MC.textRenderer.drawWithShadow(matrices,"https://discord.gg/rQC3DqQqn3",3,3, Athens.COLOR.getPacked());
        } else {
            AthensClient.MC.textRenderer.drawWithShadow(matrices,AthensClient.MOD_NAME,3,3, Athens.COLOR.getPacked());
        }
    }
}
