package Views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import AppTools.Card;

import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.StringTokenizer;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Component;

/**
 * 
 * 
 */

public class GameboardGui extends JFrame implements Runnable
{
	JLabel[] playerLabels;
	ArrayList<Card> hand = new ArrayList<>();
	JPanel notificationPanel;
	JLabel notificationLabel;
	JPanel handArea;
	JButton submit;
	private JPanel contentPane;
	Card selectedCard;
	Socket socket;
	BufferedWriter out;
	BufferedReader in;
	int playerID;
	JLabel[] cardLabels = new JLabel[3];
	JPanel[] playerPanels = new JPanel[3];
	/**
	 * Launch the application.
	 */
	
	/**
	 * Create the frame.
	 */
	public GameboardGui(Socket socket)
	{
		this.socket = socket;
		playerLabels = new JLabel[3];
		try 
		{
			out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
		} 
		catch (IOException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1000,800);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		handArea = new JPanel();
		FlowLayout flowLayout = (FlowLayout) handArea.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		notificationPanel =  new JPanel();
		notificationPanel.setPreferredSize(new Dimension(this.getWidth(), this.getHeight()/10));

		JPanel panel = new JPanel();
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
		contentPane.add(notificationPanel);
		notificationPanel.setLayout(new BoxLayout(notificationPanel, BoxLayout.Y_AXIS));
		notificationLabel = new JLabel("notification label");
		notificationLabel.setHorizontalAlignment(SwingConstants.CENTER);
		notificationLabel.setFont(new Font("Serif", Font.PLAIN, 30));
		notificationPanel.add(notificationLabel);
		contentPane.add(panel);
		contentPane.add(handArea);
		panel.setLayout(new GridLayout(0, 3, 0, 0));
		panel.setPreferredSize(new Dimension(this.getWidth(), this.getHeight()/8));

		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		//panel_1.setPreferredSize(new Dimension(100, 100));
		panel.add(panel_1);
		playerLabels[0] = new JLabel("Player 1");
		playerLabels[0].setVerticalAlignment(SwingConstants.TOP);
		playerLabels[0].setHorizontalAlignment(SwingConstants.CENTER);
		cardLabels[0] = new JLabel();
		panel_1.setLayout(new BorderLayout(0, 0));
		panel_1.add(playerLabels[0], BorderLayout.NORTH);
		panel_1.add(cardLabels[0], BorderLayout.CENTER);

		//Player two gui logic
		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		//panel_2.setPreferredSize(new Dimension(100, 150));
		panel.add(panel_2);
		playerLabels[1] = new JLabel("Player 2");
		playerLabels[1].setHorizontalAlignment(SwingConstants.CENTER);
		playerLabels[1].setVerticalAlignment(SwingConstants.TOP);
		cardLabels[1] = new JLabel();
		panel_2.setLayout(new BorderLayout(0, 0));
		panel_2.add(playerLabels[1], BorderLayout.NORTH);
		panel_2.add(cardLabels[1], BorderLayout.CENTER);

		//Player 3 logic
		JPanel panel_3 = new JPanel();
		panel_3.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		//panel_3.setPreferredSize(new Dimension(100, 150));
		panel.add(panel_3);
		playerLabels[2] = new JLabel("Player 3");
		playerLabels[2].setVerticalAlignment(SwingConstants.TOP);
		playerLabels[2].setHorizontalAlignment(SwingConstants.CENTER);
		cardLabels[2] = new JLabel();
		panel_3.setLayout(new BorderLayout(0, 0));
		panel_3.add(playerLabels[2], BorderLayout.NORTH);
		panel_3.add(cardLabels[2], BorderLayout.CENTER);
		// adding player panels to array to make them easier to reference using playerID
		playerPanels[0] = panel_1;
		playerPanels[1] = panel_2;
		playerPanels[2] = panel_3;
		this.setVisible(true);
	}
	
	public void setRearCards() {
		
		ImageIcon tempIcon = new ImageIcon(this.getClass().getResource("/cardImages/rearOfCard.png"));
		ImageIcon temp = ScaledImage(tempIcon.getImage());

		for(int i = 0; i < 3; i++) {
		
			cardLabels[i].setIcon(temp);
			cardLabels[i].setHorizontalAlignment(SwingConstants.CENTER);
			
		}
		this.repaint();
		
	}
	public void setHand(String cards) {
		cards = cards.toLowerCase();

		StringTokenizer st = new StringTokenizer(cards);
		//st.nextToken();
		System.out.println(cards);
		for(int i = 0; i < 17; i++) 
		{
			String suit = st.nextToken();
			int number = Integer.parseInt(st.nextToken());
			hand.add(new Card(suit, number));
		}
		sortHand(hand);
		for(int i = 0; i < 17; i++) 
		{
			hand.get(i).setSize(contentPane.getWidth()/10, (contentPane.getWidth()/10)*(800/500));
			Image imagetemp = hand.get(i).getImage().getImage();
			Image temp = imagetemp.getScaledInstance(contentPane.getWidth()/10, (contentPane.getWidth()/8)*(800/500),java.awt.Image.SCALE_SMOOTH);
			ImageIcon tempIcon = new ImageIcon(temp);
			hand.get(i).setIcon(tempIcon);
			handArea.add(hand.get(i));
			hand.get(i).addMouseListener(new selectCard());
	
		}
		
		submit = new JButton("Submit");
		submit.addActionListener(new submitButton());
		submit.setEnabled(false);
		handArea.add(submit);
	}
	
	public void setPlayerNames(String names) 
	{
		StringTokenizer st = new StringTokenizer(names);
		st.nextToken();
		
		playerLabels[0].setText(st.nextToken());
		playerLabels[1].setText(st.nextToken());
		playerLabels[2].setText(st.nextToken());
	}
	
	public static void sortHand(ArrayList<Card> cards) 
	{
		String suit = "c";
		for(int i = 0; i < cards.size() - 1;) 
		{
			if (!suit.equals("")) 
			{
				for(int k = i; k < cards.size(); k++) 
				{
					if(cards.get(k).getSuit().equals(suit)) 
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
		for(int i = 0; i < cards.size(); i++)
		{
			for(int k = i; k < cards.size(); k++){
				if(cards.get(k).getValue() > cards.get(i).getValue() && cards.get(i).getSuit().equals(cards.get(k).getSuit()))
				{
					Card temp = cards.get(i);
					cards.set(i, cards.get(k));
					cards.set(k, temp);
				}
			}
		}
	}
	public static String nextSuit(String suit) 
	{
		if(suit.equals("c")) return "d";
		if(suit.equals("d")) return "h";
		if(suit.equals("h")) return "s";
		if(suit.equals("s")) return "";
		return "";	
	}
	private class selectCard implements MouseListener
	{

		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			if(selectedCard != null) {
				selectedCard.setBorder(null);
			}
			selectedCard = ((Card) e.getSource());
			selectedCard.setBorder(BorderFactory.createLineBorder(Color.GREEN, 3));
			submit.setEnabled(true);
		}

		@Override
		public void mouseEntered(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mousePressed(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseReleased(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}
	}
	private class submitButton implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e) 
		{
			for(int i = 0; i < hand.size(); i++) 
			{
				if(selectedCard.equals(hand.get(i))) 
				{
					hand.remove(i);
				}
			}
			String cardStream = "Played " + " " + playerID + " " + selectedCard.getSuit() + " " + selectedCard.getValue();
			try {
				out.write(cardStream);
				out.flush();
			} 
			catch (IOException e1) 
			{
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			submit.setEnabled(false);
		}
		
	}
	
	public ImageIcon ScaledImage(Image i) {
		Image imagetemp = i;
		Image temp = imagetemp.getScaledInstance(contentPane.getWidth()/8, (contentPane.getWidth()/6)*(800/500),java.awt.Image.SCALE_SMOOTH);
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
		// TODO Auto-generated method stub
		try 
		{
			//creates the input stream to read in the output from the server
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			
			//uses StringTokenizer so that it is easy to traverse the String coming in
			StringTokenizer st = new StringTokenizer(in.readLine());
			
			//The first thing coming in is going to be the playersID and the names of the players (in order)
			playerID = Integer.parseInt(st.nextToken());
			playerLabels[0].setText(st.nextToken());
			playerLabels[1].setText(st.nextToken());
			playerLabels[2].setText(st.nextToken());
			
	
			//setHand("c 3 d 3 c 13 d 5 c 6 c 12 c 11 c 8 d 10 s 3 s 14 h 2 s 10 s 8 d 11 d 6 d 13");
			setRearCards();
			setHand(in.readLine());//Parses the string given into Card objects and puts it in the ArrayList hand

			this.setVisible(true);
			while (true) 
			{
				st = new StringTokenizer(in.readLine());
				String switchToken = st.nextToken();
				switch (switchToken)
				{
				case "Played":
					//set the icon in the appropriate player box to reflect the card that they showed
					int player = Integer.parseInt(st.nextToken());
					String suit = st.nextToken();
					int value = Integer.parseInt(st.nextToken());
					Card playedCard = new Card(suit,value);
					playerPanels[player].add(playedCard);
					playerPanels[player].repaint();
					submit.setEnabled(isMyTurn(player));
					break;
					
				case "Winner":
					String gameWinnerName = st.nextToken();
					this.notificationLabel.setText(gameWinnerName + " Wins the game!"); 
					this.notificationLabel.repaint();
					break;
					
				case "Round":
					int roundWinner = Integer.parseInt(st.nextToken());
					this.notificationLabel.setText(playerLabels[roundWinner].getText() + " Wins the round!");
					this.notificationLabel.repaint();
					submit.setEnabled(roundWinner == playerID);
					setRearCards();
					break;
					
				case "Exit":
					System.exit(0);
					break;				
				default:
					break;
				}
				
			} 
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
