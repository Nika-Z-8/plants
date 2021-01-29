package com.example.restservice.waterPlant;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.sql.Timestamp;
import java.text.ParseException;

public class Watering {
    private final String plantID;
    private final String lastWatered;
    private final String wateringTime;

    public Watering(String plantID,
                    Timestamp lastWatered,
                    Timestamp wateringTime) {
        this.plantID = plantID;
        this.lastWatered = JSONTimestampConverter.toString(lastWatered);
        this.wateringTime = JSONTimestampConverter.toString(wateringTime);
    }

    @JsonCreator
    public Watering(@JsonProperty("plantID") String plantID,
                    @JsonProperty("lastWatered") String lastWatered,
                    @JsonProperty("wateringTime") String wateringTime)  {
        this.plantID = plantID;
        this.lastWatered = lastWatered;
        this.wateringTime = wateringTime;
    }

    public String getPlantID() { return plantID; }

    public String getLastWatered() {return lastWatered;}

    public String getWateringTime() {return wateringTime;}

    @JsonIgnore
    public Timestamp getLastWateredTimestamp() throws ParseException {return JSONTimestampConverter.toTimestamp(lastWatered);}

    @JsonIgnore
    public Timestamp getWateringTimestamp() throws ParseException {return JSONTimestampConverter.toTimestamp(wateringTime);}

}
