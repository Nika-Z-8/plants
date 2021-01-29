package com.example.restservice.getPlantList;

public class GetPlantListResponse {

	private final GetPlant[] plantList;
	private final String message;

	public GetPlantListResponse(GetPlant[] plantList, String message) {
		this.plantList = plantList;
		this.message = message;
	}

	public GetPlant[] getPlantList() { return plantList; }

	public String getMessage() {
		return message;
	}
}
