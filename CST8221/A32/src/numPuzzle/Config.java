package numPuzzle;
/**
 * CET-CS-Level 4
 *
 * This class provides default configuration for server and client
 *
 * Professor Name: Dr.Paulo Sousa Course: CST8221 - JAVA Application Program
 * Assessment: Assignment 32
 *
 * @author Erneste Iradukundasenga, Akshit Sabharwal.
 * @version 3.1
 */
public class Config {
	/**
	 * protocol for connecting with server
	 */
	static final String PROTOCOL_CONNECT = "P4";
	/**
	 * protocol for ending connection
	 */
	static final String PROTOCOL_END = "P0";
	/**
	 * protocol for sending game
	 */
	static final String PROTOCOL_SENDGAME = "P1";
	/**
	 * protocol for receiving game
	 */
	static final String PROTOCOL_RECVGAME = "P2";
	/**
	 * protocol for sending data to server
	 */
	static final String PROTOCOL_DATA = "P3";
	/**
	 * Default user name
	 */
	static String DEFAULT_USER = "GUEST";
	/**
	 * default host
	 */
	static String DEFAULT_ADDR = "localhost";
	/**
	 * default port
	 */
	static int DEFAULT_PORT = 3000;
	

}
