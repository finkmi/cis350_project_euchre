package cis350_project_euchre;
import javax.swing.*;
import java.util.ArrayList;

public class EuchreModel {
	
	static final int difficulty = 1;
	
	private Player players[];
	private int currentPlayer;
	private int firstPlayer;
	private int team1Score, team2Score;
	private int team1Tricks, team2Tricks;
	
	private ArrayList<Card> deck;
	private ArrayList<Card> playedCards;
	
	private SUIT currentTrump;
	
	private Card topKitty;
	
	private int numPasses;
	
	public EuchreModel() {
		
		players = new Player[4];
		numPasses = 0;
		
		for(int i = 0; i <4; i++) {
			players[i] = new Player(i%2, i==0 ? false : true, difficulty);
		}
		
		firstPlayer = 0;
		currentPlayer = 0;
		team1Score = 0;
		team2Score = 0;
		
		playedCards = new ArrayList();
		
		deck = new ArrayList();
		fillDeck();
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
	
	public void setTrump() {
		currentTrump = topKitty.getSuit();
		if(firstPlayer == 0)
			currentPlayer = 3;
		else
			currentPlayer = firstPlayer - 1;
		//TODO in controller add some variable to determine if trump is being selected
	}
	
	public void swapWithTopKitty(int index) {
		players[currentPlayer].setCardInHand(index, topKitty);
		currentPlayer = firstPlayer;
	}
	
	public Player getPlayer(int index) {
		return players[index];
	}
	
	public int getCurrentPlayer() {
		return currentPlayer;
	}
	
	public boolean isCurrentPlayerDealer() {
		return((currentPlayer == firstPlayer-1) || (currentPlayer == 0 && firstPlayer == 3));
	}
	
	public Card getTopKitty() {
		return topKitty;
	}
	
	public void makeMove(int index) {
		//Check for bot (bots only make valid moves)
		//isValidMove()
		playedCards.add(players[currentPlayer].getCardFromHand(index));
		players[currentPlayer].removeCardFromHand(index);
		
		currentPlayer++;
		if(currentPlayer >= 4)
			currentPlayer = 0;			
	}
	
	public void playerPassed() {
		currentPlayer++;
		if(currentPlayer == 4) {
			currentPlayer = 0;
		}
		numPasses++;
	}
	
	public int getNumPasses() {
		return numPasses;
	}
	
	private void fillDeck() {
		
		deck.clear();
		
		for(int value = 9; value < 15; value++)
			for(SUIT suit : SUIT.values()) 
				deck.add(new Card(value,suit));
	}
	
	public void botSelectTrump() {
		playerPassed();
	}
	
	
}
