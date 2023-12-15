package net.lipama.athens.mixin;

import net.lipama.athens.events.TickEvent;
import net.minecraft.client.gui.DrawContext;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.Mixin;

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
    private void onFullLoad(DrawContext _context, int _mouseX, int _mouseY, float _delta, CallbackInfo _info) {
        if(firstTimeTitleScreen) {
            Athens.COMPOSER.post(TickEvent.Post.get());
            firstTimeTitleScreen = false;
        }
    }

    @Inject(method = "render", at = @At("TAIL"))
    private void onRender(DrawContext context, int mouseX, int mouseY, float delta, CallbackInfo info) {
        if(mouseX <=50 && mouseY <= 10){
            context.drawText(
                Athens.MC.textRenderer, "https://discord.gg/rQC3DqQqn3",
                3,3, Athens.SYSTEMS.COLOR.getPacked(), true
            );
        } else {
            context.drawText(
                Athens.MC.textRenderer, Athens.MOD_NAME,
                3,3, Athens.SYSTEMS.COLOR.getPacked(), true
            );
        }
    }
}
