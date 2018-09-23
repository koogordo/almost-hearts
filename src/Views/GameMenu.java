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
		joinExistingButton.setEnabled(false);
		frame.add(joinExistingButton, BorderLayout.EAST);
		
		createNewButton = new JButton("Create a new game");
		createNewButton.setEnabled(false);
		frame.add(createNewButton, BorderLayout.WEST);
		
		south = new JPanel();
		frame.add(south, BorderLayout.SOUTH);
		south.setLayout(new GridLayout(2,1));
		
		namePrompt = new JLabel("Please enter your name:");
		namePrompt.setHorizontalAlignment(SwingConstants.CENTER);
		south.add(namePrompt);
		
		name = new JTextField();
		name.addKeyListener(new TextFieldHandler());
		south.add(name);
		
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
		    	System.exit(0);
		    }
		});
		frame.setVisible(true);
	}
	
	private static class ButtonHandler implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent event) {
			
			
		}
		
	}
	private static class TextFieldHandler implements KeyListener{

		@Override
		public void keyTyped(KeyEvent e) {
			// TODO Auto-generated method stub
			if(name.getText().equals("")){
				joinExistingButton.setEnabled(false);
				createNewButton.setEnabled(false);
			}
			else {
				joinExistingButton.setEnabled(true);
				createNewButton.setEnabled(true);
			}
		}

		@Override
		public void keyPressed(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void keyReleased(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}
		
	}

}