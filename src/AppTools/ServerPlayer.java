package AppTools;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.StringTokenizer;

/**
 * 
 * 
 */

public class ServerPlayer implements Runnable {
	Socket socket;
	Game game;
	int playerID;
	BufferedReader in;
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
	public void run() {
		// TODO Auto-generated method stub
		while (true) {
			try {
				StringTokenizer input = new StringTokenizer(in.readLine());
				String firstToken = input.nextToken();
				
				switch(firstToken) {
				case "Played":
					
					break;
				
				case "Exit": System.exit(0); break;
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
