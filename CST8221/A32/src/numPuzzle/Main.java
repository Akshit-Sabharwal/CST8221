package numPuzzle;

import java.awt.Color;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

/**
 * CET-CS-Level 4
 *
 * This class is entry point for our application
 *
 * Professor Name: Dr.Paulo Sousa Course: CST8221 - JAVA Application Program
 * Assessment: Assignment 21
 *
 * @author Erneste Iradukundasenga, Akshit Sabharwal.
 * @version 3.1
 */
public class Main implements ActionListener {
	/**
	 * outer frame for main interface
	 */
	private JFrame frame = new JFrame();
	/**
	 * panel used for main interface
	 */
	private JPanel panel_1, panel_2;
	/**
	 * group layouts
	 */
	private GroupLayout gl, gl1;
	/**
	 * label for main interface
	 */
	private JLabel label;
	/**
	 * image icon
	 */
	private ImageIcon image;
	/**
	 * buttons for main interface
	 */
	private JButton client, stand_alone, server;
	/**
	 * server object
	 */
	private Server server_obj;
	/**
	 * client object
	 */
	private Client client_obj;

	/**
	 * The entry point of application.
	 *
	 * @param args the input arguments
	 */
	public static void main(String[] args) {
		Main main = new Main();
		main.configure();
	}

	/**
	 * this method configures main interface
	 */

	public void configure() {

		panel_1 = new JPanel();
		panel_2 = new JPanel();
		client = new JButton("Client");
		client.setContentAreaFilled(false);
		client.setFont(new Font("consolas", Font.BOLD, 20));
		client.setBorder(BorderFactory.createEmptyBorder(0, 45, 0, 0));
		client.setForeground(Color.gray);
		client.setFocusable(false);
		client.addActionListener(this);
		client.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				client.setForeground(new Color(0, 128, 128));
			}

			public void mouseExited(MouseEvent e) {
				client.setForeground(Color.gray);
			}
		});

		server = new JButton("Server");
		server.setContentAreaFilled(false);
		server.setFont(new Font("consolas", Font.BOLD, 20));
		server.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 30));
		server.setForeground(Color.gray);
		server.setFocusable(false);
		server.addActionListener(this);
		server.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				server.setForeground(new Color(0, 128, 128));
			}

			public void mouseExited(MouseEvent e) {
				server.setForeground(Color.gray);
			}
		});

		stand_alone = new JButton("Stand Alone");
		stand_alone.setContentAreaFilled(false);
		stand_alone.setFont(new Font("consolas", Font.BOLD, 20));
		stand_alone.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 20));
		stand_alone.setForeground(Color.gray);
		stand_alone.setFocusable(false);
		stand_alone.addActionListener(this);
		stand_alone.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				stand_alone.setForeground(new Color(0, 128, 128));
			}

			public void mouseExited(MouseEvent e) {
				stand_alone.setForeground(Color.gray);
			}
		});

		image = new ImageIcon(getClass().getClassLoader().getResource("gameclient.png"));
		label = new JLabel(image);
		panel_1.setPreferredSize(new Dimension(400, 200));
		panel_1.setBorder(BorderFactory.createEmptyBorder(0, 00, 0, 00));
		panel_1.setBackground(new Color(8, 26, 42));

		panel_2.setPreferredSize(new Dimension(400, 200));
		panel_2.setBorder(BorderFactory.createEmptyBorder(0, 00, 0, 00));
		panel_2.setBackground(new Color(8, 26, 42));

		gl1 = new GroupLayout(panel_2);

		panel_2.setLayout(gl1);

		gl1.setAutoCreateGaps(true);
		gl1.setAutoCreateContainerGaps(true);

		gl1.setVerticalGroup(gl1.createParallelGroup().addComponent(client).addGroup(gl1.createSequentialGroup())
				.addComponent(stand_alone).addComponent(server));

		gl1.setHorizontalGroup(gl1.createSequentialGroup().addComponent(client).addGroup(gl1.createSequentialGroup())
				.addComponent(stand_alone).addComponent(server));

		ImageIcon imageIcon = new ImageIcon(getClass().getClassLoader().getResource("gamemode.png"));
		Image image = imageIcon.getImage();
		Image newimg = image.getScaledInstance(370, 170, java.awt.Image.SCALE_FAST);
		imageIcon = new ImageIcon(newimg);
		label = new JLabel(imageIcon);
		panel_1.add(label);
		gl = new GroupLayout(frame.getContentPane());

		frame.getContentPane().setLayout(gl);

		gl.setAutoCreateGaps(true);
		gl.setAutoCreateContainerGaps(true);

		gl.setHorizontalGroup(gl.createParallelGroup().addComponent(panel_1).addGroup(gl.createSequentialGroup())
				.addComponent(panel_2));

		gl.setVerticalGroup(gl.createSequentialGroup().addComponent(panel_1).addGroup(gl.createSequentialGroup())
				.addComponent(panel_2));

		frame.setTitle("Number Puzzle");
		try {
			frame.setIconImage(ImageIO.read(getClass().getClassLoader().getResource("puzz.png")));
		} catch (IOException e) {
			e.printStackTrace();
		}
		frame.getContentPane().setBackground(Color.black);
		frame.setMinimumSize(new Dimension(440, 300));
		frame.setPreferredSize(new Dimension(400, 300));
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);

	}

	/**
	 *  Performs action based on events
	 * @param e the event to be processed
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == client) {
			client_obj = new Client();
			client_obj.configure();
		} else if (e.getSource() == stand_alone) {
			GameModel gameModel = new GameModel();
			GameView gameView = new GameView(gameModel);
			GameController gameController = new GameController(gameModel, gameView);
			gameController.splash_screen();
		} else if (e.getSource() == server) {
			server.setEnabled(false);
			server_obj = new Server();
			server_obj.configure();
		}
	}
}
