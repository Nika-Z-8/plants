package com.example.restservice.getPlantList;

import com.example.restservice.addPlant.AddPlantRequest;
import com.example.restservice.addPlant.AddPlantResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;

@RestController
public class GetPlantListController {

	// JDBC driver name and database URL
	static final String JDBC_DRIVER = "org.postgresql.Driver";
	static final String DB_URL = "jdbc:postgresql://localhost/app";

	// Database credentials
	static final String USER = "appuser";
	static final String PASS = "app";

	public static GetPlant[] getPlantListFromDB() throws Exception {
		Connection conn = null;
		PreparedStatement stmt = null;

		// Open a connection
		conn = DriverManager.getConnection(DB_URL, USER, PASS);

		try {
			// Register JDBC driver
			Class.forName(JDBC_DRIVER);

			ArrayList<GetPlant> result = new ArrayList<GetPlant>();

			stmt = conn.prepareStatement("SELECT plants.plantID, plantName, wateringInterval, lastWatered " +
											 "FROM plants " +
											 "INNER JOIN plantstate ON plants.plantID=plantstate.plantID");
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				String plantID = rs.getString(1);
				String plantName = rs.getString(2);
				Integer wateringInterval = rs.getInt(3);
				Timestamp lastWatered = rs.getTimestamp(4);

				GetPlant plant = new GetPlant(plantID, plantName, wateringInterval, lastWatered);
				result.add(plant);
			}

			rs.close();
			stmt.close();

			GetPlant[] plantList = result.toArray(new GetPlant[result.size()]);

			return plantList;

		} finally {
			// finally block used to close resources
			try {
				if (stmt != null)
					stmt.close();
			} catch (SQLException se2) {
				se2.printStackTrace();
			}
			try {
				conn.close();
			} catch (SQLException se) {
				se.printStackTrace();
			}
		}
	}

	@PostMapping("/getPlantList")
	public GetPlantListResponse getPlantList(@RequestBody(required = false) GetPlantListRequest request) {
		try {
			return new GetPlantListResponse(getPlantListFromDB(), null);
		}
		catch (Exception e) {
			e.printStackTrace();
			return new GetPlantListResponse(null, e.getMessage());
		}
	}
}
