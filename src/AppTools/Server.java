package AppTools;

import java.io.IOException;
import java.net.*;

public class Server implements Runnable{
	ServerSocket server;
	Socket[] sockets;
	String[] usernames;
	DatagramSocket ds;
	int numOfPlayers = 3;
	/*
	 * Class Server implements Runnable so that it is possible to make this a separate thread because the
	 * machine running the Server is also going to be a Client(player).
	 * 
	 * The class starts off by making a Server socket with the port 12345 for later use.
	 * Next it initializes the array of sockets and String(which is going to store the Clients connection and username).
	 * Now it creates a DatagramSocket with the port 12343.
	 * Server waits for incoming packets using the 12343 port and when it receives one, it parses the contents to the String array
	 * because that is going to contain the Clients username.
	 * It sends a response DatagramPacket back to the client who requested it(so the client has the ip address of the server).
	 * Then it waits for an incoming tcp connection request (socket) and saves it in the socket array which holds the connections.
	 * 
	 */
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			server = new ServerSocket(12345);
			sockets = new Socket[numOfPlayers];
			usernames = new String[numOfPlayers];
			ds = new DatagramSocket(12343);
			DatagramPacket dp = new DatagramPacket(new byte[100],100);
			for(int i = 0; i < numOfPlayers; ++i) {
				System.out.println("Server - Ready to receive request");
				ds.receive(dp);	
				usernames[i] = byteArrayToString(dp.getData());
				System.out.println("Server - Received request from " + usernames[i]);
				dp = new DatagramPacket(new byte[0], 0, InetAddress.getLocalHost(), 12344);
				ds.send(dp);
				sockets[i] = server.accept();
			}
			
			//need to make Runnable class to make each socket it's own thread
			//recommended that the Runnable class contain a reference to this server(or different shared resource)
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public String byteArrayToString(byte[] b) {
		String result = "";
		for(int i = 0; i < b.length; ++i) {
			result += (char) b[i];
		}
		return result;
	}
}
