package com.aqi.backend.model;

public class Result {
    int maxAqi;
    String Dominant;

    public int getMaxAqi() {
        return maxAqi;
    }

    public void setMaxAqi(int maxAqi) {
        this.maxAqi = maxAqi;
    }

    public String getDominant() {
        return Dominant;
    }

    public void setDominant(String dominant) {
        Dominant = dominant;
    }

    public Result(int maxAqi, String dominant) {
        this.Dominant = dominant;
        this.maxAqi = maxAqi;
    }
}
