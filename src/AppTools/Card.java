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
	ImageIcon cardImage;
	public Card(String s, int v) 
	{	
		Suit = s;
		value = v;	
		cardImage = new ImageIcon("cardImages/"+ value + Suit + ".png");
	}
	public String getSuit() 
	{
		return Suit;
	}
	public int getValue() 
	{
		return value;
	}
}

