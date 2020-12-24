package main;

import java.sql.SQLException;
//import com.microsoft.sqlserver.jdbc.SQLServerDriver;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.stream.Stream;



public class Program  {

	public static LinkedBlockingQueue<String> crawlerQueue = new LinkedBlockingQueue<String>(); // links
	public static LinkedBlockingQueue<String> scraperQueue = new LinkedBlockingQueue<String>(); // HTML

	public static Vector<String> internalUrls = new Vector<String>();

	public static CrawlerThread crawler;
	public static ScraperThread scraper;
	public static DatabaseConnection db;

	public static int pagesCrawled;
	public static int pagesScraped;

	public Program() {
		crawler = new CrawlerThread();
		scraper = new ScraperThread();
		db = new DatabaseConnection();
	}

	public static void endThreads() {
		saveQueue("ScraperQueue", scraperQueue);
		saveQueue("CrawlerQueue", crawlerQueue);
		db.close();
		try {
			crawler.stopThread();
			crawler.join();
			scraper.stopThread();
			scraper.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/***
	 * This method takes a string from user and returns ArrayList of all addresses
	 * that are in database at this moment that contained given string
	 * 
	 * @param mustContain
	 * @return
	 * @throws SQLException
	 */
	public ArrayList<String> getSpecifiedEmail(String mustContain) {
		ArrayList<String> toReturn = new ArrayList<>();
		ArrayList<String> tableContent;
		try {
			tableContent = db.getSpecifiedCollectedInfo("Email");
			Stream<String> selectedAddresses = tableContent.stream().filter(a -> a.contains(mustContain));
			selectedAddresses.forEach(a -> toReturn.add(a));
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return toReturn;
	}

	public ArrayList<String> getPhoneNumbersWithAreaCode(String areaCode) {
		ArrayList<String> toReturn = new ArrayList<>();
		ArrayList<String> tableContent;
		try {
			tableContent = db.getSpecifiedCollectedInfo("PhoneNumber");
			Stream<String> selectedAddresses = tableContent.stream().filter(a -> a.substring(0, 3).equals(areaCode));
			selectedAddresses.forEach(a -> toReturn.add(a));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return toReturn;
	}

	public ArrayList<String> getTableContent(String tableName) {
		ArrayList<String> temp = new ArrayList<>();
		try {
			temp = db.getSpecifiedCollectedInfo(tableName);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return temp;
	}

	private static void saveQueue(String table, LinkedBlockingQueue<String> collection) {
		try {
			Program.db.sendToDB("delete from " + table);// empty table
		} catch (SQLException e) {
			e.printStackTrace();
		}
		StringBuilder insertStatement = new StringBuilder("insert into " + table + " (description) " + " values (");
		while (!collection.isEmpty()) {
			insertStatement.append(collection.poll() + ",");
		}
		String finalSql = insertStatement.toString().replace(", $", ");"); // swap last comma with closing parentheses
																			// and semi-colon to end the statement
		try {
			Program.db.sendToDB(finalSql);
		} catch (SQLException e) {
		} // send insert statement to db
	}
}
