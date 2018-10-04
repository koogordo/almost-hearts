package AppTools;

import javax.swing.ImageIcon;
import javax.swing.JButton;
/**
 * 
 * 
 */
public class Card extends JButton
{	
	String Suit;
	int value;
	String imagePath;
	ImageIcon cardImage;
	public Card(String s, int v) 
	{	
		Suit = s;
		value = v;	
		imagePath = "cardImages/"+ value + Suit + ".png";
		cardImage = new ImageIcon(imagePath);
	}
	public String getSuit() 
	{
		return Suit;
	}
	public int getValue() 
	{
		return value;
	}
	
	public String toString() {
		return this.Suit + " " + this.value;
	}
}

