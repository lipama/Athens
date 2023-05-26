package net.lipama.athens.mixin;

import net.lipama.athens.events.TickEvent;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.Mixin;

import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.gui.screen.*;
import net.minecraft.text.Text;

import net.lipama.athens.*;

@Mixin(TitleScreen.class)
@SuppressWarnings({"unused"})
public class TitleScreenMixin extends Screen {
    private static boolean firstTimeTitleScreen = true;

    public TitleScreenMixin(Text title) {
        super(title);
    }

    @Inject(
        method = "render",
        at = @At("HEAD")
    )
    private void onFullLoad(MatrixStack _matrices, int _mouseX, int _mouseY, float _delta, CallbackInfo _info) {
        if(firstTimeTitleScreen) {
            Athens.COMPOSER.post(TickEvent.Post.get());
            firstTimeTitleScreen = false;
        }
    }

    @Inject(method = "render", at = @At("TAIL"))
    private void onRender(MatrixStack matrices, int mouseX, int mouseY, float delta, CallbackInfo info) {
        if(mouseX <=50 && mouseY <= 10){
            Athens.MC.textRenderer.drawWithShadow(
                matrices,"https://discord.gg/rQC3DqQqn3",3,3, Athens.SYSTEMS.COLOR.getPacked()
            );
        } else {
            Athens.MC.textRenderer.drawWithShadow(
                matrices, Athens.MOD_NAME,3,3, Athens.SYSTEMS.COLOR.getPacked()
            );
        }
    }
}
