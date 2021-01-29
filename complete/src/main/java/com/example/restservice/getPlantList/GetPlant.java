package com.example.restservice.getPlantList;

import java.sql.Timestamp;

public class GetPlant {
    private final String plantName;
    private final Integer wateringInterval;
    private final Timestamp lastWatered;

    public GetPlant(String plantName, Integer wateringInterval, Timestamp lastWatered) {
        this.plantName = plantName;
        this.wateringInterval = wateringInterval;
        this.lastWatered = lastWatered;
    }

    public String getPlantName() { return plantName; }

    public Integer getWateringInterval() {
        return wateringInterval;
    }

    public Timestamp getLastWatered() {return lastWatered;}
}
