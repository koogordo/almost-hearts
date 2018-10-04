package Views;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import AppTools.Card;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

/**
 * 
 * 
 */

public class GameboardGui extends JFrame implements Runnable
{
	JLabel lblPlayer_1;
	JLabel lblPlayer_2;
	JLabel lblPlayer_3;
	ArrayList<Card> hand = new ArrayList<>();
	JPanel handArea;
	JButton submit;
	private JPanel contentPane;
	Card selectedCard;
	Socket socket;
	BufferedWriter out;
	BufferedReader in;
	int playerID;
	/**
	 * Launch the application.
	 */
	
	/**
	 * Create the frame.
	 */
	public GameboardGui(Socket socket)
	{
		handArea = new JPanel();
		this.socket = socket;
		try {
			out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1000,800);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new GridLayout(2, 0, 0, 0));

		JPanel panel = new JPanel();
		contentPane.add(panel);
		panel.setLayout(new GridLayout(0, 3, 0, 0));

		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panel_1.setPreferredSize(new Dimension(100, 200));
		panel.add(panel_1);
		panel_1.setLayout(new GridLayout(2, 3, 0, 0));
		lblPlayer_1 = new JLabel("Player 1");
		lblPlayer_1.setVerticalAlignment(SwingConstants.TOP);
		lblPlayer_1.setHorizontalAlignment(SwingConstants.CENTER);
		ImageIcon unsizedIcon1 = new ImageIcon("cardImages/rearOfCard.png");
		ImageIcon playerOneCard = new ImageIcon(unsizedIcon1.getImage().getScaledInstance(100, 200, java.awt.Image.SCALE_DEFAULT));
		JLabel p1ImageHolder = new JLabel();
		p1ImageHolder.setIcon(playerOneCard);
		panel_1.add(lblPlayer_1);
		panel_1.add(p1ImageHolder);

		//Player two gui logic
		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panel_2.setPreferredSize(new Dimension(100, 200));
		panel.add(panel_2);
		panel_2.setLayout(new GridLayout(2, 3, 0, 5));
		lblPlayer_2 = new JLabel("Player 2");
		lblPlayer_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblPlayer_2.setVerticalAlignment(SwingConstants.TOP);
		ImageIcon unsizedIcon2 = new ImageIcon("cardImages/rearOfCard.png");
		ImageIcon playerTwoCard = new ImageIcon(unsizedIcon2.getImage().getScaledInstance(100, 200, java.awt.Image.SCALE_DEFAULT));
		JLabel p2ImageHolder = new JLabel();
		p2ImageHolder.setIcon(playerTwoCard);
		panel_2.add(lblPlayer_2);
		panel_2.add(p2ImageHolder);

		//Player 3 logic
		JPanel panel_3 = new JPanel();
		panel_3.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panel_3.setPreferredSize(new Dimension(100, 200));
		panel.add(panel_3);
		panel_3.setLayout(new GridLayout(2, 3, 0, 0));
		lblPlayer_3 = new JLabel("Player 3");
		lblPlayer_3.setVerticalAlignment(SwingConstants.TOP);
		lblPlayer_3.setHorizontalAlignment(SwingConstants.CENTER);
		ImageIcon unsizedIcon3 = new ImageIcon("cardImages/rearOfCard.png");
		ImageIcon playerThreeCard = new ImageIcon(unsizedIcon3.getImage().getScaledInstance(100, 200, java.awt.Image.SCALE_DEFAULT));
		JLabel p3ImageHolder = new JLabel();
		p3ImageHolder.setIcon(playerThreeCard);
		panel_3.add(lblPlayer_3);
		panel_3.add(p3ImageHolder);
		
		
		this.pack();
		this.setVisible(true);
		
	}
	public void setHand(String cards) {
		StringTokenizer st = new StringTokenizer(cards);
		st.nextToken();
		
		for(int i = 0; i < 17; i++) {
			String card = st.nextToken();
			byte[] cardBytes = card.getBytes();
			String suit = cardBytes[0] + "";
			int number = cardBytes[1];
			
			if(number == 1) {
				number = 10 + cardBytes[2];
			}
			hand.add(new Card(suit, number));
		}
		sortHand(hand);
		for(int i = 0; i < 17; i++) {
			handArea.add(hand.get(i));
			hand.get(i).addActionListener(new selectCard());
		}
		
		submit = new JButton("Submit");
		submit.addActionListener(new submitButton());
		submit.setEnabled(false);
		handArea.add(submit);
	}
	public void setPlayerNames(String names) {
		StringTokenizer st = new StringTokenizer(names);
		st.nextToken();
		
		lblPlayer_1.setText(st.nextToken());
		lblPlayer_2.setText(st.nextToken());
		lblPlayer_3.setText(st.nextToken());
	}
	public static void sortHand(ArrayList<Card> cards) {
		String suit = "c";
		for(int i = 0; i < cards.size() - 1;) {
			if (!suit.equals("")) {
				for(int k = i; k < cards.size(); k++) {
					if(cards.get(k).getSuit().equals(suit)) {
						Card temp = cards.get(i);
						cards.set(i, cards.get(k));
						cards.set(k, temp);
						i++;
					}
				}
			}
			suit = nextSuit(suit);
		}
		
		for(int i = 1; i < cards.size(); i++) {
			if(cards.get(i-1).getValue() > cards.get(i).getValue() && cards.get(i-1).getSuit().equals(cards.get(i).getSuit())) {
				Card temp = cards.get(i-1);
				cards.set(i-1, cards.get(i));
				cards.set(i, temp);
			}
		}
	}
	public static String nextSuit(String suit) {
		if(suit.equals("c")) return "d";
		if(suit.equals("d")) return "h";
		if(suit.equals("h")) return "s";
		if(suit.equals("s")) return "";
		return "";
		
	}
	private class selectCard implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			selectedCard = ((Card) e.getSource());
			submit.setEnabled(true);
		}
		
	}
	private class submitButton implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			
			for(int i = 0; i < hand.size(); i++) {
				if(selectedCard.equals(hand.get(i))) {
					hand.remove(i);
				}
			}
			String cardStream = "Played " + " " + playerID + " " + selectedCard.getSuit() + " " + selectedCard.getValue();
			try {
				out.write(cardStream);
				out.flush();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			
			submit.setEnabled(false);
		}
		
	}
	public void run() {
		// TODO Auto-generated method stub
		try {
			//creates the input stream to read in the output from the server
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			
			//uses StringTokenizer so that it is easy to traverse the String coming in
			StringTokenizer st = new StringTokenizer(in.readLine());
			
			//The first thing coming in is going to be the playersID and the names of the players (in order)
			playerID = Integer.parseInt(st.nextToken());
			lblPlayer_1.setText(st.nextToken());
			lblPlayer_2.setText(st.nextToken());
			lblPlayer_3.setText(st.nextToken());
			
			setHand(in.readLine());//Parses the string given into Card objects and puts it in the ArrayList hand
			this.setVisible(true);
			while (true) {
				st = new StringTokenizer(in.readLine());
				String switchToken = st.nextToken();
				switch (switchToken)
				{
				case "Played":
					
					break;
				case "Exit":
					System.exit(0);
					break;
				
				}
				
			}
			
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
