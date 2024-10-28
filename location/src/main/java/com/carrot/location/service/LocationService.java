package com.carrot.location.service;

import com.carrot.location.client.LocationClient;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class LocationService {

    private LocationClient locationClient;
    public LocationService(LocationClient locationClient) {
        this.locationClient = locationClient;
    }

    //유저 위치 반겅 5키로 이내 행정구역(동) 8개
    public Map<String, Object> getNearbyDongs(double latitude, double longitude, int radius) {

        Map<String, Object> userLocationList = new HashMap<>();

        String currentLocation =
                locationForCoordinates(latitude, longitude);
        userLocationList.put("currentLocation", currentLocation);

        List<String> nearbyLocations = new ArrayList<>();

        double kmToDegree = 0.009;
        double degreeOffset = kmToDegree * (radius / 1000.0);

        double[] latOffsets =
                {degreeOffset, -degreeOffset, 0.0, 0.0, degreeOffset, -degreeOffset, degreeOffset, -degreeOffset};
        double[] lonOffsets =
                {0.0, 0.0, degreeOffset, -degreeOffset, degreeOffset, degreeOffset, -degreeOffset, -degreeOffset};

        for (int i = 0; i < 8; i++) {
            double newLat = latitude + latOffsets[i];
            double newLon = longitude + lonOffsets[i];
            String nearbyAddress = locationForCoordinates(newLat, newLon);
            nearbyLocations.add(nearbyAddress);
        }

        userLocationList.put("nearbyLocations", nearbyLocations);

        return userLocationList;
    }

    //유저 위치
    public String locationForCoordinates(double latitude, double longitude) {
        Map<String, Object> response =
                locationClient.getGeocode(String.valueOf(longitude), String.valueOf(latitude));

        List<Map<String, Object>> documents =
                (List<Map<String, Object>>) response.get("documents");

        if (documents != null && !documents.isEmpty()) {
            Map<String, Object> firstDocument = documents.get(0);
            return (String) firstDocument.get("region_3depth_name");
        } else {
            return "No data available";
        }
    }

}
