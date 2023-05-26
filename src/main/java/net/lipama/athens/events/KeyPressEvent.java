package net.lipama.athens.events;

import java.util.ArrayList;

public class KeyPressEvent {
    public interface Event {
        void onKeyPress(KeyPressEvent event);
    }
    private static final ArrayList<KeyPressEvent.Event> EVENTS = new ArrayList<>();
    public static void call(KeyPressEvent keyPressEvent) {
        for(KeyPressEvent.Event event : EVENTS) {
            event.onKeyPress(keyPressEvent);
        }
    }
    public static void subscribe(KeyPressEvent.Event event) {
        EVENTS.add(event);
    }
    public final int keyCode;
    public final int scanCode;
    public final int action;
    public final int modifiers;
    public KeyPressEvent(int keyCode, int scanCode, int action, int modifiers) {
        this.keyCode = keyCode;
        this.scanCode = scanCode;
        this.action = action;
        this.modifiers = modifiers;
    }
}
