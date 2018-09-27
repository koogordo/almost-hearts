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
	
	// The winner will be first in the next round
	// The 2nd and 3rd turn each round will be decided on the total score of cards of the two losing players
	List<Card> sortedCards = new ArrayList<>();
	public ArrayList SortScores (ArrayList cardsArray)
	{
		int winner = 0; // or maybe it should be a string -eq of that player?
		if (cardsArray[0] > cardsArray[1])
		{	
			if (cardsArray[0].getValue > cardsArray[2].getValue)
			{
				sortedCards[0] = cardsArray[0];
				
				if (cardsArray[1].getValue > cardsArray[2].getValue)
				{
					sortedCards[1] = cardsArray[1];
				}
				else
				{
					sortedCards[1] = cardsArray[2];
				}
			}
			else
			{
				sortedCards[0] = cardsArray[2];
				sortedCards[1] = cardsArray[0];
				sortedCards[2] = cardsArray[1];
				
			}
		}
		else if (cardsArray[1].getValue > cardsArray[2].getValue)
		{
			sortedCards[0] = cardsArray[1];
			if (cardsArray[0].getValue > cardsArray[2].getValue)
			{
				sortedCards[0] = cardsArray[1];
			}
			else
			{
				sortedCards[2] = cardsArray[2];
			}
		}
		else
		{
			sortedCards[0] = cardsArray[2];
			if (cardsArray[0].getValue > cardsArray[1].getValue)
			{
				sortedCards[0] = cardsArray[1];
			}
			else
			{
				sortedCards[1] = cardsArray[2];
			}
		}
		return sortedCards;
	}
	
	List<Card> compareCards = new ArrayList<>();
	public void roundWinner (ArrayList compareCard)
	{
		String winningSuit = compareCard[0].getSuit;
		SortScores(compareCard);
		// sort through sorted cards.getSuit to find the highest card with the correct suit
		sortedCards.getSuit
		
	}
	
	
	
	
	
	
	
	
	
	{
		String winningSuit = compareCard[0].getSuit
		
		if(compareCard[1].getSuit == winningSuit && compareCard[2].getSuit == winningSuit)
		{
			if(compareCard[0].getValue > compareCard[1].getValue)
			{
				if(compareCard[0].getValue > compareCard[2].getValue)
				{
					winner = compareCard[0];
				}
				else
				{
					winner = compareCard[2];
				}
			}
			else if(compareCard[1].getValue > compareCard[2].getValue) 
			{
				winner = compareCard[1];
			}
			else 
			{
				winner = compareCard[2];
			}
		}
		if(compareCard[2].getSuit == winningSuit)
		{
			
		}
	}
	
}
