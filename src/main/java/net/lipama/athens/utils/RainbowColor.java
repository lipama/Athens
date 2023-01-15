package net.lipama.athens.utils;

@SuppressWarnings({"unused", "UnusedReturnValue"})
public class RainbowColor extends Color {
    private double speed;
    private static final float[] hsb = new float[3];

    public RainbowColor() {
        super();
    }

    public RainbowColor setSpeed(double speed) {
        this.speed = speed;
        return this;
    }

    public RainbowColor getNext() {
        if( speed <= 0 ) return this;
        java.awt.Color.RGBtoHSB(r, g, b, hsb);
        int c = java.awt.Color.HSBtoRGB(hsb[0] + (float) (speed), 1, 1);

        r = toRGBAR(c);
        g = toRGBAG(c);
        b = toRGBAB(c);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        return Double.compare(((RainbowColor) o).speed, speed) == 0;
    }
}
