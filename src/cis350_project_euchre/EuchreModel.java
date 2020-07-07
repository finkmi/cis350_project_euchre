package cis350_project_euchre;
import java.util.ArrayList;

public class EuchreModel {
	
	static final int difficulty = 1;
	
	private Player players[];
	private int currentPlayer;
	private int firstPlayer;
	private int team0Score, team1Score;
	private int team0Tricks, team1Tricks;
	
	private ArrayList<Card> deck;
	private ArrayList<Card> playedCards;
	
	private SUIT currentTrump;
	private int trumpSelectingTeam;
	
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
		team0Score = 0;
		team1Score = 0;
		
		playedCards = new ArrayList();
		
		deck = new ArrayList();
		fillDeck();
	}
	
	public boolean clearPlayedCards() {
		if(playedCards.size() == 4) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				System.out.println("Clear Played Cards");
			}
			playedCards.clear();
			//deal();
			//incrementFirstPlayer();
			//TODO: firstPlayer should be previous winner
			return true;
		}
		return false;
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
		trumpSelectingTeam = players[currentPlayer].getTeam();
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
	
	private SUIT sameColor(SUIT suit) {
		switch(suit) {
		case CLUB:
			return SUIT.SPADE;
		case SPADE:
			return SUIT.CLUB;
		case HEART:
			return SUIT.DIAMOND;
		case DIAMOND:
			return SUIT.HEART;
		default:
			return SUIT.SPADE;
		}
	}
	
	private void evalTricks() {
		int[] modifiedValues = new int[4];
		SUIT followSuit = playedCards.get(0).getSuit();
		int maxIndex = 0;
		for(int i=0; i<4; i++) {
			modifiedValues[i] = playedCards.get(i).getValue();
			if(playedCards.get(i).getSuit() == followSuit) {
				modifiedValues[i] *= 2;
			}
			if(playedCards.get(i).getSuit() == currentTrump) {		
				modifiedValues[i] *= 4;
			}	
			if(playedCards.get(i).getValue() == 11) {
				if(playedCards.get(i).getSuit() == currentTrump) {
					modifiedValues[i] = 100;
				}
				else if(playedCards.get(i).getSuit() == sameColor(currentTrump)) {
					modifiedValues[i] = 99;
				}
			}
		}
		
		for(int i=1; i<4; i++) {
			if(modifiedValues[i] > modifiedValues[maxIndex])
				maxIndex = i;
		}
		
		if(players[(firstPlayer + maxIndex)%4].getTeam() == 0) {
			team0Tricks++;
		}
		else if(players[(firstPlayer + maxIndex)%4].getTeam() == 1) {
			team1Tricks++;
		}		
	}
	
	private void evalScore() {
		if((team0Tricks == 3 && team1Tricks >= 1)) {
			if(trumpSelectingTeam == 0) {
				team0Score += 1;
			}
			else {
				team0Score += 2;
			}
			team0Tricks = team1Tricks = 0;
		}
		else if((team0Tricks >= 1 && team1Tricks == 3)) {
			if(trumpSelectingTeam == 1) {
				team1Score += 1;
			}
			else {
				team1Score += 2;
			}	
			team0Tricks = team1Tricks = 0;
		}
		else if(team0Tricks + team1Tricks == 5) {
			if(team0Tricks == 5) {
				if(trumpSelectingTeam == 0) {
					team0Score += 2;
				}
				else {
					team0Score += 4;
				}
				team0Tricks = team1Tricks = 0;
			}
			else if(team1Tricks == 5) {
				if(trumpSelectingTeam == 1) {
					team1Score += 2;
				}
				else {
					team1Score += 4;
				}
				team0Tricks = team1Tricks = 0;
			}
		}
	}
	
	public void makeMove(int index) {
		isValidMove(index);
		playedCards.add(players[currentPlayer].getCardFromHand(index));
		players[currentPlayer].removeCardFromHand(index);
		
		if(playedCards.size() >= 4) {
			/* 4 cards have been played */
			evalTricks();
			evalScore();			
		}
		
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
