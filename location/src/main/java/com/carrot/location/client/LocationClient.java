package com.carrot.location.client;

import com.carrot.location.config.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@FeignClient(name = "geocodeClient", url = "https://dapi.kakao.com", configuration = FeignConfig.class)
public interface LocationClient {
    @GetMapping("/v2/local/geo/coord2regioncode.json")
    Map<String, Object> getGeocode(@RequestParam("x") String longitude, @RequestParam("y") String latitude);

}
