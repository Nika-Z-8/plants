package com.example.restservice.removePlant;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.*;
import java.util.UUID;

@RestController
public class RemovePlantController {

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
	public static String removePlantFromDB(String plantName) throws Exception {
		Connection conn = null;
		PreparedStatement stmt = null;

		// Open a connection
		conn = DriverManager.getConnection(DB_URL, USER, PASS);

		try {
			// Register JDBC driver
			Class.forName(JDBC_DRIVER);

			String plantID = "";

			// get plantID
			stmt = conn.prepareStatement("SELECT plantID FROM plants WHERE plantName=?");
			stmt.setString(1, plantName);

			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				plantID = rs.getString(1);
			}

			rs.close();
			stmt.close();

			// delete from plants
			stmt = conn.prepareStatement("DELETE FROM plants WHERE plantID=?");
			stmt.setString(1, plantID);

			Integer removed = stmt.executeUpdate();

			stmt.close();

			// delete from plantstate
			stmt = conn.prepareStatement("DELETE FROM plantstate WHERE plantID=?");
			stmt.setString(1, plantID);

			removed = stmt.executeUpdate();

			stmt.close();

			// delete from waterings
			stmt = conn.prepareStatement("DELETE FROM waterings WHERE plantID=?");
			stmt.setString(1, plantID);

			removed = stmt.executeUpdate();

			stmt.close();

			return "Plant removed.";

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

	@PostMapping("/removePlant")
	public RemovePlantResponse removePlant(@RequestBody RemovePlantRequest request) {
		try {
			return new RemovePlantResponse(removePlantFromDB(request.getPlantName()));
		}
		catch (Exception e) {
			e.printStackTrace();
			return new RemovePlantResponse(e.getMessage());
		}
	}
}