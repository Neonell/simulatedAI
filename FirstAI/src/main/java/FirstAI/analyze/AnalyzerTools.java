package FirstAI.analyze;

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

}
