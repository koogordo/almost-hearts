package AppTools;

import java.io.IOException;
import java.net.*;

public class Server{
	ServerSocket server;
	Socket[] sockets;
	String[] usernames;
	DatagramSocket ds;
	public Server() {
		try {
			server = new ServerSocket(12345);
			sockets = new Socket[3];
			usernames = new String[3];
			ds = new DatagramSocket(12343);
			DatagramPacket dp = new DatagramPacket(new byte[0],0);
			for(int i = 0; i < 3; ++i) {
				System.out.println("Server - Ready to receive request");
				ds.receive(dp);	
				usernames[i] = dp.getData().toString();
				dp = new DatagramPacket(new byte[0], 0, InetAddress.getLocalHost(), 12344);
				ds.send(dp);
				sockets[i] = server.accept();
			}
			
			//need to make Runnable class to make each socket it's own thread
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
