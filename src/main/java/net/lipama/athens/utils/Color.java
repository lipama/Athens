package net.lipama.athens.utils;

@SuppressWarnings({"unused", "SpellCheckingInspection"})
public class Color {
    public static final Color WHITE = new Color(java.awt.Color.WHITE);
    public static final Color LIGHT_GRAY = new Color(java.awt.Color.LIGHT_GRAY);
    public static final Color GRAY = new Color(java.awt.Color.GRAY);
    public static final Color DARK_GRAY = new Color(java.awt.Color.DARK_GRAY);
    public static final Color BLACK = new Color(java.awt.Color.BLACK);
    public static final Color RED = new Color(java.awt.Color.RED);
    public static final Color PINK = new Color(java.awt.Color.PINK);
    public static final Color ORANGE = new Color(java.awt.Color.ORANGE);
    public static final Color YELLOW = new Color(java.awt.Color.YELLOW);
    public static final Color GREEN = new Color(java.awt.Color.GREEN);
    public static final Color MAGENTA = new Color(java.awt.Color.MAGENTA);
    public static final Color CYAN = new Color(java.awt.Color.CYAN);
    public static final Color BLUE = new Color(java.awt.Color.BLUE);

    public int r, g, b, a;

    public Color() {
        this(255, 255, 255, 255);
    }

    public Color(int r, int g, int b, int a) {
        this.r = r;
        this.g = g;
        this.b = b;
        this.a = a;

        validate();
    }

    public Color(java.awt.Color color) {
        this.r = color.getRed();
        this.g = color.getGreen();
        this.b = color.getBlue();
        this.a = color.getAlpha();
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

    public void validate() {
        if (r < 0) r = 0;
        else if (r > 255) r = 255;

        if (g < 0) g = 0;
        else if (g > 255) g = 255;

        if (b < 0) b = 0;
        else if (b > 255) b = 255;

        if (a < 0) a = 0;
        else if (a > 255) a = 255;
    }

    public int getPacked() {
        return fromRGBA(r, g, b, a);
    }

    @Override
    public String toString() {
        return r + " " + g + " " + b + " " + a;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Color color = (Color) o;

        return r == color.r && g == color.g && b == color.b && a == color.a;
    }
}
