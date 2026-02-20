package com.aqi.backend.model;

import java.util.List;

public class GovernmentApiResponse {
    private String status;
    private int total;
    private List<GovtRecord> records;
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<GovtRecord> getRecords() {
        return records;
    }

    public void setRecords(List<GovtRecord> records) {
        this.records = records;
    }




}
