package net.lipama.athens.utils;

import org.lwjgl.glfw.GLFW;

@SuppressWarnings("SpellCheckingInspection")
public class KeyBinds {
    public static KeyBind zoomKey = new KeyBind("zoom", GLFW.GLFW_KEY_V);
    public static void registerKeybinds() {
        zoomKey.register();
    }
}
