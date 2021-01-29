package com.example.restservice.removePlant;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.sql.Timestamp;

public class RemovePlantRequest {
    private final String plantName;

    @JsonCreator
    public RemovePlantRequest(@JsonProperty("plantName") String plantName) {
        this.plantName = plantName;
    }

    public String getPlantName() {
        return plantName;
    }
}

