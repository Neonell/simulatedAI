package FirstAI.analyze;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import FirstAI.FirstAI.App;

public class GoogleAnalyzer {
	/**
	 * This method search a therm on the internet if the sentence speak about
	 * searching something
	 * 
	 * @param sentence
	 * @return
	 */
	static String search(String sentence) {
		String result = null;
		if (sentence.contains("search")) {
			int index = sentence.indexOf("search");
			String search = sentence.substring(index + 6);
			result = googleSearch(search);
		}
		return result;
	}

	/**
	 * This method try to give an answer from the internet if the user want to
	 * learn something new
	 * 
	 * @param sentence
	 * @return
	 */
	static String question(String sentence) {
		String result = null;
		if (sentence.contains("can you tell me")) {
			int index = sentence.indexOf("can you tell me");
			String search = sentence.substring(index + 15);
			result = googleFind(search);
		} else if (sentence.contains("do you know")) {
			int index = sentence.indexOf("do you know");
			String search = sentence.substring(index + "do you know".length());
			result = googleFind(search);
		}
		return result;
	}

	static String googleSearch(String search) {
		String google = "http://www.google.com/search?q=";
		String charset = "UTF-8";
		String userAgent = "ExampleBot 1.0 (+http://example.com/bot)"; // Change
																		// this
																		// to
																		// your
																		// company's
																		// name
																		// and
																		// bot
																		// homepage!
		String response = "";
		Elements links;
		try {
			links = Jsoup.connect(google + URLEncoder.encode(search, charset)).userAgent(userAgent).get()
					.select(".g>.r>a");

			for (Element link : links) {
				String title = link.text();
				String url = link.absUrl("href"); // Google returns URLs in
													// format
													// "http://www.google.com/url?q=<url>&sa=U&ei=<someKey>".
				url = URLDecoder.decode(url.substring(url.indexOf('=') + 1, url.indexOf('&')), "UTF-8");

				if (!url.startsWith("http")) {
					continue; // Ads/news/etc.
				}

				System.out.println("Title: " + title);
				System.out.println("URL: " + url);
				System.out.println("Does it answer your question? ");
				String confirmation = App.reader.nextLine();
				if (AnalyzerTools.checkPositiveAnswer(confirmation)) {
					break;
				}
			}

		} catch (UnsupportedEncodingException e) {
			response = "sorry I didn't find anything relevant about this subject";
		} catch (IOException e) {
			response = "sorry I didn't find anything relevant about this subject";
		}

		return response;
	}

	static String googleFind(String search) {
		String google = "http://www.google.com/search?q=";
		String charset = "UTF-8";
		String userAgent = "ExampleBot 1.0 (+http://example.com/bot)"; // Change
																		// this
																		// to
																		// your
																		// company's
																		// name
																		// and
																		// bot
																		// homepage!
		String response = "";
		Elements links;
		try {
			links = Jsoup.connect(google + URLEncoder.encode(search, charset)).userAgent(userAgent).get()
					.select(".g>.r>a");

			for (Element link : links) {
				String title = link.text();
				String urlString = link.absUrl("href"); // Google returns URLs
														// in format
														// "http://www.google.com/url?q=<url>&sa=U&ei=<someKey>".
				urlString = URLDecoder.decode(urlString.substring(urlString.indexOf('=') + 1, urlString.indexOf('&')),
						"UTF-8");

				if (!urlString.startsWith("http")) {
					continue; // Ads/news/etc.
				}

				URL url = new URL(urlString);
				URLConnection conn = url.openConnection();

				BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

				String inputLine;
				String answer = null;
				while ((inputLine = br.readLine()) != null) {
					answer = computeContent(inputLine);
					if (answer != null) {
						answer = computeContent(answer, search);
						if (answer != null) {
							answer = AnalyzerTools.deleteTag(answer);
							System.out.println(answer);
						}
					}
				}
				System.out.println("Does it answer your question? ");
				String confirmation = App.reader.nextLine();
				if (AnalyzerTools.checkPositiveAnswer(confirmation)) {
					break;
				}
			}
		} catch (UnsupportedEncodingException e) {
			response = "sorry I didn't find anything relevant about this subject";
		} catch (IOException e) {
			response = "sorry I didn't find anything relevant about this subject";
		}

		return response;
	}

	static String computeContent(String inputLine) {
		if (inputLine.contains("<li>") && inputLine.contains("</li>")) {
			if (inputLine.indexOf("<li>") < inputLine.indexOf("</li>")) {
				return inputLine.substring(inputLine.indexOf("<li>"), inputLine.indexOf("</li>"));
			}
		}
		if (inputLine.contains("<p>") && inputLine.contains("</p>")) {
			if (inputLine.indexOf("<p>") < inputLine.indexOf("</p>")) {
				return inputLine.substring(inputLine.indexOf("<p>"), inputLine.indexOf("</p>"));
			}
		}

		return null;
	}

	static String computeContent(String inputLine, String search) {
		search = search.substring(0, search.length() - 1);
		ArrayList<String> keywords = AnalyzerTools.parse(search);
		keywords = AnalyzerTools.deleteUnusefullWords(keywords);
		int counter = 0;
		for (String keyword : keywords) {

			if (inputLine.contains(keyword)) {
				counter++;
			}
		}
		if (counter >= keywords.size() / 2) {
			return inputLine;
		} else {
			return null;
		}
	}
}
