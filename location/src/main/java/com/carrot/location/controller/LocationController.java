package com.carrot.location.controller;


import com.carrot.location.service.LocationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/locations")
public class LocationController {

    private LocationService locationService;

    public LocationController(LocationService locationService) {
        this.locationService = locationService;
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> getNearbyDongs(
            @RequestBody Map<String, Object> coordinates
    ) {
        double latitude = (double) coordinates.get("latitude");
        double longitude = (double) coordinates.get("longitude");
        int radius = (int) coordinates.get("radius");

        Map<String, Object> addresses =
                locationService.getNearbyDongs(latitude, longitude, radius);

        return ResponseEntity.ok(addresses);
    }
}
