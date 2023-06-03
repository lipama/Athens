package net.lipama.athens.systems.screens;

import net.lipama.athens.Athens;
import net.lipama.athens.systems.modules.Modules;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.math.MatrixStack;

import java.util.ArrayList;

public class AthensHud {
    private static final ArrayList<Renderable> QUEUE = new ArrayList<>();
    public static final int MODULE_NAME_PADDING = 10;
    public static void add(Renderable renderable) {
        QUEUE.add(renderable);
    }
    public AthensHud() {

    }
    public void init() {
        Modules.MODULES.iter((_name, m) -> add(m));
    }
    public void renderHudElements(MatrixStack matrices, float tickDelta) {
        int i = 5;
        for(Renderable item : QUEUE) {
            i += item.render(i, Athens.MC, matrices, tickDelta);
        }
    }
    public interface Renderable {
        int render(int level, MinecraftClient mc, MatrixStack matrices, float tickDelta);
    }
}
