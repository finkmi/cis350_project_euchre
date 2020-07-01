package cis350_project_euchre;
import javax.swing.*;
import java.util.ArrayList;

public class EuchreModel {
	
	static final int difficulty = 1;
	
	private Player players[];
	private int currentPlayer;
	private int currentDealer;
	private int team1Score, team2Score;
	
	private ArrayList<Card> deck;
	
	private SUIT currentTrump;
	
	private Card topKitty;
	
	public EuchreModel() {
		
		players = new Player[4];
		
		for(int i = 0; i <4; i++) {
			players[i] = new Player(i%2, i==0 ? false : true, difficulty);
		}
		
		currentDealer = 0;
		currentPlayer = 1;
		team1Score = 0;
		team2Score = 0;
		
		deck = new ArrayList();
		
		for(int value = 0; value < 6; value++)
			for(SUIT suit : SUIT.values()) 
				deck.add(new Card(value,suit));
	}
	
	public void deal() {
		
		int randCard;
		
		for(int i = 0; i < 5; i++) {
			for(int player = 0; player < 4; player++) {
				randCard = (int)(Math.random() * deck.size());
				players[player].setCardInHand(i, deck.get(randCard));
				deck.remove(randCard);
			}
		}
		
		topKitty = deck.get(0);
	}
	

}
