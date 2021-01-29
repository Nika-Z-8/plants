package com.example.restservice.addPlant;

public class AddPlantResponse {

	private final String id;
	private final String message;

	public AddPlantResponse(String id, String message) {
		this.id = id;
		this.message = message;
	}

	public String getId() {
		return id;
	}

	public String getMessage() {
		return message;
	}
}
