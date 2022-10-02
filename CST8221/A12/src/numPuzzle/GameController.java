package numPuzzle;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JTextField;
/**
 * CET-CS-Level 4
 * 
 * This class is part of num puzzle game it handles the
 * panel design and action defined for the game
 * Professor Name: Dr.Paulo Sousa
 * Student Number: , 041025056 
 * Course: CST8221 - JAVA Application Program 
 * Assessment: Assignment 12
 * 
 * @author Erneste Iradukundasenga, Akshit Sabharwal.
 * 
 * @version 1.1
 */
public class GameController implements ActionListener {
  /**
   * outermost frame for our application (content) 
   */
	private JFrame frame = new JFrame();
	/**
	 *  displaying pop-ups
	 */
	private JFrame frame1 = new JFrame();
	/**
	 *  describes menu bar
	 */
	private JMenuBar menuBar;
	/**
	 * for displaying Menu options when clicked
	 */
	private JMenu menu;
	/**
	 * panels used for designing
	 *
	 */
	private JPanel panel1, panel2;
	/**
	 * objects used for drawing tiles
	 *
	 */
	protected GameGrid obj;
	/**
	 * image icons
	 */
	private ImageIcon image;
	/**
	 * labels used in game
	 */
	private JLabel label, label_1, label_2, label_3, label_4, label_5, label_6, label_7;
	/**
	 * Buttons used in game
	 */
	private JButton button, button_1, button_2, button_3, button_4, button_5, button_6, button_7, button_8,mn_1;
	/**
	 * for dimensions
	 */
	private Integer[] dimension = { 2, 3, 4, 5, 6, 7, 8, 9, 10 };
	/**
	 * combobox for dimensions
	 */
	private JComboBox<Integer> comboBox = new JComboBox<Integer>(dimension);
	/**
	 * type of element present for playing
	 */
	private String[] type = { "Numbers", "Letters" };
	/**
	 * combox1 for getting string value
	 */
	private JComboBox<String> comboBox1 = new JComboBox<>(type);
	/**
	 * group layouts
	 */
	private GroupLayout gl, gl1, gl2;
	/**
	 * text fields used in game
	 */
	private JTextField tf, tf_1, tf_2, tf_3, tf_4;
	/**
	 * initialise array
	 */
	private int[] arr = {1,2,3,4,5,6,7,8,0};

     
	/**
	 * This method configures our game
	 */
	protected void configure() {

		menuBar = new JMenuBar();
		menu = new JMenu("Menu");
		mn_1 = new JButton("About");
		
		panel1 = new JPanel();
		panel2 = new JPanel();
		obj = new GameGrid(3, arr,false);
	
		panel1.setPreferredSize(new Dimension(200, 300));
		panel1.setBorder(BorderFactory.createEmptyBorder(0, 00, 0, 00));
		panel1.setBackground(new Color(39, 51, 52));
		panel2.setPreferredSize(new Dimension(800, 300));
		panel2.setBorder(BorderFactory.createEmptyBorder(0, 0, 00, 00));
		panel2.setBackground(new Color(39, 51, 52));

	
  
		/*
		 * Text field for decoration purposes for now
		 */
		tf = new JTextField();
		tf.setFont(new Font("consolas", Font.BOLD, 18));
		tf.setBorder(BorderFactory.createEmptyBorder(20, 1, 1, 1));
		tf.setPreferredSize(new Dimension(500, 500));
		tf.setBackground(new Color(14, 32, 32));
		tf.setEditable(false);

		/*
		 * Text field for the moves
		 */
		tf_1 = new JTextField();
		tf_1.setForeground(Color.GRAY);
		tf_1.setFont(new Font("consolas", Font.BOLD, 3));
		tf_1.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
		tf_1.setPreferredSize(new Dimension(10, 1));
		tf_1.setBackground(new Color(14, 32, 32));
		tf_1.setEditable(false);

		/*
		 * Text field for the points
		 */
		tf_2 = new JTextField();
		tf_2.setForeground(Color.GRAY);
		tf_2.setFont(new Font("consolas", Font.BOLD, 3));
		tf_2.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
		tf_2.setPreferredSize(new Dimension(10, 1));
		tf_2.setBackground(new Color(14, 32, 32));
		tf_2.setEditable(false);

		/*
		 * Text filed for time
		 */
		tf_3 = new JTextField();
		tf_3.setForeground(Color.GRAY);
		tf_3.setFont(new Font("consolas", Font.BOLD, 3));
		tf_3.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
		tf_3.setPreferredSize(new Dimension(10, 1));
		tf_3.setBackground(new Color(14, 32, 32));
		tf_3.setEditable(false);

		/*
		 * Text field for Adding Text 
		 */
		tf_4 = new JTextField();
		tf_4.setFont(new Font("Consolas", Font.BOLD, 20));
		tf_3.setPreferredSize(new Dimension(10, 1));
		tf_4.setBackground(new Color(14, 32, 32));
		tf_4.setForeground(Color.gray);
		tf_4.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));


		
		menu.setContentAreaFilled(false);
		menu.setFont(new Font("consolas", Font.BOLD, 20));
		menu.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));
		menu.setForeground(Color.gray);
		menu.setFocusable(false);

		mn_1.setContentAreaFilled(false);
		mn_1.setFont(new Font("consolas", Font.BOLD, 20));
		mn_1.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));
		mn_1.setForeground(Color.gray);
		mn_1.setFocusable(false);
		mn_1.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseEntered(java.awt.event.MouseEvent evt) {
				mn_1.setForeground(Color.white);
			}

			public void mouseExited(java.awt.event.MouseEvent evt) {
				mn_1.setForeground(Color.gray);

			}
		});
		mn_1.addActionListener(this);
		
		menuBar.add(menu);
		menuBar.add(mn_1);

		menuBar.setBackground(Color.black);
		menuBar.setBorderPainted(false);
		menu.setForeground(Color.gray);

		button = new JButton();
		button_1 = new JButton("Design");
		button_2 = new JButton("Play");
		button_3 = new JButton("Show");
		button_4 = new JButton("Hide");
		button_5 = new JButton("Save");
		button_6 = new JButton("Load");
		button_7 = new JButton("Rand");
		button_8 = new JButton("Finish");

		button.setText("Restart");
		button.setContentAreaFilled(false);
		button.setFont(new Font("consolas", Font.BOLD, 20));
		button.setBorder(BorderFactory.createEmptyBorder(5, 5, 1, 5));
		button.setForeground(Color.gray);
		button.setFocusable(false);
		button.addActionListener(this);

		/*
		 * action listener for restart
		 */
		button.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseEntered(java.awt.event.MouseEvent evt) {
				button.setForeground(Color.white);
			}

			public void mouseExited(java.awt.event.MouseEvent evt) {
				button.setForeground(Color.gray);

			}
		});

		button_1.setContentAreaFilled(false);
		button_1.setFont(new Font("consolas", Font.BOLD, 20));
		button_1.setBorder(BorderFactory.createEmptyBorder(5, 5, 1, 5));
		button_1.setForeground(Color.gray);
		button_1.setFocusable(false);


		button_2.setContentAreaFilled(false);
		button_2.setFont(new Font("consolas", Font.BOLD, 20));
		button_2.setBorder(BorderFactory.createEmptyBorder(5, 5, 1, 5));
		button_2.setForeground(Color.gray);
		button_2.setFocusable(false);


		button_3.setContentAreaFilled(false);
		button_3.setFont(new Font("consolas", Font.BOLD, 20));
		button_3.setBorder(BorderFactory.createEmptyBorder(5, 25, 1, 5));
		button_3.setForeground(Color.gray);
		button_3.setFocusable(false);

		comboBox.setMaximumSize(new Dimension(1, 18));
		comboBox.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
		comboBox1.setMaximumSize(new Dimension(1, 18));
		comboBox.addActionListener(this);
		comboBox1.addActionListener(this);

		button_4.setContentAreaFilled(false);
		button_4.setFont(new Font("consolas", Font.BOLD, 20));
		button_4.setBorder(BorderFactory.createEmptyBorder(5, 5, 1, 5));
		button_4.setForeground(Color.gray);
		button_4.setFocusable(false);

		button_5.setContentAreaFilled(false);
		button_5.setFont(new Font("consolas", Font.BOLD, 20));
		button_5.setBorder(BorderFactory.createEmptyBorder(5, 25, 1, 5));
		button_5.setForeground(Color.gray);
		button_5.setFocusable(false);
		
		button_6.setContentAreaFilled(false);
		button_6.setFont(new Font("consolas", Font.BOLD, 20));
		button_6.setBorder(BorderFactory.createEmptyBorder(5, 25, 1, 5));
		button_6.setForeground(Color.gray);
		button_6.setFocusable(false);

		button_7.setContentAreaFilled(false);
		button_7.setFont(new Font("consolas", Font.BOLD, 20));
		button_7.setBorder(BorderFactory.createEmptyBorder(5, 25, 1, 30));
		button_7.setForeground(Color.gray);
		button_7.setFocusable(false);

		button_8.setContentAreaFilled(false);
		button_8.setFont(new Font("consolas", Font.BOLD, 20));
		button_8.setBorder(BorderFactory.createEmptyBorder(0, 5, 1, 30));
		button_8.setForeground(Color.gray);
		button_8.setFocusable(false);
		button_8.addActionListener(this);

         /*
          * action listener for Restart button
          */
		button_8.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseEntered(java.awt.event.MouseEvent evt) {
				button_8.setForeground(Color.white);
			}

			public void mouseExited(java.awt.event.MouseEvent evt) {
				button_8.setForeground(Color.gray);
			}
		});

		image = new ImageIcon("src\\numPuzzle\\gamelogo.png");
		/*
		 * labels defined
		 */
		label = new JLabel(image);
		label_1 = new JLabel("Mode: ");
		label_2 = new JLabel("Type: ");
		label_3 = new JLabel("Time: ");
		label_4 = new JLabel("Dim: ");
		label_5 = new JLabel("Moves: ");
		label_6 = new JLabel("Points: ");
		label_7 = new JLabel("Text: ");

		label.setBorder(BorderFactory.createEmptyBorder(0, 70, 0, 60));

		button.setBorder(BorderFactory.createEmptyBorder(0, 70, 0, 60));

		label_1.setBorder(BorderFactory.createEmptyBorder(6, 0, 0, 0));
		label_1.setForeground(Color.gray);
		label_1.setFont(new Font("consolas", Font.BOLD, 20));

		label_2.setBorder(BorderFactory.createEmptyBorder(0, 10, 1, 0));
		label_2.setForeground(Color.gray);
		label_2.setFont(new Font("consolas", Font.BOLD, 20));

		label_3.setBorder(BorderFactory.createEmptyBorder(0, 50, 1, 0));
		label_3.setForeground(Color.gray);
		label_3.setFont(new Font("consolas", Font.BOLD, 20));

		label_4.setBorder(BorderFactory.createEmptyBorder(4, 6, 0, 10));
		label_4.setForeground(Color.gray);
		label_4.setFont(new Font("consolas", Font.BOLD, 20));

		label_5.setBorder(BorderFactory.createEmptyBorder(6, 0, 0, 0));
		label_5.setForeground(Color.gray);
		label_5.setFont(new Font("consolas", Font.BOLD, 20));

		label_3.setBorder(BorderFactory.createEmptyBorder(0, 70, 0, 0));
		label_3.setForeground(Color.gray);
		label_3.setFont(new Font("consolas", Font.BOLD, 20));

		label_6.setBorder(BorderFactory.createEmptyBorder(7, 0, 0, 0));
		label_6.setForeground(Color.gray);
		label_6.setFont(new Font("consolas", Font.BOLD, 20));

		label_7.setBorder(BorderFactory.createEmptyBorder(7, 0, 0, 0));
		label_7.setForeground(Color.gray);
		label_7.setFont(new Font("consolas", Font.BOLD, 20));

		label.setVisible(true);

		gl2 = new GroupLayout(panel2);

		panel2.setLayout(gl2);

		gl2.setAutoCreateGaps(true);
		gl2.setAutoCreateContainerGaps(true);

		gl2.setHorizontalGroup(gl2.createParallelGroup().addComponent(obj));

		gl2.setVerticalGroup(gl2.createSequentialGroup().addComponent(obj));

		gl1 = new GroupLayout(panel1);

		panel1.setLayout(gl1);

		gl1.setAutoCreateGaps(true);
		gl1.setAutoCreateContainerGaps(true);

		gl1.setHorizontalGroup(gl1.createParallelGroup().addComponent(label)
				.addGroup(gl1.createSequentialGroup().addGroup(gl1.createParallelGroup().addComponent(label_1))
						.addComponent(button_1).addComponent(button_2))
				.addGroup(gl1.createSequentialGroup().addGroup(gl1.createParallelGroup().addComponent(label_4))
						.addComponent(comboBox).addComponent(button_3).addComponent(button_4))
				.addGroup(gl1.createSequentialGroup().addGroup(gl1.createParallelGroup().addComponent(button_5))
						.addComponent(button_6).addComponent(button_7))
				.addGroup(gl1.createSequentialGroup().addGroup(gl1.createParallelGroup().addComponent(label_2))
						.addComponent(comboBox1).addComponent(button_8))
				.addGroup(gl1.createSequentialGroup().addGroup(gl1.createParallelGroup().addComponent(label_5))
						.addComponent(tf_1).addComponent(label_6).addComponent(tf_2))

				.addGroup(gl1.createParallelGroup().addComponent(tf))
				.addGroup(gl1.createSequentialGroup().addGroup(gl1.createParallelGroup().addComponent(label_3))
						.addComponent(tf_3))

				.addComponent(button));

		gl1.setVerticalGroup(gl1.createSequentialGroup().addComponent(label)
				.addGroup(gl1.createSequentialGroup()
						.addGroup(gl1.createParallelGroup().addGroup(gl1.createSequentialGroup().addComponent(label_1))
								.addComponent(button_1).addComponent(button_2)))
				.addGroup(gl1.createParallelGroup().addGroup(gl1.createSequentialGroup().addComponent(label_4))
						.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE,
								GroupLayout.PREFERRED_SIZE)
						.addComponent(button_3).addComponent(button_4))
				.addGroup(gl1.createParallelGroup().addGroup(gl1.createSequentialGroup().addComponent(button_5))
						.addComponent(button_6).addComponent(button_7))
				.addGroup(gl1.createParallelGroup().addGroup(gl1.createSequentialGroup().addComponent(label_2))
						.addComponent(comboBox1, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE,
								GroupLayout.PREFERRED_SIZE)
						.addComponent(button_8))
				.addGroup(gl1.createParallelGroup().addGroup(gl1.createSequentialGroup().addComponent(label_5))
						.addComponent(tf_1).addComponent(label_6).addComponent(tf_2))

				.addGroup(gl1.createParallelGroup().addComponent(tf))
				.addGroup(gl1.createParallelGroup().addGroup(gl1.createSequentialGroup().addComponent(label_3))
						.addComponent(tf_3))

				.addComponent(button));

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
			frame.setIconImage(ImageIO.read(new File("src\\numPuzzle\\puzz.png")));
		} catch (IOException e) {
			e.printStackTrace();
		}
		frame.getContentPane().setBackground(Color.black);
		frame.setMinimumSize(new Dimension(1000, 600));
		frame.setPreferredSize(new Dimension(1000, 600));
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setResizable(true);
		frame.setVisible(true);
		

	}

	/**
	 * This method is used re-define the panel2 again
	 * based on user selection for type - number or letters 
	 * and for dimensions as well
	 * @param num
	 *        new dimensions
	 */
	public void redefineFrame(int num) {

		panel2.removeAll();

		if (comboBox1.getSelectedItem() == "Numbers") {
	    int[] numArray = new int[num*num];
			for(int i =0;i< (num*num) -1;i++) {
				numArray[i] = i+1;
			}
			obj = new GameGrid(num, numArray, false);
			gl2.setHorizontalGroup(gl2.createParallelGroup().addComponent(obj));
			gl2.setVerticalGroup(gl2.createSequentialGroup().addComponent(obj));
		} else {
			tf_4.removeAll();
			obj = new GameGrid(num, arr,true);
			gl2.setHorizontalGroup(gl2.createParallelGroup().addComponent(obj).addGroup(gl2.createSequentialGroup()
					.addGroup(gl2.createParallelGroup().addComponent(label_7)).addComponent(tf_4))

			);

			gl2.setVerticalGroup(gl2.createSequentialGroup().addComponent(obj)
					.addGroup(gl2.createSequentialGroup().addGroup(gl2.createParallelGroup()
							.addGroup(gl2.createSequentialGroup().addComponent(label_7)).addComponent(tf_4)))

			);
		}
	}

	/**
	 * This override methods takes care button action
	 * @param e 
	 *         for listening events
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
	 if (e.getSource() == comboBox1 || e.getSource() == button || e.getSource() == comboBox) {

			String text = tf_4.getText();
			int lenStr = text.length();
			int length = (int) comboBox.getSelectedItem() * (int) comboBox.getSelectedItem();
			arr = new int[length];
			for (int i = 0; i < length; i++) {
				if (i < lenStr) {
					char character = text.charAt(i); // start on the first character
					int ascii = (int) character;
					arr[i] = ascii;
				} else {
					arr[i] = 32;
				}
			}
			redefineFrame((int) comboBox.getSelectedItem());

		}  else if (e.getSource() == button_8) {
			frame1 = new JFrame();
			frame1.setLayout(new BorderLayout());
			try {
				frame1.setIconImage(ImageIO.read(new File("src\\numPuzzle\\puzz.png")));
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			frame1.setContentPane(new JLabel(new ImageIcon("src\\numPuzzle\\gameend.png")));
			label.setPreferredSize(new Dimension(700, 1000));
			frame1.pack();
			frame1.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			frame1.setLocationRelativeTo(null);
			frame1.setResizable(false);
			frame1.setVisible(true);
		}else if(e.getSource() == mn_1) {
			frame1 = new JFrame();
			frame1.setLayout(new BorderLayout());
			try {
				frame1.setIconImage(ImageIO.read(new File("src\\numPuzzle\\puzz.png")));
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			frame1.setContentPane(new JLabel(new ImageIcon("src\\numPuzzle\\gameabout.png")));
			label.setPreferredSize(new Dimension(700, 1000));
			frame1.pack();
			frame1.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			frame1.setLocationRelativeTo(null);
			frame1.setResizable(false);
			frame1.setVisible(true);
		}
	}
	
	
}

  