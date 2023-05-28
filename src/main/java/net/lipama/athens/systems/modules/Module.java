package net.lipama.athens.systems.modules;

import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;

import net.lipama.athens.systems.screens.AthensOptionsScreen;
import net.lipama.athens.utils.*;
import net.lipama.athens.*;

public abstract class Module implements HudUtils.Renderable {
    private static final int TOP_PADDING = 25;
    private static final int SIDE_PADDING = 5;
    private static final int BOX_SIZE = 150;
    private static class Point {
        public int x;
        public int y;
        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
    protected static class Position {
        private final int indexFromTop;
        private final boolean left;
        private Position(int indexFromTop, boolean left) {
            this.indexFromTop = indexFromTop;
            this.left = left;
        }
        public static Position Custom(boolean left, int indexFromTop) {
            return new Position(indexFromTop, left);
        }
        public static Position Left(int indexFromTop) {
            return Custom(true, indexFromTop);
        }
        public static Position Right(int indexFromTop) {
            return Custom(false, indexFromTop);
        }
        public Point update(int width) {
            int y = TOP_PADDING + 25 * this.indexFromTop;
            int center = width / 2;
            if(this.left) return new Point(center - (SIDE_PADDING + BOX_SIZE), y);
            return new Point(center + SIDE_PADDING, y);
        }
    }
    protected Position position = Position.Left(0);
    protected int hudHeight = 5;
    private final String NAME;
    protected boolean enabled;
    public Module(String name) {
        this.NAME = name;
    }
    public Text status() {
        if(this.enabled){
            return Text.of(NAME + " is enabled");
        } else {
            return Text.of(NAME + " is disabled");
        }
    }
    public String name() {
        return this.NAME;
    }
    public String id() {
        return name().toLowerCase();
    }
    public ButtonWidget buildButtonWidget() {
        ButtonWidget.Builder button = new ButtonWidget.Builder(this.status(), (btn) -> {
            this.toggle();
            btn.setMessage(this.status());
        });
        Point pos = this.position.update(AthensOptionsScreen.WIDTH);
        button.dimensions(
//            AthensOptionsScreen.WIDTH / 2 - this.position.x,
            pos.x,
//            AthensOptionsScreen.HEIGHT / 2 - this.position.y,
            pos.y,
                BOX_SIZE,
            20
        );
        return button.build();
    }
    public boolean getEnabled() {
        return this.enabled;
    }
    public void enable() {
        this.enabled = true;
        Athens.COMPOSER.subscribe(this);
        this.onEnable();
    }
    public void disable() {
        this.enabled = false;
        Athens.COMPOSER.unsubscribe(this);
        this.onDisable();
    }
    public void toggle() {
        if(enabled) {
            this.disable();
        } else {
            this.enable();
        }
    }

    public abstract void onEnable();
    public abstract void onDisable();

    @Override
    public void render(MinecraftClient mc, MatrixStack matrices, float tickDelta) {
        if(this.enabled) {
            mc.textRenderer.draw(matrices, this.NAME, 5, hudHeight, Athens.SYSTEMS.COLOR.getPacked());
        }
    }
}
