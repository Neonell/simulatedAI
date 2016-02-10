package FirstAI.FirstAI;

import java.util.Scanner;

import FirstAI.analyze.InputAnalyzer;

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
