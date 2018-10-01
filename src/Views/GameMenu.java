package Views;

import java.awt.*;
import java.awt.event.*;
import java.util.concurrent.*;
import java.applet.Applet;
import javax.swing.*;

import AppTools.*;

public class GameMenu {
	static JButton joinExistingButton;
	static JButton createNewButton;
	static JTextField name;
	static JTextField address;
	static JLabel namePrompt;
	static JPanel north;
	static JFrame frame;
    
	public static void main(String args[]) 
	{
		frame = new JFrame("GameMenu");
		frame.setSize(325, 200);
		frame.setResizable(false);
		BorderLayout layout = new BorderLayout();
		frame.setLayout(layout);
		
		joinExistingButton = new JButton("Join existing game");
		joinExistingButton.setEnabled(false);
		joinExistingButton.addActionListener(new ButtonHandler());
		frame.add(joinExistingButton, BorderLayout.EAST);
		
		createNewButton = new JButton("Create a new game");
		createNewButton.setEnabled(false);
		createNewButton.addActionListener(new ButtonHandler());
		frame.add(createNewButton, BorderLayout.WEST);
		
		north = new JPanel();
		frame.add(north, BorderLayout.NORTH);
		north.setLayout(new GridLayout(2,1));
		
		namePrompt = new JLabel("Please enter your name:");
		namePrompt.setHorizontalAlignment(SwingConstants.CENTER);
		north.add(namePrompt);
		
		name = new JTextField();
		name.addKeyListener(new TextFieldHandler());
		north.add(name);
		
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
		frame.setVisible(true);
	}
	
	private static class ButtonHandler implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent event) {
			if(event.getActionCommand().equals(createNewButton.getText())) 
			{
				ExecutorService executorService = Executors.newCachedThreadPool();
				executorService.execute(new Server());
			}
			frame.setVisible(false);
			
			Client client = new Client(name.getText(), address.getText());
			//JOptionPane.showMessageDialog(null, "Something", "Waiting for Other Players", JOptionPane.INFORMATION_MESSAGE);
			//here we use client.getSocket() function to return the socket from client
			//and use that socket for entering the start of the game/waiting area
		}
		
	}
	private static class TextFieldHandler implements KeyListener
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