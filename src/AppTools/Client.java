package AppTools;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.io.IOException;
import java.net.*;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
/*
 * Client constructor sends out a broadcast packet to any DatagramSocket receiving on port 12343
 * The contents of the sent packet is the name of the user joining (in bytes)
 * Then waits for a response back from the server with a receiving port 12344
 * 		(The reason for using a different port for the case that another client doesn't accidently connect to this client)
 * The response is received and uses the ip-address that it received from the server to 
 * attempt a TCP connection with server using the port 12345.
 */
public class Client implements Runnable
{
	Socket socket; // Initializing the socket
	ArrayList<DatagramPacket> dpArray = new ArrayList<>(); // Setting dpArray to an arrayList
	byte[] bytesToSend; // Initializing an array of the bytesToSend
	String name; // Initializing a name string
	boolean serverNotFound = true; // Setting serverNotFound to true
	JFrame screen; // Initializing screen as a JFrame
	DatagramPacket packet; // Initializing packet as a DatagramPacket
	public Client(String name, String address) // Method Client that takes in name and address
	{
		DatagramSocket ds; // Initializing ds of type DatagramSocket
		try 
		{
			packet = new DatagramPacket(name.getBytes(), name.length(), InetAddress.getByName(address), 12343);
			// 
		} 
		catch (UnknownHostException e1) 
		{
			e1.printStackTrace();
		}
		screen = new JFrame("Loading...");
		this.name = name; 
		try 
		{
			ds = new DatagramSocket(12344);
			//ds.setSoTimeout(2000);

			System.out.println("Attempting to find server"); // Informing the user that we are attempting to find the server
			Thread t1 = new Thread(this); // Make the loading Screen
			ds.send(packet); // Send the packet
			ds.receive(packet); // Receive the packet
			System.out.println("Client - Server found attempting to connect"); // Informing the user that we are attempting to connect
			socket = new Socket(packet.getAddress().getHostAddress(), 12345); // Setting socket
			System.out.println("Client - connection established"); // Informing the user that the connection has been established
			//screen.setVisible(false);
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
	
	public Socket getSocket() // getSocket Method that returns the socket
	{
		return socket;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub

		//final ImageIcon icon = new ImageIcon("cardImages/cardLoading.gif");
		//JOptionPane optionPane = new JOptionPane("Waiting for other players to join...", JOptionPane.QUESTION_MESSAGE, JOptionPane.DEFAULT_OPTION, icon, new Object[]{}, null);
		//screen = new JDialog();
		screen = new JFrame("Loading...");
		screen.setSize(500,200);
		screen.setResizable(false); // Do not allow the user to adjust the size of the frame
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (int) ((dimension.getWidth() - screen.getWidth()) / 2);
		int y = (int) ((dimension.getHeight() - screen.getHeight()) / 2);
	    screen.setLocation(x, y);
		//JPanel panel = new JPanel();
		screen.setLayout(new FlowLayout());
		//screen.setTitle();
		screen.add(new JLabel(new ImageIcon(this.getClass().getResource("/cardImages/cardLoading.gif"))));
		screen.add(new JLabel("Waiting for other players to join..."));
		//screen.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		//screen.pack();
		screen.setVisible(true);
	}	
	public JFrame getLoadingScreen() // Method to get the loading screen
	{
		return screen;
	}
}