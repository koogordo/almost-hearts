package AppTools;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
/**
 * 
 * 
 */
public class Card extends JLabel
{	
	String Suit; // Initializing the Suit of the cards
	int value; // Initializing the value of the cards
	ImageIcon cardImage; // Initializing the image of the cards
	public Card(String s, int v)  // Method to initialize the Cards in the Deck
	{	
		Suit = s;  // Setting Suit to s
		value = v; // Setting value to v
		cardImage = new ImageIcon("cardImages/"+ value + Suit + ".png"); // Setting cardImage's ImageIcon to the png image
	}
	public String getSuit() // getSuit Method the returns the suit
	{
		return Suit;
	}
	public int getValue() // getValue Method that returns the value
	{
		return value;
	}
	public ImageIcon getImage() {
		return cardImage;
	}
}

