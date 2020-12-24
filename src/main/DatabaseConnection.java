package main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;
import java.util.Vector;

/**
 * class that will handle database connectivity and transactions
 */
public class DatabaseConnection {

	private final String jdbcDriver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
	private final String jdbcURL = "jdbc:sqlserver://localhost:1433;databasename=DS2Project;integratedSecurity=true;";
	public static Connection dbConn = null;

	public DatabaseConnection() {

		try {
			connectDB();
			geLinkstFromDB();
			// getCollectedInfo();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.exit(0);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

	private void connectDB() throws ClassNotFoundException, SQLException {
		Class.forName(jdbcDriver);
		dbConn = DriverManager.getConnection(jdbcURL);
	}

	private void geLinkstFromDB() throws SQLException, InterruptedException {
		Statement sqlStatement = dbConn.createStatement();
		ResultSet results = sqlStatement.executeQuery("select description from scraperqueue");
		while (results.next()) {
			String link = results.getString("description");
			Program.scraperQueue.put(link);
		}
		results = sqlStatement.executeQuery("select description from crawlerqueue");
		while (results.next()) {
			String link = results.getString("description");
			Program.crawlerQueue.put(link);
		}
		if (Program.crawlerQueue.isEmpty()) {
			Program.crawlerQueue.put("https://www.touro.edu");
		}

	}

	public ArrayList<String> getSpecifiedCollectedInfo(String tableName) throws SQLException {
		Statement sql = dbConn.createStatement();
		ResultSet results = sql.executeQuery("select description from " + tableName); // query db table
		ArrayList<String> collection = new ArrayList<>(); // collection that will be populated
		while (results.next()) {
			collection.add(results.getString("description"));
		}
		return collection;
	}

	public void sendToDB(String sql) throws SQLException {
		Statement insertStatement = dbConn.createStatement();
		insertStatement.executeUpdate(sql);
	}
	

	// releases the database connection resource
	// returns boolean value indicating success
	public boolean close() {
		try {
			dbConn.close();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}

	}

}
