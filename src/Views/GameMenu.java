package Views;

import java.awt.*;
import java.awt.event.*;
import java.applet.Applet;
import javax.swing.*;

public class GameMenu {
	static JButton joinExistingButton;
	static JButton createNewButton;
	static JTextField name;
	static JLabel namePrompt;
	static JPanel south;
    
	public static void main(String args[]) {
		JFrame frame = new JFrame("GameMenu");
		frame.setSize(400, 200);
		frame.setResizable(false);
		BorderLayout layout = new BorderLayout();
		frame.setLayout(layout);
		
		joinExistingButton = new JButton("Join existing game");
		frame.add(joinExistingButton, BorderLayout.WEST);
		
		createNewButton = new JButton("Create a new game");
		frame.add(createNewButton, BorderLayout.EAST);
		
		south = new JPanel();
		frame.add(south, BorderLayout.SOUTH);
		south.setLayout(new GridLayout(2,1));
		
		namePrompt = new JLabel("Please enter your name:");
		namePrompt.setHorizontalAlignment(SwingConstants.CENTER);
		south.add(namePrompt);
		
		name = new JTextField();
		south.add(name);
		
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
		    	System.exit(0);
		    }
		});
		frame.setVisible(true);
	}

}