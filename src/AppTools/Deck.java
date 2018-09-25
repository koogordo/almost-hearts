package AppTools;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class Deck
{
	List<Card> deck = new ArrayList<>();
	
	public Deck() 
	{	
		for(int i=2; i < 15; i++) 
		{
			Card temp = new Card("H",i);
			deck.add(temp);
		}
		
		for(int i=2; i < 15; i++) 
		{
			Card temp = new Card("C",i);
			deck.add(temp);
		}
		
		for(int i=2; i < 15; i++) 
		{
			Card temp = new Card("D",i);
			deck.add(temp);
		}
		
		for(int i=2; i < 15; i++) 
		{
			Card temp = new Card("S",i);
			deck.add(temp);
		}
	}
	
	public void Shuffle() 
	{
	Collections.shuffle(deck);
	}
}
