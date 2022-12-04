package numPuzzle;

import java.awt.*;
import java.awt.event.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.plaf.basic.BasicScrollBarUI;

/**
 * CET-CS-Level 4
 * <p>
 * This class implements server's interface and actions
 * <p>
 * Professor Name: Dr.Paulo Sousa Course: CST8221 - JAVA Application Program
 * Assessment: Assignment 21
 *
 * @author Erneste Iradukundasenga, Akshit Sabharwal.
 * @version 3.1
 */
public class Server implements Runnable, ActionListener {
    /**
     * outer frame for server interface
     */
    private JFrame frame;
    /**
     * buttons used in server side
     */
    private JButton end, start, result, finalize;
    /**
     * labels used in server interface
     */
    private JLabel port, label;
    /**
     * image icon
     */
    private ImageIcon image;
    /**
     * group layouts
     */
    private GroupLayout gl, gl1;
    /**
     * text areas
     */
    public JTextArea tf, result_ta;
    /**
     * text field
     */
    private JTextField tf_port;
    /**
     * scrolls for text area
     */
    private JScrollPane scroll, result_scroll;
    /**
     * panels used in server interface
     */
    private JPanel panel1, panel2;
    /**
     * server socket
     */
    private ServerSocket listener;
    /**
     * connection socket for client
     */
    private Socket connection;
    /**
     * protocols
     */
    private String protocol;
    /**
     * number of clients
     */
    private static int nclient = 0, nclients = 0;
    /**
     * dialog for result and error
     */
    private JDialog errorDialog, result_dialog;
    /**
     * Arraylist for Clients
     */
    private ArrayList<Clients> client_list = new ArrayList<>();
    /**
     * for checking if server is close
     */
    boolean close_server = false;
    /**
     * server end
     */
    boolean end_server = false;
    /**
     * Server Thread
     */
    Thread serverDaemon;
    /**
     * for game configuration
     */
    int[] elements;
    /**
     * for storing game dimension
     */
    int dimension;
    /**
     * data type for game configuration
     */
    boolean type;

    /**
     * Default Constructor
     */
    public Server() {
    }


    /**
     * get the server frame
     *
     * @return server main frame
     */
    public JFrame getFrame() {
        return frame;
    }

    /**
     * @param frame - outer frame for server
     */
    public void setFrame(JFrame frame) {
        this.frame = frame;
    }

    /**
     * configures server interface
     */
    public void configure() {

        frame = new JFrame();
        panel1 = new JPanel();
        panel2 = new JPanel();

        port = new JLabel("Port:");
        port.setBorder(BorderFactory.createEmptyBorder(0, 50, 0, 0));
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

        scroll = new JScrollPane(tf);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scroll.setBackground(Color.black);
        scroll.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

        scroll.getVerticalScrollBar().setBackground(new Color(5, 17, 25));
        scroll.getVerticalScrollBar().setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        scroll.getVerticalScrollBar();

        errorDialog = new JDialog(getFrame());
        errorDialog.setContentPane(new JLabel(new ImageIcon((getClass().getClassLoader().getResource("gameerr.png")))));
        errorDialog.setResizable(false);
        errorDialog.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        errorDialog.pack();
        errorDialog.setLocationRelativeTo(null);

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

        tf_port = new JTextField();
        tf_port.setForeground(Color.GRAY);
        tf_port.setFont(new Font("consolas", Font.BOLD, 15));
        tf_port.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 0));
        tf_port.setBackground(new Color(5, 17, 25));
        tf_port.setPreferredSize(new Dimension(8, 20));
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
        end = new JButton("End");
        end.setContentAreaFilled(false);
        end.setFont(new Font("consolas", Font.BOLD, 17));
        end.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 50));
        end.setForeground(Color.gray);
        end.setFocusable(false);
        end.addActionListener(this);
        end.setEnabled(false);
        end.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                end.setForeground(new Color(0, 128, 128));
            }

            public void mouseExited(MouseEvent e) {
                end.setForeground(Color.gray);
            }
            @Override
            public void mousePressed(MouseEvent e) {
                if (end_server == false)
                    end.setForeground(new Color(0, 128, 128));
                else
                    end.setForeground(Color.gray);
            }
        });

        start = new JButton("Start");
        start.setContentAreaFilled(false);
        start.setFont(new Font("consolas", Font.BOLD, 17));
        start.setBorder(BorderFactory.createEmptyBorder(0, 5, 1, 5));
        start.setForeground(Color.gray);
        start.setFocusable(false);
        start.addActionListener(this);
        start.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                start.setForeground(new Color(0, 128, 128));
            }

            public void mouseExited(MouseEvent e) {
                start.setForeground(Color.gray);
            }
        });
        result = new JButton("Results");
        result.setContentAreaFilled(false);
        result.setFont(new Font("consolas", Font.BOLD, 17));
        result.setBorder(BorderFactory.createEmptyBorder(0, 5, 1, 5));
        result.setForeground(Color.gray);
        result.setFocusable(false);
        result.addActionListener(this);
        result.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                result.setForeground(new Color(0, 128, 128));
            }

            public void mouseExited(MouseEvent e) {
                result.setForeground(Color.gray);
            }
        });
        finalize = new JButton("Finalize");
        finalize.setContentAreaFilled(false);
        finalize.setFont(new Font("consolas", Font.BOLD, 17));
        finalize.setBorder(BorderFactory.createEmptyBorder(0, 5, 1, 5));
        finalize.setForeground(Color.gray);
        finalize.setFocusable(false);


        gl1 = new GroupLayout(panel2);

        panel2.setLayout(gl1);

        gl1.setAutoCreateGaps(true);
        gl1.setAutoCreateContainerGaps(true);

        gl1.setHorizontalGroup(gl1.createParallelGroup()

                .addGroup(gl1.createSequentialGroup().addGroup(gl1.createParallelGroup().addComponent(port))
                        .addComponent(tf_port).addComponent(start).addComponent(result).addComponent(finalize)
                        .addComponent(end))

                .addComponent(scroll));

        gl1.setVerticalGroup(gl1.createSequentialGroup()

                .addGroup(gl1.createSequentialGroup()
                        .addGroup(gl1.createParallelGroup().addGroup(gl1.createSequentialGroup().addComponent(port))
                                .addComponent(tf_port, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE,
                                        GroupLayout.DEFAULT_SIZE)
                                .addComponent(start).addComponent(result).addComponent(finalize).addComponent(end)))
                .addComponent(scroll));

        image = new ImageIcon(getClass().getClassLoader().getResource("gameclient.png"));
        label = new JLabel(image);
        panel1.setPreferredSize(new Dimension(300, 300));
        panel1.setBorder(BorderFactory.createEmptyBorder(0, 00, 0, 00));
        panel1.setBackground(new Color(8, 26, 42));
        panel2.setPreferredSize(new Dimension(300, 500));
        panel2.setBorder(BorderFactory.createEmptyBorder(0, 0, 00, 00));
        panel2.setBackground(new Color(8, 26, 42));

        ImageIcon imageIcon = new ImageIcon(getClass().getClassLoader().getResource("gameserver.png"));
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

        frame.setTitle("Number Puzzle Server");
        try {
            frame.setIconImage(ImageIO.read(getClass().getClassLoader().getResource("puzz.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        frame.getContentPane().setBackground(Color.black);
        frame.setMinimumSize(new Dimension(630, 500));
        frame.setPreferredSize(new Dimension(630, 500));
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);
    }

    /**
     * Performs actions based on events
     *
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == start && listener == null) {
            try {
                if(tf_port.getText().isEmpty()){
                    tf_port.setText(Integer.toString( Config.DEFAULT_PORT));
                }

                tf.append("Server running on " + InetAddress.getLocalHost() + " at port " + tf_port.getText() + "!"
                        + "\n");
                tf.append("Starting Server Thread on port " + tf_port.getText() + "\n");
                tf.append("Waiting for client to connect\n");
                listener = new ServerSocket(Integer.parseInt(tf_port.getText()));
                serverDaemon = new Thread(this);
                serverDaemon.start();
                tf_port.setEnabled(false);
                finalize.setForeground(Color.white);
                start.setEnabled(false);
                end.setEnabled(true);
            } catch (IOException e1) {
                errorDialog.setTitle("<<< Connection set >>>");
                this.errorDialog.setVisible(true);
            }
        } else if (e.getSource() == result) {
            results();
        } else if (e.getSource() == end) {
            if (end_server == true) {
                end_server = false;
            }
            else {
                end_server = true;
            }
            if (nclients == 0) {
                System.exit(0);
            }

            else {
                    tf.setText("Total Client Still Connected :" + nclient + "\n");
                    tf.append("Can't Stop Server.......... " + "\n");
                    tf.append("Wait for all clients to disconnect from Server" + "\n");
            }
        }

    }

    /**
     * display results
     */
    private void results() {

        result_ta = new JTextArea();
        result_ta.setFont(new Font("consolas", Font.BOLD, 15));
        result_ta.setBorder(BorderFactory.createEmptyBorder(20, 20, 1, 1));
        result_ta.setPreferredSize(new Dimension(100, 100000));
        result_ta.setBackground(new Color(5, 17, 25));
        result_ta.setEditable(false);
        result_ta.setForeground(Color.gray);
        result_ta.setLineWrap(true);

        result_scroll = new JScrollPane(result_ta);
        result_scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        result_scroll.setBackground(Color.black);
        result_scroll.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        result_scroll.getVerticalScrollBar().setBackground(new Color(5, 17, 25));
        result_scroll.getVerticalScrollBar().setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        result_scroll.getVerticalScrollBar();
        result_scroll.getVerticalScrollBar().setUI(new BasicScrollBarUI() {
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
        result_ta.append("Game Results:\n");
        for (int i = 0; i < nclients; i++) {
            result_ta.append(
                    "Player[" + this.client_list.get(i).clientid + "] " + this.client_list.get(i).client_data + "\n");
        }
        this.result_dialog = new JDialog(getFrame());
        this.result_dialog.setTitle("Results");
        this.result_dialog.setResizable(false);
        this.result_dialog.setContentPane(result_scroll);
        this.result_dialog.setPreferredSize(new Dimension(550, 400));
        this.result_dialog.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.result_dialog.pack();
        this.result_dialog.setLocationRelativeTo(null);
        this.result_dialog.setVisible(true);
    }

    /**
     * Override method
     */
    @Override
    public void run() {

        while (true) {
            try {
                connection = listener.accept();
                DataOutputStream outputToclient = new DataOutputStream(connection.getOutputStream());
                if (close_server == false || end_server == false) {
                    nclient += 1;
                    nclients += 1;
                    tf.append("Connecting client No.[" + nclients + "]" + connection.getInetAddress() + " at port "
                            + connection.getPort() + ".\n");
                    Clients w = new Clients(connection, nclient);
                    this.client_list.add(w);
                    outputToclient.writeUTF("Connection Running ....\n");
                    w.start();
                    finalize.setForeground(Color.gray);
                    end.setEnabled(false);
                } else {
                    outputToclient.writeUTF("The Server is closed ....\n");
                    outputToclient.flush();
                    connection.close();
                }
            } catch (IOException ioe) {
                System.out.println(ioe);
            }
        }
    }


    /**
     * In class for managing clients
     */
    public class Clients extends Thread implements Comparable<Clients> {
        /**
         * connection socket
         */
        Socket connection;
        /**
         * client data
         */
        String client_data;
        /**
         * client id
         */
        int clientid;

        /**
         *
         * @param s socket number
         * @param nclient - number of clients
         */
        public Clients(Socket s, int nclient) {
            connection = s;
            clientid = nclient;
        }

        /**
         * Run Method
         */
        @Override
        public void run() {

            DataInputStream inputFromClient;
            DataOutputStream outputToclient;
            try {
                inputFromClient = new DataInputStream(connection.getInputStream());
                outputToclient = new DataOutputStream(connection.getOutputStream());
                do {
                    do {
                        protocol = inputFromClient.readUTF();
                        if (protocol.equals(Config.PROTOCOL_CONNECT)) {
                            outputToclient.writeInt(clientid);
                            outputToclient.flush();
                        } else if (protocol.equals(Config.PROTOCOL_DATA)) {
                            String client_name = inputFromClient.readUTF();
                            int points = inputFromClient.readInt();
                            int seconds = inputFromClient.readInt();
                            int minutes = inputFromClient.readInt();
                            int hours = inputFromClient.readInt();
                            client_data = String.format(
                                    "Name:" + client_name + " " + "Points:" + points + " Time: %02d:%02d:%02d", hours,
                                    minutes, seconds);
                            tf.append(client_data + "\n");
                            outputToclient.flush();
                        } else if (protocol.equals(Config.PROTOCOL_SENDGAME)) {
                            tf.append("Recieving the game ...\n");
                            int count = 0;
                            dimension = inputFromClient.readInt();
                            elements = new int[dimension * dimension];
                            type = inputFromClient.readBoolean();
                            if (type == false) {
                                for (int i = 0; i < elements.length; i++) {
                                    elements[i] = inputFromClient.readInt();
                                    String str = Integer.toString(elements[i]);
                                    tf.append(String.format("%1$3s", str));
                                    count++;
                                    if (dimension == count) {
                                        String newline = inputFromClient.readUTF();
                                        tf.append(newline);
                                        count = 0;
                                    }
                                }
                            } else {
                                for (int i = 0; i < elements.length; i++) {
                                    elements[i] = inputFromClient.readInt();
                                    char str = (char) (elements[i]);
                                    tf.append(String.format("%1$3s", str));
                                    count++;
                                    if (dimension == count) {
                                        String newline = inputFromClient.readUTF();
                                        tf.append(newline);
                                        count = 0;
                                    }
                                }
                            }
                        } else if (protocol.equals(Config.PROTOCOL_RECVGAME)) {
                            if (elements != null) {
                                tf.append("Sending the game ...\n");
                                outputToclient.writeBoolean(true);
                                outputToclient.writeInt(dimension);
                                outputToclient.writeBoolean(type);
                                int count = 0;
                                if (type == false) {
                                    for (int i = 0; i < elements.length; i++) {
                                        outputToclient.writeInt(elements[i]);
                                        String str = Integer.toString(elements[i]);
                                        tf.append(String.format("%1$3s", str));
                                        count++;
                                        if (dimension == count) {
                                            outputToclient.writeUTF("\n");
                                            tf.append("\n");
                                            count = 0;
                                        }
                                    }
                                } else {
                                    for (int i = 0; i < elements.length; i++) {
                                        outputToclient.writeInt(elements[i]);
                                        char str = (char) (elements[i]);
                                        tf.append(String.format("%1$3s", str));
                                        count++;
                                        if (dimension == count) {
                                            outputToclient.writeUTF("\n");
                                            tf.append("\n");
                                            count = 0;
                                        }
                                    }
                                }
                            } else {
                                outputToclient.writeBoolean(false);
                            }
                        }
                    } while (!(protocol.equals(Config.PROTOCOL_END)));
                    nclients -= 1;
                    tf.append("Disconecting client ID No.[" + clientid + "]" + connection.getInetAddress() + "!\n");
                    tf.append("Remaining Clients: " + nclients + "\n");
                    if(nclients == 0) {
                        finalize.setForeground(Color.white);
                        end.setEnabled(true);
                    }
                } while (nclients != 0 && !(protocol.equals(Config.PROTOCOL_END)));
                connection.close();
                if (nclients == 0 && close_server == true || end_server == true)
                    System.exit(0);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        /**
         * compares client id
         *
         * @param o the object to be compared.
         * @return 1 if client id is lesser
         * -1 otherwise
         */
        @Override
        public int compareTo(Clients o) {
            if (clientid > o.clientid) // comparing different code by ascending order
                return 1;
            else
                return -1;
        }
    }
}