package AppTools;

import java.io.*;
import java.net.Socket;
import java.util.StringTokenizer;

/*
 * Class ServerPlayer implements class Runnable.
 * This class waits to receive an output stream from the GameboardGui class and depending
 * on the data it receives, then calls methods of the Game object.
 * 
 * Since it implements Runnable, that makes this class multiple threaded.
 * Reason for making this multiple threaded is so that it is possible to listen to 3 different
 * sockets all at once (one for each player).
 */

public class ServerPlayer implements Runnable {
	Socket socket; // Initialize socket of type Socket
	Game game; // Initialize game of type Game
	int playerID; // Initialize playerID of type int
	BufferedReader in; // Initialize in of type BufferedReader
	/*
	 * The constructor takes in the 3 parameters,
	 * A reference to the socket that it is going to get input from, a reference to the
	 * game object which will controls the game, along with the id of the player.
	 * 
	 * The constructor is called in the Server class.
	 */
	public ServerPlayer(Socket s, Game g, int i) {
		socket = s; // Setting socket to s
		game = g; // Setting game to g
		playerID = i; // Setting playerID to i
		try {
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			// Setting in to a bufferedReader of a InputStreamReader of the inputStream of the socket
		} catch (IOException e) { // Catch any errors
			e.printStackTrace(); // Print out said errors
		}
	}
	@Override
	/*
	 * (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 * 
	 * This method is implemented from the Runnable class and is used to make it multiple threaded.
	 * It infinitely loops through for waiting for input from the user until the server broadcasts the exit string
	 */
	public void run() {
		while (true) {
			try {
				StringTokenizer input = new StringTokenizer(in.readLine());
				String firstToken = input.nextToken();
				
				System.out.println(firstToken);
				switch(firstToken) {
				case "Played":
					game.turnPlayed(Integer.parseInt(input.nextToken()), input.nextToken(), Integer.parseInt(input.nextToken()));
					break;
				case "Reset":
					game.resetGame();
					break;
				case "Exit": 
					game.Broadcast("Exit " + playerID);
					break;
				default:
					break;
				}	
			} catch (IOException e) { // Catch any errors
				e.printStackTrace(); // Print out said errors
				System.out.println("Lost connection to " + playerID);
			}
		}
	}
}
