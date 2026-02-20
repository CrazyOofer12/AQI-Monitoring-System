package com.aqi.backend.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GovtRecord {

    private String country;
    private String state;
    private String city;
    private String station;

    @JsonProperty("last_update")
    private String lastUpdate;

    private String latitude;
    private String longitude;

    @JsonProperty("pollutant_id")
    private String pollutantId;

    @JsonProperty("min_value")
    private String minValue;

    @JsonProperty("max_value")
    private String maxValue;

    @JsonProperty("avg_value")
    private String avgValue;

    // Getters & Setters

    public String getCountry() { return country; }
    public void setCountry(String country) { this.country = country; }

    public String getState() { return state; }
    public void setState(String state) { this.state = state; }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public String getStation() { return station; }
    public void setStation(String station) { this.station = station; }

    public String getLastUpdate() { return lastUpdate; }
    public void setLastUpdate(String lastUpdate) { this.lastUpdate = lastUpdate; }

    public String getLatitude() { return latitude; }
    public void setLatitude(String latitude) { this.latitude = latitude; }

    public String getLongitude() { return longitude; }
    public void setLongitude(String longitude) { this.longitude = longitude; }

    public String getPollutantId() { return pollutantId; }
    public void setPollutantId(String pollutantId) { this.pollutantId = pollutantId; }

    public String getMinValue() { return minValue; }
    public void setMinValue(String minValue) { this.minValue = minValue; }

    public String getMaxValue() { return maxValue; }
    public void setMaxValue(String maxValue) { this.maxValue = maxValue; }

    public String getAvgValue() { return avgValue; }
    public void setAvgValue(String avgValue) { this.avgValue = avgValue; }
}