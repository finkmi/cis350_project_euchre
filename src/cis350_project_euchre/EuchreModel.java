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
		fillDeck();
		for(int i = 0; i < 5; i++) {
			for(int player = 0; player < 4; player++) {
				randCard = (int)(Math.random() * deck.size());
				players[player].addCardToHand(i, deck.get(randCard));
				deck.remove(randCard);
			}
		}
		numPasses = 0;
		topKitty = deck.get(0);
	}
	
	public void setTrump(SUIT s) {
		currentTrump = s;

		//TODO in controller add some variable to determine if trump is being selected
	}
	
	public ArrayList<Card> getPlayedCards() {
		return playedCards;
	}
	
	public void setCurrentPlayerDealer() {
		if(firstPlayer == 0)
			currentPlayer = 3;
		else
			currentPlayer = firstPlayer - 1;
	}
	
	public void setCurrentPlayerFirst() {
		currentPlayer = firstPlayer;
	}
	
	public void swapWithTopKitty(int index) {
		players[currentPlayer].setCardInHand(index, topKitty);
		setCurrentPlayerFirst();
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
		isValidMove(index);
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
		if(numPasses >= 8) {
    		deal();
    		incrementFirstPlayer();
		}
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
	
	/* return true if bot selcts trump and false if bot passes */
	private boolean botSelectTrump() {
		playerPassed();
		return false;
	}
	
	private void botSelectKitty() {
		playerPassed();
	}
	
	private void botSwapWithKitty() {
		int randCard = (int)(Math.random() * 5);
		swapWithTopKitty(randCard);
	}
	
	private void botPlayCard() {
		
		for(Card card : players[currentPlayer].getHand())
			if(isValidMove(players[currentPlayer].getHand().indexOf(card))) {
				makeMove(players[currentPlayer].getHand().indexOf(card));
				return;
			}
		
		makeMove(0);
		
	}
	
	/* return true if bot selcts trump and false otherwise(if bot passes) */

	public boolean botPlay(BOTCODE code) {
		switch(code) {
		case TRUMP:
			return botSelectTrump();
		case PLAY:
			botPlayCard();
			return false;
		case SWAP:
			botSwapWithKitty();
			return true;
		case HITKITTY:
			botSelectKitty();
			return false;
		}
		return false;
	}
	
	public void incrementFirstPlayer() {
		firstPlayer++;
		if(firstPlayer >= 4)
			firstPlayer = 0;
		currentPlayer = firstPlayer;
	}
	
	private boolean isValidMove(int index) {
		
		if(playedCards.isEmpty())
			return true;
		
		SUIT follow = playedCards.get(0).getSuit();
		
		if(players[currentPlayer].getCardFromHand(index).getSuit() != follow) {
			for(Card possiblePlay : players[currentPlayer].getHand())
			{
				if(possiblePlay.getSuit() == follow) {
					players[currentPlayer].setRenegeable(true);
					return false;
				}
			}
		}
		return true;
	}
	
	
}
