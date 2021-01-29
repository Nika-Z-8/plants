package com.example.restservice.addPlant;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.sql.Time;
import java.sql.Timestamp;

public class AddPlantRequest {
    private final String plantName;
    private final Integer wateringInterval;
    private final Timestamp lastWatered;

    @JsonCreator
    public AddPlantRequest(@JsonProperty("plantName") String plantName,
                           @JsonProperty("wateringInterval") Integer wateringInterval,
                           @JsonProperty("lastWatered") Timestamp lastWatered) {
        this.plantName = plantName;
        this.wateringInterval = wateringInterval;
        this.lastWatered = lastWatered;
    }

    public String getPlantName() {
        return plantName;
    }
    public Integer getWateringInterval() {return wateringInterval;}
    public Timestamp getLastWatered() {return lastWatered;}
}

