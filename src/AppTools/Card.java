package AppTools;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
/**
 * 
 * 
 */
public class Card extends JLabel
{	
	private String suit; // Initialize suit of type string
	private int value; // Initialize value of type int
	private String imagePath; // Initialize imagePath of type String
	private ImageIcon cardImage; // Initialize cardImage of type ImageIcon
	
	public Card(String s, int v) // Card Method that take in an int and String
	{	
		suit = s; // Set suit to s
		value = v;	// Set value to v
		imagePath = "/cardImages/"+ value + suit + ".png"; // Setting the card image path for the card
		cardImage = new ImageIcon(this.getClass().getResource(imagePath)); // Setting the card's image to the image of the above path
	}
	public String getSuit() // getSuit Method the returns the card's suit
	{
		return suit;
	}
	public int getValue() // getValue Method that returns the card's value
	{
		return value;
	}
	public ImageIcon getImage() // imageIcon Method that returns the card's image
	{
		return cardImage;
	}
	public String toString() 
	{
		return this.suit + " " + this.value;
	}
}

