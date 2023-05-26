package net.lipama.athens.utils;

import java.util.Iterator;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiConsumer;

public class JMap<K, V> implements Iterable<V> {
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
    public Optional<V> get(K key) {
        return Optional.ofNullable(INTERNAL.get(key));
    }
    public void iter(BiConsumer<K, V> consumer) {
        INTERNAL.forEach(consumer);
    }
    public V rm(K key) {
        return INTERNAL.remove(key);
    }
    @Override
    public Iterator<V> iterator() {
        return INTERNAL.values().iterator();
    }
}
