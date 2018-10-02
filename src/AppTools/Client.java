package AppTools;

import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
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
	ArrayList<DatagramPacket> dpArray = new ArrayList<>();
	byte[] bytesToSend;
	String name;
	boolean serverNotFound = true;
	DatagramPacket receivePacket = new DatagramPacket(new byte[1], 1);
	public Client(String name) {
		DatagramSocket ds;
		this.name = name;
		try {
			ds = new DatagramSocket(12344);
			ds.setSoTimeout(2000);
			//ds.setBroadcast(true);
			Thread.sleep(1000);
			System.out.println("Attempting to find server");
			for (int i = 0; i < 4 && serverNotFound; i++) {
				makeDatagramPackets(i * 64);
				for(int k = 0; k < dpArray.size(); k++) {
					ds.send(dpArray.remove(0));
				}
				try {
					ds.receive(receivePacket);
					serverNotFound = false;
				} catch(SocketTimeoutException  e) {
					
				}
			}
			
			System.out.println("Client - Server found attempting to connect");
			socket = new Socket(receivePacket.getAddress().getHostAddress(), 12345);
			System.out.println("Client - connection established");
			
		} catch (IOException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public String makeWithoutSubMaskAddress(String s) {
		return s.substring(0, s.lastIndexOf(".")) + ".255";
	}
	public void makeDatagramPackets(int startingSubMask)
	{
		String addressWithoutSubMask = "";
		try {
			 addressWithoutSubMask = InetAddress.getLocalHost().getHostAddress().substring(0, InetAddress.getLocalHost().getHostAddress().lastIndexOf("."));
			bytesToSend = this.name.getBytes();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(startingSubMask < 255)
		{
			for(int i = startingSubMask; i < startingSubMask + 64; i++) {
				if(i != 0 && i != 255)
				{
					try {
						dpArray.add(new DatagramPacket(bytesToSend, bytesToSend.length, 
								InetAddress.getByName(addressWithoutSubMask + "." + i), 12343));
					} catch (UnknownHostException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
	}
	public Socket getSocket() {
		return socket;
	}
}