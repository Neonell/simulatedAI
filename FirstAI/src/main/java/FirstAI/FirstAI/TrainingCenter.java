package FirstAI.FirstAI;

import java.util.Scanner;

import FirstAI.analyze.InputAnalyzer;

/**
 * This class allow us to train the conversation with the AI for non question sentences
 * The idea is to be able to train the AI just by talking with her - this is pure training without thinking
 * It's like learning a vocabulary at school.
 * 
 * @author fnell
 *
 */
public class TrainingCenter {
	public static Scanner reader;
	public static void main(String[] args) {	
		//create the ia object
		InputAnalyzer ia = new InputAnalyzer(); 
		
		reader = new Scanner(System.in); // Reading from System.in
		System.out.println("Hello I'm an AI - give me a sentence to learn");
		while (true) {
			String input = reader.nextLine();
			String answer = ia.training(input);
			System.out.println(answer);
		}
	}
}
