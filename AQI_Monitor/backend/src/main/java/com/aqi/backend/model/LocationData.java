package com.aqi.backend.model;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexType;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;
import org.springframework.data.mongodb.core.mapping.Document;
import java.io.Serializable;
import java.util.List;


@Document(collection = "places")
public class LocationData implements Serializable {

    @Id
    private String id;

    private String country;
    private String state;
    private String city;
    private String station;
    private String lastUpdate;

    @GeoSpatialIndexed(type= GeoSpatialIndexType.GEO_2DSPHERE)
    private GeoJsonPoint location;

    private GeoJsonPoint getLocation() {
        return location;
    }

    private List<Pollutant> pollutants;

    private int Aqi;

    private String dominant;

    public void setLocation(GeoJsonPoint location) {
        this.location = location;
    }

    public int getAqi() {
        return Aqi;
    }

    public void setAqi(int aqi) {
        Aqi = aqi;
    }

    public String getDominant() {
        return dominant;
    }

    public void setDominant(String dominant) {
        this.dominant = dominant;
    }

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

    public List<Pollutant> getPollutants() { return pollutants; }
    public void setPollutants(List<Pollutant> pollutants) { this.pollutants = pollutants; }

    public static class Pollutant implements Serializable{
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
