package net.lipama.athens.systems.screens;

import net.lipama.athens.systems.modules.modules.FullBright;
import net.lipama.athens.*;

import net.minecraft.client.util.math.MatrixStack;

public class AthensHudOverlay {
    public static void render(MatrixStack matrices, float tickDelta) {
//        if(Athens.Config.useHud) {
            Athens.SYSTEMS.HUD.renderHudElements(matrices, tickDelta);
//        }
    }
    public static void tickFullBright() {
        FullBright.update();
    }
}
