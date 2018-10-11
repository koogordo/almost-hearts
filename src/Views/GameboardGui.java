package Views;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import java.io.*;
import java.net.Socket;
import java.util.*;
import AppTools.Card;

/**
 * 
 * 
 */

public class GameboardGui extends JFrame implements Runnable 
{
	private JLabel[] playerLabels;
	private ArrayList<Card> hand = new ArrayList<>();
	private JPanel notificationPanel;
	private JLabel notificationLabel;
	private JPanel handArea;
	private JPanel handAndSubmit;
	private JPanel submitArea;
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
	private boolean myTurn = false;
	int totalRounds = 1;    // total rounds

	// Launch the application and create the frame.
	public GameboardGui(Socket socket, JFrame screen) 
	{
		this.socket = socket;
		loadingScreen = screen;
		playerLabels = new JLabel[3];
		try 
		{
			out = new PrintStream(socket.getOutputStream());
		} 
		catch (IOException e) // Catching any errors
		{
			e.printStackTrace(); // Printing out said errors
		}
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1100, 900);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		handAndSubmit = new JPanel();

		handArea = new JPanel();
		FlowLayout flowLayout = (FlowLayout) handArea.getLayout();
		
		submitArea = new JPanel();
		handAndSubmit.setLayout(new BoxLayout(handAndSubmit, BoxLayout.Y_AXIS));
		
		handAndSubmit.add(handArea);
		handAndSubmit.add(submitArea);
		submitArea.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		submitArea.setMaximumSize(new Dimension(this.getWidth(), this.getHeight()/10));
		
		
		//--------------------------------------------------------------------------------------
		//BorderLayout Borderlayout = (BorderLayout) handArea.getLayout();
		
		notificationPanel = new JPanel();
		notificationPanel.setPreferredSize(new Dimension(this.getWidth(), this.getHeight() / 10));
		notificationPanel.setAlignmentX(CENTER_ALIGNMENT);

		JPanel panel = new JPanel();
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
		contentPane.add(notificationPanel);
		notificationPanel.setLayout(new BoxLayout(notificationPanel, BoxLayout.Y_AXIS));
		notificationLabel = new JLabel("notification label");
		notificationLabel.setHorizontalAlignment(SwingConstants.CENTER);
		notificationLabel.setFont(new Font("Serif", Font.PLAIN, 40));
		notificationPanel.add(notificationLabel);
		contentPane.add(panel);
		contentPane.add(handAndSubmit);
		panel.setLayout(new GridLayout(0, 3, 0, 0)); 
		panel.setPreferredSize(new Dimension(this.getWidth(), this.getHeight() / 8));
		
		//Player one GUI logic
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		// panel_1.setPreferredSize(new Dimension(100, 100));
		panel.add(panel_1);
		playerLabels[0] = new JLabel("Player 1");
		playerLabels[0].setVerticalAlignment(SwingConstants.TOP);
		playerLabels[0].setHorizontalAlignment(SwingConstants.CENTER);
		playerLabels[0].setFont(new Font("Serif", Font.PLAIN, 25));
		cardLabels[0] = new JLabel();
		panel_1.setLayout(new BorderLayout(0, 0));
		panel_1.add(playerLabels[0], BorderLayout.NORTH);
		panel_1.add(cardLabels[0], BorderLayout.CENTER);

		// Player two GUI logic
		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		// panel_2.setPreferredSize(new Dimension(100, 150));
		panel.add(panel_2);
		playerLabels[1] = new JLabel("Player 2");
		playerLabels[1].setHorizontalAlignment(SwingConstants.CENTER);
		playerLabels[1].setVerticalAlignment(SwingConstants.TOP);
		playerLabels[1].setFont(new Font("Serif", Font.PLAIN, 25));
		cardLabels[1] = new JLabel();
		panel_2.setLayout(new BorderLayout(0, 0));
		panel_2.add(playerLabels[1], BorderLayout.NORTH);
		panel_2.add(cardLabels[1], BorderLayout.CENTER);

		// Player 3 GUI logic
		JPanel panel_3 = new JPanel();
		panel_3.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		// panel_3.setPreferredSize(new Dimension(100, 150));
		panel.add(panel_3);
		playerLabels[2] = new JLabel("Player 3");
		playerLabels[2].setVerticalAlignment(SwingConstants.TOP);
		playerLabels[2].setHorizontalAlignment(SwingConstants.CENTER);
		playerLabels[2].setFont(new Font("Serif", Font.PLAIN, 25));
		cardLabels[2] = new JLabel();
		panel_3.setLayout(new BorderLayout(0, 0));
		panel_3.add(playerLabels[2], BorderLayout.NORTH);
		panel_3.add(cardLabels[2], BorderLayout.CENTER);
		
		// adding player panels to array to make them easier to reference using playerID
		playerPanels[0] = panel_1;
		playerPanels[1] = panel_2;
		playerPanels[2] = panel_3;
		this.setVisible(false);
	}

	public void setRearCards() 
	{
		ImageIcon tempIcon = new ImageIcon(this.getClass().getResource("/cardImages/rearOfCard.png"));
		ImageIcon temp = ScaledImage(tempIcon.getImage());

		for (int i = 0; i < 3; i++) 
		{
			cardLabels[i].setIcon(temp);
			cardLabels[i].setHorizontalAlignment(SwingConstants.CENTER);
		}
		this.repaint();
	}

	public void setHand(String cards) 
	{
		cards = cards.toLowerCase();

		StringTokenizer st = new StringTokenizer(cards);
		// st.nextToken();
		System.out.println(cards);
		for (int i = 0; i < 17; i++) 
		{
			String suit = st.nextToken();
			int number = Integer.parseInt(st.nextToken());
			hand.add(new Card(suit, number));
		}
		sortHand(hand);
		//JPanel HandHolder = new JPanel(new FlowLayout()); // Creating a JPanel with a FlowLayout
		//JPanel SubmitHolder = new JPanel(new FlowLayout()); // Creating a JPanel with a FlowLayout
		for (int i = 0; i < 17; i++) 
		{
			hand.get(i).setSize(contentPane.getWidth() / 10, (contentPane.getWidth() / 10) * (800 / 500));
			Image imagetemp = hand.get(i).getImage().getImage();
			Image temp = imagetemp.getScaledInstance(contentPane.getWidth() / 10,
					(contentPane.getWidth() / 8) * (800 / 500), java.awt.Image.SCALE_SMOOTH);
			ImageIcon tempIcon = new ImageIcon(temp);
			//-----------------------------------
			hand.get(i).setIcon(tempIcon);
			handArea.add(hand.get(i));
			//HandHolder.add(hand.get(i));
			//handArea.add(HandHolder, BorderLayout.CENTER);
			hand.get(i).setBorder(BorderFactory.createLineBorder(Color.lightGray, 3));
			hand.get(i).addMouseListener(new selectCard());
		}
		// MOVE TO A NEW LINE ----------------------------------------------------
		submit = new JButton("Submit");
		submit.addActionListener(new submitButton());
		submit.setEnabled(false);
		//----------------------------------- Adding submit to a flow layout and the flow layout to handArea
		submitArea.add(submit);
		//SubmitHolder.add(submit);
		//handArea.add(SubmitHolder, BorderLayout.SOUTH);
		this.revalidate();
		this.repaint();
	}

	public void setPlayerNames(String names)  // Method to set the player's names
	{
		StringTokenizer st = new StringTokenizer(names);
		st.nextToken();

		playerLabels[0].setText(st.nextToken());
		playerLabels[1].setText(st.nextToken());
		playerLabels[2].setText(st.nextToken());
	}

	public static void sortHand(ArrayList<Card> cards) // Method to sort the Hand
	{
		String suit = "c";
		for (int i = 0; i < cards.size() - 1;) 
		{
			if (!suit.equals("")) 
			{
				for (int k = i; k < cards.size(); k++) 
				{
					if (cards.get(k).getSuit().equals(suit)) 
					{
						Card temp = cards.get(i);
						cards.set(i, cards.get(k));
						cards.set(k, temp);
						i++;
					}
				}
			}
			suit = nextSuit(suit);
		}
		for (int i = 0; i < cards.size(); i++) 
		{
			for (int k = i; k < cards.size(); k++) 
			{
				if (cards.get(k).getValue() > cards.get(i).getValue()
						&& cards.get(i).getSuit().equals(cards.get(k).getSuit())) 
				{
					Card temp = cards.get(i);
					cards.set(i, cards.get(k));
					cards.set(k, temp);
				}
			}
		}
	}

	public static String nextSuit(String suit) // Method to return the next suit
	{
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

	private class selectCard implements MouseListener 
	{
		@Override
		public void mouseClicked(MouseEvent e) 
		{
			if (selectedCard != null) 
			{
				selectedCard.setBorder(BorderFactory.createLineBorder(Color.lightGray, 3));
				//selectedCard.setBorder(null);
			}
			selectedCard = ((Card) e.getSource());
			selectedCard.setBorder(BorderFactory.createLineBorder(Color.GREEN, 3));
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

	private class submitButton implements ActionListener 
	{
		@Override
		public void actionPerformed(ActionEvent e) 
		{
			System.out.println(selectedCard.getSuit() + selectedCard.getValue());
			for (int i = 0; i < hand.size(); i++) 
			{
				if (selectedCard.getSuit() == hand.get(i).getSuit() && selectedCard.getValue() == hand.get(i).getValue()) 
				{
					handArea.remove(hand.remove(i));
				}
			}
			String cardStream = "Played " + " " + playerID + " " + selectedCard.getSuit() + " "
					+ selectedCard.getValue();
			System.out.println(cardStream);
			out.println(cardStream);
			out.flush();
			submit.setEnabled(false);
		}
	}

	public ImageIcon ScaledImage(Image i) 
	{
		Image imagetemp = i;
		Image temp = imagetemp.getScaledInstance(contentPane.getWidth() / 8, (contentPane.getWidth() / 6) * (800 / 500),
				java.awt.Image.SCALE_SMOOTH);
		ImageIcon scaledImage = new ImageIcon(temp);
		return scaledImage;
	}

	public boolean isMyTurn(int playedPerson) 
	{
		playedPerson++;
		return playedPerson % 3 == playerID;
	}

	public void run() 
	{
		try 
		{
			// Creates the input stream to read in the output from the server
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

			// Uses StringTokenizer so that it is easy to traverse the String coming in
			StringTokenizer st = new StringTokenizer(in.readLine());
			
			this.setVisible(true);

			// The first thing coming in is going to be the playersID and the names of the players (in order)
			playerID = Integer.parseInt(st.nextToken());
			playerLabels[0].setText(st.nextToken());
			playerLabels[1].setText(st.nextToken());
			playerLabels[2].setText(st.nextToken());
			myTurn = (playerID == 0);
			
			setRearCards();
			setHand(in.readLine());// Parses the string given into Card objects and puts it in the ArrayList hand
			
			loadingScreen.setVisible(false);

			while (true) 
			{
				st = new StringTokenizer(in.readLine());
				String switchToken = st.nextToken();
				switch (switchToken) 
				{
				case "Played":
					// set the icon in the appropriate player box to reflect the card that they showed
					int player = Integer.parseInt(st.nextToken());
					String suit = st.nextToken();
					int value = Integer.parseInt(st.nextToken());
					System.out.println("Got the played card");
					Card playedCard = new Card(suit, value);
					ImageIcon temp = ScaledImage(playedCard.getImage().getImage());
					cardLabels[player].setIcon(temp);
					myTurn = isMyTurn(player);
					this.repaint();
					break;

				case "Winner":
					int gameWinnerName = Integer.parseInt(st.nextToken());
					this.notificationLabel.setText(playerLabels[gameWinnerName] + " wins the game!");
					this.notificationLabel.repaint();
					break;

				case "Round":
					int roundWinner = Integer.parseInt(st.nextToken());
					this.notificationLabel.setText(playerLabels[roundWinner].getText() + " won round " + totalRounds + "!");
					this.notificationLabel.repaint();
					myTurn = (roundWinner == playerID);
					setRearCards();
					totalRounds++;
					break;

				case "Exit":
					System.exit(0);
					break;
				default:
					break;
				}
			}
		} 
		catch (IOException e) // Catch any error(s)
		{
			e.printStackTrace(); // Print out the error(s)
		}
	}
}
