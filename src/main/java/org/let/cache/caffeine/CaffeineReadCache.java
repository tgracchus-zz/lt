package org.let.cache.caffeine;

import com.github.benmanes.caffeine.cache.CacheLoader;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;
import org.let.cache.ReadCache;

import java.util.concurrent.TimeUnit;

public class CaffeineReadCache<K, V> implements ReadCache<K, V> {
    private final LoadingCache<K, V> cacheSystem;

    public CaffeineReadCache(CacheLoader<K, V> cacheLoader) {
        this.cacheSystem = Caffeine.newBuilder()
                .maximumSize(1000)
                .expireAfterWrite(5, TimeUnit.MINUTES)
                .build(cacheLoader);
    }

    @Override
    public V getOrLoad(K key) {
        return cacheSystem.get(key);
    }

}
