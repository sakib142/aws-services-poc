package com.trax.ems.marketdata.controller;

import com.trax.ems.marketdata.model.RequestDTO;
import com.trax.ems.marketdata.model.ResponseDTO;
import com.trax.ems.marketdata.service.RedisCacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class RedisController {

    @Autowired
    private RedisCacheService redisCacheService;

    ResponseDTO response = new ResponseDTO();

    @GetMapping
    public ResponseDTO getCacheValue(@RequestBody RequestDTO request){
        System.out.println("RedisController Controller start GET ");
        List<String> result = redisCacheService.existingConnections(request);

        if(!result.contains(request.getConnectionID())) {
            request.setExistingConnectionIDs(result);
            result = redisCacheService.cache(request);
        }

        response.setConnectionIDs(result);
        return response;
    }

    @DeleteMapping
    public String getDeleteCacheValue(@RequestBody RequestDTO request){
        System.out.println("RedisController Controller start DELETE ");
        redisCacheService.evictCache(request);
        return "Delete";
    }
}
