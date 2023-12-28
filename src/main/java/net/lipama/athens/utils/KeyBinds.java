package net.lipama.athens.utils;

import net.lipama.athens.events.system.PreInitEvent;

import net.titanium.composer.*;

import org.lwjgl.glfw.GLFW;

public class KeyBinds {
    public static KeyBind.CustomizableKeyBind zoomKey = KeyBind.generateCustomizable("zoom", GLFW.GLFW_KEY_V);
    public static KeyBind.ExplicitKeyBind moduleKey = KeyBind.generateExplicit(GLFW.GLFW_KEY_RIGHT_SHIFT);
//    public static KeyBind.CustomizableKeyBind flightKey = KeyBind.generateCustomizable("flight", GLFW.GLFW_KEY_PERIOD);
//    public static KeyBind.CustomizableKeyBind xrayKey = KeyBind.generateCustomizable("xray", GLFW.GLFW_KEY_X);
    @EventHandler
    public static void onPreInit(PreInitEvent event) {
        zoomKey.register();
        moduleKey.register();
//        flightKey.register();
//        xrayKey.register();
    }
}
