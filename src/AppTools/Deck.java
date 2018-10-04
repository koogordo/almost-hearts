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
			deck.add(new Card("h",i));
			deck.add(new Card("c",i));
			deck.add(new Card("d",i));
			deck.add(new Card("s",i));
		}
	}

	public void Shuffle() 
	{
		Collections.shuffle(deck);
	}
}