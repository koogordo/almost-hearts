package Views;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.concurrent.*;
import java.applet.Applet;

import javax.imageio.ImageIO;
import javax.swing.*;

import AppTools.*;

/**
 * GameMenu.java is the main driver to initialize the program
 * It starts up a JFrame Gui that allows the users to enter their name and connect to the game
 */

public class GameMenu 
{
	static JLabel gameLogo;
	static JButton joinExistingButton; // Button to join and exiting game
	static JButton createNewButton; // Button to create a new game
	static JLabel namePrompt; // Label to inform the user to enter their name in the TextField
	static JTextField name; // Field for the User(s) to enter their name
	static JTextField address; // This will be removed eventually
	static JPanel center;
	static JPanel north; // JPanel for the Name to be entered
	static JPanel south;
	static JFrame frame; // JFrame
    
	public static void main(String args[]) 
	{
		frame = new JFrame("GameMenu"); // Creating a new JFrame called Game Menu
		frame.setSize(380, 380); // Setting the sixe of the frame to 325 wide x 200 high
		frame.setResizable(false); // Do not allow the user to adjust the size of the frame
	    Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
	    int x = (int) ((dimension.getWidth() - frame.getWidth()) / 2);
	    int y = (int) ((dimension.getHeight() - frame.getHeight()) / 2);
	    frame.setLocation(x, y);
		BorderLayout layout = new BorderLayout(); // Creating a border layout
		frame.setLayout(layout); // Setting the layout of our frame to the border layout
		
		// NORTH with Game Logo-----------------------------------------------------------------------------
		gameLogo = new JLabel();
		gameLogo.setSize(200,200);
		
		BufferedImage logo = null;
		try 
		{
		    logo = ImageIO.read(new File("cardImages/hand.png"));
		} catch (IOException e) 
		{
		    e.printStackTrace();
		}
		Image dimg = logo.getScaledInstance(gameLogo.getWidth(), gameLogo.getHeight(), Image.SCALE_SMOOTH);
		ImageIcon imageIcon = new ImageIcon(dimg);
		gameLogo.setIcon(imageIcon);
		//gameLogo.setHorizontalAlignment(SwingConstants.CENTER); // Setting the alignment to center
		//north.add(gameLogo); // Add namePrompt to the north border
		JPanel TopLogo = new JPanel(new FlowLayout());
		TopLogo.add(gameLogo);
		frame.add(BorderLayout.NORTH, TopLogo);
		
		// CENTER Grid with Text Boxes----------------------------------------------------------------------
		JPanel CenterOutter = new JPanel(new FlowLayout());
		CenterOutter.setSize(200, 200);
		JPanel centerInfo = new JPanel(new BorderLayout());
		JPanel topLine = new JPanel(new BorderLayout());
		JPanel bottomLine = new JPanel(new BorderLayout());
		//center = new JPanel(); // Creating a JPanel
		//frame.add(center, BorderLayout.CENTER); // Adding a north pane that we can add multiple things to
		//center.setLayout(new GridLayout(2,1));
		
		namePrompt = new JLabel("Please enter your name:"); // Adding a JLabel that tells the user to enter their name
		namePrompt.setHorizontalAlignment(SwingConstants.CENTER); // Setting the alignment to center
		topLine.add(BorderLayout.NORTH, namePrompt); // Add namePrompt to the north border
		
		name = new JTextField(); // Creating a new JTextField
		name.addKeyListener(new TextFieldHandler()); // 
		topLine.add(BorderLayout.CENTER, name); // Add name to the north border
		
		namePrompt = new JLabel("Please enter the IP Address:"); // Adding a JLabel that tells the user to enter their name
		namePrompt.setHorizontalAlignment(SwingConstants.CENTER); // Setting the alignment to center
		bottomLine.add(BorderLayout.NORTH, namePrompt); // Add namePrompt to the north border
		
		address = new JTextField();
		address.addKeyListener(new TextFieldHandler());
		bottomLine.add(BorderLayout.CENTER, address); 
		
		centerInfo.add(topLine, BorderLayout.NORTH);
		centerInfo.add(bottomLine, BorderLayout.CENTER);
		
		CenterOutter.add(centerInfo);
		frame.add(BorderLayout.CENTER, CenterOutter);
		
		// SOUTH Grid with Text Boxes----------------------------------------------------------------------
		south = new JPanel(); // Creating a JPanel
		frame.add(south, BorderLayout.SOUTH); // Adding a north pane that we can add multiple things to
		south.setLayout(new GridLayout(0,2));
		
		//WEST
		createNewButton = new JButton("Create a new game"); // Creating a button called "Create a new Game"
		createNewButton.setEnabled(false); // Setting enabled to false
		createNewButton.addActionListener(new ButtonHandler()); // Creating an actionListener for when the button is pressed
		JPanel createNewFrame = new JPanel(new FlowLayout());
		createNewFrame.add(createNewButton);
		south.add(createNewFrame);
		
		//EAST
		joinExistingButton = new JButton("Join existing game"); // Creating a button called "Join existing Game"
		joinExistingButton.setEnabled(false); // Setting enabled to false
		joinExistingButton.addActionListener(new ButtonHandler()); // Creating an actionListener for when the button is pressed
		joinExistingButton.setSize(10, 10);
		JPanel joinExistingFrame = new JPanel(new FlowLayout());
		joinExistingFrame.add(joinExistingButton);
		south.add(joinExistingFrame);
		
		
		frame.addWindowListener(new WindowAdapter() 
		{
			public void windowClosing(WindowEvent e) 
			{
		    	System.exit(0);
		    }
		});
		frame.setVisible(true); // Set the frame's visibility to true
	}
	
	private static class ButtonHandler implements ActionListener // ButtonHandler method that implements the ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent event)
		{
			if(event.getActionCommand().equals(createNewButton.getText())) // if a button is pressed, do the following:
			{
				ExecutorService executorService = Executors.newCachedThreadPool(); //creates a thread pool
				executorService.execute(new Server());//adds a new server thread to the thread pool
				try {
					address.setText(InetAddress.getLocalHost().getHostAddress());//if the person is hosting, just make the local
						//address the parameter
				} catch (UnknownHostException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			frame.setVisible(false); // set the visibility to false
			Client client = new Client(name.getText(), null);
			//JOptionPane.showMessageDialog(null, "Something", "Waiting for Other Players", JOptionPane.INFORMATION_MESSAGE);
			//here we use client.getSocket() function to return the socket from client
			//and use that socket for entering the start of the game/waiting area
		}
		
	}
	private static class TextFieldHandler implements KeyListener // TextField Handler method to handle the Action Listener
	{
		@Override
		public void keyTyped(KeyEvent e) 
		{
			// TODO Auto-generated method stub
			if(name.getText().equals("")){
				joinExistingButton.setEnabled(false);
				createNewButton.setEnabled(false);
			}
			else 
			{
				joinExistingButton.setEnabled(true);
				createNewButton.setEnabled(true);
			}
		}

		@Override
		public void keyPressed(KeyEvent e) 
		{
			// TODO Auto-generated method stub
			
		}

		@Override
		public void keyReleased(KeyEvent e) 
		{
			// TODO Auto-generated method stub
			
		}
	}
}