package numPuzzle;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.Formatter;
import java.util.Scanner;

import javax.swing.JFileChooser;

/**
 * CET-CS-Level 4
 *
 * This class defines the model of our application, and stores data
 *
 * Professor Name: Dr.Paulo Sousa Course: CST8221 - JAVA Application Program
 * Assessment: Assignment 21
 *
 * @author Erneste Iradukundasenga, Akshit Sabharwal.
 * @version 2.1
 */
public class GameModel {
	/**
	 * for writing the data
	 */
	private static Formatter output;
	/**
	 * for taking input from file
	 */
	private static Scanner input;

	/**
	 * points in game
	 */
	protected int points;

	/**
	 * used for timer
	 */
	private int time = 0;

	/**
	 * time in seconds
	 */
	private int seconds = 0;

	/**
	 * time in minutes
	 */

	private int minutes = 0;
	/**
	 * for counting moves
	 */
	protected int moves;

	/**
	 * time in hours
	 */
	private int hours = 0;
	/**
	 * default text for letters
	 */
 protected String defaultText = "Hey, there";

	/**
	 * default constructor
	 */

	public GameModel() {

	}

	/**
	 * getting the time
	 * @return time
	 */
	public int getTime() {
		return time;
	}

	/**
	 * setter for time
	 * @param time - sets the time
	 */
	public void setTime(int time) {
		this.time = time;
	}

	/**
	 * getter for seconds
	 * @return time in seconds
	 */
	public int getSeconds() {
		return seconds;
	}

	/**
	 * setter for seconds
	 * @param seconds - sets time in seconds
	 */
	public void setSeconds(int seconds) {
		this.seconds = seconds;
	}

	/**
	 * getter for minutes
	 * @return time in minutes
	 */
	public int getMinutes() {
		return minutes;
	}
	/**
	 * setter for minutes
	 * @param minutes - sets time in minutes
	 */
	public void setMinutes(int minutes) {
		this.minutes = minutes;
	}

	/**
	 * getter for hours
	 * @return time in hours
	 */
	public int getHours() {
		return hours;
	}

	/**
	 * setter for hours
	 * @param hours - sets time in minutes
	 */
	public void setHours(int hours) {
		this.hours = hours;
	}
	
	

	/**
	 * @return the points
	 */
	public int getPoints() {
		return points;
	}

	/**
	 * @param points the points to set
	 */
	public void setPoints(int points) {
		this.points = points;
	}

	/**
	 * This method is used to save the game
	 * @param view - view object
	 * @return true - if success,
	 *         false - if failed
	 */
	public boolean save(GameView view) {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setDialogTitle("Save the file");
		int response = fileChooser.showSaveDialog(view.getFrame());
		if (response == JFileChooser.APPROVE_OPTION) {
			File f = fileChooser.getSelectedFile();
			try {
				FileWriter fileWriter = new FileWriter(f.getPath());
				output = new Formatter(fileWriter);
				output.format("%s,", view.getGridObject().getDimension());
				if (view.getGridObject().isCheck() == false) {
					output.format("%s,", "1");
					for (int i = 0; i < view.getGridObject().getElements().length; i++) {
						output.format("%s,", view.getGridObject().getElements()[i]);
					}
					for (int i = 0; i < view.getGridObject().getTempArr().length; i++) {
						output.format("%s,", view.getGridObject().getTempArr()[i]);
					}
				} else {
					output.format("%d,", 2);
					for (int i = 0; i < view.getGridObject().getElements().length; i++) {
						output.format("%s,", view.getGridObject().getElements()[i]);
					}
					for (int i = 0; i < view.getGridObject().getTempArr().length; i++) {
						output.format("%s,", view.getGridObject().getTempArr()[i]);
					}
				}

				fileWriter.flush();
				fileWriter.close();
				return true;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			}
		}
		return false;
	}

	/**
	 * This method is used to load the game
	 * @param view - view object
	 * @return true - if success,
	 *         false - if failed
	 */
	public boolean open(GameView view) {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setDialogTitle("Open the file");
		int dimension;
		int[] arr;
		int response = fileChooser.showOpenDialog(view.getFrame());
		if (response == JFileChooser.APPROVE_OPTION) {
			try {
				input = new Scanner(Paths.get(fileChooser.getSelectedFile().getAbsolutePath()));
				input.useDelimiter(",");
				dimension = input.nextInt();
				if (input.nextInt() == 1) {
					view.getGridObject().setDimension(dimension);
					int length = dimension * dimension;
					view.setArr(new int[length]);
					arr = (new int[length]);
					for (int i = 0; i < length; i++) {
						view.getArr()[i] = input.nextInt();
						
					}
					for (int i = 0; i < length; i++) {
						arr[i] = input.nextInt();


					}
					view.redefineFrame_1(dimension, view.getArr(), arr, false, false);
				} else{
					view.getGridObject().setDimension(dimension);
					int length = dimension * dimension;
					view.setArr(new int[length]);
					arr = (new int[length]);
					for (int i = 0; i < length; i++) {
						view.getArr()[i] = input.nextInt();
					}
					for (int i = 0; i < length; i++) {
						arr[i] = input.nextInt();
					}
					view.redefineFrame_1(dimension, view.getArr(), arr, true, false);
				}
				// close file
				if (input != null) {
					input.close();

					return true;
				}
			} catch (NoSuchFileException fne) { // catches exceptions while reading from file
				System.out.println("File Not Found, ignoring... ");
				return false;

			} catch (FileNotFoundException fne) {
				System.out.println("File Not Found, ignoring... ");
				return false;

			} catch (IOException e) {
				System.out.println("error ");
				return false;

			} catch (Exception e) {
				System.out.println(e);
				return false;

			}
		}
		return true;
	}
}
