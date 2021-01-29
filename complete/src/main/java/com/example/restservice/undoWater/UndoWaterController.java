package com.example.restservice.undoWater;

import com.example.restservice.waterPlant.Watering;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.*;

@RestController
public class
UndoWaterController {

	// JDBC driver name and database URL
	static final String JDBC_DRIVER = "org.postgresql.Driver";
	static final String DB_URL = "jdbc:postgresql://localhost/app";

	// Database credentials
	static final String USER = "appuser";
	static final String PASS = "app";

	public static String removeWateringFromDB(Watering watering) throws Exception {
		Connection conn = null;
		PreparedStatement stmt = null;

		// Open a connection
		conn = DriverManager.getConnection(DB_URL, USER, PASS);

		try {
			// Register JDBC driver
			Class.forName(JDBC_DRIVER);

			String plantID = watering.getPlantID();
			Timestamp lastWatered = watering.getLastWateredTimestamp();
			Timestamp wateringTime = watering.getWateringTimestamp();

			// delete from waterings
			stmt = conn.prepareStatement("DELETE FROM waterings WHERE plantID=? AND wateringTime=?");
			stmt.setString(1, plantID);
			stmt.setTimestamp(2, wateringTime);

			Integer removed = stmt.executeUpdate();

			stmt.close();

			// update lastWatered in plantstate
			stmt = conn.prepareStatement("UPDATE plantstate SET lastWatered=? WHERE plantID=?");
			stmt.setTimestamp(1, lastWatered);
			stmt.setString(2, plantID);
			stmt.executeUpdate();

			return null;

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

	@PostMapping("/undoWater")
	public UndoWaterResponse undoWater(@RequestBody UndoWaterRequest request) {
		try {
			return new UndoWaterResponse(removeWateringFromDB(request.getWatering()));
		}
		catch (Exception e) {
			e.printStackTrace();
			return new UndoWaterResponse(e.toString());
		}
	}
}