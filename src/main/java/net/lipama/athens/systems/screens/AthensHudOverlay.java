package net.lipama.athens.systems.screens;

import net.lipama.athens.systems.modules.modules.FullBright;
import net.lipama.athens.*;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.util.math.MatrixStack;

public class AthensHudOverlay {
    public static void render(DrawContext context, float tickDelta) {
//        if(Athens.Config.useHud) {
            Athens.SYSTEMS.HUD.renderHudElements(context, tickDelta);
//        }
    }
    public static void tickFullBright() {
        FullBright.update();
    }
}
