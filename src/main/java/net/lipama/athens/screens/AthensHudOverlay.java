package net.lipama.athens.screens;

import net.lipama.athens.Athens;
import net.lipama.athens.AthensClient;
import net.lipama.athens.modules.FullBright;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.util.math.MatrixStack;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.util.Identifier;

public class AthensHudOverlay {
    private static final Identifier icon =
            new Identifier(AthensClient.MOD_ID, "icon.png");
    public static void render(MatrixStack matrices, float tickDelta, CallbackInfo info) {
        if(Athens.useHud) {
            Athens.HUD.renderHudElements(matrices, tickDelta, info);
            Athens.HUD.tick();


//            RenderSystem.setShaderColor(1, 1, 1, 1);
//            GL11.glEnable(GL11.GL_BLEND);
//            RenderSystem.setShaderTexture(0, icon);
//            DrawableHelper.drawTexture(matrices, 0, 3, 0, 0, 72, 72, 72, 72);
        }
    }

    public static void gamma(){
        if(FullBright.active) {
            AthensClient.MC.options.getGamma().setValue(69420.0);
        } else {
            AthensClient.MC.options.getGamma().setValue(1.0);
        }
    }
}
