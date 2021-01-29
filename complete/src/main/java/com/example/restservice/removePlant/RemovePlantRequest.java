package com.example.restservice.removePlant;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.sql.Timestamp;

public class RemovePlantRequest {
    private final String plantID;

    @JsonCreator
    public RemovePlantRequest(@JsonProperty("plantID") String plantID) {
        this.plantID = plantID;
    }

    public String getPlantID() {
        return plantID;
    }
}

