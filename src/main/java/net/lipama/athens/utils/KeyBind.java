package net.lipama.athens.utils;

import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;

import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.*;

import net.lipama.athens.AthensClient;

@SuppressWarnings({"SpellCheckingInspection", "unused", "UnusedAssignment"})
public class KeyBind {
    private static final String CATEGORY = "key.categories." + AthensClient.MOD_ID;
    private static final String PREFIX = "key." + AthensClient.MOD_ID + ".";
    private KeyBinding internalKeybind;
    private int key = -1;
    private String id;
    private InputUtil.Type type;

    public KeyBind(String id, int key) {
        this.id = PREFIX + id;
        this.key = key;
        update();
    }
    public KeyBind(String id, int key, InputUtil.Type type) {
        this.id = PREFIX + id;
        this.type = type;
        this.key = key;
        update();
    }
    public boolean wasPressed() {
        return this.internalKeybind.wasPressed();
    }
    public boolean isPressed() {
        return this.internalKeybind.isPressed();
    }
    public void register() {
        update();
        KeyBindingHelper.registerKeyBinding(internalKeybind);
    }


    private void update() {
        if(this.id==null||!this.id.startsWith(PREFIX)){
            this.id = "key.athens.__UNREGISTERED";
        }
        if(this.key==-1){
            this.key = 344;
        }
        if(this.type==null){
            this.type = InputUtil.Type.KEYSYM;
        }
        this.internalKeybind = new KeyBinding(this.id,this.type,this.key,KeyBind.CATEGORY);
    }
}
