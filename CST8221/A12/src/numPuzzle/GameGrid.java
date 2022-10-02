package numPuzzle;

import java.awt.*;

import javax.swing.JPanel;

/**
 * CET-CS-Level 4
 * 
 * This class draws the grids/tiles for num puzzle
 * by overriding paint component
 * 
 * Professor Name: Dr.Paulo Sousa
 * Course: CST8221 - JAVA Application Program 
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
     *
	 * total size of the grid window
	 */
	private int size = 500;
	/**
	 * dimensions of our game
	 */
	private int dimension;
	/**
	 * array containing element values
	 */
	private int[] elements;
	/**
	 * individual tile size 
	 */
	private int tileSize;
	/**
	 * boolean to check if its string or number
	 */
	private boolean check = true;

	/**
	 * Arg Constructor - defines the dimensions, values for element array
	 * 
	 * @param dimension
	 *           defines the dimension of the game from 2 to 10
	 * @param arr
	 *        array containing the values of elements 
	 * @param check
	 *         true - for string
	 *         false - for number
	 */
	public GameGrid(int dimension, int[] arr,boolean check) {
		
		this.dimension = dimension;   // defines dimensions
		this.check = check;

		elements = new int[dimension * dimension];        // Initialize element array
		
		
		for (int i = 0; i < arr.length - 1; i++)
			elements[i] = arr[i];                // stores values into elements array

		
		tileSize = (size - 65) / dimension;      //finds the individual tile size

		setPreferredSize(new Dimension(size, size));          //defines the dimensions for numpuzzle
		setBackground(new Color(39, 51, 52));  
		setForeground(new Color(14, 32, 32));
	

	}
	

	/**
	 * This methods draw element in the centre of the 
	 * tile.
	 * @param g
	 *        graphics object for drawing
	 * @param s 
	 *         value of the element
	 * @param x
	 *        x-coordinate
	 * @param y
	 *        y-coordinate
	 */
	public void drawElement(Graphics2D g, String s, int x, int y) {
		g.setFont(new Font("Consolas", Font.PLAIN, tileSize / 2));
		FontMetrics f = g.getFontMetrics();  
		x = x + (tileSize - f.stringWidth(s)) / 2;
		y = y + (f.getAscent() + (tileSize - (f.getAscent()+ f.getDescent())) / 2);
		g.drawString(s,x , y);

	}
	
    /**
     * This method overrides the paintComponent and 
     * draw our grid 
     * 
     *  @param g 
     *         Graphics object.
     */
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D graphics2D = (Graphics2D) g;  //casting to Graphics2D
		graphics2D.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_GASP);  //to improve drawing
	 for (int i = 0; i < elements.length; i++) {
 
			int row = i / dimension;   //calculates the row position
			int column = i % dimension;   //calculates the column position

			int x =  75 +column * tileSize;   //x-coordinate
			int y = 10 + row * tileSize;     //y-coordinate

			
			if (elements[i] == 0) {				//blank space check
				g.setColor(Color.BLACK);
				g.fillRoundRect(x, y, tileSize, tileSize, 25, 25);  //create empty tile

				continue;    // skips its character drawing 
			}

	
			g.setColor(getForeground());
			g.fillRoundRect(x, y, tileSize, tileSize, 25, 25);
			g.setColor(Color.black);
			g.drawRoundRect(x, y, tileSize, tileSize, 20, 20);
			g.setColor(Color.gray);
			
			if(check ) { //if its string
			
			 String str = Character.toString((char)elements[i]);   // convert integer to its corresponding character value 	
			 drawElement(graphics2D, str , x, y);    //calls draw element method
			}else {
	    	drawElement(graphics2D, Integer.toString(elements[i]), x, y);   //calls draw element
			}
		}

	}
}


