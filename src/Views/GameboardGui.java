package Views;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import javax.swing.border.*;
import java.io.*;
import java.net.Socket;
import java.net.URL;
import java.util.*;
import AppTools.Card;

/**
 * The GameBoardGui is the is the graphical user interface for gameplay.
 * This GUI handles all the actions a player can take along with displaying appropriate notifications
 * for game over, winner, round over... etc. The gameboard GUI also handles the communication between the
 * players and the server.  
 * 
 */

public class GameboardGui extends JFrame implements Runnable {
	private JLabel[] playerLabels;
	private ArrayList<Card> hand = new ArrayList<>();
	private JPanel notificationPanel;
	private JLabel notificationLabel;
	private JPanel handArea;
	private JPanel handAndSubmit;
	private JPanel submitArea;
	private JPanel newGameSection;
	private JPanel exitSection;
	private JPanel NorthStuff;
	private JPanel CenterStuff;
	private JButton submit;
	private JPanel contentPane;
	private Card selectedCard;
	private Socket socket;
	private PrintStream out;
	private BufferedReader in;
	private int playerID;
	private JLabel[] cardLabels = new JLabel[3];
	private JPanel[] playerPanels = new JPanel[3];
	private JFrame loadingScreen;
	private JFrame newGameFrame;
	private JPanel newGamePanel;
	private JButton newGameBtn;
	private JButton quitBtn;
	private JLabel playAgainMessage;
	private JLabel gameLogo; // Initialize gameLogo JLabel
	private boolean myTurn = false;
	int totalRounds = 1;    // total rounds


	public GameboardGui(Socket socket, JFrame screen) {
		this.socket = socket;
		loadingScreen = screen;
		playerLabels = new JLabel[3];
		try {
			out = new PrintStream(socket.getOutputStream());
		} catch (IOException e) { // Catching any errors
			e.printStackTrace(); // Printing out said errors
		}

		setBounds(100, 100, 1100, 900);
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e)
		    {
				out.println("Exit");
				out.flush();
		    }
		});
		
		contentPane = new ImagePanel("/cardImages/greenlogo.jpg");
		contentPane.setOpaque(false);
		contentPane.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		
		//contentPane = new JPanel();
		//contentPane.setBackground(new Color(41,158,48)); // Green Color ---------------------------------
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		handAndSubmit = new JPanel();
		handAndSubmit.setOpaque(false);
		handArea = new JPanel();
		handArea.setOpaque(false);
		FlowLayout flowLayout = (FlowLayout) handArea.getLayout();
		submitArea = new JPanel();
		handAndSubmit.setLayout(new BoxLayout(handAndSubmit, BoxLayout.Y_AXIS));
		handAndSubmit.add(handArea);
		handAndSubmit.add(submitArea);
		submitArea.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		submitArea.setMaximumSize(new Dimension(this.getWidth(), this.getHeight()/10));

		// Notification GUI logic
		// Components for displaying appropriate messages to all players.
		notificationPanel = new JPanel();
		notificationPanel.setOpaque(false);
		notificationPanel.setPreferredSize(new Dimension(this.getWidth(), this.getHeight() / 10));
		notificationPanel.setAlignmentX(CENTER_ALIGNMENT);

		// Base content pane logic
		JPanel panel = new ImagePanel("/cardImages/woodWallpaper.jpg");
		panel.setOpaque(false);
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
		contentPane.add(notificationPanel);
		notificationPanel.setLayout(new BoxLayout(notificationPanel, BoxLayout.Y_AXIS));
		notificationLabel = new JLabel("notification label");
		notificationLabel.setHorizontalAlignment(SwingConstants.CENTER);
		notificationLabel.setForeground(Color.white);
		notificationLabel.setFont(new Font("Serif", Font.PLAIN, 40));
		notificationPanel.add(notificationLabel);
		contentPane.add(panel);
		contentPane.add(handAndSubmit);
		panel.setLayout(new GridLayout(0, 3, 0, 0)); 
		panel.setPreferredSize(new Dimension(this.getWidth(), this.getHeight() / 8));

		/*
		 * The player GUIs are main focus of the GUI, this is where all hands are played.
		 * Each player gui follows the same design, format, and components. The have a panel 
		 * to designate their area, a label to display their card, and a label to display their name.
		 * following this comment block the player GUI logic will be designated by the following headers:
		 * 
		 * -- Player one GUI logic
		 * -- Player two GUI logic
		 * -- Player 3 GUI logic
		 */
		
		

		//Player one GUI logic
		JPanel panel_1 = new JPanel();
		panel_1.setOpaque(false);
		panel_1.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panel.add(panel_1);
		playerLabels[0] = new JLabel("Player 1");
		playerLabels[0].setForeground(Color.WHITE);
		playerLabels[0].setVerticalAlignment(SwingConstants.TOP);
		playerLabels[0].setHorizontalAlignment(SwingConstants.CENTER);
		playerLabels[0].setFont(new Font("Serif", Font.PLAIN, 25));
		cardLabels[0] = new JLabel();
		panel_1.setLayout(new BorderLayout(0, 0));
		panel_1.add(playerLabels[0], BorderLayout.NORTH);
		panel_1.add(cardLabels[0], BorderLayout.CENTER);

		// Player two GUI logic
		JPanel panel_2 = new JPanel();
		panel_2.setOpaque(false);
		panel_2.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panel.add(panel_2);
		playerLabels[1] = new JLabel("Player 2");
		playerLabels[1].setForeground(Color.WHITE);
		playerLabels[1].setHorizontalAlignment(SwingConstants.CENTER);
		playerLabels[1].setVerticalAlignment(SwingConstants.TOP);
		playerLabels[1].setFont(new Font("Serif", Font.PLAIN, 25));
		cardLabels[1] = new JLabel();
		panel_2.setLayout(new BorderLayout(0, 0));
		panel_2.add(playerLabels[1], BorderLayout.NORTH);
		panel_2.add(cardLabels[1], BorderLayout.CENTER);

		// Player 3 GUI logic
		JPanel panel_3 = new JPanel();
		panel_3.setOpaque(false);
		panel_3.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panel.add(panel_3);
		playerLabels[2] = new JLabel("Player 3");
		playerLabels[2].setForeground(Color.WHITE);
		playerLabels[2].setVerticalAlignment(SwingConstants.TOP);
		playerLabels[2].setHorizontalAlignment(SwingConstants.CENTER);
		playerLabels[2].setFont(new Font("Serif", Font.PLAIN, 25));
		cardLabels[2] = new JLabel();
		panel_3.setLayout(new BorderLayout(0, 0));
		panel_3.add(playerLabels[2], BorderLayout.NORTH);
		panel_3.add(cardLabels[2], BorderLayout.CENTER);

		// Player panels get added to a string so that they are easier to reference by an ID number
		playerPanels[0] = panel_1;
		playerPanels[1] = panel_2;
		playerPanels[2] = panel_3;
		

		newGameFrame = new JFrame();
		newGameFrame.setSize(450, 250);
		
		newGameFrame.setResizable(false); // Do not allow the user to adjust the size of the frame
	    Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize(); // set the dimension to the screenSize
	    int x = (int) ((dimension.getWidth() - newGameFrame.getWidth()) / 2); // Set x to the width of the screen - the width of the frame
	    int y = (int) ((dimension.getHeight() - newGameFrame.getHeight()) / 2); // Set y to the height of the screen - the height of the frame
	    newGameFrame.setLocation(x, y); // Set the frame's starting location to x, y
		// New or Exit Game Option Panel ------------------------------------------------------------------
		newGamePanel = new JPanel();
		newGamePanel.setLayout(new BorderLayout());
		
		gameLogo = new JLabel(); // Setting gameLogo to a new JLabel
		gameLogo.setSize(200,100); // Seting gameLogo to the size of 200, 58
		
		NorthStuff = new JPanel();
		NorthStuff.setLayout(new BorderLayout());
		newGamePanel.add(NorthStuff, BorderLayout.NORTH);
		
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
		NorthStuff.add(BorderLayout.NORTH, TopLogo); // Adding TopLogo to North of the frame
		
		
		playAgainMessage = new JLabel("Would you like to play again?");
		playAgainMessage.setFont(new Font("Arial", Font.BOLD, 30));
		playAgainMessage.setHorizontalAlignment(SwingConstants.CENTER);
		NorthStuff.add(playAgainMessage, BorderLayout.CENTER);
		
		CenterStuff = new JPanel();
		CenterStuff.setLayout(new BorderLayout());
		newGamePanel.add(CenterStuff, BorderLayout.CENTER);
		
		newGameBtn = new JButton("New Game");
		newGameBtn.setFont(new Font("Arial", Font.BOLD, 20));
		newGameBtn.addActionListener(new newGameOrQuitBtn());
		newGameSection = new JPanel(new FlowLayout());
		newGameSection.setSize(100, 50);
		newGameSection.add(newGameBtn);
		CenterStuff.add(newGameSection, BorderLayout.NORTH);
		
		
		quitBtn = new JButton("Exit");
		quitBtn.setFont(new Font("Arial", Font.BOLD, 20));
		quitBtn.addActionListener(new newGameOrQuitBtn());
		exitSection = new JPanel(new FlowLayout());
		exitSection.setSize(100, 50);
		exitSection.add(quitBtn);
		CenterStuff.add(exitSection, BorderLayout.CENTER);
		
		//newGamePanel.add(playAgainMessage);
		//newGamePanel.add(newGameBtn);
		
		newGameFrame.add(newGamePanel);
		newGameFrame.setVisible(false);
		
		//this.setVisible(true);//erase later, only used for temporary use
	}

	public void setRearCards() {
		/*
		 * setRearCards() is a method that will set the three card icons to show the backs of cards by default
		 * this provides an immediate indication has to how the gameboard is going to be structured right away,
		 * by making it clear as to where the cards should show up when played.
		 */
		ImageIcon tempIcon = new ImageIcon(this.getClass().getResource("/cardImages/rearOfCard.png"));
		ImageIcon temp = ScaledImage(tempIcon.getImage());

		for (int i = 0; i < 3; i++) {
			cardLabels[i].setIcon(temp);
			cardLabels[i].setHorizontalAlignment(SwingConstants.CENTER);
		}
		this.repaint();
	}

	public void setHand(String cards) {
		/*
		 * At the beginning of each round players are dealt a hand. The hand is displayed at the bottom of the screen
		 * each card is displayed a JLabel. This method is used to set the image icon on each label to display the cards
		 * of the player.
		 */
		cards = cards.toLowerCase();

		// Cards are sent to and from the server as a string
		// This string gets processed into cards by passing the values to instantiate new card objects
		// The cards themselves, inherit from JLabel making it very easy to add them to panel once instantiated.
		StringTokenizer st = new StringTokenizer(cards);
		for (int i = 0; i < 17; i++) {
			String suit = st.nextToken();
			int number = Integer.parseInt(st.nextToken());
			hand.add(new Card(suit, number));
		}

		// Each players hand is sorted to satisfy users who like to have their hands ordered.
		sortHand(hand);

		// The image icon for each card is scaled so that it can fit nicely into the gui.
		for (int i = 0; i < 17; i++) {
			hand.get(i).setSize(contentPane.getWidth() / 10, (contentPane.getWidth() / 10) * (800 / 500));
			Image imagetemp = hand.get(i).getImage().getImage();
			Image temp = imagetemp.getScaledInstance(contentPane.getWidth() / 10,
					(contentPane.getWidth() / 8) * (800 / 500), java.awt.Image.SCALE_SMOOTH);
			ImageIcon tempIcon = new ImageIcon(temp);
			hand.get(i).setIcon(tempIcon);
			handArea.add(hand.get(i));
			hand.get(i).setBorder(BorderFactory.createLineBorder(Color.lightGray, 3));
			hand.get(i).addMouseListener(new selectCard());
		}

		//SubmitHolder.add(submit);
		//handArea.add(SubmitHolder, BorderLayout.SOUTH);
		this.revalidate();
		this.repaint();
	}

	public void setPlayerNames(String names) { // Method to set the player's names
		/*
		 * Player names, like the cards, are received from the server as a single string.
		 * In this method the string is parsed for each individual name and the appropriate
		 * label for each player is set to display their name
		 */
		StringTokenizer st = new StringTokenizer(names);
		st.nextToken();


		playerLabels[0].setText(st.nextToken());
		playerLabels[1].setText(st.nextToken());
		playerLabels[2].setText(st.nextToken());

	}

	public static void sortHand(ArrayList<Card> cards) {
		/*
		 * This is the method that is used to sort the player hands, it sorts the cards first by suit
		 * and then sorts the groups of suits by value. This leaves the player with a nicely ordered hand
		 */

		String suit = "c";
		for (int i = 0; i < cards.size() - 1;) {
			if (!suit.equals("")) {
				for (int k = i; k < cards.size(); k++) {
					if (cards.get(k).getSuit().equals(suit)) {
						Card temp = cards.get(i);
						cards.set(i, cards.get(k));
						cards.set(k, temp);
						i++;
					}
				}
			}
			suit = nextSuit(suit);
		}
		for (int i = 0; i < cards.size(); i++) {
			for (int k = i; k < cards.size(); k++) {
				if (cards.get(k).getValue() > cards.get(i).getValue()
						&& cards.get(i).getSuit().equals(cards.get(k).getSuit())) {
					Card temp = cards.get(i);
					cards.set(i, cards.get(k));
					cards.set(k, temp);
				}
			}
		}
	}

	public static String nextSuit(String suit) {
		/*
		 * This method dictates the order of the suits. In other words, it returns the suits in the order 
		 * that they should be sorted. This method handles this by returning the first letter of each suit in 
		 * alphabetical order
		 */

		if (suit.equals("c"))
			return "d";
		if (suit.equals("d"))
			return "h";
		if (suit.equals("h"))
			return "s";
		if (suit.equals("s"))
			return "";
		return "";
	}

	private class selectCard implements MouseListener {
		/*
		 * This is the mouse event listener. It is only set up to listen for one type of mouse event
		 * and that is the moused clicked event. We want the players to be able to select a card without actually
		 * submitting their turn. This listener is set up so that it outlines the card that is selected by the player
		 * so that they can see it on the GUI. It also sets the backend selectedCard value which can be sent to the server
		 * as soon as the submit button is clicked.
		 */

		@Override
		public void mouseClicked(MouseEvent e) {
			if (selectedCard != null) {
				selectedCard.setBorder(BorderFactory.createLineBorder(Color.lightGray, 3));
				//selectedCard.setBorder(null);
			}
			selectedCard = ((Card) e.getSource());
			selectedCard.setBorder(BorderFactory.createLineBorder(Color.ORANGE, 3));
			submit.setEnabled(myTurn);
		}
		@Override
		public void mouseEntered(MouseEvent arg0) { }

		@Override
		public void mouseExited(MouseEvent arg0) { }

		@Override
		public void mousePressed(MouseEvent arg0) { }

		@Override
		public void mouseReleased(MouseEvent arg0) { }
	}

	private class submitButton implements ActionListener {
		/*
		 * An button listener that that implements a method upon clicking that removes a played card from the 
		 * players hand so that it no longer shows up in their hand area. A move string is then consructed and is sent
		 * to the server to be processed.
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			System.out.println(selectedCard.getSuit() + selectedCard.getValue());
			for (int i = 0; i < hand.size(); i++) {
				if (selectedCard.getSuit() == hand.get(i).getSuit() && selectedCard.getValue() == hand.get(i).getValue()) {
					handArea.remove(hand.remove(i));
				}
			}

			// Move string - a string representation of the player move so it can be easily transmitted
			String cardStream = "Played " + " " + playerID + " " + selectedCard.getSuit() + " " + selectedCard.getValue();
			System.out.println(cardStream);
			out.println(cardStream);
			out.flush();
			submit.setEnabled(false);
		}
	}

	private class newGameOrQuitBtn implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			JButton source = (JButton) e.getSource();
			String btnText = source.getText();
			switch(btnText) {
			case "New Game" :
				out.println("Reset");
				out.flush();
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				break;
			case "Exit":
				out.println("Exit");
				out.flush();
				break;
			}

		}

	}
	
	public ImageIcon ScaledImage(Image i) {
		/*
		 * A method for scaling the images on image icons so they remain proportional to the screen and so that
		 * they can fit nicely on the GUI
		 */
		Image imagetemp = i;
		Image temp = imagetemp.getScaledInstance(contentPane.getWidth() / 8, (contentPane.getWidth() / 6) * (800 / 500),
				java.awt.Image.SCALE_SMOOTH);
		ImageIcon scaledImage = new ImageIcon(temp);
		return scaledImage;
	}
	
	public Image ScaledPanelImage(Image i) {
		Image temp = i.getScaledInstance(this.getWidth(), this.getHeight(), java.awt.Image.SCALE_SMOOTH);
		return temp;
	}

	public boolean isMyTurn(int playedPerson) {
		// A method for determining whether or not a players turn is up.
		playedPerson++;
		return playedPerson % 3 == playerID;
	}
	
	public void run() {
		/*
		 * This thread handles all gameplay tasks that require passing information to the ServerPlayer
		 * The general structure of the logic is represented by a case statement. Their are 4 main categories
		 * of actions that might have to be submitted and in our case statement we have one case for each category.
		 * This thread uses streams sent via sockets to report events happening on the client side to the server.
		 * 
		 */
		try {
			// Creates the input stream to read in the output from the server
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

			// Uses StringTokenizer so that it is easy to traverse the String coming in
			StringTokenizer st = new StringTokenizer(in.readLine());

			this.setVisible(true);

			// The first thing coming in is going to be the playersID and the names of the players (in order)
			playerID = Integer.parseInt(st.nextToken());
			playerLabels[0].setText(st.nextToken().replace("-", " "));
			playerLabels[1].setText(st.nextToken().replace("-", " "));
			playerLabels[2].setText(st.nextToken().replace("-", " "));
			myTurn = (playerID == 0);

			setRearCards();

			// Parses the string given into Card objects and puts it in the ArrayList hand
			setHand(in.readLine());
			submit = new JButton("Play Card");
			submit.setFont(new Font("Arial", Font.BOLD, 30)); 
			submit.addActionListener(new submitButton());
			submit.setEnabled(false);

			//Adding submit to a flow layout and the flow layout to handArea
			submitArea.setOpaque(false); 
			submitArea.add(submit);
			this.notificationLabel.setText(playerLabels[0].getText() + "'s Turn");
			this.revalidate();
			this.repaint();
			
			// Display the loading screen as we are waiting for players to connect/join
			loadingScreen.setVisible(false);
			newGameFrame.setVisible(false);
			while (true) {
				/* 
				 * While true creates in infinite loop which allows this threads sockets to listen constantly
				 * and respond or act when necessary. The information that they are receiving is coming from the Game.
				 * All the different message types carry info that can be used to make some change on the gameboard.
				 */
				st = new StringTokenizer(in.readLine());
				String switchToken = st.nextToken();
				switch (switchToken) {
				case "Played":
					// Messages that come in with a "Played" header are handled here

					// set the icon in the appropriate player box to reflect the card that they showed
					int player = Integer.parseInt(st.nextToken());
					String suit = st.nextToken();
					int value = Integer.parseInt(st.nextToken());
					System.out.println("Got the played card");
					Card playedCard = new Card(suit, value);
					ImageIcon temp = ScaledImage(playedCard.getImage().getImage());
					cardLabels[player].setIcon(temp);
					myTurn = isMyTurn(player);
					this.notificationLabel.setText(playerLabels[++player%3].getText() + "'s Turn");
					this.repaint();
					break;

				case "Winner":
					// Messages that come in with a "Winner" header are handled here
					int gameWinnerName = Integer.parseInt(st.nextToken());
					// Set notification to display the game winning players name
					this.notificationLabel.setText(playerLabels[gameWinnerName].getText() + " wins the game!");
					this.notificationLabel.repaint();

					newGameFrame.setVisible(true);
					break;

				case "Round":
					// Messages that come in with a "Round" header are handled here
					int roundWinner = Integer.parseInt(st.nextToken());

					// Set notification to display the round winning players name
					this.notificationLabel.setText(playerLabels[roundWinner].getText() + " won round " + totalRounds + "!");
					this.notificationLabel.repaint();
					try {
						Thread.sleep(2000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					myTurn = (roundWinner == playerID);
					setRearCards();
					totalRounds++;
					break;
				case "Reset":
					newGameFrame.setVisible(false);
					totalRounds = 0;
					this.notificationLabel.setText(playerLabels[0].getText() + "'s Turn");
					myTurn = (playerID == 0);
					setRearCards();
					setHand(in.readLine());
					break;

				case "Exit":
					int personWhoQuit = Integer.parseInt(st.nextToken());
					JOptionPane.showMessageDialog(null, playerLabels[personWhoQuit].getText() + " has left the game");
					// Messages that come in with a "Round" header are handled here
					System.exit(0);
					break;
				default:
					break;
				}
			}
		} catch (IOException e) { // Catch any error(s)
			e.printStackTrace(); // Print out the error(s)
		}
	}
	public class ImagePanel extends JPanel {

	    private BufferedImage image;
	    private Image scaledImage;

	    public ImagePanel(String location) {
	       try {                
	          image = ImageIO.read(this.getClass().getResource(location));
	       } catch (IOException ex) {
	            // handle exception...
	       }
	       scaledImage = ScaledPanelImage(image);
	    }

	    @Override
	    protected void paintComponent(Graphics g) {
	        super.paintComponent(g);
	        g.drawImage(scaledImage, 0, 0, this); // see javadoc for more info on the parameters            
	    }

	}
}
