package net.lipama.athens.events;

public class TickEvent {
    public static class Pre {
        private static final Pre INSTANCE = new Pre();
        public static Pre get() { return INSTANCE; }
    }

    public static class Post {
        private static final Post INSTANCE = new Post();
        public static Post get() { return INSTANCE; }
    }
}
