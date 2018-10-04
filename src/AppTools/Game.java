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
	String[] playerNames = new String[3];   // Array to hold the player's names
	Socket[] playerSockets = new Socket[3]; // Array to hold the player's sockets
	PrintStream[] out = new PrintStream[3]; // Array to hold the Player's print streams
	Card[]cardsOfRound = new Card[3];       // Array to hold the cards of each round
	int[] roundsWon = new int[3];           // Array to hold the rounds won by each player
	int[] totalScore = new int[3];          // Array to hold the total score of each player
	int previousWinner; // Previous winner (i of PlayerNames array)
	int playerTurn;     // Which player's turn it is
	int numOfTurns;     // Number of Turns played in the game
	int totalRounds;    // total rounds 
	
	public Game(String[] n, Socket[] s) 
	{
		playerNames = n; // Player names (array of strings)
		playerSockets = s; // Player sockets (array of strings)
		for(int i = 0; i < 3; i++) //sets up the output streams for all the players and sends them the names of all the players
		{
			try
			{
				out[i] = new PrintStream(playerSockets[i].getOutputStream());
				out[i].println(i + " " + playerNames[0] + " " + playerNames[1] + " " + playerNames[2]);
				out[i].flush();
				
			} catch (IOException e) 
			{
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
				initialCards += chosenCard.Suit + " " + chosenCard.value + " ";
			}
			out[i].println(initialCards);
			out[i].flush();
		}
	}
	public void turnPlayed(int player, String suit, int value) {
		if(player == playerTurn)
		{
			cardsOfRound[playerTurn] = new Card(suit, value);
			
			++playerTurn; 
			playerTurn %= 3;//wrap back to 0 if it hits 3
			++numOfTurns;
			if(numOfTurns == 3)
			{
				numOfTurns = 0; // Setting the number of turns to 0
				roundWinner(); // Checking the round winner
			}
		}
	}
	public void roundWinner () // method to decide the round winner
	{	
		int playerWhoPlayedFirst = previousWinner; // Setting PlayerWhoPlayedFirst to previousWinner
		for (int i = 0; i < cardsOfRound.length; i++)
		{
			if ((cardsOfRound[i].getSuit().equals(cardsOfRound[playerWhoPlayedFirst].getSuit())) && (cardsOfRound[i].getValue() > cardsOfRound[previousWinner].getValue()))
			{
				previousWinner = i; // Setting the previousWinner to i
			}
		}
		playerTurn = previousWinner; // Setting the playerTurn to the previousWinner
		roundsWon[previousWinner]++; // Incrementing previousWinner in roundsWon
		totalScore[previousWinner] += cardsOfRound[previousWinner].getValue(); // Adding the value of the previous Winner in the CardsOfRound array to previousWinner of the totalScore array
		totalRounds++; // Increment totalRounds
		checkEndGame(); //Check to see if it is the end of the game
	}
	
	public int gameWinner() 
	{
		int WinCount = roundsWon[0]; // 
		int gWinner = 0;
		for(int i = 0; i < roundsWon.length; i++) 
		{
			if(roundsWon[i] > WinCount) // If the rounds won of player i is greater than WinCount
			{
				gWinner = i; // Set gWinner to i
			}
			else if(roundsWon[i] == WinCount) // Else if the rounds one of player i equals the Win Count
			{
				if(totalScore[i] > totalScore[gWinner]) // If player i's total score is higher than current gWinner's total score
				{
					gWinner = i; // Set the game winner to i
				}
			}	
		}
		return gWinner; // return the Game Winner
	}
	
	public void checkEndGame() // Method to check if it is the end of the game 
	{
		if(totalRounds == 17) // If the totalRounds = 17 the game is over
		{
			String WinnerName = playerNames[gameWinner()]; // Setting WinnerName to the name of the game Winner
			//We will have an array of the winners names
			// P1 = [0], P2 [1] and P3 [2]
			// prompt the users with the name of the winner
			//end game or whatever
		}
	}
	public void broadcast(String broadcastString) // Method to broadcast a string
	{
		for(int i = 0; i < 3; i++) 
		{
			out[i].println(broadcastString); // printing out the broadcastString
		}
	}
}
