package net.lipama.athens.utils;

import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;

import net.lipama.athens.Athens;
import net.lipama.athens.events.KeyPressEvent;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.*;

import java.util.function.Consumer;


public class KeyBind {
    public static CustomizableKeyBind generateCustomizable(String id, int key) {
        return generateCustomizable(id, key, null);
    }

    public static CustomizableKeyBind generateCustomizable(String id, int key, InputUtil.Type type) {
        return new CustomizableKeyBind(id, key, type);
    }

    public static ExplicitKeyBind generateExplicit(int key) {
        return new ExplicitKeyBind(key);
    }

    interface IKeyBind {
        void register();
    }

    private static final String CATEGORY = "key.categories." + Athens.MOD_ID;
    private static final String PREFIX = "key." + Athens.MOD_ID + ".";

    public static class ExplicitKeyBind implements IKeyBind {
        private boolean registered = false;
        public final int key;
        private ExplicitKeyBind() {
            this.registered = false;
            this.key = 344;
        }
        private ExplicitKeyBind(int key) {
            this.registered = false;
            this.key = key;
        }
        @Override
        public void register() {
            this.registered = true;
        }
        public void subscribe(KeyPressEvent.Event subscriber) {
            if(!registered) return;
            KeyPressEvent.subscribe(subscriber);
        }
        public void registerListener(Consumer<KeyPressEvent> listener) {
            if(!registered) return;
            this.subscribe(listener::accept);
        }
        public boolean isCorrect(KeyPressEvent event) {
            return event.keyCode == this.key;
        }
        public boolean isWrong(KeyPressEvent event) {
            return !isCorrect(event);
        }
    }

    @SuppressWarnings({"SpellCheckingInspection", "unused", "UnusedAssignment"})
    public static class CustomizableKeyBind implements IKeyBind {
        private KeyBinding internalKeybind;
        private int key = -1;
        private String id;
        private InputUtil.Type type;

        private CustomizableKeyBind(String id, int key, InputUtil.Type type) {
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

        @Override
        public void register() {
            update();
            KeyBindingHelper.registerKeyBinding(internalKeybind);
        }


        private void update() {
            if (this.id == null || !this.id.startsWith(PREFIX)) {
                this.id = "key.athens.__UNREGISTERED";
            }
            if (this.key == -1) {
                this.key = 344;
            }
            if (this.type == null) {
                this.type = InputUtil.Type.KEYSYM;
            }
            this.internalKeybind = new KeyBinding(this.id, this.type, this.key, CATEGORY);
        }
    }
}
