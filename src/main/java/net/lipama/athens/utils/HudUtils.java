package net.lipama.athens.utils;

import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.MinecraftClient;

import net.lipama.athens.*;

import java.util.ArrayList;

public class HudUtils<R extends HudUtils.Renderable> {
    private final ArrayList<R> QUEUE;
    public HudUtils() {
        this.QUEUE = new ArrayList<>();
    }
    public void add(R element) {
        QUEUE.add(element);
    }
    public void renderHudElements(MatrixStack matrices, float tickDelta) {
        for(R item : QUEUE) {
            item.render(Athens.MC, matrices, tickDelta);
        }
        QUEUE.clear();
    }

    public interface Renderable {
        void render(MinecraftClient mc, MatrixStack matrices, float tickDelta);
    }
}