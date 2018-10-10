package AppTools;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* 
 * The purpose of this class is to create the cards and holds them in an ArrayList which
 * has a function to shuffle the cards.
 * Also includes a function for drawing a card
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
	public Card drawOneCard() {
		return deck.remove(0);
	}
}