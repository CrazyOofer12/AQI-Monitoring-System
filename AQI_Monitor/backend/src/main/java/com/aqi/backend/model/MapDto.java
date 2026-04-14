package com.aqi.backend.model;

public class MapDto{

    private String city;
    private String station;
    private double lat;
    private double lon;
    private int aqi;

    public MapDto(String city, String station, double lat, double lon, int aqi) {
        this.city = city;
        this.station = station;
        this.lat = lat;
        this.lon = lon;
        this.aqi = aqi;
    }

    public String getCity() { return city; }
    public String getStation() { return station; }
    public double getLat() { return lat; }
    public double getLon() { return lon; }
    public int getAqi() { return aqi; }
}