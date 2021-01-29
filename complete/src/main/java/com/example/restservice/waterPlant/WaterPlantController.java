package com.example.restservice.waterPlant;

import com.example.restservice.getPlantList.GetPlant;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.*;
import java.util.UUID;

@RestController
public class WaterPlantController {

	// JDBC driver name and database URL
	static final String JDBC_DRIVER = "org.postgresql.Driver";
	static final String DB_URL = "jdbc:postgresql://localhost/app";

	// Database credentials
	static final String USER = "appuser";
	static final String PASS = "app";

	public static Watering addWateringToDB(String plantID) throws Exception {
		Connection conn = null;
		PreparedStatement stmt = null;

		// Open a connection
		conn = DriverManager.getConnection(DB_URL, USER, PASS);

		try {
			// Register JDBC driver
			Class.forName(JDBC_DRIVER);

			Timestamp lastWatered = null;
			Timestamp wateringTime = new Timestamp(System.currentTimeMillis());

			// insert new watering into waterings
			stmt = conn.prepareStatement("INSERT INTO waterings(plantID, wateringTime) VALUES (?,?)");
			stmt.setString(1, plantID);
			stmt.setTimestamp(2, wateringTime);
			stmt.executeUpdate();

			stmt.close();

			// select lastWatered from plantstate
			stmt = conn.prepareStatement("SELECT lastWatered FROM plantstate WHERE plantID=?");
			stmt.setString(1, plantID);

			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				lastWatered = rs.getTimestamp(1);
			}

			rs.close();
			stmt.close();

			// update lastWatered in plantstate
			stmt = conn.prepareStatement("UPDATE plantstate SET lastWatered=? WHERE plantID=?");
			stmt.setTimestamp(1, wateringTime);
			stmt.setString(2, plantID);
			stmt.executeUpdate();

			Watering watering = new Watering(plantID, lastWatered, wateringTime);

			return watering;

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

	@PostMapping("/waterPlant")
	public WaterPlantResponse waterPlant(@RequestBody WaterPlantRequest request) {
		try {
			return new WaterPlantResponse(addWateringToDB(request.getPlantID()), null);
		}
		catch (Exception e) {
			e.printStackTrace();
			return new WaterPlantResponse(null, e.getMessage());
		}
	}
}