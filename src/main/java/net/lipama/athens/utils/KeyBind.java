package net.lipama.athens.utils;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;

import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.*;

import java.util.function.Consumer;

@SuppressWarnings({"SpellCheckingInspection", "unused", "UnusedAssignment"})
public class KeyBind {
    private static final String CATEGORY = "key.categories.athens";
    private static final String PREFIX = "key.athens.";
    private Consumer<MinecraftClient> func;
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

    public KeyBind setOnTick(Consumer<MinecraftClient> func) {
        this.func = func;
        return this;
    }

    public void register() {
        update();
        KeyBindingHelper.registerKeyBinding(internalKeybind);
        ClientTickEvents.END_CLIENT_TICK.register(client->this.func.accept(client));
    }


    private void update() {
        if(this.id==null||!this.id.startsWith("key.athens.")){
            this.id = "key.athens.__UNREGISTERED";
        }
        if(this.key==-1){
            this.key = 344;
        }
        if(this.type==null){
            this.type = InputUtil.Type.KEYSYM;
        }
        if(this.func==null){
            this.func = (client) -> {};
        }
        this.internalKeybind = new KeyBinding(this.id,this.type,this.key,KeyBind.CATEGORY);
    }
}
