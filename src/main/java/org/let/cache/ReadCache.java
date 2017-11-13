package org.let.cache;

public interface ReadCache<K,V> {
    V getOrLoad(K key);
}
