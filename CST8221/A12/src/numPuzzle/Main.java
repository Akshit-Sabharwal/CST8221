package numPuzzle;

/**
 * CET-CS-Level 4
 * 
 * This class contains main method and calls 
 * configure method from GameController.
 * 
 * Professor Name: Dr.Paulo Sousa
 * Course: CST8221 - JAVA Application Program 
 * Assessment: Assignment 12
 * 
 * @author Erneste Iradukundasenga, Akshit Sabharwal.
 * @version 1.1
 */
public class Main  {

   /*This is entry point for our application*/
	public static void main(String[] args) {

	GameController gc = new GameController();					//calls game controller object.
		gc.configure(); 			//configures our game design
	}


}
