package com.example.restservice.waterPlant;

public class WaterPlantResponse {

	private final Watering watering;
	private final String message;

	public WaterPlantResponse(Watering watering, String message) {
		this.watering = watering;
		this.message = message;
	}

	public Watering getWatering() {return watering;}

	public String getMessage() {
		return message;
	}
}
