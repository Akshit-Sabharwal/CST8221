package numPuzzle;

import java.awt.*;
import java.awt.event.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.plaf.basic.BasicScrollBarUI;
/**
 * CET-CS-Level 4
 *
 * This class implements client interface and actions
 *
 * Professor Name: Dr.Paulo Sousa Course: CST8221 - JAVA Application Program
 * Assessment: Assignment 21
 *
 * @author Erneste Iradukundasenga, Akshit Sabharwal.
 * @version 3.1
 */
public class Client implements ActionListener {

	/**
	 * outer frame for client
	 */
	private JFrame frame;
	/**
	 * buttons used in client side
	 */
	private JButton connect, end, new_game, send_game, receive_game, send_data, play;
	/**
	 * labels used in client
	 */
	private JLabel user, server, port, label;
	/**
	 * for storing image
	 */
	private ImageIcon image;
	/**
	 * grouplayout for craeting interface
	 */
	private GroupLayout gl, gl1;
	/**
	 * text area
	 */
	private JTextArea tf;
	/**
	 * text fields used in client
	 */
	private JTextField tf_user, tf_server, tf_port;
	/**
	 * for scrolling in text area
	 */
	private JScrollPane scroll;
	/**
	 * panels used in client side
	 */
	private JPanel panel1, panel2;
	/**
	 * error dialog for displaying errors
	 */
	private JDialog errorDialog;
	/**
	 * Model Object
	 */
	GameModel gameModel = new GameModel();
	/**
	 * view object
	 */
	GameView gameView = new GameView(gameModel);
	/**
	 *Controller object
	 */
	GameController gameController = new GameController(gameModel, gameView);
	/**
	 * for reading from server
	 */
	DataInputStream inputFromServer;
	/**
	 * for sending the data to server
	 */
	DataOutputStream outputToServer;
  /**
   * client socket
   */
	Socket client;

	/**
	 * default constructor
	 */
	Client() {

	}

	/**
	 *  outer frame of the client
	 * @return outer frame of the client
	 */
	public JFrame getFrame() {
		return frame;
	}

	/**
	 *
	 * @param frame - sets outer frame of the game
	 */
	public void setFrame(JFrame frame) {
		this.frame = frame;
	}

	/**
	 * configures the client interface
	 */
	public void configure() {

		frame = new JFrame();
		panel1 = new JPanel();
		panel2 = new JPanel();

		user = new JLabel("User:");
		user.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		user.setForeground(Color.gray);
		user.setFont(new Font("consolas", Font.BOLD, 17));
		server = new JLabel("Server:");
		server.setBorder(BorderFactory.createEmptyBorder(0, 15, 0, 0));
		server.setForeground(Color.gray);
		server.setFont(new Font("consolas", Font.BOLD, 17));
		port = new JLabel("Port:");
		port.setBorder(BorderFactory.createEmptyBorder(0, 15, 0, 0));
		port.setForeground(Color.gray);
		port.setFont(new Font("consolas", Font.BOLD, 17));

		tf = new JTextArea();
		tf.setFont(new Font("consolas", Font.BOLD, 15));
		tf.setBorder(BorderFactory.createEmptyBorder(20, 20, 1, 1));
		tf.setPreferredSize(new Dimension(300, 10000000));
		tf.setBackground(new Color(5, 17, 25));
		tf.setEditable(false);
		tf.setForeground(Color.gray);
		tf.setLineWrap(true);

		errorDialog = new JDialog(new JDialog(getFrame()));
		errorDialog.setContentPane(new JLabel(new ImageIcon((getClass().getClassLoader().getResource("gameerr.png")))));
		errorDialog.setResizable(false);
		if (client == null)
			errorDialog.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		errorDialog.pack();
		errorDialog.setLocationRelativeTo(null);

		scroll = new JScrollPane(tf);
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scroll.setBackground(Color.black);
		scroll.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

		scroll.getVerticalScrollBar().setBackground(new Color(5, 17, 25));
		scroll.getVerticalScrollBar().setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		scroll.getVerticalScrollBar();

		scroll.getVerticalScrollBar().setUI(new BasicScrollBarUI() {
			@Override
			protected void configureScrollBarColors() {
				this.thumbColor = new Color(5, 17, 25);
			}

			@Override
			protected JButton createIncreaseButton(int orientation) {
				JButton button = super.createDecreaseButton(orientation);
				button.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
				button.setBackground(new Color(5, 17, 25));
				button.setFocusable(false);
				button.setContentAreaFilled(false);
				return button;
			}

			@Override
			protected JButton createDecreaseButton(int orientation) {
				JButton button = super.createIncreaseButton(orientation);
				button.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
				button.setBackground(new Color(5, 17, 25));
				button.setFocusable(false);
				button.setContentAreaFilled(false);
				return button;
			}
		});

		tf_user = new JTextField();
		tf_user.setForeground(Color.GRAY);
		tf_user.setFont(new Font("consolas", Font.BOLD, 13));
		tf_user.setBorder(BorderFactory.createEmptyBorder(3, 5, 0, 0));
		tf_user.setBackground(new Color(5, 17, 25));
		tf_user.setEditable(true);
		tf_user.setText(Config.DEFAULT_USER);

		tf_server = new JTextField();
		tf_server.setForeground(Color.GRAY);
		tf_server.setFont(new Font("consolas", Font.BOLD, 13));
		tf_server.setBorder(BorderFactory.createEmptyBorder(3, 5, 0, 0));
		tf_server.setBackground(new Color(5, 17, 25));
		tf_server.setEditable(false);
		tf_server.setText(Config.DEFAULT_ADDR);
        tf_server.setEnabled(false);

		tf_port = new JTextField();
		tf_port.setForeground(Color.GRAY);
		tf_port.setFont(new Font("consolas", Font.BOLD, 13));
		tf_port.setBorder(BorderFactory.createEmptyBorder(3, 5, 0, 0));
		tf_port.setBackground(new Color(5, 17, 25));
		tf_port.setEditable(true);
		tf_port.setText(Integer.toString(Config.DEFAULT_PORT));
		tf_port.addKeyListener(
				new KeyListener(){

					@Override
					public void keyTyped(KeyEvent e) {
						char c = e.getKeyChar();

						if (!((c >= '0') && (c <= '9') )){
							Toolkit.getDefaultToolkit().beep();
							e.consume();
						}
					}

					public void keyPressed(KeyEvent e){
					}

					@Override
					public void keyReleased(KeyEvent e) {

					}
				}
		);
		connect = new JButton("Connect ");
		connect.setContentAreaFilled(false);
		connect.setFont(new Font("consolas", Font.BOLD, 17));
		connect.setBorder(BorderFactory.createEmptyBorder(0, 18, 0, 0));
		connect.setForeground(Color.gray);
		connect.setFocusable(false);
		connect.addActionListener(this);
		connect.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				connect.setForeground(new Color(0, 128, 128));
			}

			public void mouseExited(MouseEvent e) {
				connect.setForeground(Color.gray);
			}
		});
		end = new JButton("End");
		end.setContentAreaFilled(false);
		end.setFont(new Font("consolas", Font.BOLD, 17));
		end.setBorder(BorderFactory.createEmptyBorder(0, 18, 0, 0));
		end.setForeground(Color.black);
		end.setFocusable(false);
		end.addActionListener(this);

		end.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				if (client != null)
				end.setForeground(new Color(0, 128, 128));
			}

			public void mouseExited(MouseEvent e) {
				if (client != null)
				end.setForeground(Color.gray);
			}
		});
		new_game = new JButton("New game ");
		new_game.setContentAreaFilled(false);
		new_game.setFont(new Font("consolas", Font.BOLD, 17));
		new_game.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0));
		new_game.setForeground(Color.black);
		new_game.setFocusable(false);
		new_game.addActionListener(this);
		new_game.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				if (client != null)
					new_game.setForeground(new Color(0, 128, 128));
			}

			public void mouseExited(MouseEvent e) {
				if (client != null)
					new_game.setForeground(Color.gray);
			}
		});
		send_game = new JButton("Send game ");
		send_game.setContentAreaFilled(false);
		send_game.setFont(new Font("consolas", Font.BOLD, 17));
		send_game.setBorder(BorderFactory.createEmptyBorder(0, 18, 0, 0));
		send_game.setForeground(Color.black);
		send_game.setFocusable(false);
		send_game.addActionListener(this);
		send_game.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				if (client != null)
					send_game.setForeground(new Color(0, 128, 128));
			}

			public void mouseExited(MouseEvent e) {
				if (client != null)
					send_game.setForeground(Color.gray);
			}
		});
		receive_game = new JButton("Receive game ");
		receive_game.setContentAreaFilled(false);
		receive_game.setFont(new Font("consolas", Font.BOLD, 17));
		receive_game.setBorder(BorderFactory.createEmptyBorder(0, 18, 0, 0));
		receive_game.setForeground(Color.black);
		receive_game.setFocusable(false);
		receive_game.addActionListener(this);
		receive_game.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				if (client != null)
					receive_game.setForeground(new Color(0, 128, 128));
			}

			public void mouseExited(MouseEvent e) {
				if (client != null)
					receive_game.setForeground(Color.gray);
			}
		});
		send_data = new JButton("Send data ");
		send_data.setContentAreaFilled(false);
		send_data.setFont(new Font("consolas", Font.BOLD, 17));
		send_data.setBorder(BorderFactory.createEmptyBorder(0, 18, 0, 0));
		send_data.setForeground(Color.black);
		send_data.setFocusable(false);
		send_data.addActionListener(this);
		send_data.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				if (client != null)
					send_data.setForeground(new Color(0, 128, 128));
			}

			public void mouseExited(MouseEvent e) {
				if (client != null)
					send_data.setForeground(Color.gray);
			}
		});


		gl1 = new GroupLayout(panel2);

		panel2.setLayout(gl1);

		gl1.setAutoCreateContainerGaps(true);

		gl1.setHorizontalGroup(gl1.createParallelGroup()

				.addGroup(gl1.createSequentialGroup().addGroup(gl1.createParallelGroup().addComponent(user))
						.addComponent(tf_user).addComponent(server).addComponent(tf_server).addComponent(port)
						.addComponent(tf_port).addComponent(connect).addComponent(end))

				.addGroup(gl1.createSequentialGroup().addGroup(gl1.createParallelGroup().addComponent(new_game))
						.addComponent(send_game).addComponent(receive_game).addComponent(send_data))
				.addComponent(scroll));
		gl1.setVerticalGroup(
				gl1.createSequentialGroup()
						.addGroup(
								gl1.createSequentialGroup()
										.addGroup(gl1.createParallelGroup()
												.addGroup(gl1.createSequentialGroup().addComponent(user))
												.addComponent(tf_user, GroupLayout.PREFERRED_SIZE,
														GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
												.addComponent(server)
												.addComponent(tf_server, GroupLayout.PREFERRED_SIZE,
														GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
												.addComponent(port)
												.addComponent(tf_port, GroupLayout.PREFERRED_SIZE,
														GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
												.addComponent(connect).addComponent(end)))

						.addGroup(gl1.createSequentialGroup().addGroup(gl1.createParallelGroup()
								.addGroup(gl1.createSequentialGroup().addComponent(new_game)).addComponent(send_game)
								.addComponent(receive_game).addComponent(send_data)))
						.addComponent(scroll));

		image = new ImageIcon(getClass().getClassLoader().getResource("gameclient.png"));
		label = new JLabel(image);
		panel1.setPreferredSize(new Dimension(300, 300));
		panel1.setBorder(BorderFactory.createEmptyBorder(0, 00, 0, 00));
		panel1.setBackground(new Color(8, 26, 42));
		panel2.setPreferredSize(new Dimension(300, 500));
		panel2.setBorder(BorderFactory.createEmptyBorder(0, 0, 00, 00));
		panel2.setBackground(new Color(8, 26, 42));

		ImageIcon imageIcon = new ImageIcon(getClass().getClassLoader().getResource("gameclient.png"));
		Image image = imageIcon.getImage();
		Image newimg = image.getScaledInstance(590, 170, java.awt.Image.SCALE_FAST);
		imageIcon = new ImageIcon(newimg);
		label = new JLabel(imageIcon);
		panel1.add(label);

		gl = new GroupLayout(frame.getContentPane());

		frame.getContentPane().setLayout(gl);

		gl.setAutoCreateGaps(true);
		gl.setAutoCreateContainerGaps(true);

		gl.setHorizontalGroup(gl.createParallelGroup().addComponent(panel1).addGroup(gl.createSequentialGroup())
				.addComponent(panel2));

		gl.setVerticalGroup(gl.createSequentialGroup().addComponent(panel1).addGroup(gl.createSequentialGroup())
				.addComponent(panel2));

		frame.setTitle("Number Puzzle Client");
		try {
			frame.setIconImage(ImageIO.read(getClass().getClassLoader().getResource("puzz.png")));
		} catch (IOException e) {
			e.printStackTrace();
		}
		frame.getContentPane().setBackground(Color.black);
		frame.setMinimumSize(new Dimension(630, 500));
		frame.setPreferredSize(new Dimension(630, 500));
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setVisible(true);
	}

	/**
	 * this method defines the action on client side
	 * @param e the event to be processed
	 */
	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == connect && client == null) {
			connect();
		} else if (e.getSource() == end && client != null) {
			end();
		} else if (e.getSource() == new_game && client != null) {
			new_game();
		} else if (e.getSource() == send_game && client != null) {
			send_game();
		} else if (e.getSource() == receive_game && client != null) {
			receive_game();
		} else if (e.getSource() == send_data && client != null) {
			send_data();
		}
	}

	/**
	 * for connecting with server
	 */
	public void connect() {

		try {
			if(tf_user.getText().isEmpty()){
				tf_user.setText(Integer.toString( Config.DEFAULT_PORT));
			}
			client = new Socket(tf_server.getText(), Integer.parseInt(tf_port.getText()));
			inputFromServer = new DataInputStream(client.getInputStream()); // data get input stream is instantiated
			outputToServer = new DataOutputStream(client.getOutputStream()); // data get output stream is instantiated
			String str = inputFromServer.readUTF();
			if (str.equals("The Server is closed ....\n")) {
				tf.append(str);
				client.close();
				client = null;
			}
			else {
				tf.append(str);
				outputToServer.writeUTF(Config.PROTOCOL_CONNECT);
				tf.append("Client ID no." + inputFromServer.readInt() + " ...\n");
				send_game.setForeground(Color.gray);
				receive_game.setForeground(Color.gray);
				new_game.setForeground(Color.gray);
				send_data.setForeground(Color.gray);

				end.setForeground(Color.gray);
				tf.append("Connect ...\n");
				tf.append("Connecting with server on " + tf_server.getText() + " at port " + tf_port.getText() + "\n");
				tf.append("Create a new MVC\n");
				tf_user.setEnabled(false);
				tf_port.setEnabled(false);
 			}
			outputToServer.flush();
		} catch (NumberFormatException | IOException e1) {
			this.errorDialog.setTitle(" <<< Connection Failed >>>");
			this.errorDialog.setVisible(true);
		}
	}

	/**
	 * for ending the connection with server
	 */
	public void end() {
		try {
			outputToServer.writeUTF(Config.PROTOCOL_END);
			tf.append("Disconnected ...\n");
			connect.setForeground(Color.gray);
			send_game.setForeground(Color.black);
			receive_game.setForeground(Color.black);
			new_game.setForeground(Color.black);
			send_data.setForeground(Color.black);

			end.setForeground(Color.black);
			tf_user.setEnabled(true);
			tf_port.setEnabled(true);
		} catch (NullPointerException | IOException e) {
			this.errorDialog.setTitle(" <<<  End Connection Failed >>>");
			this.errorDialog.setVisible(true);
		} finally {
			try {
				outputToServer.flush();
				client.close();
				client = null;
			} catch (IOException e) {
				this.errorDialog.setTitle(" <<<  End Connection Failed >>>");
				this.errorDialog.setVisible(true);
			}
		}
	}

	/**
	 * handles when user clicks on new game button
	 */
	public void new_game() {
		gameModel = new GameModel();
		gameView = new GameView(gameModel);
		gameController = new GameController(gameModel, gameView);
		gameController.splash_screen();
		;
	}

	/**
	 * sends game configuration to server
	 */
	public void send_game() {
		if (gameController.getView().getGridObject().getElements() != null) {
			tf.append("Send game ...\n");
			try {
				outputToServer.writeUTF(Config.PROTOCOL_SENDGAME);
				outputToServer.writeInt(gameController.getView().getGridObject().getDimension());
				outputToServer.writeBoolean(gameController.getView().getGridObject().isCheck());
				int count = 0;
				if (gameController.getView().getGridObject().isCheck() == false) {
					for (int i = 0; i < gameController.getView().getGridObject().getElements().length; i++) {
						String str = Integer.toString(gameController.getView().getGridObject().getElements()[i]);
						tf.append(String.format("%1$3s", str));
						outputToServer.writeInt(gameController.getView().getGridObject().getElements()[i]);
						count++;
						if (gameController.getView().getGridObject().getDimension() == count) {
							tf.append("\n");
							outputToServer.writeUTF("\n");
							count = 0;
						}
					}
				} else {
					for (int i = 0; i < gameController.getView().getGridObject().getElements().length; i++) {
						char str = (char) (gameController.getView().getGridObject().getElements()[i]);
						tf.append(String.format("%1$3s", str));
						outputToServer.writeInt(gameController.getView().getGridObject().getElements()[i]);
						count++;
						if (gameController.getView().getGridObject().getDimension() == count) {
							tf.append("\n");
							outputToServer.writeUTF("\n");
							count = 0;
						}
					}
				}
			} catch (IOException e) {
				this.errorDialog.setTitle(" <<<  Send Game Failed >>>");
				this.errorDialog.setVisible(true);
				tf.append("No connection with the server");
			}

		} else {
			tf.append("Please Configure game design/New game ... \n");
			this.errorDialog.setTitle(" <<<  Send Game Failed >>>");
			this.errorDialog.setVisible(true);
		}
	}

	/**
	 * receives game configuration from server
	 */
	public void receive_game() {

			tf.append("Receive game ...\n");
			try {
				outputToServer.writeUTF(Config.PROTOCOL_RECVGAME);
				boolean check = inputFromServer.readBoolean();
				if(check == true) {
				gameController.getView().getGridObject().setDimension(inputFromServer.readInt());
				int dimension = gameController.getView().getGridObject().getDimension();
				gameController.getView().getGridObject().setCheck(inputFromServer.readBoolean());
				int[] arr = new int[dimension * dimension];
				gameController.getView().getGridObject().setElements(arr);
				int count = 0;
				if (gameController.getView().getGridObject().isCheck() == false) {
					for (int i = 0; i < gameController.getView().getGridObject().getElements().length; i++) {
						int element = inputFromServer.readInt();
						gameController.getView().getGridObject().getElements()[i] = element;
						String str = Integer.toString(element);
						tf.append(String.format("%1$3s", str));
						count++;
						if (dimension == count) {
							String newline = inputFromServer.readUTF();
							tf.append(newline);
							count = 0;
						}
					}
				} else {
					for (int i = 0; i < gameController.getView().getGridObject().getElements().length; i++) {
						int element = inputFromServer.readInt();
						gameController.getView().getGridObject().getElements()[i] = element;
						char str = (char) (element);
						tf.append(String.format("%1$3s", str));
						count++;
						if (dimension == count) {
							String newline = inputFromServer.readUTF();
							tf.append(newline);
							count = 0;
						}
					}
				}
			} else {
				tf.append("Please Configure game design ... \n");
				this.errorDialog.setTitle(" <<<  No Game Available configured >>>");
				this.errorDialog.setVisible(true);
				tf.append("No game configured ...\n");
			}
			} catch (IOException e) {
				this.errorDialog.setTitle(" <<<  No connection with the server >>>");
				tf.append("No game configured ...\n");
				this.errorDialog.setVisible(true);
			}

	}

	/**
	 * sends data to server
	 */
	public void send_data() {
		try {
			tf.append("Sending data ...\n");
			outputToServer.writeUTF(Config.PROTOCOL_DATA);
			outputToServer.writeUTF(tf_user.getText());
			outputToServer.writeInt(gameController.getModel().getPoints());
			outputToServer.writeInt(gameController.getModel().getSeconds());
			outputToServer.writeInt(gameController.getModel().getMinutes());
			outputToServer.writeInt(gameController.getModel().getHours());
			tf.append(String.format(
					"Name:" + tf_user.getText()  + " " + "Points:" + gameController.getModel().getPoints() + " Time: %02d:%02d:%02d", gameController.getModel().getHours(),
					gameController.getModel().getMinutes(), gameController.getModel().getSeconds())+ "\n");
		} catch (IOException e) {
			this.errorDialog.setTitle(" <<< Enable to Send Data >>>");
			this.errorDialog.setVisible(true);
			tf.append("No connection with the server\n");
		}
	}

	/**
	 * for displaying splash screen
	 */
	public void play() {
		if (gameController != null)
			gameController.splash_screen();
	}

}
