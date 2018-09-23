package AppTools;

import java.io.IOException;
import java.net.*;

public class Client {
	Socket socket;
	public Client(String name) {
		DatagramSocket ds;
		
		try {
			ds = new DatagramSocket(12344);
			ds.setBroadcast(true);
			byte[] bytesToSend = name.getBytes();
			
			DatagramPacket dp = new DatagramPacket(bytesToSend, bytesToSend.length, InetAddress.getByName("255.255.255.255"), 12343);
			
			System.out.println("Attempting to find server");
			ds.send(dp);
			ds.receive(dp);
			System.out.println("Server found attempting to connect");
			socket = new Socket(dp.getAddress().getHostAddress(), 12345);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public Socket getSocket() {
		return socket;
	}
}
