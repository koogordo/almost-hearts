package AppTools;

import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Game 
{
	String[] playerNames = new String[3];
	Socket[] playerSockets = new Socket[3];
	List<Card> cardsOfRound = new ArrayList<>();
	int[] roundsWon = new int[3];
	int[] totalScore = new int[3];
	int previousWinner;
	int turn;
	int totalRounds;
	
	public Game(String[] n, Socket[] s) 
	{
		playerNames = n;
		playerSockets = s;
	}
	public void roundWinner (ArrayList<Card> cardsPlayed)
	{	
		int playerWhoPlayedFirst  = previousWinner;
		for (int i = 0; i < cardsPlayed.size(); i++)
		{
			if ((cardsPlayed.get(i).getSuit() == cardsPlayed.get(playerWhoPlayedFirst).getSuit()) && (cardsPlayed.get(i).getValue() > cardsPlayed.get(previousWinner).getValue()))
			{
				previousWinner = i;
			}
		}
		turn = previousWinner;
		roundsWon[previousWinner]++;
		totalScore[previousWinner] += cardsPlayed.get(previousWinner).getValue();
		totalRounds++;
		checkEndGame();
	}
	
	public int gameWinner() 
	{
		int WinCount = roundsWon[0];
		int gWinner = 0;
		for(int i = 0; i < roundsWon.length; i++) 
		{
			if(roundsWon[i] > WinCount) 
			{
				gWinner = i;
			}
			else if(roundsWon[i] == WinCount) 
			{
				if(totalScore[i] > totalScore[gWinner]) 
				{
					gWinner = i;
				}
			}	
		}
		return gWinner;
	}
	public void checkEndGame() 
	{
		if(totalRounds == 17) 
		{
			String WinnerName = playerNames[gameWinner()];
			//We will have an array of the winners names
			// P1 = [0], P2 [1] and P3 [2]
			// prompt the users with the name of the winner
			//end game or whatever
		}
	}
}
