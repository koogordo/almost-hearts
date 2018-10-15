package Views;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.*;
import java.util.concurrent.*;
import javax.imageio.ImageIO;
import javax.swing.*;

import AppTools.*;

/**
 * GameMenu is a small GUI that takes no input for the constructor
 * 
 * In the GUI, there are 2 buttons (joinExistingButton and createNewButton) and 2 text fields (name and address).
 * joinExistingButton takes the name and address text inside the text fields and instantiates a Client object with them.
 * createNewButton does the exact same thing as joinExistingButton except that it creates a server in a new thread
 * and replaces the address text field with it's own address.
 */

public class GameMenu {
	private JLabel gameLogo; // Initialize gameLogo JLabel
	private JButton joinExistingButton; // Initialize JButton to join an existing game
	private JButton createNewButton; // Initialize button to create a new game
	private JLabel namePrompt; // Initialize JLabel promping the user to enter their name and IP address
	private JTextField name; // Initialize JTextField for the user's name to be entered
	private JTextField address; // Initialize JTextField for the IP Address to be entered
	private JPanel center;// Initialize JPanel for Center
	private JPanel north; // Initialize JPanel for North
	private JPanel south; // Initialize JPanel for South
	private JFrame frame; // Initialize JFrame
    
	public GameMenu() { // GameMenu Method
		frame = new JFrame("GameMenu"); // Creating a new JFrame called Game Menu
		frame.setSize(380, 320); // Setting the size of the frame to 325 wide x 200 high
		frame.setResizable(false); // Do not allow the user to adjust the size of the frame
	    Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize(); // set the dimension to the screenSize
	    int x = (int) ((dimension.getWidth() - frame.getWidth()) / 2); // Set x to the width of the screen - the width of the frame
	    int y = (int) ((dimension.getHeight() - frame.getHeight()) / 2); // Set y to the height of the screen - the height of the frame
	    frame.setLocation(x, y); // Set the frame's starting location to x, y
		BorderLayout layout = new BorderLayout(); // Creating a border layout
		frame.setLayout(layout); // Setting the layout of our frame to the border layout
		
		// NORTH with Game Logo-----------------------------------------------------------------------------
		gameLogo = new JLabel(); // Setting gameLogo to a new JLabel
		gameLogo.setSize(300,150); // Seting gameLogo to the size of 200, 58
		
		BufferedImage logo = null; // Initializing logo
		try {
			java.net.URL url = this.getClass().getResource("/cardImages/AlmostHearts.png");
		    logo = ImageIO.read(url); // Setting logo to the logo image
		} catch (IOException e) { // Checking for errors
		    e.printStackTrace(); // Print out said errors
		}
		Image dimg = logo.getScaledInstance(gameLogo.getWidth(), gameLogo.getHeight(), Image.SCALE_SMOOTH); // Setting dimg to the width and height of gameLogo
		ImageIcon imageIcon = new ImageIcon(dimg); // Setting imageIcon to the dimg
		gameLogo.setIcon(imageIcon); // Setting gameLogo's icon to imageIcon
		JPanel TopLogo = new JPanel(new FlowLayout()); // Setting JPanel TopLogo to flowLayout
		TopLogo.add(gameLogo); // Adding gameLogo to the topLogo
		frame.add(BorderLayout.NORTH, TopLogo); // Adding TopLogo to North of the frame
		
		// CENTER Grid with Text Boxes----------------------------------------------------------------------
		JPanel CenterOutter = new JPanel(new FlowLayout()); // Setting JPanel CenterOutter to a FlowLayout
		CenterOutter.setSize(200, 200); // Seting the size of CenterOutter to 200, 200
		JPanel centerInfo = new JPanel(new BorderLayout()); // Setting JPanel centerInfo to Border Layout
		JPanel topLine = new JPanel(new BorderLayout()); // Setting JPanel topLine to Border Layout
		JPanel bottomLine = new JPanel(new BorderLayout()); // Setting JPanel bottomLine to Border Layout
		
		namePrompt = new JLabel("Please enter your name:"); // Adding a JLabel that tells the user to enter their name
		namePrompt.setHorizontalAlignment(SwingConstants.CENTER); // Setting the alignment to center
		topLine.add(BorderLayout.NORTH, namePrompt); // Add namePrompt to the north border
		
		name = new JTextField(); // Creating a new JTextField
		name.addKeyListener(new TextFieldHandler()); // Setting the keyListener of name to a new TextFieldHandler
		topLine.add(BorderLayout.CENTER, name); // Add name to the north border
		
		namePrompt = new JLabel("Please enter the IP Address:"); // Adding a JLabel that tells the user to enter their name
		namePrompt.setHorizontalAlignment(SwingConstants.CENTER); // Setting the alignment to center
		bottomLine.add(BorderLayout.NORTH, namePrompt); // Add namePrompt to the north border
		
		address = new JTextField(); // Creating a new JTextField
		address.addKeyListener(new TextFieldHandler()); // Setting the keyListener of address to a new TextFieldHandler
		bottomLine.add(BorderLayout.CENTER, address);  // adding address to Center of bottomLine
		
		centerInfo.add(topLine, BorderLayout.NORTH); // Adding topLine to North of CenterInfo
		centerInfo.add(bottomLine, BorderLayout.CENTER); // Adding bottomLine to Center of CenterInfo
		
		CenterOutter.add(centerInfo); // Adding centerInfo to CenterOutter
		frame.add(BorderLayout.CENTER, CenterOutter); // Adding CenterOutter to Center of frame
		
		// SOUTH Grid with Text Boxes----------------------------------------------------------------------
		south = new JPanel(); // Creating a JPanel
		frame.add(south, BorderLayout.SOUTH); // Adding a north pane that we can add multiple things to
		south.setLayout(new GridLayout(0,2)); // Setting the gridLayout of south to 0,2
		
		//WEST
		createNewButton = new JButton("Create a new game"); // Creating a button called "Create a new Game"
		createNewButton.setEnabled(false); // Setting enabled to false
		createNewButton.addActionListener(new ButtonHandler()); // Creating an actionListener for when the button is pressed
		JPanel createNewFrame = new JPanel(new FlowLayout()); // Creating a JPanel as a FlowLayout
		createNewFrame.add(createNewButton); // Adding createNewButton to the CreateNewFrame
		south.add(createNewFrame); // Adding CreateNewFram to south
		
		//EAST
		joinExistingButton = new JButton("Join existing game"); // Creating a button called "Join existing Game"
		joinExistingButton.setEnabled(false); // Setting enabled to false
		joinExistingButton.addActionListener(new ButtonHandler()); // Creating an actionListener for when the button is pressed
		joinExistingButton.setSize(10, 10); // Setting the size of joinExistingButton to 10,10
		JPanel joinExistingFrame = new JPanel(new FlowLayout()); // Creating a JPanel with a FlowLayout
		joinExistingFrame.add(joinExistingButton); // Adding joinExistingButton to the joinExistingFrame
		south.add(joinExistingFrame); // Adding joinExistingFrame to south
		
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) { // Method to close the window
		    	System.exit(0); // Exit the window
		    }
		});
		frame.setVisible(true); // Set the frame's visibility to true
	}
	
	private class ButtonHandler implements ActionListener { // ButtonHandler method that implements the ActionListener
		@Override
		public void actionPerformed(ActionEvent event) {
			ExecutorService executorService = Executors.newCachedThreadPool(); //creates a thread pool
			if(event.getActionCommand().equals(createNewButton.getText())) { // if a button is pressed, do the following:				
				executorService.execute(new Server());//adds a new server thread to the thread pool
				try {
					address.setText(InetAddress.getLocalHost().getHostAddress());
					//if the person is hosting, just make the local address the parameter
				} catch (UnknownHostException e) {
					e.printStackTrace(); // Print out the error
				}
			}
			frame.setVisible(false); // set the visibility to false
			Client client = new Client(name.getText(), address.getText(), frame); // Setting client to the text of name and address
			if(!frame.isVisible()) {
				GameboardGui gui = new GameboardGui(client.getSocket(), client.getLoadingScreen());
				Thread t1 = new Thread(gui);
				t1.start();
			}
		}
	}
	
	private class TextFieldHandler implements KeyListener { // TextField Handler method to handle the Action Listener
		@Override
		public void keyTyped(KeyEvent e) {
			if(name.getText().equals("")) { // If the name is blank
				createNewButton.setEnabled(false); // createNewButton is grayed out
				joinExistingButton.setEnabled(false); // joinExistingButton is grayed out
			}
			else {
				createNewButton.setEnabled(true); // createNewButton enabled
				joinExistingButton.setEnabled(true); // joinExistingButton enabled
			}
		}
		@Override
		public void keyPressed(KeyEvent e) { }
		
		@Override
		public void keyReleased(KeyEvent e) { }
	}
}