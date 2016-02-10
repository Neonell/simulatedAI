package FirstAI.analyze;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import FirstAI.FirstAI.App;
import FirstAI.FirstAI.TrainingCenter;
import FirstAI.graph.GraphBuilder;

/**
 * Analyze the input of the "human" and compute them with know process or with the AI brain
 * @author fnell
 *
 */
public class InputAnalyzer {
	private GraphBuilder graphBuilder;

	/**
	 * Constructor, make the connection between the AI process and his brain
	 */
	public InputAnalyzer() {
		graphBuilder = new GraphBuilder();
	}

	/**
	 * The main entry, the input analyzer of the AI
	 * @param input
	 * @return
	 */
	public String compute(String input) {
		String answer = null;
		//if we ask a question
		if (AnalyzerTools.identifyQuestion(input)) {
			//we try to identify if it's a calculation
			answer = computeProcess(input);
			//if not (return null) we try to identify if it's a search
			if(answer == null){
				answer = search(input);
			}
			//otherwise we compute the question in our neuronal database and try to learn new one if unknown
			if(answer == null){
				answer = computeQuestion(input);
			}
			return answer;
		} else {
			return computeSentence(input);
		}
	}

	private String computeSentence(String input) {
		String somethingToSay = graphBuilder.searchInput(input);
		if (somethingToSay != null) {
			return somethingToSay;
		} else {
			return "";
		}
	}

	private String computeQuestion(String input) {
		String answer = null;
		// compute answer
		// first search if input known:
		answer = graphBuilder.searchInput(input);
		if (answer != null) {
			return answer;
		} else {
			answer = graphBuilder.searchPartialInput(input);
			if (answer != null) {
				System.out.println("Is this answer correct? " + answer);
				String confirmation = App.reader.nextLine();
				if (AnalyzerTools.checkPositiveAnswer(confirmation)) {
					graphBuilder.learnNewQuestion(input, answer);
				} else {
					System.out.println("Then what is the correct answer?");
					String outputToLearn = App.reader.nextLine();
					if (outputToLearn.length() > 0) {
						graphBuilder.learn(input, outputToLearn);
					}
				}
				return "";
			} else {

				System.out.println("I don't know this question can you tell me how to answer?");
				String outputToLearn = App.reader.nextLine();
				if (outputToLearn.equalsIgnoreCase("no")) {
					return "too bad I won't learn today";
				} else if (AnalyzerTools.checkPositiveAnswer(outputToLearn)) {
					outputToLearn = learningProcess();
					if (outputToLearn != null) {
						graphBuilder.learn(input, outputToLearn);
						return ("Thanks I finally learned a new thing today :)");
					} else {
						return "too bad I won't learn today";
					}
				} else {
					graphBuilder.learn(input, outputToLearn);
					return ("Thanks I learned a new thing today :)");
				}
			}
		}
	}

	/**
	 * This method determine if the sentence is a know process
	 * 
	 * @return
	 */
	private String computeProcess(String sentence) {
		String result = null;
		if (sentence.contains("calculate") && sentence.contains("+") || sentence.contains("add") || sentence.contains("+")) {
			result = doCalculation(sentence, 0);
		} else if (sentence.contains("calculate") && sentence.contains("*") || sentence.contains("multiply") || sentence.contains("*")) {
			result = doCalculation(sentence, 1);
		} else if (sentence.contains("calculate") && sentence.contains("/") || sentence.contains("divide") || sentence.contains("/")) {
			result = doCalculation(sentence, 2);
		} else if (sentence.contains("calculate") && sentence.contains("-") || sentence.contains("substract") || sentence.contains("-")) {
			result = doCalculation(sentence, 3);
		}

		return result;
	}

	public String doCalculation(String sentence, int type) {
		ArrayList<Double> number = new ArrayList<Double>();
		Pattern p = Pattern.compile("-?\\d+");
		Matcher m = p.matcher(sentence);
		while (m.find()) {
			number.add(Double.parseDouble(m.group()));
		}
		String result = null;
		double total = 0;
		if (number.size() > 1) {
			switch (type) {
			case 0:
				for (int i = 0; i < number.size(); i++) {
					total += number.get(i);
				}
				result = "The result of the addition is : " + total;
				break;
			case 1:
				for (int i = 0; i < number.size(); i++) {
					if(i == 0){
					total = number.get(i);
					}
					else{
						total *= number.get(i);
					}
				}
				result = "The result of the multiplication is : " + total;
				break;
			case 2:
				for (int i = 0; i < number.size(); i++) {
					if(i == 0){
						total = number.get(i);
						}
						else{
							total /= number.get(i);
						}
				}
				result = "The result of the division is : " + total;
				break;
			case 3:
				for (int i = 0; i < number.size(); i++) {
					if(i == 0){
						total = number.get(i);
						}
						else{
							total -= number.get(i);
						}
				}
				result = "The result of the substraction is : " + total;
				break;
			default:
				for (int i = 0; i < number.size(); i++) {
					total += number.get(i);
				}
				result = "The result of the addition is : " + total;
				break;
			}
		}
		return result;
	}

	/**
	 * This method search a therm on the internet if the sentence speak about
	 * searching something
	 * 
	 * @param sentence
	 * @return
	 */
	private String search(String sentence) {
		String result = null;
		if(sentence.contains("search")){
			int index = sentence.indexOf("search");
			String search = sentence.substring(index+5);
			result = googleSearch(search);
		}
		return result;
	}
	
	private String googleSearch(String search){
		String google = "http://www.google.com/search?q=";
		String charset = "UTF-8";
		String userAgent = "ExampleBot 1.0 (+http://example.com/bot)"; // Change this to your company's name and bot homepage!
		String response = "";
		Elements links;
		try {
			links = Jsoup.connect(google + URLEncoder.encode(search, charset)).userAgent(userAgent).get().select(".g>.r>a");
		

		for (Element link : links) {
		    String title = link.text();
		    String url = link.absUrl("href"); // Google returns URLs in format "http://www.google.com/url?q=<url>&sa=U&ei=<someKey>".
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

	private String learningProcess() {
		System.out.println("Is yes the answer?");
		String confirmation = App.reader.nextLine();
		if (AnalyzerTools.checkPositiveAnswer(confirmation)) {
			return "yes";
		} else {
			System.out.println("Then tell me what I need to learn please");
			String outputToLearn = App.reader.nextLine();
			if (outputToLearn.equalsIgnoreCase("no")) {
				return null;
			} else if (AnalyzerTools.checkPositiveAnswer(outputToLearn)) {
				return learningProcess();
			} else {
				return outputToLearn;
			}
		}
	}

	/**
	 * dummy training conversation method, used by the training Center
	 * 
	 * @param input
	 * @return
	 */
	public String training(String input) {
		// TODO Auto-generated method stub
		System.out.println("Tell me what to answer to this?");
		String outputToLearn = TrainingCenter.reader.nextLine();
		if (outputToLearn.equalsIgnoreCase("no")) {
			return "Too bad I won't learn like this";
		} else if (AnalyzerTools.checkPositiveAnswer(outputToLearn)) {
			return training(input);
		} else {
			graphBuilder.learn(input, outputToLearn);
			return "Thanks I learned a new sentence :)";
		}

	}

}
