package net.lipama.athens.systems.screens;

import net.lipama.athens.*;

import net.lipama.athens.systems.modules.modules.FullBright;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class AthensHudOverlay {
    private static final Identifier icon = new Identifier(Athens.MOD_ID, "icon.png");
    public static void render(MatrixStack matrices, float tickDelta) {
        if(Athens.Config.useHud) {
            Athens.SYSTEMS.HUD.renderHudElements(matrices, tickDelta);


//            RenderSystem.setShaderColor(1, 1, 1, 1);
//            GL11.glEnable(GL11.GL_BLEND);
//            RenderSystem.setShaderTexture(0, icon);
//            DrawableHelper.drawTexture(matrices, 0, 3, 0, 0, 72, 72, 72, 72);
        }
    }
    public static void tickFullBright() { FullBright.update(); }
}
