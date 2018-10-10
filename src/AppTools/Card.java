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
	private String suit;
	private int value;
	private String imagePath;
	private ImageIcon cardImage;
	public Card(String s, int v) 
	{	
		suit = s;
		value = v;	
		imagePath = "/cardImages/"+ value + suit + ".png";
		cardImage = new ImageIcon(this.getClass().getResource(imagePath));
	}
	public String getSuit() // getSuit Method the returns the suit
	{
		return suit;
	}
	public int getValue() // getValue Method that returns the value
	{
		return value;
	}
	public ImageIcon getImage() {
		return cardImage;
	}
	public String toString() {
		return this.suit + " " + this.value;
	}
}

