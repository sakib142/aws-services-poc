package com.mktx.order.svc.gradle.configuration;

import com.mktx.order.svc.gradle.common.constants.Headers;
import com.mktx.order.svc.gradle.service.RedisCacheService;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Header;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class EmsCacheManager {

    @Autowired
    private RedisCacheService redisCacheService;


    public void cacheFirmID(@Header(Headers.EMS_FIRM_ID) String firmId, @Header(Headers.WS_CLIENT_ID) String clientID) {
        List<String> list = redisCacheService.cache(firmId);
        log.info("##The ccleinet id is:{}",clientID);
        redisCacheService.cachePut(firmId, list, clientID);

    }


}
