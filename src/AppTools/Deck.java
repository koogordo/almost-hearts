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
	List<Card> deck = new ArrayList<>(); // Initializing deck to an arrayList
	
	public Deck() // Method to initialize the deck
	{	
		for(int i=2; i < 15; i++) 
		{
			deck.add(new Card("h",i)); // Initializing hearts cards
			deck.add(new Card("c",i)); // Initializing clubs cards
			deck.add(new Card("d",i)); // Initializing diamonds cards
			deck.add(new Card("s",i)); // Initializing spades cards
		}
	}

	public void Shuffle() // Method to shuffle 
	{
		Collections.shuffle(deck);
	}
	public Card drawOneCard() {
		return deck.remove(0);
	}
}