package FirstAI.FirstAI;

import java.util.Scanner;

import FirstAI.graph.GraphBuilder;

import FirstAI.analyze.InputAnalyzer;

/**
 * Hello world!
 *
 */
public class App {
	public static Scanner reader;
	
	public static void main(String[] args) {	
		//create the ia object
		InputAnalyzer ia = new InputAnalyzer(); 
		
		reader = new Scanner(System.in); // Reading from System.in
		System.out.println("Hello I'm an AI");
		while (true) {
			String input = reader.nextLine();
			String answer = ia.compute(input);
			System.out.println(answer);
		}
	}
}
