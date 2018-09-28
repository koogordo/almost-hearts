package AppTools;

import java.io.IOException;
import java.net.*;
/*
 * Client constructor sends out a broadcast packet to any DatagramSocket receiving on port 12343
 * The contents of the sent packet is the name of the user joining (in bytes)
 * Then waits for a response back from the server with a receiving port 12344
 * 		(The reason for using a different port for the case that another client doesn't accidently connect to this client)
 * The response is received and uses the ip-address that it received from the server to 
 * attempt a TCP connection with server using the port 12345.
 */
public class Client {
	Socket socket;
	public Client(String name) {
		DatagramSocket ds;
		
		try {
			ds = new DatagramSocket(12344);
			ds.setBroadcast(true);
			byte[] bytesToSend = name.getBytes();
			DatagramPacket dp = new DatagramPacket(bytesToSend, bytesToSend.length, 
					InetAddress.getByName(makeBroadcastAddress(InetAddress.getLocalHost().getHostAddress())), 12343);
			
			System.out.println("Attempting to find server");
			ds.send(dp);
			//ds.receive(dp); 255.255.255.255
			//System.out.println("Client - Server found attempting to connect");
			//socket = new Socket(dp.getAddress().getHostAddress(), 12345);
			//System.out.println("Client - connection established");
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public String makeBroadcastAddress(String s) {
		return s.substring(0, s.lastIndexOf(".")) + ".255";
	}
	public Socket getSocket() {
		return socket;
	}
}
