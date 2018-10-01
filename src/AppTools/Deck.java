package AppTools;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/** 
 * 
 * 
 */

public class Deck
{
	List<Card> deck = new ArrayList<>();
	
	public Deck() 
	{	
		for(int i=2; i < 15; i++) 
		{
			deck.add(new Card("H",i));
			deck.add(new Card("C",i));
			deck.add(new Card("D",i));
			deck.add(new Card("S",i));
		}
	}

	public void Shuffle() 
	{
		Collections.shuffle(deck);
	}
}