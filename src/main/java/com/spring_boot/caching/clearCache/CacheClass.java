package com.spring_boot.caching.clearCache;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;
@Component
public class CacheClass {

//    private final HazelcastInstance hazelcastInstance;
//    @Autowired
//    private CacheManager cacheManager;
//
//    public CacheClass() {
//        this.hazelcastInstance = Hazelcast.newHazelcastInstance();
//    }
//
//    public void clearSpecificCache(String cacheName,Long Id) {
//        hazelcastInstance.getMap(cacheName).remove(Id);
//    }
//    public void clearAllCaches() {
//        cacheManager.getCacheNames().forEach(cacheName -> {
//            Cache cache = cacheManager.getCache(cacheName);
//            if (cache != null) {
//                cache.clear();
//            }
//        });
//
//        hazelcastInstance.getDistributedObjects().forEach(distributedObject -> {
//            if (distributedObject instanceof com.hazelcast.map.IMap) {
//                ((com.hazelcast.map.IMap<?, ?>) distributedObject).clear();
//            }
//        });
//    }

}
