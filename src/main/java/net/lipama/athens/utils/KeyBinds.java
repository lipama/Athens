package net.lipama.athens.utils;

import net.lipama.athens.screens.AthensOptionsScreen;
import org.lwjgl.glfw.GLFW;

@SuppressWarnings("SpellCheckingInspection")
public class KeyBinds {
    public static KeyBind screenKey = new KeyBind("options", GLFW.GLFW_KEY_RIGHT_SHIFT);
    public static KeyBind zoomKey = new KeyBind("zoom", GLFW.GLFW_KEY_V);
    public static void registerKeybinds() {
        screenKey.setOnTick(AthensOptionsScreen::handle).register();
        zoomKey.register();
    }
}
