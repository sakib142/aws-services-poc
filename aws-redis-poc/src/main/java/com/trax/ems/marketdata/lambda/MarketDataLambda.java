package com.trax.ems.marketdata.lambda;

import java.util.List;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.trax.ems.marketdata.model.RequestDTO;
import com.trax.ems.marketdata.model.ResponseDTO;
import com.trax.ems.marketdata.service.RedisCacheService;

@Component
public class MarketDataLambda implements Function<RequestDTO, ResponseDTO> {

    @Autowired
    private RedisCacheService redisCacheService;

    @Override
    public ResponseDTO apply(RequestDTO request) {

        ResponseDTO response = new ResponseDTO();
        response.setInstId(request.getData().getInstId());

        switch (request.getMethod()) {
            case "GET":
                List<String> result = redisCacheService.existingConnections(request);
                
                if(!result.contains(request.getConnectionID())) {
                    request.setExistingConnectionIDs(result);
                    result = redisCacheService.cache(request);
                }
                
                response.setConnectionIDs(result);
                return response;

            case "DELETE":
                redisCacheService.evictCache(request);
                break;

            default:
                break;
        }

        response.setConnectionIDs(redisCacheService.existingConnections(request));
        return response;
    }

}
