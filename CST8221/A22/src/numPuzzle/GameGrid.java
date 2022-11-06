package numPuzzle;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;

import javax.swing.JPanel;

/**
 * CET-CS-Level 4
 * 
 * This class draws the grids/tiles for num puzzle by overriding paint component
 * and responsible for tile movement
 * 
 * Professor Name: Dr.Paulo Sousa Course: CST8221 - JAVA Application Program
 * Assessment: Assignment 12
 * 
 * @author Erneste Iradukundasenga, Akshit Sabharwal.
 * @version 1.1
 */
public class GameGrid extends JPanel {

	/**
	 * serialization unique id
	 */
	private static final long serialVersionUID = 1L;


	/**
	 * dimensions of game
	 */
	private int dimension;

	/**
	 * margin between tiles
	 */
	private int margin = 10;

	/**
	 * array containing element values
	 */
	private int[] elements;
	/**
	 * tiles present in grid
	 */
	private int numTiles;
	/**
	 * individual tile size
	 */
	private int tileSize;
	/**
	 * total grid size
	 */
	private int gridSize;

	/**
	 * boolean to check if its string or number
	 */
	private boolean check = true;

	/**
	 * boolean to check if game is finished
	 */
	private boolean gameFinished = true;
	/**
	 * model object
	 */
	private GameModel gameModel;

	/**
	 * blank tile
	 */
	private int blankCell;
	/**
	 * to check if show is  clicked
	 */
	protected boolean show = false;
	/**
	 * to randomize the array
	 */
	private static final Random RANDOM = new Random();

	/**
	 * temp array
	 */
	private int[] tempArr;
	/**
	 * for loading the file
	 */
	private boolean load;

	/**
	 * color of tiles
	 */
	private Color coloredTile = Color.gray;

	/**
	 * grid color
	 */
	private Color colored = new Color(0, 128, 128);

	/**
	 * getter for grid color
	 * @return grid color
	 */
	public Color getColored() {
		return colored;
	}

	/**
	 * set grid color
	 * @param colored - grid color
	 */
	public void setColored(Color colored) {
		this.colored = colored;
	}

	/**
	 * Default constructor
	 */
	public GameGrid() {

	}


	/**
	 *
	 *  Arg Constructor - defines the dimensions, values for element array etc
	 *
	 * @param dimension defines the dimension of the game from 2 to 10
	 * @param arr       array containing the values of elements
	 * @param arr1      temp array
	 * @param check     true - for string false - for number
	 * @param show      show - to show the solution
	 * @param load      to load the file
	 * @param model     model object
	 */
	public GameGrid(int dimension, int[] arr, int[] arr1, boolean check, boolean show, boolean load, GameModel model) {

		this.dimension = dimension; // defines dimensions
		this.setCheck(check);
		this.show = show;
		this.load = load;
		gameModel = model;


		numTiles = dimension * dimension - 1;
		elements = new int[dimension * dimension]; // Initialize element array
		tempArr = new int[dimension * dimension];


		for (int i = 0; i < arr.length - 1; i++)
			elements[i] = arr[i];

		for (int i = 0; i < arr.length - 1; i++)
			tempArr[i] = arr1[i];


		// calculate grid size and tile size
		gridSize = (480 - 2 * 30);
		tileSize = gridSize / dimension; // finds the individual tile size

		setPreferredSize(new Dimension(gridSize, gridSize)); // defines the dimensions for numpuzzle
		setBackground(new Color(8, 26, 42));
		setForeground(new Color(5, 17, 25));



	}

	/**
	 * getter for temp array
	 * @return tempArr
	 */
	public int[] getTempArr() {
		return tempArr;
	}

	/**
	 * this method configures the listener for the tile movement
	 */
	public void configure() {
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				coloredTile = new Color(0, 128, 128);
				repaint();
			}

			public void mouseExited(MouseEvent e) {
				coloredTile = Color.gray;
				repaint();
			}

			public void mousePressed(MouseEvent e) {	
				if (gameFinished) {
						newGame();
				} else{

					/*position of click*/
					int x = e.getX() - margin;
					int y = e.getY() - margin;

					//if clicked within the grid
					if (x < 0 || x > gridSize || y < 0 || y > gridSize)
						return;

				//finds the position of clicked col
					int clickedCol = x / tileSize;
					int clickedRow = y / tileSize;
					


					int clickPos = clickedRow * dimension + clickedCol;
					if(isTileMovable(clickedRow,clickedCol))
						moveTile(clickPos);

					gameFinished = solved();
				}

				countPoints();

				repaint();
			}
		});

		if (load == false) {
			newGame();
		}else {
			show = false;
			gameFinished = false;
			blankCell = elements.length - 1;
		}
	}

	/**
	 * checks whether game created is Solvable or not
	 * @return true - if it's solvable
	 *          false - if its not
	 */
	public boolean isGameSolvable() {
		int numberInversions = 0;

		if (show == true)
			return true;

		if (isCheck() != true) {
			for (int i = 0; i < numTiles; i++) {
				for (int j = 0; j < i; j++) {
					if (elements[j] > elements[i])
						numberInversions++;
				}
			}
			return numberInversions % 2 == 0;
		} else {
			for (int i = 0; i < numTiles; i++) {
				for (int j = 0; j < i; j++) {
					if (elements[i] > elements[i + 1] && elements[0] != tempArr[0]
							&& tempArr[numTiles - 1] != elements[numTiles - 1])
						numberInversions++;
					if (show == false && elements[0] == tempArr[0] && tempArr[numTiles - 1] == elements[0])
						return true;
				}
			}

			if (numberInversions % 2 == 0 && numberInversions != 0)
				check = true;
			return check;
		}
	}

	/**
	 * This method creates the new game instance
	 */
	public void newGame() {

		do {

			reset();
			if (!show) {
				shuffle(); // shuffle
			}
			countPoints();
		} while (!isGameSolvable());


		show = false;
		gameFinished = false;
	}

	/**
	 * this method resets the position
	 */
	public void reset() {
		if (!isCheck()) {
			for (int i = 0; i < elements.length; i++) {
				elements[i] = (i + 1) % elements.length;
			}
			blankCell = elements.length - 1;
		} else {
			for (int i = 0; i < elements.length - 1; i++)
				elements[i] = tempArr[i];
			blankCell = elements.length - 1;
			elements[blankCell] = 0;
		}


	}

	/**
	 * this function moves the tiles and increase the moves simultaneously
	 * @param clickedIndex index of the clicked tile
	 */
	public void moveTile(int clickedIndex) {

		int temp = elements[blankCell];
		elements[blankCell]= elements[clickedIndex];
		elements[clickedIndex] = temp;
		blankCell = clickedIndex;

	gameModel.moves++;
}

	/**
	 * This method checks if tile is movable
	 * @param clickedRow - row of the clicked tile
	 * @param clickedColumn - column of clicked tile
	 * @return true - if movable
	 *         false - if not
	 */
	public boolean isTileMovable(int clickedRow, int clickedColumn) {
	int blankCol = blankCell % dimension;
	int blankRow = blankCell / dimension;
	//check whether its in same row or column
	if(blankRow == clickedRow || blankCol == clickedColumn){
		//if its only 1 position away from blank tile
		if(Math.abs(clickedRow - blankRow) == 1 || Math.abs(clickedColumn - blankCol) == 1) return true;
	}
		//false for every other possible case
		return false;
}

	/**
	 * shuffles the array
	 */
	public void shuffle() {
    //ignores the blank tile
		int totalTiles = numTiles -1;
		for (int i = totalTiles;i>0;i--){
			int random = RANDOM.nextInt(i+1);
			int temp = elements[i];
			elements[i] = elements[random];
			elements[random] = temp;
		}

	}

	/**
	 * This method checks if same is solved or not
	 * @return true - if solved
	 *         false - if not finished
	 */
	public boolean solved() {

		if (elements[elements.length - 1] != 0)
			return false;
		if (!isCheck()) {
			for (int i = numTiles - 1; i >= 0; i--) {
				if (elements[i] != i + 1)
					return false;
			}
		} else {

			for (int i = numTiles - 1; i >= 0; i--) {
				if ((elements[i] != tempArr[i]))
					return false;
			}
		}
		return true;
	}

	/**
	 * This method counts points
	 */
	public void countPoints() {
		gameModel.points = 0;
		for (int i = numTiles - 1; i >= 0; i--) {
			if (elements[i] == tempArr[i]) {
				gameModel.points++;
			}
		}
	}

	/**
	 * This method draw element in the centre of the tile.
	 * 
	 * @param g graphics object for drawing
	 * @param s value of the element
	 * @param x x-coordinate
	 * @param y y-coordinate
	 */
	public void drawElement(Graphics2D g, String s, int x, int y) {
		g.setFont(new Font("Consolas", Font.PLAIN, getTileSize() / 2));
		FontMetrics f = g.getFontMetrics();
		x = x + (getTileSize() - f.stringWidth(s)) / 2;
		y = y + (f.getAscent() + (getTileSize() - (f.getAscent() + f.getDescent())) / 2);
		g.drawString(s, x, y);

	}

	/**
	 * This method overrides the paintComponent and draw our grid
	 * 
	 * @param g Graphics object.
	 */
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D graphics2D = (Graphics2D) g; // casting to Graphics2D
		graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON); // to
																														// //
																														// improve
																														// drawing
		for (int i = 0; i < getElements().length; i++) {

			int row = i / getDimension(); // calculates the row position
			int column = i % getDimension(); // calculates the column position

			int x = margin + column * getTileSize(); // x-coordinate
			int y = margin + row * getTileSize(); // y-coordinate

			if (getElements()[i] == 0) { // blank space check
				g.setColor(Color.BLACK);
				g.fillRoundRect(x, y, getTileSize(), getTileSize(), 25, 25); // create empty tile
				continue; // skips its character drawing
			}

			g.setColor(getForeground());
			g.fillRoundRect(x, y, getTileSize(), getTileSize(), 25, 25);
			g.setColor(Color.black);
			g.drawRoundRect(x, y, getTileSize(), getTileSize(), 20, 20);
			g.setColor(coloredTile);

			if (getElements()[i] == tempArr[i]) {
				g.setColor(getColored());
				g.fillRoundRect(x, y, getTileSize(), getTileSize(), 25, 25);
				g.setColor(Color.black);
				g.drawRoundRect(x, y, getTileSize(), getTileSize(), 20, 20);
				g.setColor(new Color(13, 13, 38));
			}

			if (isCheck()) { // if its string

				String str = Character.toString((char) getElements()[i]); // convert integer to its corresponding
																			// character
																			// value
				drawElement(graphics2D, str, x, y); // calls draw element method
			} else {
				drawElement(graphics2D, Integer.toString(getElements()[i]), x, y); // calls draw element
			}
		}
	}

	/**
	 * getter for tileSize
	 * @return present size of tiles
	 */
	public int getTileSize() {
		return tileSize;
	}

	/**
	 * getter for dimension
	 * @return present selected dimension
	 */
	public int getDimension() {
		return dimension;
	}

	/**
	 * sets the dimension
	 * @param dimension - dimension size
	 */
	public void setDimension(int dimension) {
		this.dimension = dimension;
	}

	/**
	 * getter for elements
	 * @return elements array
	 */
	public int[] getElements() {
		return elements;
	}


	/**
	 * getter for number of tiles
	 * @return number of tiles
	 */
	public int getNumTiles() {
		return numTiles;
	}

	/**
	 * getter for check
	 * @return check - whether its string or number
	 */
	public boolean isCheck() {
		return check;
	}

	/**
	 * setter for check
	 * @param check - sets check value
	 */
	public void setCheck(boolean check) {
		this.check = check;
	}
}
