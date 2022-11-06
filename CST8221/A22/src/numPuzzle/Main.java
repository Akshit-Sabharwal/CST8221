package numPuzzle;
/**
 * CET-CS-Level 4
 *
 * This class is entry point for our application
 *
 * Professor Name: Dr.Paulo Sousa Course: CST8221 - JAVA Application Program
 * Assessment: Assignment 21
 *
 * @author Erneste Iradukundasenga, Akshit Sabharwal.
 * @version 2.1
 */
public class Main {

	/**
	 * The entry point of application.
	 *
	 * @param args the input arguments
	 */
	public static void main(String[] args) {
		
		 GameModel gameModel = new GameModel();
		 GameView gameView = new GameView(gameModel);
		 GameController gameController = new GameController(gameModel, gameView);
		 gameController.display();
	}
}
