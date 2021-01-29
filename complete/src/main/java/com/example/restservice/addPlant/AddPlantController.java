package com.example.restservice.addPlant;

import org.springframework.web.bind.annotation.*;

import java.sql.*;
import java.util.UUID;

@RestController
public class AddPlantController {

	// JDBC driver name and database URL
	static final String JDBC_DRIVER = "org.postgresql.Driver";
	static final String DB_URL = "jdbc:postgresql://localhost/app";

	// Database credentials
	static final String USER = "appuser";
	static final String PASS = "app";

	/**
	 *
	 * @param plantName
	 * @return plantID
	 * @throws Exception
	 */
	public static String addPlantToDB(String plantName,
									  Integer wateringInterval,
									  Timestamp lastWatered) throws Exception {

		if (plantName == null || wateringInterval == null || lastWatered == null) {
			throw new Exception("Please fill all required fields.");
		}

		Connection conn = null;
		PreparedStatement stmt = null;

		// Open a connection
		conn = DriverManager.getConnection(DB_URL, USER, PASS);

		try {
			// Register JDBC driver
			Class.forName(JDBC_DRIVER);

			stmt = conn.prepareStatement("SELECT plantName FROM plants WHERE plantName=?");
			stmt.setString(1, plantName);

			ResultSet rs = stmt.executeQuery();

			// Extract data from result set
			if (rs.next()) {
				throw new Exception(plantName + " already exists.");
			}

			rs.close();
			stmt.close();

			stmt = conn.prepareStatement("INSERT INTO plants(plantID, plantName) VALUES (?,?)");
			String plantID = UUID.randomUUID().toString();
			stmt.setString(1, plantID);
			stmt.setString(2, plantName);
			stmt.executeUpdate();

			stmt.close();

			stmt = conn.prepareStatement("INSERT INTO plantstate(plantID, wateringInterval, lastWatered) VALUES (?,?,?)");
			stmt.setString(1, plantID);
			stmt.setInt(2, wateringInterval);
			stmt.setTimestamp(3, lastWatered);
			stmt.executeUpdate();

			return plantID;

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

	@PostMapping("/addPlant")
	public AddPlantResponse addPlant(@RequestBody AddPlantRequest request) {
		try {
			return new AddPlantResponse(addPlantToDB(request.getPlantName(),
													 request.getWateringInterval(),
													 request.getLastWatered()),
								"Plant added.");
		}
		catch (Exception e) {
			e.printStackTrace();
			return new AddPlantResponse(null, e.getMessage());
		}
	}
}