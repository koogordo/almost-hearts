package AppTools;

import java.util.ArrayList;
import java.util.List;

public class Player {

	int playerID;
	String playerName;
	List<Card> hand = new ArrayList<>();
	List<Card> winBasket = new ArrayList<>();
	int trickCounter;
	boolean isTurn;
	
	public Player(int id, String name) {
		
		isTurn = false;
		if (id == 1) {
			isTurn = true;
		}
		playerID = id;
		playerName = name;
		trickCounter = 0;
	}
}
