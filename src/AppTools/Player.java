package AppTools;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import Views.GameboardGui;

public class Player implements Runnable
{
	int playerID;
	String playerName;
	List<Card> hand = new ArrayList<>();
	List<Card> winBasket = new ArrayList<>();
	int trickCounter;
	boolean isTurn;
	
	Socket socket;
	BufferedReader in;
	public Player(Socket socket, GameboardGui gui) 
	{
		this.socket = socket;
		try 
		{
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			String names = in.readLine();
			StringTokenizer st = new StringTokenizer(names);
			playerID = Integer.parseInt(st.nextToken());
			gui.setName(names);
			
			String cards = in.readLine();
			st = new StringTokenizer(cards);
			gui.setHand(cards);
			
			while (true) 
			{
				st = new StringTokenizer(in.readLine());
			}	
		} 
		catch (IOException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Override
	public void run() 
	{
		// TODO Auto-generated method stub
	}
}