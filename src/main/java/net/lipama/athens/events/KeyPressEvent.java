package net.lipama.athens.events;

public class KeyPressEvent {
    private static final KeyPressEvent INSTANCE = new KeyPressEvent();
    public int keyCode;
    public int scanCode;
    public int action;
    public int modifiers;
    public static KeyPressEvent get(int keyCode, int scanCode, int action, int modifiers) {
        INSTANCE.keyCode = keyCode;
        INSTANCE.scanCode = scanCode;
        INSTANCE.action = action;
        INSTANCE.modifiers = modifiers;
        return INSTANCE;
    }
}
