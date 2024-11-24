package com.spring_boot.caching.clearCache;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;
import org.springframework.cache.Cache;

@Component
@Data
@RequiredArgsConstructor
public class CacheClass {
    private final CacheManager cacheManager;
    public void clearSpecificCache(String cacheName,Long Id) {
        Cache cache = cacheManager.getCache(cacheName);
        if(cache != null) {
            cache.evict(Id);
        }
    }
}
