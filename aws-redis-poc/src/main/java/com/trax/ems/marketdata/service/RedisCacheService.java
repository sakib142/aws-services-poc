package com.trax.ems.marketdata.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.trax.ems.marketdata.model.RequestDTO;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class RedisCacheService {
    
    @Cacheable(value = "socket-connections", key = "#request.data.instId", unless = "#result.size()==0")
    public List<String> existingConnections(RequestDTO request) {
        log.info("### No connectionID associated in cache with the InstID:{}", request.getData().getInstId());
        return Collections.emptyList();
    }
    
    @CachePut(value = "socket-connections", key = "#request.data.instId", unless = "#result.size()==0")
    public List<String> cache(RequestDTO request) {
        log.info("### Preparing connectionID list to cache for the InstID:{}", request.getData().getInstId());
        List<String> list = new ArrayList<>(request.getExistingConnectionIDs());
        if(request.getConnectionID() != null && !list.contains(request.getConnectionID())) {
            list.add(request.getConnectionID());
        }
        return list;
    }
    
    @CacheEvict(value = "socket-connections", key = "#request.data.instId")
    public void evictCache(RequestDTO request) {
        log.info("### Evicting the socket-connections cache for the InstID:{}", request.getData().getInstId());
    }
        
       

}
