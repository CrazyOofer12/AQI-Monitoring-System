package com.aqi.backend.model;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;


@Document(collection = "places")
public class LocationData {

    @Id
    private String id;

    private String country;
    private String state;
    private String city;
    private String station;
    private String lastUpdate;
    private String latitude;
    private String longitude;

    private List<Pollutant> pollutants;

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

    public List<Pollutant> getPollutants() { return pollutants; }
    public void setPollutants(List<Pollutant> pollutants) { this.pollutants = pollutants; }

    public static class Pollutant {
        private String pollutantId;
        private String minValue;
        private String maxValue;
        private String avgValue;

        public String getPollutantId() { return pollutantId; }
        public void setPollutantId(String pollutantId) { this.pollutantId = pollutantId; }

        public String getMinValue() { return minValue; }
        public void setMinValue(String minValue) { this.minValue = minValue; }

        public String getMaxValue() { return maxValue; }
        public void setMaxValue(String maxValue) { this.maxValue = maxValue; }

        public String getAvgValue() { return avgValue; }
        public void setAvgValue(String avgValue) { this.avgValue = avgValue; }
    }

}
