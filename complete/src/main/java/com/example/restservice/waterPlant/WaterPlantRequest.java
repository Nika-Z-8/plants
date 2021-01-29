package com.example.restservice.waterPlant;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.sql.Timestamp;

public class WaterPlantRequest {
    private final String plantID;

    @JsonCreator
    public WaterPlantRequest(@JsonProperty("plantID") String plantID) {
        this.plantID = plantID;
    }

    public String getPlantID() {
        return plantID;
    }
}

