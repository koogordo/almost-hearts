package AppTools;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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
	Socket socket;
	Game game;
	int playerID;
	BufferedReader in;
	/*
	 * The constructor takes in the 3 parameters,
	 * A reference to the socket that it is going to get input from, a reference to the
	 * game object which will controls the game, along with the id of the player.
	 * 
	 * The constructor is called in the Server class.
	 */
	public ServerPlayer(Socket s, Game g, int i) {
		socket = s;
		game = g;
		playerID = i;
		try {
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Override
	/*
	 * (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 * This method is implemented from the Runnable class and is used to make it multiple threaded.
	 * 
	 * It infinitely 
	 */
	public void run() {
		// TODO Auto-generated method stub
		while (true) {
			try {
				StringTokenizer input = new StringTokenizer(in.readLine());
				String firstToken = input.nextToken();
				
				switch(firstToken) {
				case "Played":
					game.turnPlayed(Integer.parseInt(input.nextToken()), input.nextToken(), Integer.parseInt(input.nextToken()));
					break;
				case "Exit": 
					game.Broadcast("Exit");
					System.exit(0);
					break;
				default:
					break;
				}
				
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
