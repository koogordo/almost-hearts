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
public class Client {
	Socket socket;
	ArrayList<DatagramPacket> dpArray = new ArrayList<>();
	byte[] bytesToSend;
	String name;
	boolean serverNotFound = true;
	JFrame screen;
	DatagramPacket packet;
	public Client(String name, String address) {
		DatagramSocket ds;
		try {
			packet = new DatagramPacket(name.getBytes(), name.length(), InetAddress.getByName(address), 12343);
		} catch (UnknownHostException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		screen = new JFrame("Loading...");
		this.name = name;
		try {
			ds = new DatagramSocket(12344);
			//ds.setSoTimeout(2000);
			
			System.out.println("Attempting to find server");
			//makeLoadingScreen();
			ds.send(packet);
			ds.receive(packet);
			/*Thread.sleep(1000);
			for (int i = 0; i < 4 && serverNotFound; i++) {
				System.out.println(i);
				makeDatagramPackets(i * 64);
				for(int k = 0; k < dpArray.size(); k++) {
					ds.send(dpArray.remove(0));
				}
				try {
					ds.receive(receivePacket);
					serverNotFound = false;
				} catch(SocketTimeoutException  e) {
					
				}
			}*/
			
			System.out.println("Client - Server found attempting to connect");
			socket = new Socket(packet.getAddress().getHostAddress(), 12345);
			System.out.println("Client - connection established");
			//screen.setVisible(false);
			
		} catch (IOException e) {
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
	public Socket getSocket() 
	{
		return socket;
	}
	public void makeLoadingScreen() 
	{
		//final ImageIcon icon = new ImageIcon("cardImages/cardLoading.gif");
		//JOptionPane optionPane = new JOptionPane("Waiting for other players to join...", JOptionPane.QUESTION_MESSAGE, JOptionPane.DEFAULT_OPTION, icon, new Object[]{}, null);
		//screen = new JDialog();
		screen = new JFrame("Loading...");
		screen.setResizable(false); // Do not allow the user to adjust the size of the frame
	    //Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
	    //int x = (int) ((dimension.getWidth() - screen.getWidth()) / 2);
	    //int y = (int) ((dimension.getHeight() - screen.getHeight()) / 2);
	    //screen.setLocation(x, y);
		//JPanel panel = new JPanel();
		screen.setLayout(new FlowLayout());
		//screen.setTitle();
		screen.add(new JLabel(new ImageIcon("cardImages/cardLoading.gif")));
		screen.add(new JLabel("Waiting for other players to join..."));
		screen.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		screen.pack();
		screen.setVisible(true);
		
	}
}