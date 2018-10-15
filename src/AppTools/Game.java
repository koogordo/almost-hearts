package AppTools;

import java.io.*;
import java.net.Socket;
/*
 * This class holds the shared resources that the ServerPlayer threads use.
 * It also is how the game is controlled with methods called from ServerPlayer to
 * which simulates the inputs from the player.
 * 
 * The main purpose of this class is to get inputs called from ServerPlayer, then
 * execute whatever is needed for that state of the game, then broadcast to all players
 * the update that just occurred.
 * EX: 
 * 		ServerPlayer receives "Played 1 h 5" from GameboardGui on client side, ServerPlayer
 * 		calls game.turnPlayed(1, h, 5) which the game class will verify that it is the
 * 		correct persons turn, if it is the correct persons turn it will simulate that turn
 * 		then broadcast "Played 1 h 5" to every single player.
 */
public class Game {
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
	
	/*
	 * This Game constructor is called once all of the sockets/players have connect and are ready
	 * to receive input.
	 * 
	 * This is the main constructor for the Game class (shared resource)
	 * The 2 parameters is the array of names for the players, and the array of sockets which
	 * is the stream for the players. And is called from the Server class.
	 * 
	 * Then creates output streams for the different players by using the sockets and sends
	 * out the current player id along with the name of all of the players in order.
	 * 
	 * After that it creates a deck of 52 cards and shuffles it randomly between 1 and 20
	 * different times. Once it is done shuffling it then sends out the cards individually
	 * to each players hand.
	 */
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
				
			} catch (IOException e) { // Catch any error(s)
				e.printStackTrace(); // Print out said error(s)
			}
		}
		distributeCards();
	}
	public void distributeCards() {
		Deck deck = new Deck(); // Set Deck to a new deck
		for(int i = 0; i < Math.random() * 20; i++) { //shuffles deck random amount of times
			deck.Shuffle(); // Shuffle the deck
		}
		for(int i = 0; i < 3; i++) { //distributes cards to players
			String initialCards = "";
			for(int k = 0; k < 17; k++) {
				initialCards += deck.drawOneCard().toString() + " ";
			}
			out[i].println(initialCards);
			out[i].flush();
		}
	}
	/*
	 * Called by the ServerPlayer.
	 * Parameters are the player id of who called it, along with the card values.
	 * 
	 * The method first validates that it is indeed the correct persons turn, once it is
	 * then add a card object with the parameters to the cardsOfRound array.
	 * It broadcasts the play made to all of the clients then increment the turn which
	 * wraps back to 0 once it hits 3.
	 * Then increment the number of turns for the round counter by 1, if the total number
	 * of turns for the round is 3, then reset the counter and execute the roundWinner method.
	 */
	public void turnPlayed(int player, String suit, int value) {
		if(player == playerTurn) { // If the player equal's the player turn
			cardsOfRound[playerTurn] = new Card(suit, value);
			Broadcast("Played" + " " + playerTurn + " " + cardsOfRound[playerTurn].toString());
			
			++playerTurn; // Increment the player's turn
			playerTurn %= 3;//wrap back to 0 if it hits 3
			++numOfTurns; // Increment the number of turns
			if(numOfTurns == 3) { // If the number of turns is 3:
				numOfTurns = 0; // Setting the number of turns to 0
				roundWinner(); // Checking the round winner
			}
		}
	}
	/*
	 * Called by the turnPlayed method which takes no parameters.
	 * It checks by looping cardsOfRound array along with who played first and broadcasts the
	 * winner of the round to all of the players. Then sets the winner of the round as the 
	 * next person to start. Then increments the number of rounds that player has won, and the score
	 * for that person.
	 * 
	 */
	public void roundWinner () { // method to decide the round winner
		int playerWhoPlayedFirst = previousWinner; // Setting PlayerWhoPlayedFirst to previousWinner
		for (int i = 0; i < cardsOfRound.length; i++) {
			if ((cardsOfRound[i].getSuit().equals(cardsOfRound[playerWhoPlayedFirst].getSuit())) && (cardsOfRound[i].getValue() > cardsOfRound[previousWinner].getValue())) {
				previousWinner = i; // Setting the previousWinner to i
			}
		}
		
		playerTurn = previousWinner; // set first player to the winner of the previous round
		
		Broadcast("Round " + playerTurn + " " + playerNames[previousWinner]);
	
		roundsWon[previousWinner]++; // increment the roundsWon count of the previous winner
		totalScore[previousWinner] += cardsOfRound[previousWinner].getValue(); // add the value of the winning card to the total score of the previous winner
		totalRounds++; // Increment the totalRounds count
		checkEndGame(); // Check if it is the end of the game
	}
	/*
	 * Is called by checkEndGame and return the player id of the person who won
	 * Loops through the roundsWon array which contains the number of rounds each player
	 * has won and returns the id of the player who won.
	 */
	public int gameWinner() {
		int WinCount = roundsWon[0];
		int gWinner = 0;
		for(int i = 0; i < roundsWon.length; i++) {
			if(roundsWon[i] > WinCount) { // If the rounds won of player i is greater than WinCount
				gWinner = i; // Set gWinner to i
			}
			else if(roundsWon[i] == WinCount) { // Else if the rounds one of player i equals the Win Count
				if(totalScore[i] > totalScore[gWinner]) { // If player i's total score is higher than current gWinner's total score
					gWinner = i; // Set the game winner to i
				}
			}	
		}
		return gWinner; // return the Game Winner
	}
	/*
	 * Called by round Winner.
	 * It first checks if the number of round played has been 17 and if it has, then
	 * broadcast the text "Winner" followed by the id of the person who won.
	 */
	public void checkEndGame() {
		if(totalRounds == 17) { // If the totalRounds = 17 the game is over
			Broadcast("Winner " + gameWinner()); 
			//We will have an array of the winners names
			// P1 = [0], P2 [1] and P3 [2]
			// prompt the users with the name of the winner
			//end game or whatever
		}
	}

	public void resetGame() {
		Broadcast("Reset");
		for(int i = 0; i < 3; i++) {
			roundsWon[i] = 0;
			totalScore[i] = 0;
		}
		previousWinner = 0;
		numOfTurns = 0;
		playerTurn = 0;
		totalRounds = 0;
		distributeCards();
	}
	
	public void quitGame(int p)
	{
		for(int i = 0; i < 3; i++)
		{
		}
	}

	public void Broadcast(String broadcastString) { // Method that takes the string given and sends it out to each player
		for(int i = 0; i < 3; i++) {
			out[i].println(broadcastString); // printing out the broadcastString
			out[i].flush();
		}
	}
}
