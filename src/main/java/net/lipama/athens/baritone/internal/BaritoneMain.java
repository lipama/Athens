package net.lipama.athens.baritone.internal;

public class BaritoneMain {
    public final Baritone baritone;
    public static BaritoneMain self;
    private BaritoneMain(Baritone baritone) { this.baritone = baritone; }
    private void run() {
        // All the baritone code should go here
    }



    public static void Main(Baritone baritone) {
        assert Baritone.BARITONE_LOADED;
        self = new BaritoneMain(baritone);
        self.run();
    }
}
