package AppTools;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
/*
 * This class holds the shared resources that the ServerPlayer threads use.
 * It also is how the game is controlled
 */
public class Game 
{
	String[] playerNames = new String[3];
	Socket[] playerSockets = new Socket[3];
	PrintStream[] out = new PrintStream[3];
	Card[]cardsOfRound = new Card[3];
	int[] roundsWon = new int[3];
	int[] totalScore = new int[3];
	int previousWinner;
	int playerTurn;
	int numOfTurns;
	int totalRounds;
	
	public Game(String[] n, Socket[] s) 
	{
		playerNames = n;
		playerSockets = s;
		for(int i = 0; i < 3; i++) //sets up the output streams for all the players and sends them the names of all the players
		{
			try
			{
				out[i] = new PrintStream(playerSockets[i].getOutputStream());
				out[i].println(i + " " + playerNames[0] + " " + playerNames[1] + " " + playerNames[2]);
				out[i].flush();
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		Deck deck = new Deck();
		for(int i = 0; i < Math.random() * 20; i++)//shuffles deck random amount of times
		{
			deck.Shuffle();
		}
		for(int i = 0; i < 3; i++)//distributes cards to players
		{
			String initialCards = "";
			for(int k = 0; k < 17; k++)
			{
				Card chosenCard = deck.deck.remove(0);
				initialCards += chosenCard.Suit + chosenCard.value + " ";
			}
			out[i].println(initialCards);
			out[i].flush();
		}
	}
	public void turnPlayed(int player, String suit, int value) {
		if(player == playerTurn)
		{
			cardsOfRound[playerTurn] = new Card(suit, value);
			Broadcast("Played" + " " + playerTurn + " " + cardsOfRound[playerTurn].toString());
			
			++playerTurn;
			playerTurn %= 3;//wrap back to 0 if it hits 3
			++numOfTurns;
			if(numOfTurns == 3)
			{
				numOfTurns = 0;
				roundWinner();
			}
		}
	}
	public void roundWinner ()
	{	
		int playerWhoPlayedFirst = previousWinner;
		for (int i = 0; i < cardsOfRound.length; i++)
		{
			if ((cardsOfRound[i].getSuit().equals(cardsOfRound[playerWhoPlayedFirst].getSuit())) && (cardsOfRound[i].getValue() > cardsOfRound[previousWinner].getValue()))
			{
				previousWinner = i;
			}
		}
		Broadcast("Round" + " " + playerNames[previousWinner]);
		
		playerTurn = previousWinner;
		roundsWon[previousWinner]++;
		totalScore[previousWinner] += cardsOfRound[previousWinner].getValue();
		totalRounds++;
		checkEndGame(previousWinner);
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
	public void checkEndGame(int winner) 
	{
		if(totalRounds == 17) 
		{
			String winnerName = playerNames[winner];
			Broadcast("Winner" + " " + winnerName); 
			//We will have an array of the winners names
			// P1 = [0], P2 [1] and P3 [2]
			// prompt the users with the name of the winner
			//end game or whatever
		}
	}
	public void Broadcast(String broadcastString)
	{
		for(int i = 0; i < 3; i++) {
			out[i].println(broadcastString);
		}
	}
}
