package AppTools;

import javax.swing.ImageIcon;
/**
 * 
 * 
 */
public class Card 
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

