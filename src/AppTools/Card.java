package AppTools;

public class Card 
{	
	String Suit;
	int value;
	
	public Card() 
	{
		Suit = "";
		value = 0;	
	}
	
	public Card(String s, int v) 
	{	
		Suit = s;
		value = v;	
	}
	public String getSuit() {
		return Suit;
	}
	public int getValue() {
		return value;
	}
}

