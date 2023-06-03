package net.lipama.athens.utils;

import net.lipama.athens.events.*;
import net.lipama.chams.api.*;
import net.lipama.athens.*;

import net.titanium.composer.*;

public class RainbowColor extends Color {
    private double speed;
    private static final float[] hsb = new float[3];

    public RainbowColor() {
        super();
        Athens.COMPOSER.subscribe(this);
    }

    public RainbowColor setSpeed(double speed) {
        this.speed = speed;
        return this;
    }

    public static int getPacked(Color color) {
        return fromRGBA(color.r, color.g, color.b, color.a);
    }

    public static Color fromPacked(int packed) {
        return new Color(
            toRGBAR(packed),
            toRGBAG(packed),
            toRGBAB(packed),
            toRGBAA(packed)
        );
    }

    public int getPacked() {
        return getPacked(this);
    }

    public RainbowColor getNext() {
        if (speed <= 0) return this;
        java.awt.Color.RGBtoHSB(r, g, b, hsb);
        int c = java.awt.Color.HSBtoRGB(hsb[0] + (float) (speed), 1, 1);

        r = toRGBAR(c);
        g = toRGBAG(c);
        b = toRGBAB(c);
        return this;
    }

    public static int fromRGBA(int r, int g, int b, int a) {
        return (r << 16) + (g << 8) + (b) + (a << 24);
    }

    public static int toRGBAR(int color) {
        return (color >> 16) & 0x000000FF;
    }

    public static int toRGBAG(int color) {
        return (color >> 8) & 0x000000FF;
    }

    public static int toRGBAB(int color) {
        return (color) & 0x000000FF;
    }

    public static int toRGBAA(int color) {
        return (color >> 24) & 0x000000FF;
    }

    @EventHandler
    @SuppressWarnings("unused")
    private void onPostTick(TickEvent.Post event) {
        this.getNext();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        return Double.compare(((RainbowColor) o).speed, speed) == 0;
    }
}
