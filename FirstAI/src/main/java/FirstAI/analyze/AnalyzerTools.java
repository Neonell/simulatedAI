package FirstAI.analyze;

import java.util.ArrayList;

public class AnalyzerTools {

	public static boolean checkPositiveAnswer(String answer) {
		if (answer.equalsIgnoreCase("yes") || answer.contains("yes") || answer.contains("YES")
				|| answer.contains("Yes")) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean checkNegativeAnswer(String answer) {
		if (answer.equalsIgnoreCase("no") || answer.contains("NO") || answer.contains("no") || answer.contains("No")) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean identifyQuestion(String input) {
		if (input.endsWith("?") || input.contains("who") || input.contains("Who") || input.contains("where")
				|| input.contains("Where") || input.contains("how") || input.contains("How")) {
			return true;
		} else {
			return false;
		}
	}

	public static ArrayList<String> deleteUnusefullWords(ArrayList<String> words) {
		for (int i = 0; i < words.size(); i++) {
			String word = words.get(i);
			if (word.equalsIgnoreCase("how") || word.equalsIgnoreCase("to") || word.equalsIgnoreCase("a") || word.equalsIgnoreCase("an")) {
				words.remove(i);
				i--;
			}
		}
		return words;
	}

	public static ArrayList<String> parse(String sentence) {
		ArrayList<String> wordList = new ArrayList<String>();
		sentence = sentence.trim();
		sentence += " ";
		while (sentence.indexOf(" ") != -1) {
			String newWord = sentence.substring(0, sentence.indexOf(" "));
			wordList.add(newWord);
			sentence = sentence.substring(sentence.indexOf(" ") + 1);
		}

		return wordList;
	}

	public static String deleteTag(String input) {
		if (input.startsWith("<")) {
			boolean more = true;
			while (more) {
				input = input.substring(1, input.length());
				if (input.startsWith(">")) {
					input = input.substring(1, input.length());
					if (input.startsWith("<")) {
						more = true;
					} else {
						more = false;
					}
				}
			}
		}
		if (input.endsWith(">")) {
			boolean more = true;
			while (more) {
				input = input.substring(0, input.length() - 1);
				if (input.endsWith("<")) {
					input = input.substring(0, input.length() - 1);
					if (input.endsWith(">")) {
						more = true;
					} else {
						more = false;
					}
				}
			}
		}
		return input;
	}

}
