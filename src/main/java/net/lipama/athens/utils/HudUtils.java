package net.lipama.athens.utils;

import net.lipama.athens.Athens;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.MinecraftClient;

import net.lipama.athens.AthensClient;

import java.util.ArrayList;

public class HudUtils<R extends HudUtils.Renderable> {
    private final ArrayList<R> QUEUE;
    public HudUtils() {
        this.QUEUE = new ArrayList<>();
    }

    public void add(R element) {
        QUEUE.add(element);
    }

    public void renderHudElements(MatrixStack matrices, float tickDelta, CallbackInfo info) {
        for(R item : QUEUE) {
            item.render(AthensClient.MC,matrices,tickDelta,info);
        }
        QUEUE.clear();
    }
    public void tick() {
        Athens.MODULES.tick();
    }

    public interface Renderable {
        void render(MinecraftClient mc, MatrixStack matrices, float tickDelta, CallbackInfo info);
    }
}