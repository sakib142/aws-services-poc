package com.mktx.order.svc.gradle.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class RedisCacheService {
    
    @Cacheable(value = "firmID-connections", key = "#firmId", unless = "#result.size()==0")
    public List<String> cache(String firmId) {
        log.info("### No connectionID associated in cache with the FirmID:{}", firmId);
        return new ArrayList<String>();
    }
    
    @CachePut(value = "firmID-connections", key = "#firmId", unless = "#result.size()==0")
    public List<String> cachePut(String firmId, List<String> clientIDs, String clientID) {
        log.info("### Preparing connectionID list to cache for the FirmID:{}", firmId);
        if(!clientIDs.contains(clientID)) {

            clientIDs.add(clientID);

        }
        return clientIDs;
    }

       

}