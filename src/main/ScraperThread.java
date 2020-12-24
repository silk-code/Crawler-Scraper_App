package main;

import java.sql.SQLException;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ScraperThread extends Thread {

	private boolean exit = false;

	/**
	 * ScraperThread's run() method will run when the thread is started it extracts
	 * useful information from the HTML
	 */
	public void run() {
		try {
			this.sleep(8000);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		while (!Program.scraperQueue.isEmpty() && !exit) {
			System.out.println("In scraper");
			try {
				extractInfo(Program.scraperQueue.take());
				System.out.println("Extracted");
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			Program.pagesScraped++;
			try {
				this.sleep(7000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.println("Empty scraper");
	}

	public void stopThread() {
		exit = true;
	}

	/**
	 * this method calls methods that parse the HTML for useful information and
	 * update the corresponding ArrayList
	 * 
	 * @param html
	 *            - a String with the HTML of a web page
	 */
	private void extractInfo(String html) {
		System.out.println("Got html");
		addInternalLinks(html);
		addColors(html);
		addPhoneNumbers(html);
		addExternalUrls(html);
		addEmails(html);
		addSocialMedia(html);
	}

	/**
	 * collect internal links from Touro's website and add them to the internalUrls
	 * collection as well as to the crawlerQueue (the CrawlerThread will keep
	 * downloading the HTML of internal links that are found by the ScraperThread)
	 * 
	 * @param html
	 */
	private void addInternalLinks(String html) {
		addPattern(html, "href=\"[^>]*\">", "(https?://|//)(www.)?(\\.|\\w+)*(touro)(\\.|\\w+)*(/\\w+)*",
				Program.internalUrls);
	}

	/**
	 * find social media links and add them to the collection
	 * 
	 * @param html
	 */
	private void addSocialMedia(String html) {
		Pattern socialMediaFinder = Pattern.compile(
				"https://www.(facebook|instagram|flickr|vimeo|linkedin|youtube|collegeblog|vimeo|twitter)([A-Za-z]|/|\\.)*(?=\">)");
		Matcher socialMediaMatcher = socialMediaFinder.matcher(html);
		while (socialMediaMatcher.find()) {
			try {
				Program.db.sendToDB(
						"insert into socialmedia (description) values('" + socialMediaMatcher.group() + "');");
			} catch (SQLException e) {
			}
		}

	}

	/**
	 * find colors and add them to the collection
	 * 
	 * @param html
	 */
	private void addColors(String html) {
		Pattern colorFinder = Pattern.compile("(style=\"?)(color:\\s)(\\#\\S{6});");
		Matcher colorMatcher = colorFinder.matcher(html);
		while (colorMatcher.find()) {
			try {
				Program.db.sendToDB("insert into color (description) values('" + colorMatcher.group() + "');");
			} catch (SQLException e) {
			}
		}

	}

	/**
	 * find phone numbers and add them to the collection
	 * 
	 * @param html
	 */
	private void addPhoneNumbers(String html) {
		Pattern phoneNumFinder = Pattern.compile("(\\+\\d{1,2}\\s)?\\(?\\d{3}\\)?[\\s.-]\\d{3}[\\s.-]\\d{4}");
		Matcher phoneNumMatcher = phoneNumFinder.matcher(html);
		while (phoneNumMatcher.find()) {
			try {
				Program.db.sendToDB("insert into phonenumber (description) values('" + phoneNumMatcher.group() + "');");
			} catch (SQLException e) {
			}
		}

	}

	/**
	 * find external urls and add them to the collection
	 * 
	 * @param html
	 */
	private void addExternalUrls(String html) {
		Pattern externalURLFinder = Pattern.compile("(https?://(www)?(\\w+)?\\.(?!touro\\.edu)\\w{2,3})");
		Matcher externalURLMatcher = externalURLFinder.matcher(html);
		while (externalURLMatcher.find()) {
			try {
				Program.db.sendToDB(
						"insert into externallink (description) values('" + externalURLMatcher.group() + "');");
			} catch (SQLException e) {
			}
		}

	}

	/**
	 * find emails and add them to the collection
	 * 
	 * @param html
	 */
	private void addEmails(String html) {
		Pattern emailFinder = Pattern.compile("(>)(\\S+@\\S+(\\.\\S+)+)(<)");
		Matcher emailMatcher = emailFinder.matcher(html);
		while (emailMatcher.find()) {
			try {
				Program.db.sendToDB("insert into email (description) values('" + emailMatcher.group(2) + "');");
			} catch (SQLException e) {
			}

		}
	}

	/**
	 * this method applies regex to html and saves the results in the collection
	 * 
	 * @param html
	 *            - String with HTML of the web page
	 * @param linkRegex
	 *            - String with the regex pattern of a link in an HTML "a" tag
	 * @param patternRegex
	 *            - String with a regex of a certain pattern
	 * @param list
	 */
	private static void addPattern(String html, String linkRegex, String patternRegex, Vector<String> list) {
		Pattern linkFinder = Pattern.compile(linkRegex);
		Pattern patternFinder = Pattern.compile(patternRegex);
		Matcher linkMatcher = linkFinder.matcher(html);
		while (linkMatcher.find()) {
			Matcher patternMatcher = patternFinder.matcher(linkMatcher.group());
			while (patternMatcher.find()) {
				String toAdd = patternMatcher.group().replace("href=\"", "").replace("\">", "").replace("mailto:", "")
						.replace("\"", "");
				if (!list.contains(toAdd)) {
					list.add(toAdd);
					try {
						Program.db.sendToDB("insert into internallink (description) values('" + toAdd + "');");
					} catch (SQLException e) {
					}
					try {
						Program.crawlerQueue.put(toAdd);
					} catch (InterruptedException e) {
					}
				}

			}

		}
	}
}
