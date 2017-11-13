package org.let.cache;

public interface ReadThroughCache<K,V> {
    V getOrLoad(K key);
}
