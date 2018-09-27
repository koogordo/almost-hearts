package AppTools;

import java.util.ArrayList;
import java.util.List;

public class game 
{
	int playerOneRoundWon = 0;
	int playerOneTotalScore = 0;
	
	
	
	// The winner will be first in the next round
	// 
	List<Card> sortedCards = new ArrayList<>();
	
	//The three cards sent to the server will be put in an array and added to the array's
	//the array goes into this function, which returns the winner and adds their score to the player's win 
	//The server then displays the winner to the users, initiates the new round and updates the score board
	
		public String Winner (ArrayList<Card> cardsPlayed)
		{	
			Card roundWinner = cardsPlayed.get(0);
			for (int i = 0; i < cardsPlayed.length; i++)
			{
				if ((cardsPlayed.get(i).getSuit() == cardsPlayed.get(0).getSuit()) && (cardsPlayed.get(i).getValue() > roundWinner.getValue()))
				{
					roundWinner = cardsPlayed[i];
				}
			}
			
			return roundWinner;
		}	
}
