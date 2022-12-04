package numPuzzle;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JWindow;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;
import javax.swing.plaf.basic.BasicComboBoxUI;
import javax.swing.plaf.basic.BasicScrollBarUI;

/**
 * CET-CS-Level 4
 *
 * This class defines the view part of our game
 *
 * Professor Name: Dr.Paulo Sousa Course: CST8221 - JAVA Application Program
 * Assessment: Assignment 12
 *
 * @author Erneste Iradukundasenga, Akshit Sabharwal.
 * @version 1.1
 */
public class GameView extends JWindow {

	/**
	 * serialization unique id
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * outermost frame for our application (content)
	 */
	private JFrame frame = new JFrame();

	/**
	 * model object
	 */
	private GameModel gameModel;
	/**
	 * describes menu bar
	 */
	private JMenuBar menuBar;
	/**
	 * for displaying Menu options when clicked
	 */
	private JMenu menu, help;
	/**
	 * panels used for designing
	 *
	 */
	private JPanel panel1, panel2;
	/**
	 * object used for drawing tiles
	 *
	 */
	private GameGrid gameGrid = new GameGrid();
	/**
	 * image icons
	 * 
	 */
	private ImageIcon image, image_1, icon_New, icon_sol, icon_next, icon_about, icon_colors;
	/**
	 * labels used in game
	 */
	private JLabel label, mode, type, time, dim, moves, points, text;

	/**
	 * getter for frame
	 * 
	 * @return outermost frame
	 */
	public JFrame getFrame() {
		return frame;
	}

	/**
	 * Buttons used in game
	 */
	private JButton design, play, show, hide, save, load, rand, finish, restart, color1, color2;
	/**
	 * for dimensions
	 */
	private Integer[] dimension = { 3, 4, 5, 6, 7, 8, 9, 10 };
	/**
	 * combobox for dimensions
	 */
	private JComboBox<Integer> dimComboBox = new JComboBox<Integer>(dimension);

	/**
	 * type of element present for playing
	 */
	private String[] value = { "Numbers", "Letters" };
	/**
	 * combox1 for getting string value
	 */
	private JComboBox<String> typeComboBox = new JComboBox<>(value);
	/**
	 * group layouts
	 */
	private GroupLayout gl, gl1, gl2, gl3;
	/**
	 * text fields used in game
	 */
	private JTextField tfMoves, tfPoints, tfTime, tfLetters;

	/**
	 * text file for displaying actions
	 */
	private JTextArea tf;
	/**
	 * initialise array
	 */
	private int[] arr = new int[9];

	/**
	 * splashscreen image
	 */
	private Image splashScreen;

	/**
	 * defines the menu options for game
	 */
	private JMenuItem new_item, exit, color, about, solution;

	/**
	 * getter for New button
	 * 
	 * @return new button
	 */
	public JMenuItem getNew_item() {
		return new_item;
	}

	/**
	 * dialog for finished
	 */
	private JDialog finishDialog = new JDialog(getFrame(), "FINISH");

	/**
	 * dialog for win
	 */
	private JDialog winDialog = new JDialog(getFrame(), "WIN");

	/**
	 * dialog for about
	 */
	private JDialog aboutDialog = new JDialog(getFrame(), "ABOUT");

	/**
	 * dialog for error button
	 */
	private JDialog errorDialog = new JDialog(getFrame(), "ERROR");

	/**
	 * dialog for changing colors
	 */
	private JDialog colorOption = new JDialog(getFrame(), "COLOR");

	/**
	 * for adding scroll
	 */
	private JScrollPane scroll;

	/**
	 * getter for win dialog
	 * 
	 * @return win dialog window
	 */
	public JDialog getWinDialog() {
		return winDialog;
	}

	/**
	 * getter for finish dialog
	 * 
	 * @return finished window
	 */
	public JDialog getFinishDialog() {
		return finishDialog;
	}

	/**
	 * Arg constructor
	 * 
	 * @param model - model object
	 */
	public GameView(GameModel model) {
		gameModel = model;
	}

	/**
	 * Default Constructor
	 */
	public GameView() {
	}

	/**
	 * splash screen
	 */
	public void splash_screen() {
		splashScreen = Toolkit.getDefaultToolkit().getImage(getClass().getClassLoader().getResource("gamecfg.png"));
		image_1 = new ImageIcon(splashScreen);
		setSize(image_1.getIconWidth(), image_1.getIconHeight());
		setLocationRelativeTo(null);
		setVisible(true);

	}

	/**
	 * This method configures our game
	 */
	protected void configure() {

		this.dimComboBox.setSelectedIndex(0);

		menuBar = new JMenuBar();
		menu = new JMenu("Menu");
		help = new JMenu("Help");

		// create a label
		finishDialog
				.setContentPane(new JLabel(new ImageIcon((getClass().getClassLoader().getResource("gameend.png")))));

		// set visibility of dialog

		finishDialog.pack();

		finishDialog.setLocationRelativeTo(null);

		finishDialog.setResizable(false);

		finishDialog.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		// create a label
		winDialog
				.setContentPane(new JLabel(new ImageIcon((getClass().getClassLoader().getResource("gamewinner.png")))));

		// set visibility of dialog

		winDialog.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		winDialog.pack();

		winDialog.setLocationRelativeTo(null);

		winDialog.setResizable(false);

		aboutDialog
				.setContentPane(new JLabel(new ImageIcon((getClass().getClassLoader().getResource("gameabout.png")))));

		// set visibility of dialog

		aboutDialog.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		aboutDialog.pack();

		aboutDialog.setLocationRelativeTo(null);

		aboutDialog.setResizable(false);

		errorDialog.setContentPane(new JLabel(new ImageIcon((getClass().getClassLoader().getResource("gameerr.png")))));

		errorDialog.setResizable(false);

		errorDialog.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		errorDialog.pack();

		errorDialog.setLocationRelativeTo(null);

		colorOption.setResizable(false);

		colorOption.getContentPane().setBackground((new Color(8, 26, 42)));

		colorOption.setPreferredSize(new Dimension(300, 80));

		colorOption.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		colorOption.pack();

		colorOption.setLocationRelativeTo(null);

		icon_New = new ImageIcon((getClass().getClassLoader().getResource("iconext.png")));
		icon_sol = new ImageIcon((getClass().getClassLoader().getResource("iconsol.png")));
		icon_next = new ImageIcon((getClass().getClassLoader().getResource("iconnew.png")));
		icon_about = new ImageIcon((getClass().getClassLoader().getResource("iconabt.png")));
		icon_colors = new ImageIcon((getClass().getClassLoader().getResource("iconcol.png")));

		UIManager.put("PopupMenu.border", new LineBorder(Color.BLACK));

		new_item = new JMenuItem("New");
		solution = new JMenuItem("Solution");
		exit = new JMenuItem("Exit");
		about = new JMenuItem("About");
		color = new JMenuItem("Colors");

		new_item.setIcon(icon_New);
		solution.setIcon(icon_sol);
		exit.setIcon(icon_next);
		about.setIcon(icon_about);
		color.setIcon(icon_colors);

		new_item.setBackground(new Color(8, 26, 42));
		new_item.setForeground(Color.GRAY);
		new_item.setFocusable(false);
		new_item.setBorder(BorderFactory.createEmptyBorder(0, 00, 0, 00));

		solution.setBackground(new Color(8, 26, 42));
		solution.setForeground(Color.GRAY);
		solution.setBorder(BorderFactory.createEmptyBorder(0, 00, 0, 00));

		exit.setBackground(new Color(8, 26, 42));
		exit.setForeground(Color.GRAY);
		exit.setBorder(BorderFactory.createEmptyBorder(0, 00, 0, 00));

		about.setBackground(new Color(8, 26, 42));
		about.setForeground(Color.GRAY);
		about.setBorder(BorderFactory.createEmptyBorder(0, 00, 0, 00));

		color.setBackground(new Color(8, 26, 42));
		color.setForeground(Color.GRAY);
		color.setBorder(BorderFactory.createEmptyBorder(0, 00, 0, 00));

		menu.setFont(new Font("consolas", Font.BOLD, 20));
		menu.setForeground(Color.gray);
		menu.setFocusable(false);

		menu.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseEntered(java.awt.event.MouseEvent evt) {
				menu.setForeground(Color.white);
			}

			public void mouseExited(java.awt.event.MouseEvent evt) {
				menu.setForeground(Color.gray);

			}
		});

		help.setFont(new Font("consolas", Font.BOLD, 20));
		help.setForeground(Color.gray);
		help.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseEntered(java.awt.event.MouseEvent evt) {
				help.setForeground(Color.white);
			}

			public void mouseExited(java.awt.event.MouseEvent evt) {
				help.setForeground(Color.gray);
			}
		});

		menu.add(new_item);
		menu.add(solution);
		menu.add(exit);

		help.add(about);
		help.add(color);

		menuBar.add(menu);
		menuBar.add(help);
		menuBar.setBackground(Color.black);
		menuBar.setBorderPainted(false);

		panel1 = new JPanel();
		panel2 = new JPanel();
		for (int i = 0; i < arr.length; i++)
			arr[i] = 1 + i;

		setObj(new GameGrid(3, arr, arr, false, true, false, gameModel, false));

		gameGrid.shuffle();
		panel1.setPreferredSize(new Dimension(200, 300));
		panel1.setBorder(BorderFactory.createEmptyBorder(0, 00, 0, 00));
		panel1.setBackground(new Color(8, 26, 42));
		panel2.setPreferredSize(new Dimension(500, 300));
		panel2.setBorder(BorderFactory.createEmptyBorder(0, 0, 00, 00));
		panel2.setBackground(new Color(8, 26, 42));

		/*
		 * text field for decoration purposes for now
		 */

		setTf(new JTextArea());
		getTf().setFont(new Font("consolas", Font.BOLD, 18));
		getTf().setBorder(BorderFactory.createEmptyBorder(20, 20, 1, 1));
		getTf().setPreferredSize(new Dimension(500, 3000));
		getTf().setBackground(new Color(5, 17, 25));
		getTf().setEditable(false);
		getTf().setForeground(Color.gray);
		getTf().setLineWrap(true);

		scroll = new JScrollPane(getTf());
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scroll.setBackground(Color.black);
		scroll.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

		scroll.getVerticalScrollBar().setBackground(Color.BLACK);
		scroll.getVerticalScrollBar().setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		scroll.getVerticalScrollBar();

		scroll.getVerticalScrollBar().setUI(new BasicScrollBarUI() {
			@Override
			protected void configureScrollBarColors() {
				this.thumbColor = Color.GRAY;
			}

			@Override
			protected JButton createIncreaseButton(int orientation) {
				JButton button = super.createDecreaseButton(orientation);
				button.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
				button.setBackground(Color.black);
				button.setFocusable(false);
				button.setContentAreaFilled(false);
				return button;
			}

			@Override
			protected JButton createDecreaseButton(int orientation) {
				JButton button = super.createIncreaseButton(orientation);
				button.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
				button.setBackground(Color.black);
				button.setFocusable(false);
				button.setContentAreaFilled(false);
				return button;
			}
		});

		this.dimComboBox.setBackground(new Color(8, 26, 42));
		this.dimComboBox.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		this.dimComboBox.setForeground(Color.GRAY);
		this.dimComboBox.setFocusable(false);
		this.dimComboBox.setMaximumRowCount(this.dimComboBox.getModel().getSize());
		this.dimComboBox.setUI(new BasicComboBoxUI() {
			@Override
			protected JButton createArrowButton() {
				JButton button = super.createArrowButton();
				button.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
				button.setBackground(new Color(8, 26, 42));
				button.setFocusable(false);
				button.setContentAreaFilled(false);

				return button;
			}

		});
		this.typeComboBox.setBackground(new Color(8, 26, 42));
		this.typeComboBox.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		this.typeComboBox.setForeground(Color.GRAY);
		this.typeComboBox.setFocusable(false);
		this.typeComboBox.setMaximumRowCount(this.typeComboBox.getModel().getSize());
		this.typeComboBox.setUI(new BasicComboBoxUI() {
			@Override
			protected JButton createArrowButton() {
				JButton button = super.createArrowButton();
				button.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
				button.setBackground(new Color(8, 26, 42));
				button.setFocusable(false);
				button.setContentAreaFilled(false);

				return button;
			}

		});
		/*
		 * text field for the moves
		 */
		tfMoves = new JTextField();
		tfMoves.setForeground(Color.GRAY);
		tfMoves.setFont(new Font("consolas", Font.BOLD, 15));
		tfMoves.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
		tfMoves.setPreferredSize(new Dimension(45, 1));
		tfMoves.setBackground(new Color(5, 17, 25));
		tfMoves.setEditable(false);

		/*
		 * text field for the points
		 */
		tfPoints = new JTextField();
		tfPoints.setForeground(Color.GRAY);
		tfPoints.setFont(new Font("consolas", Font.BOLD, 15));
		tfPoints.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
		tfPoints.setPreferredSize(new Dimension(10, 1));
		tfPoints.setBackground(new Color(5, 17, 25));
		tfPoints.setEditable(false);

		/*
		 * text filed for time
		 */
		setTfTime(new JTextField());
		getTfTime().setForeground(Color.GRAY);
		getTfTime().setFont(new Font("consolas", Font.BOLD, 20));
		getTfTime().setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
		getTfTime().setPreferredSize(new Dimension(10, 1));
		getTfTime().setBackground(new Color(8, 26, 42));
		getTfTime().setEditable(false);

		/*
		 * text field for Adding text
		 */
		setTfLetters(new JTextField());
		getTfLetters().setFont(new Font("Consolas", Font.BOLD, 20));
		getTfLetters().setPreferredSize(new Dimension(12, 1));
		getTfLetters().setBackground(new Color(5, 17, 25));
		getTfLetters().setForeground(Color.gray);
		getTfLetters().setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));

		restart = new JButton("Reset");
		design = new JButton("Design");
		play = new JButton("Play");
		show = new JButton("Show");
		hide = new JButton("Hide");
		save = new JButton("Save");
		load = new JButton("Load");
		rand = new JButton("Rand");
		finish = new JButton("Finish");
		color2 = new JButton("COLOR 2");
		color1 = new JButton("COLOR 1");

		restart.setContentAreaFilled(false);
		restart.setFont(new Font("consolas", Font.BOLD, 20));
		restart.setBorder(BorderFactory.createEmptyBorder(5, 5, 1, 5));
		restart.setForeground(Color.gray);
		restart.setFocusable(false);

		design.setContentAreaFilled(false);
		design.setFont(new Font("consolas", Font.BOLD, 20));
		design.setBorder(BorderFactory.createEmptyBorder(5, 5, 1, 5));
		design.setForeground(Color.gray);
		design.setFocusable(false);

		play.setContentAreaFilled(false);
		play.setFont(new Font("consolas", Font.BOLD, 20));
		play.setBorder(BorderFactory.createEmptyBorder(5, 5, 1, 5));
		play.setForeground(Color.gray);
		play.setFocusable(false);

		show.setContentAreaFilled(false);
		show.setFont(new Font("consolas", Font.BOLD, 20));
		show.setBorder(BorderFactory.createEmptyBorder(5, 25, 1, 5));
		show.setForeground(Color.gray);
		show.setFocusable(false);

		getDimComboBox().setMaximumSize(new Dimension(1, 18));
		getDimComboBox().setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
		getTypeComboBox().setMaximumSize(new Dimension(1, 18));

		hide.setContentAreaFilled(false);
		hide.setFont(new Font("consolas", Font.BOLD, 20));
		hide.setBorder(BorderFactory.createEmptyBorder(5, 5, 1, 5));
		hide.setForeground(Color.gray);
		hide.setFocusable(false);

		save.setContentAreaFilled(false);
		save.setFont(new Font("consolas", Font.BOLD, 20));
		save.setBorder(BorderFactory.createEmptyBorder(5, 25, 1, 5));
		save.setForeground(Color.gray);
		save.setFocusable(false);

		load.setContentAreaFilled(false);
		load.setFont(new Font("consolas", Font.BOLD, 20));
		load.setBorder(BorderFactory.createEmptyBorder(5, 25, 1, 5));
		load.setForeground(Color.gray);
		load.setFocusable(false);

		rand.setContentAreaFilled(false);
		rand.setFont(new Font("consolas", Font.BOLD, 20));
		rand.setBorder(BorderFactory.createEmptyBorder(5, 25, 1, 30));
		rand.setForeground(Color.gray);
		rand.setFocusable(false);

		finish.setContentAreaFilled(false);
		finish.setFont(new Font("consolas", Font.BOLD, 20));
		finish.setBorder(BorderFactory.createEmptyBorder(0, 5, 1, 30));
		finish.setForeground(Color.gray);
		finish.setFocusable(false);

		color1.setContentAreaFilled(false);
		color1.setFont(new Font("consolas", Font.BOLD, 20));
		color1.setBorder(BorderFactory.createEmptyBorder(0, 50, 1, 30));
		color1.setForeground(Color.gray);
		color1.setFocusable(false);

		color2.setContentAreaFilled(false);
		color2.setFont(new Font("consolas", Font.BOLD, 20));
		color2.setBorder(BorderFactory.createEmptyBorder(0, 5, 1, 0));
		color2.setForeground(Color.gray);
		color2.setFocusable(false);

		gl3 = new GroupLayout(colorOption.getContentPane());

		colorOption.setLayout(gl3);

		gl3.setAutoCreateGaps(true);
		gl3.setAutoCreateContainerGaps(true);

		gl3.setVerticalGroup(gl3.createParallelGroup().addComponent(color1).addGroup(gl3.createSequentialGroup())
				.addComponent(color2));

		gl3.setHorizontalGroup(gl3.createSequentialGroup().addComponent(color1).addGroup(gl3.createSequentialGroup())
				.addComponent(color2));

		image = new ImageIcon(getClass().getClassLoader().getResource("gamelogo.png"));
		/*
		 * labels defined
		 */
		label = new JLabel(image);
		mode = new JLabel("Mode: ");
		type = new JLabel("Type: ");
		time = new JLabel("Time: ");
		dim = new JLabel("dim: ");
		moves = new JLabel("Moves: ");
		points = new JLabel("Points: ");
		text = new JLabel("Text: ");

		label.setBorder(BorderFactory.createEmptyBorder(0, 70, 0, 60));

		restart.setBorder(BorderFactory.createEmptyBorder(0, 70, 0, 60));

		mode.setBorder(BorderFactory.createEmptyBorder(6, 0, 0, 0));
		mode.setForeground(Color.gray);
		mode.setFont(new Font("consolas", Font.BOLD, 20));

		type.setBorder(BorderFactory.createEmptyBorder(0, 10, 1, 0));
		type.setForeground(Color.gray);
		type.setFont(new Font("consolas", Font.BOLD, 20));

		time.setBorder(BorderFactory.createEmptyBorder(0, 50, 1, 0));
		time.setForeground(Color.gray);
		time.setFont(new Font("consolas", Font.BOLD, 20));

		dim.setBorder(BorderFactory.createEmptyBorder(4, 6, 0, 10));
		dim.setForeground(Color.gray);
		dim.setFont(new Font("consolas", Font.BOLD, 20));

		moves.setBorder(BorderFactory.createEmptyBorder(6, 0, 0, 0));
		moves.setForeground(Color.gray);
		moves.setFont(new Font("consolas", Font.BOLD, 20));

		points.setBorder(BorderFactory.createEmptyBorder(7, 0, 0, 0));
		points.setForeground(Color.gray);
		points.setFont(new Font("consolas", Font.BOLD, 20));

		text.setBorder(BorderFactory.createEmptyBorder(7, 0, 0, 0));
		text.setForeground(Color.gray);
		text.setFont(new Font("consolas", Font.BOLD, 20));

		label.setVisible(true);

		gl2 = new GroupLayout(panel2);

		panel2.setLayout(gl2);

		gl2.setAutoCreateGaps(true);
		gl2.setAutoCreateContainerGaps(true);

		gl2.setHorizontalGroup(gl2.createParallelGroup().addComponent(getGridObject()));

		gl2.setVerticalGroup(gl2.createSequentialGroup().addComponent(getGridObject()));

		gl1 = new GroupLayout(panel1);

		panel1.setLayout(gl1);

		gl1.setAutoCreateGaps(true);
		gl1.setAutoCreateContainerGaps(true);

		gl1.setHorizontalGroup(gl1.createParallelGroup().addComponent(label)
				.addGroup(gl1.createSequentialGroup().addGroup(gl1.createParallelGroup().addComponent(mode))
						.addComponent(design).addComponent(play))
				.addGroup(gl1.createSequentialGroup().addGroup(gl1.createParallelGroup().addComponent(dim))
						.addComponent(getDimComboBox()).addComponent(show).addComponent(hide))
				.addGroup(gl1.createSequentialGroup().addGroup(gl1.createParallelGroup().addComponent(save))
						.addComponent(load).addComponent(rand))
				.addGroup(gl1.createSequentialGroup().addGroup(gl1.createParallelGroup().addComponent(type))
						.addComponent(getTypeComboBox()).addComponent(finish))
				.addGroup(gl1.createSequentialGroup().addGroup(gl1.createParallelGroup().addComponent(moves))
						.addComponent(tfMoves).addComponent(points).addComponent(tfPoints))
				.addGroup(gl1.createParallelGroup().addComponent(scroll)).addGroup(gl1.createSequentialGroup()
						.addGroup(gl1.createParallelGroup().addComponent(time)).addComponent(getTfTime()))
				.addComponent(restart));
		gl1.setVerticalGroup(gl1.createSequentialGroup().addComponent(label)
				.addGroup(gl1.createSequentialGroup()
						.addGroup(gl1.createParallelGroup().addGroup(gl1.createSequentialGroup().addComponent(mode))
								.addComponent(design).addComponent(play)))
				.addGroup(gl1.createParallelGroup().addGroup(gl1.createSequentialGroup().addComponent(dim))
						.addComponent(getDimComboBox(), GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE,
								GroupLayout.PREFERRED_SIZE)
						.addComponent(show).addComponent(hide))
				.addGroup(gl1.createParallelGroup().addGroup(gl1.createSequentialGroup().addComponent(save))
						.addComponent(load).addComponent(rand))
				.addGroup(gl1.createParallelGroup().addGroup(gl1.createSequentialGroup().addComponent(type))
						.addComponent(getTypeComboBox(), GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE,
								GroupLayout.PREFERRED_SIZE)
						.addComponent(finish))
				.addGroup(gl1.createParallelGroup().addGroup(gl1.createSequentialGroup().addComponent(moves))
						.addComponent(tfMoves).addComponent(points).addComponent(tfPoints))

				.addGroup(gl1.createParallelGroup().addComponent(scroll)).addGroup(gl1.createParallelGroup()
						.addGroup(gl1.createSequentialGroup().addComponent(time)).addComponent(getTfTime()))
				.addComponent(restart));

		frame.setJMenuBar(menuBar);

		gl = new GroupLayout(frame.getContentPane());

		frame.getContentPane().setLayout(gl);

		gl.setAutoCreateGaps(true);
		gl.setAutoCreateContainerGaps(true);

		gl.setVerticalGroup(gl.createParallelGroup().addComponent(panel2).addGroup(gl.createSequentialGroup())
				.addComponent(panel1));

		gl.setHorizontalGroup(gl.createSequentialGroup().addComponent(panel2).addGroup(gl.createSequentialGroup())
				.addComponent(panel1));

		frame.setTitle("Number Puzzle");
		try {
			frame.setIconImage(ImageIO.read(getClass().getClassLoader().getResource("puzz.png")));
		} catch (IOException e) {
			e.printStackTrace();
		}
		frame.getContentPane().setBackground(Color.black);
		frame.setMinimumSize(new Dimension(1000, 600));
		frame.setPreferredSize(new Dimension(1000, 600));
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setVisible(true);

	}

	/**
	 * colors for solved tiles
	 * 
	 * @return color of soleved tiles
	 */
	public JButton getColor1() {
		return color1;
	}

	/**
	 * color for unsolved tiles
	 * 
	 * @return color of unsolved tiles
	 */
	public JButton getColor2() {
		return color2;
	}

	/**
	 * getter for color dialog box
	 * 
	 * @return color options
	 */
	public JDialog getColorOption() {
		return colorOption;
	}

	/**
	 * getter for error dialog
	 * 
	 * @return error dialog
	 */
	public JDialog getErrorDialog() {
		return errorDialog;
	}

	/**
	 *
	 * @return exit button
	 */
	public JMenuItem getExit() {
		return exit;
	}

	/**
	 *
	 * @return color button in menu
	 */
	public JMenuItem getColor() {
		return color;
	}

	/**
	 *
	 * @return About dialog box
	 */
	public JDialog getAboutDialog() {
		return aboutDialog;
	}

	/**
	 *
	 * @return About button in menu
	 */
	public JMenuItem getAbout() {
		return about;
	}

	/**
	 *
	 * @return hide button
	 */
	public JButton getHide() {
		return hide;
	}

	/**
	 *
	 * @return save button
	 */
	public JButton getSave() {
		return save;
	}

	/**
	 *
	 * @return load button
	 */
	public JButton getLoad() {
		return load;
	}

	/**
	 *
	 * @return finished button
	 */
	public JButton getFinish() {
		return finish;
	}

	/**
	 *
	 * @return play button
	 */
	public JButton getPlay() {
		return play;
	}

	/**
	 * getter for design button
	 * 
	 * @return design button
	 */
	public JButton getDesign() {
		return design;
	}

	/**
	 *
	 * @return random button
	 */
	public JButton getRand() {
		return rand;
	}

	/**
	 *
	 * @return textField for points
	 */
	public JTextField getTfPoints() {
		return tfPoints;
	}

	/**
	 * getter for array
	 * 
	 * @return array
	 */
	public int[] getArr() {
		return arr;
	}

	/**
	 * set the array elements
	 * 
	 * @param arr - elements
	 */
	public void setArr(int[] arr) {
		this.arr = arr;
	}

	/**
	 * getetr for show button
	 * 
	 * @return show button
	 */
	public JButton getShow() {
		return show;
	}

	/**
	 *
	 * @return textField for moves
	 */
	public JTextField getTfMoves() {
		return tfMoves;
	}

	/**
	 * This method is used re-define the panel2 again based on user selection for
	 * type - number or letters and for dimensions as well
	 *
	 * @param num  new dimensions
	 * @param show - if its solution to be displayed
	 */
	public void redefineFrame(int num, boolean show) {

		panel2.removeAll();
		boolean playMode = !play.isEnabled();
		if (getTypeComboBox().getSelectedItem() == "Numbers") {
			int[] numArray = new int[num * num];
			for (int i = 0; i < (num * num) - 1; i++) {
				numArray[i] = i + 1;
			}
			gameGrid = new GameGrid(num, numArray, numArray, false, show, false, gameModel, playMode);
			gl2.setHorizontalGroup(gl2.createParallelGroup().addComponent(getGridObject()));
			gl2.setVerticalGroup(gl2.createSequentialGroup().addComponent(getGridObject()));
		} else {
			getTfLetters().removeAll();

			gameGrid = (new GameGrid(num, arr, arr, true, show, false, gameModel, playMode));
			gl2.setHorizontalGroup(
					gl2.createParallelGroup().addComponent(getGridObject()).addGroup(gl2.createSequentialGroup()
							.addGroup(gl2.createParallelGroup().addComponent(text)).addComponent(getTfLetters()))

			);

			gl2.setVerticalGroup(gl2.createSequentialGroup().addComponent(getGridObject())
					.addGroup(gl2.createSequentialGroup().addGroup(gl2.createParallelGroup()
							.addGroup(gl2.createSequentialGroup().addComponent(text)).addComponent(getTfLetters())))

			);
		}
	}

	/**
	 * This method is used to define frame based on saved file
	 * 
	 * @param num   number of tiles
	 * @param arr   elements in array
	 * @param arr1  temp array
	 * @param check number or letters
	 * @param show  if it's solution or not
	 */
	public void redefineFrame_1(int num, int[] arr, int[] arr1, boolean check, boolean show) {

		panel2.removeAll();
		boolean playMode = !play.isEnabled();
		if (check == false) {

			gameGrid = new GameGrid(num, arr, arr1, check, show, true, gameModel, playMode);
			gameGrid.configure();
			gl2.setHorizontalGroup(gl2.createParallelGroup().addComponent(getGridObject()));
			gl2.setVerticalGroup(gl2.createSequentialGroup().addComponent(getGridObject()));
		} else {
			getTfLetters().removeAll();

			gameGrid = new GameGrid(num, arr, arr1, check, show, true, gameModel, playMode);
			gameGrid.configure();
			gl2.setHorizontalGroup(
					gl2.createParallelGroup().addComponent(getGridObject()).addGroup(gl2.createSequentialGroup()
							.addGroup(gl2.createParallelGroup().addComponent(text)).addComponent(getTfLetters()))

			);

			gl2.setVerticalGroup(gl2.createSequentialGroup().addComponent(getGridObject())
					.addGroup(gl2.createSequentialGroup().addGroup(gl2.createParallelGroup()
							.addGroup(gl2.createSequentialGroup().addComponent(text)).addComponent(getTfLetters())))

			);
		}
	}

	/**
	 * Paint image onto JWindow
	 */
	public void paint(Graphics g) {
		super.paint(g);
		g.drawImage(splashScreen, 0, 0, this);
	}

	/**
	 * getter for dimCombobox
	 * 
	 * @return combobox selection type
	 */
	public JComboBox<Integer> getDimComboBox() {
		return dimComboBox;
	}

	/**
	 * getter for typeCombobox
	 * 
	 * @return selection type - numbers or letters
	 */
	public JComboBox<String> getTypeComboBox() {
		return typeComboBox;
	}

	/**
	 *
	 * @return text field for letter
	 */
	public JTextField getTfLetters() {
		return tfLetters;
	}

	/**
	 * getter for solution button
	 * 
	 * @return solution button
	 */
	public JMenuItem getSolution() {
		return solution;
	}

	/**
	 *
	 * @param tfLetter - letters entered by user
	 */
	public void setTfLetters(JTextField tfLetter) {
		this.tfLetters = tfLetter;
	}

	/**
	 *
	 * @return text field for time
	 */
	public JTextField getTfTime() {
		return tfTime;
	}

	/**
	 * return textfield for time
	 * 
	 * @param tfTime - time
	 */
	public void setTfTime(JTextField tfTime) {
		this.tfTime = tfTime;
	}

	/**
	 * getter for grid object
	 * 
	 * @return gamegrid object
	 */
	public GameGrid getGridObject() {
		return gameGrid;
	}

	/**
	 * @return the gameGrid
	 */
	public GameGrid getGameGrid() {
		return gameGrid;
	}

	/**
	 * @param gameGrid the gameGrid to set
	 */
	public void setGameGrid(GameGrid gameGrid) {
		this.gameGrid = gameGrid;
	}

	/**
	 * getter for restart button
	 * 
	 * @return restart button
	 */
	public JButton getRestart() {
		return restart;
	}

	/**
	 * setter for game grid object
	 * 
	 * @param obj - grid object
	 */
	public void setObj(GameGrid obj) {
		this.gameGrid = obj;
	}

	/**
	 * getter for text area
	 * 
	 * @return text area for defining current actions
	 */
	public JTextArea getTf() {
		return tf;
	}

	/**
	 * setter for text
	 * 
	 * @param tf - text area
	 */
	public void setTf(JTextArea tf) {
		this.tf = tf;
	}
}
