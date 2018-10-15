package AppTools;

import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import javax.swing.*;
import java.awt.*;
/*
 * Client constructor has a parameter for the name address and implements class Runnable.
 * It then sends out a packet to the address of the user's name under port 12343.
 * The contents of the sent packet is the name of the user joining (in bytes).
 * Then waits for a response back from the server with a receiving port 12344
 * 		(The reason for using a different port for the case that another client doesn't accidently connect to this client)
 * The response is received and uses the ip-address that it received from the server to 
 * attempt a TCP connection with server using the port 12345.
 * 
 * In the implemented method run, it launches a gui for the loading screen until all users are connected.
 * In the gui, there is a JLabel that states "Waiting for other players to join..." along with a gif loading icon.
 * The reason this implements class Runnable is so that it is possible to multi-thread the loading screen gui
 * because Swing is not thread safe and can break when waiting for TCP responses. The gui is disabled in the
 * GameboardGui once all 3 players have connected to the game.
 */
public class Client implements Runnable {
	private Socket socket; // Initializing the socket
	private String name; // Initializing a name string
	private JFrame screen; // Initializing screen as a JFrame
	private DatagramPacket packet; // Initializing packet as a DatagramPacket
	private String address; // Initialize IP address as a String
	private JFrame gameMenu;
	private DatagramSocket ds; // Initializing ds of type DatagramSocket
	public Client(String name, String address, JFrame gameMenu) { // Method Client that takes in name and address
		this.name = name;
		this.address = address;
		this.gameMenu = gameMenu;
		try {
			packet = new DatagramPacket(name.getBytes(), name.length(), InetAddress.getByName(address), 12343);
		} catch (UnknownHostException e1) {
			e1.printStackTrace();
		}
		screen = new JFrame("Loading...");
		try {
			ds = new DatagramSocket(12344);
			ds.setSoTimeout(5000);
			System.out.println("Attempting to find server"); // Informing the user that we are attempting to find the server
			Thread t1 = new Thread(this); // Make the loading Screen
			t1.start();
			ds.send(packet); // Send the packet
			ds.receive(packet); // Receive the packet
			System.out.println("Client - Server found attempting to connect"); // Informing the user that we are attempting to connect
			socket = new Socket(packet.getAddress().getHostAddress(), 12345); // Setting socket
			System.out.println("Client - connection established"); // Informing the user that the connection has been established
			
		} catch (IOException e) {
			screen.setVisible(false);
			ds.close();
			JOptionPane.showMessageDialog(null, "Could not connect to address " + this.address);
			gameMenu.setVisible(true);
		}
	}
	/*
	 * Simple public getter method meant to be used by the GameMenu to return the socket connection object
	 */
	public Socket getSocket() {
		return socket;
	}

	@Override
	public void run() {
		screen = new JFrame("Loading...");
		screen.setSize(500,250);
		screen.setResizable(false); // Do not allow the user to adjust the size of the frame
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (int) ((dimension.getWidth() - screen.getWidth()) / 2);
		int y = (int) ((dimension.getHeight() - screen.getHeight()) / 2);
	    screen.setLocation(x, y);
		screen.setLayout(new FlowLayout());
		screen.add(new JLabel(new ImageIcon(this.getClass().getResource("/cardImages/cardLoading.gif"))));
		screen.add(new JLabel("Waiting for other players to join..."));
		screen.add(new JLabel("The server address is " + this.address));
		screen.setVisible(true);
	}	
	public JFrame getLoadingScreen() { // Method to get the loading screen
		return screen;
	}
}