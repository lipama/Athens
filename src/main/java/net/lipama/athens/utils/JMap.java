package net.lipama.athens.utils;

import java.util.concurrent.*;
import java.util.function.*;
import java.util.*;

public class JMap<K, V> extends ConcurrentHashMap<K, V> {
    private final ConcurrentHashMap<K,V> INTERNAL;
    private JMap() {
        INTERNAL = new ConcurrentHashMap<>();
    }
    public static <K, V> JMap<K, V> init() {
        return new JMap<>();
    }
    public void add(K key, V value) {
        INTERNAL.put(key, value);
    }
    public Optional<V> g(K key) {
        return Optional.ofNullable(INTERNAL.get(key));
    }
    public void iter(BiConsumer<K, V> consumer) {
        INTERNAL.forEach(consumer);
    }
    public V rm(K key) {
        return INTERNAL.remove(key);
    }
}
