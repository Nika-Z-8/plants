package com.example.restservice.undoWater;

import com.example.restservice.waterPlant.Watering;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

public class UndoWaterRequest {
    private final Watering watering;

    @JsonCreator
    public UndoWaterRequest(@JsonProperty("watering") Watering watering) {
        this.watering = watering;
    }

    public Watering getWatering() {
        return watering;
    }
}