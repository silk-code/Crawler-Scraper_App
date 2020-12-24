package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

//TODO handle exception in run() in user-friendly way

public class CrawlerThread extends Thread {

	private boolean exit = false;

	/**
	 * CrawlerThread's run method will run when the thread is given time on the
	 * processor this thread downloads HTML code from web pages
	 * 
	 */
	public void run() {

		while (!Program.crawlerQueue.isEmpty() && !exit) {
			try {
				String url = Program.crawlerQueue.take();
				System.out.println("Got url");
				Program.scraperQueue.put(getHtml(url));
				System.out.println("Put html on queue");
				//
				Program.pagesCrawled++;
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			try {
				this.sleep(8000);
			} catch (InterruptedException e) {

			}
		}
		System.out.println();

	}

	public void stopThread() {
		exit = true;
	}

	/**
	 * extract the HTML of a webpage
	 * 
	 * @param url
	 *            - a link to the webpage
	 * @return the HTML of the webpage
	 */
	private static String getHtml(String url) {
		StringBuilder builder = new StringBuilder();
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(new URL(url).openStream()));
			String s = null;
			while ((s = reader.readLine()) != null) {
				builder.append(s);
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return builder.toString();
	}
}
