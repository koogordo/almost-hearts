package Views;

import java.awt.*;
import java.awt.event.*;
import java.util.concurrent.*;
import java.applet.Applet;
import javax.swing.*;

import AppTools.*;

/**
 * GameMenu.java is the main driver to initialize the program
 * It starts up a JFrame Gui that allows the users to enter their name and connect to the game
 */

public class GameMenu 
{
	static JButton joinExistingButton; // Button to join and exiting game
	static JButton createNewButton; // Button to create a new game
	static JLabel namePrompt; // Label to inform the user to enter their name in the TextField
	static JTextField name; // Field for the User(s) to enter their name
	static JTextField address; // This will be removed eventually
	static JPanel north; // JPanel for the Name to be entered
	static JFrame frame; // JFrame
    
	public static void main(String args[]) 
	{
		frame = new JFrame("GameMenu"); // Creating a new JFrame called Game Menu
		frame.setSize(325, 200); // Setting the sixe of the frame to 325 wide x 200 high
		frame.setResizable(false); // Do not allow the user to adjust the size of the frame
		BorderLayout layout = new BorderLayout(); // Creating a border layout
		frame.setLayout(layout); // Setting the layout of our frame to the border layout
		
		joinExistingButton = new JButton("Join existing game"); // Creating a button called "Join existing Game"
		joinExistingButton.setEnabled(false); // Setting enabled to false
		joinExistingButton.addActionListener(new ButtonHandler()); // Creating an actionListener for when the button is pressed
		frame.add(joinExistingButton, BorderLayout.EAST); // Setting the button's location to the East side of the border layout
		
		createNewButton = new JButton("Create a new game"); // Creating a button called "Create a new Game"
		createNewButton.setEnabled(false); // Setting enabled to false
		createNewButton.addActionListener(new ButtonHandler()); // Creating an actionListener for when the button is pressed
		frame.add(createNewButton, BorderLayout.WEST); // Setting the button's location to the West side of the border layout
		
		north = new JPanel(); // Creating a JPanel
		frame.add(north, BorderLayout.NORTH); // Adding a north pane that we can add multiple things to
		north.setLayout(new GridLayout(2,1));
		
		namePrompt = new JLabel("Please enter your name:"); // Adding a JLabel that tells the user to enter their name
		namePrompt.setHorizontalAlignment(SwingConstants.CENTER); // Setting the alignment to center
		north.add(namePrompt); // Add namePrompt to the north border
		
		name = new JTextField(); // Creating a new JTextField
		name.addKeyListener(new TextFieldHandler()); // 
		north.add(name); // Add name to the north border
		
		// This will go BYE BYE eventually...
		address = new JTextField();
		address.addKeyListener(new TextFieldHandler());
		north.add(address); 
		
		
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
				ExecutorService executorService = Executors.newCachedThreadPool(); // 
				executorService.execute(new Server()); // 
			}
			frame.setVisible(false); // set the visibility to false
			Client client = new Client(name.getText(), address.getText());
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