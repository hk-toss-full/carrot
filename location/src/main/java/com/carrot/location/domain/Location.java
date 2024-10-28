package com.carrot.location.domain;

import lombok.Getter;

@Getter
public class Location {
    private Long id;
    private double x;
    private double y;
    private String region_1depth_name;
    private String region_2depth_name;
    private String region_3depth_name;
}
