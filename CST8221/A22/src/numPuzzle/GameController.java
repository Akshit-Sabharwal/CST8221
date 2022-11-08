package numPuzzle;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JColorChooser;
import javax.swing.JFrame;
/**
 * CET-CS-Level 4
 *
 * This class controls the actions of our application
 *
 * Professor Name: Dr.Paulo Sousa Course: CST8221 - JAVA Application Program
 * Assessment: Assignment 21
 *
 * @author Erneste Iradukundasenga, Akshit Sabharwal.
 * @version 2.1
 */
public class GameController extends JFrame implements ActionListener {

	/**
	 * serialization unique id
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * View Object
	 */
	private GameView view;

	/**
	 * Model Object
	 */
	private GameModel model;


	/**
	 * check - to check if its string or number
	 * finish - if game is finished
	 */
	boolean check, finish;
	/**
	 * timer tasks
	 */
	TimerTask timerTask, timerTask1;
	/**
	 * timer - to get time
	 */
	Timer timer = new Timer();
	/**
	 * to check if game is restarted
	 */
	private boolean restart;

	/**
	 * for changing the color of tile or tile's element
	 */
	Color newColor, newColor_1;

	/**
	 * to check if game is in play mode
	 */
	boolean playMode;


	/**
	 * Default Constructor
	 */
	public GameController() {

	}

	/**
	 * Arg Constructor - initializes the view and model object
	 * @param gameModel - model object
	 * @param gameView - view object
	 */
	public GameController(GameModel gameModel, GameView gameView) {
		view = gameView;
		model = gameModel;
	}

	/**
	 * To start the timer
	 */
	public void startTimer() {
		// Timer task
		timerTask = new TimerTask() {
			public void run() {
				model.setTime(model.getTime() + 1000);
				model.setHours(model.getTime() / 3600000);
				model.setMinutes((model.getTime() / 60000) % 60);
				model.setSeconds((model.getTime() / 1000) % 60);
				view.getTfTime().setText(String.format(" %02d:%02d:%02d", model.getHours(), model.getMinutes(), model.getSeconds()));
				check = true;
			}
		};
		try {
			timer.scheduleAtFixedRate(timerTask, 0, 1000);

		} catch (Exception e) {
			// Eventual treatment
		}

		timerTask1 = new TimerTask() {
			/*
			run the timer
			 */
			public void run() {
				view.getTfMoves().setText(String.format(" %d", model.moves));
				view.getTfPoints().setText(String.format(" %02d", model.points));
				if (model.points == view.getGridObject().getNumTiles()) {
					view.getWinDialog().setVisible(true);

					timerTask.cancel();
					timerTask1.cancel();

					model.moves = 0;
					model.points = 0;
					model.setTime(0);

					model.setHours(0);
					model.setMinutes(0);
					model.setSeconds(0);
					check = false;
                    view.getDesign().doClick();
				}
			}
		};
		try {
			timer.scheduleAtFixedRate(timerTask1, 0, 500);
		} catch (Exception e) {
				e.printStackTrace();
		}
	}

	/**
     *this method displays the application structure
     */
	public void display() {
		view.configure();// display the view
		view.getDimComboBox().addActionListener(this);
		view.getTypeComboBox().addActionListener(this);
		view.getShow().addActionListener(this);
		view.getRand().addActionListener(this);
		view.getRestart().addActionListener(this);
		view.getDesign().addActionListener(this);
		view.getPlay().addActionListener(this);
		view.getLoad().addActionListener(this);
		view.getSave().addActionListener(this);
		view.getFinish().addActionListener(this);
		view.getHide().addActionListener(this);
		view.getAbout().addActionListener(this);
		view.getColor().addActionListener(this);
		view.getExit().addActionListener(this);
		view.getNew_item().addActionListener(this);
		view.getSolution().addActionListener(this);
		view.getColor1().addActionListener(this);
		view.getColor2().addActionListener(this);

		designMode();

		view.getTfMoves().setText(String.format(" %d", model.moves));
		view.getTfPoints().setText(String.format(" %02d", model.points));
		view.getTfTime().setText(String.format(" %02d:%02d:%02d", model.getHours(), model.getMinutes(), model.getSeconds()));
		view.getTf().setText("");
		view.getTf().append("Design...\n");
		printElements();
	}

	/**
	 * this method overrides the action performed to perform
	 * tasks based on events
	 * @param e the event to be processed
	 */
	@Override
	public void actionPerformed(ActionEvent e) {

		int numTiles;
		if (e.getSource() == view.getDimComboBox() || e.getSource() == view.getTypeComboBox()) {  //defines action for comboboxes
			String text1 = view.getTfLetters().getText();
			
			if(text1 == null || text1.length() == 0) {
				text1 = model.defaultText;
				view.getTfLetters().setText(text1);
			}
			int lenStr = text1.length();
			int length = (int) view.getDimComboBox().getSelectedItem() * (int) view.getDimComboBox().getSelectedItem();
			view.setArr(new int[length]);
			for (int i = 0; i < length; i++) {
				if (i < lenStr) {
					char character = text1.charAt(i); // start on the first character
					int ascii = (int) character;
					view.getArr()[i] = ascii;
				} else {
					view.getArr()[i] = 32;
				}
			}
			view.redefineFrame((int) view.getDimComboBox().getSelectedItem(), false);
			view.getGridObject().shuffle();
			if (newColor != null) {
				view.getGridObject().setColored(newColor);
			}
		} else if (e.getSource() == view.getShow() || e.getSource() == view.getSolution()) {  //defines the action for show or solution
			String text1 = view.getTfLetters().getText();

			if(text1 == null || text1.length() == 0) {
				text1 = model.defaultText;
				view.getTfLetters().setText(text1);
			}
			int lenStr = text1.length();
			int length = (int) view.getDimComboBox().getSelectedItem() * (int) view.getDimComboBox().getSelectedItem();
			view.setArr(new int[length]);
			for (int i = 0; i < length; i++) {
				if (i < lenStr) {
					char character = text1.charAt(i); // start on the first character
					int ascii = (int) character;
					view.getArr()[i] = ascii;
				} else {
					view.getArr()[i] = 32;
				}
			}
			view.redefineFrame((int) view.getDimComboBox().getSelectedItem(), true);
			if (newColor != null) {
				view.getGridObject().setColored(newColor);
			}
			if (e.getSource() == view.getShow()) {
				view.getTf().setText("");
				view.getTf().append("Show ...\n");
				printElements();
			} else {
				view.getTf().setText("");
				view.getTf().append("Solution ...\n");
                 if(!view.getDesign().isEnabled()) {
					 printElements();
				 }
				view.getDesign().doClick();
			}


		} else if (e.getSource() == view.getHide()) {   //defines the action for hide
			if(!view.getGridObject().show){
				return;
			}

			numTiles = (int) view.getDimComboBox().getSelectedItem();
			view.redefineFrame(numTiles, false);
			view.getGridObject().configure();

			view.getTf().setText("");
			view.getTf().append("Hide...\n");

			printElements();
		} else if (e.getSource() == view.getRand()) {      //randomizes the puzzle
			numTiles = (int) view.getDimComboBox().getSelectedItem();
			view.redefineFrame(numTiles, false);
			view.getGridObject().configure();

			view.getGridObject().shuffle();
			if (newColor != null) {
				view.getGridObject().setColored(newColor);
			}
			view.getTf().setText("");
			view.getTf().append("Random...\n");
			printElements();
			model.moves=0;
			view.getGridObject().countPoints();
		} else if (e.getSource() == view.getDesign()) {  //defines the action for design
			designMode();
			playMode = false;
			view.getGridObject().playMode = false;
			//view.redefineFrame((int) view.getDimComboBox().getSelectedItem(), false);
			timerTask.cancel();
			timerTask1.cancel();

			model.moves = 0;
			model.points = 0;
			model.setTime(0);
			model.setHours(0);
			model.setMinutes(0);
			model.setSeconds(0);
			view.getTfTime().setText(String.format(" %02d:%02d:%02d", model.getHours(), model.getMinutes(), model.getSeconds()));
			view.getTfMoves().setText(String.format(" %d", model.moves));
			view.getTfPoints().setText(String.format(" %02d", model.points));
			if (newColor != null) {
				view.getGridObject().setColored(newColor);
			}
			view.getTf().setText("");
			view.getTf().append("Design...\n");
			printElements();
			restart = true;
		} else if (e.getSource() == view.getPlay()) {    //defines the action for play button
		playMode();
			playMode = true;
			if (check == false || finish == true || restart == true) {
				startTimer();
				finish = false;
				restart = false;
			}

			String text1 = view.getTfLetters().getText();

			if(text1 == null || text1.length() == 0) {
				text1 = model.defaultText;
				view.getTfLetters().setText(text1);
			}

			int lenStr = text1.length();
			int length = (int) view.getDimComboBox().getSelectedItem() * (int) view.getDimComboBox().getSelectedItem();
			view.setArr(new int[length]);
			for (int i = 0; i < length; i++) {
				if (i < lenStr) {
					char character = text1.charAt(i); // start on the first character
					int ascii = (int) character;
					view.getArr()[i] = ascii;
				} else {
					view.getArr()[i] = 32;
				}
			}
			view.redefineFrame((int) view.getDimComboBox().getSelectedItem(), false);
			view.getGridObject().configure();
			view.getGridObject().playMode = true;

			if (newColor != null) {
				view.getGridObject().setColored(newColor);
			}
			view.getTf().setText("");
			view.getTf().append("Play...\n");
			printElements();

		} else if (e.getSource() == view.getFinish()) {   //for finished
			view.getFinishDialog().setVisible(true);

			timerTask.cancel();
			timerTask1.cancel();

			view.getTf().setText("");
			view.getTf().append("Finish...\n");
			view.getGridObject().playMode=false;
		} else if (e.getSource() == view.getAbout()) {
			view.getTf().setText("");
			view.getAboutDialog().setVisible(true);
			view.getTf().append("About...\n");
		} else if (e.getSource() == view.getColor() || e.getSource() == view.getColor1() || e.getSource() == view.getColor2()) {
			view.getColorOption().setVisible(true);
			view.getTf().setText("");
			view.getTf().append("Colors...\n");
			if (e.getSource() == view.getColor1()) {
				newColor = JColorChooser.showDialog(view.getFrame(), "New Color", view.getGridObject().getColored());
				view.getGridObject().setColored(newColor);
				view.getTf().append("Color 1...\n");
				view.getGridObject().setForeground(newColor_1);
			} else if (e.getSource() == view.getColor2()) {
				newColor_1 = JColorChooser.showDialog(view.getFrame(), "New Color", view.getGridObject().getForeground());
				view.getTf().append("Color 2...\n");
				view.getGridObject().setForeground(newColor_1);
			}

		} else if (e.getSource() == view.getSave()) {
			boolean check = model.save(view);
			view.getTf().setText("");
			view.getTf().append("Save...\n");
			if (check == false) {
				view.getErrorDialog().setVisible(true);
			}
		} else if (e.getSource() == view.getLoad()) {
			boolean check = model.open(view);
			view.getTf().setText("");
			view.getTf().append("Load...\n");
			int count = 0;
			for (int i = 0; i < view.getGridObject().getElements().length; i++) {
				String str = Integer.toString(view.getGridObject().getElements()[i]);
				view.getTf().append(String.format("%1$3s", str));
				count++;
				if (view.getGridObject().getDimension() == count) {
					view.getTf().append("\n");
					count = 0;
				}
			}
			if (check == false) {
				view.getErrorDialog().setVisible(true);
			}
			if (newColor != null) {
				view.getGridObject().setColored(newColor);
			}
		} else if (e.getSource() == view.getRestart()) {   //defines the action for restart
			if (playMode == true) {
				timerTask.cancel();
				timerTask1.cancel();

				model.moves = 0;
				model.points = 0;
				model.setTime(0);
				model.setHours(0);
				model.setMinutes(0);
				model.setSeconds(0);
				numTiles = (int) view.getDimComboBox().getSelectedItem();
				view.redefineFrame(numTiles, false);
				view.getGridObject().configure();

				if (newColor != null) {
					view.getGridObject().setColored(newColor);
				}
				restart = true;
			} else {
				restart = false;
				return;

			}
			if (check == false || finish == true || restart == true) {
				if (playMode)
					startTimer();
				finish = false;
				restart = false;
			}
			view.getTf().setText("");
			view.getTf().append("Restart...\n");
			printElements();
		} else if (e.getSource() == view.getExit()) {    //defines the action for exit
			System.exit(0);
		} else if (e.getSource() == view.getNew_item()) {    //defines the action for New Game
			playMode();
			playMode = true;

			String text1 = view.getTfLetters().getText();

			if(text1 == null || text1.length() == 0) {
				text1 = model.defaultText;
				view.getTfLetters().setText(text1);
			}
			int lenStr = text1.length();
			int length = (int) view.getDimComboBox().getSelectedItem() * (int) view.getDimComboBox().getSelectedItem();
			view.setArr(new int[length]);
			for (int i = 0; i < length; i++) {
				if (i < lenStr) {
					char character = text1.charAt(i); // start on the first character
					int ascii = (int) character;
					view.getArr()[i] = ascii;
				} else {
					view.getArr()[i] = 32;
				}
			}
			view.redefineFrame((int) view.getDimComboBox().getSelectedItem(), false);
			view.getGridObject().configure();

			if (newColor != null) {
				view.getGridObject().setColored(newColor);
			}
			view.getTf().setText("");
			view.getTf().append("New Game...\n");

		printElements();
			if (playMode == true && timerTask != null && timerTask1 != null) {
				timerTask.cancel();
				timerTask1.cancel();

				model.moves = 0;
				model.points = 0;
				model.setTime(0);
				model.setHours(0);
				model.setMinutes(0);
				model.setSeconds(0);
				numTiles = (int) view.getDimComboBox().getSelectedItem();
				view.getGridObject().configure();

				if (newColor != null) {
					view.getGridObject().setColored(newColor);
				}
				restart = true;
			}
			if (check == false || finish == true || restart == true) {
				if (playMode == true)
					startTimer();
				finish = false;
				restart = false;
			}
		}
	}

	/**
	 * defines button states for design mode
	 */
	public void designMode(){
		view.getDesign().setForeground(Color.gray);
		view.getDesign().setEnabled(false);
		view.getPlay().setForeground(Color.white);
		view.getPlay().setEnabled(true);
		view.getRand().setForeground(Color.gray);
		view.getRand().setEnabled(false);
		view.getLoad().setForeground(Color.gray);
		view.getLoad().setEnabled(false);
		view.getSave().setForeground(Color.gray);
		view.getSave().setEnabled(false);
		view.getFinish().setForeground(Color.gray);
		view.getFinish().setEnabled(false);
		view.getShow().setForeground(Color.white);
		view.getShow().setEnabled(true);
		view.getHide().setForeground(Color.white);
		view.getHide().setEnabled(true);
		view.getDimComboBox().setEnabled(true);
		view.getTypeComboBox().setEnabled(true);
		view.getColor1().setForeground(Color.white);
		view.getColor1().setEnabled(true);
		view.getColor2().setForeground(Color.gray);
		view.getColor2().setEnabled(false);
		view.getRestart().setVisible(false);
		view.getTfLetters().setEnabled(true);
	}

	/**
	 * Define buttons state for play mode
	 */
	public void playMode(){
		view.getDesign().setForeground(Color.white);
		view.getDesign().setEnabled(true);
		view.getPlay().setForeground(Color.gray);
		view.getPlay().setEnabled(false);
		view.getRand().setForeground(Color.white);
		view.getRand().setEnabled(true);
		view.getLoad().setForeground(Color.white);
		view.getLoad().setEnabled(true);
		view.getSave().setForeground(Color.white);
		view.getSave().setEnabled(true);
		view.getFinish().setForeground(Color.white);
		view.getFinish().setEnabled(true);
		view.getShow().setForeground(Color.gray);
		view.getShow().setEnabled(false);
		view.getHide().setForeground(Color.gray);
		view.getHide().setEnabled(false);
		view.getDimComboBox().setEnabled(false);
		view.getTypeComboBox().setEnabled(false);
		view.getTfLetters().setEnabled(false);
		view.getRestart().setVisible(true);
		view.getRestart().setForeground(Color.white);
		view.getRestart().setEnabled(true);
		view.getColor1().setForeground(Color.white);
		view.getColor1().setEnabled(true);
		view.getColor2().setForeground(Color.white);
		view.getColor2().setEnabled(true);
	}

	/**
	 * This method prints the elements present in the array
	 */
	public void printElements() {
		int count = 0;
		if (view.getGridObject().isCheck() == false) {
			for (int i = 0; i < view.getGridObject().getElements().length; i++) {
				String str = Integer.toString(view.getGridObject().getElements()[i]);
				view.getTf().append(String.format("%1$3s", str));
				count++;
				if (view.getGridObject().getDimension() == count) {
					view.getTf().append("\n");
					count = 0;
				}
			}
		} else {
			for (int i = 0; i < view.getGridObject().getElements().length; i++) {
				char str = (char) (view.getGridObject().getElements()[i]);
				view.getTf().append(String.format("%1$3s", str));
				count++;
				if (view.getGridObject().getDimension() == count) {
					view.getTf().append("\n");
					count = 0;
				}
			}
		}
	}
}


