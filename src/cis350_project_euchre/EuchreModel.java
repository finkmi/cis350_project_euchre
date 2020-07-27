//CHECKSTYLE:OFF
package cis350_project_euchre;
import java.util.ArrayList;
import java.util.Random;
//CHECKSTYLE:ON

/**********************************************************************
 * The Euchre Model class. Contains functions and variables needed
 * for the gameplay logic of euchre.
 * 
 * @author Michael Fink, Charlie Dorn
 *********************************************************************/
public class EuchreModel {
	
	/** Array of Player objects to represent the four players. */
	private Player[] players;
	
	/** The index of the current player within the players array. */
	private int currentPlayer;
	
	/** The index of this tricks first player in the players array. */
	private int firstPlayer;
	
	/** The index of the dealer of this hand in the players array. */
	private int dealer;
	
	/** Scores of team 0. */
	private int team0Score;
	
	/** Scores of team 1. */
	private int team1Score;
	
	/** Current number of tricks team 0 has. */
	private int team0Tricks;
	
	/** Current number of tricks team 1 has. */
	private int team1Tricks;
	
	/** ArrayList of card that make up the 24 cards used in Euchre. */
	private ArrayList<Card> deck;
	
	/** ArrayList of the played cards for each trick. */
	private ArrayList<Card> playedCards;
	
	/** The suit that is trump for this hand. */
	private SUIT currentTrump;
	
	/** The team that selected the trump. */
	private int trumpSelectingTeam;
	
	/** The face up card that can be selected as trump. */
	private Card topKitty;
	
	/** The number of times the players have passed. */
	private int numPasses;
	
	/** Random number generator used in deal method. */
	private Random randomNum = new Random();
	
	/******************************************************************
	 * The Euchre model constructor. Sets up the array of players, sets
	 * the dealer index, and sets up the deck of cards to be use in
	 * dealing.
	 *****************************************************************/
	public EuchreModel() {
		
		/* Set up the array of players, and instantiate each player */
		players = new Player[4];
		for (int i = 0; i < 4; i++) {
			players[i] = new Player(i % 2, i == 0 ? false : true);
		}
		
		/* Set trump to null to begin with */
		currentTrump = null;
		
		/* Set the number of passes to 0, as no one has passed yet */
		numPasses = 0;
		
		/* Set the dealer to the player in index 3 */
		dealer = 3;
		currentPlayer = 0;
		firstPlayer = 1;
		
		/* Initialize both scores to 0 */
		team0Score = 0;
		team1Score = 0;
		
		/* Instantiate the arraylist for played cards */
		playedCards = new ArrayList<Card>();
		
		/* Instantiate the arrayList for the deck, and fill the deck */
		deck = new ArrayList<Card>();
		fillDeck();
	}
	
	
	
	
	//CHECKSTYLE:OFF
	/********************* Getters & Setters *************************/
	//CHECKSTYLE:ON
	
	/******************************************************************
	 * Return the index of the dealer.
	 * 
	 * @return The index of the dealer currently.
	 *****************************************************************/
	public int getDealer() {
		return dealer;
	}
	
	
	/******************************************************************
	 * Return the player at the index specified in the players array.
	 * 
	 * @param index The index of the desired player within the players
	 * 				array.
	 * @return The player at the specified index.
	 *****************************************************************/
	public Player getPlayer(final int index) {
		return players[index];
	}
	
	/******************************************************************
	 * Get the index of the currentPlayer.
	 * 
	 * @return An integer representing the index of the currentPlayer.
	 *****************************************************************/
	public int getCurrentPlayer() {
		return currentPlayer;
	}
	
	/******************************************************************
	 * Sets the dealer as the current player. This is done to allow the
	 * dealer to choose a card to swap with the flipped over card in 
	 * the event that someone orders the card up as trump.
	 *****************************************************************/
	public void setCurrentPlayerDealer() {
		/* Handle the wrap-around case where firstPlayer=0, dealer=3 */
		if (firstPlayer == 0) {
			currentPlayer = 3;
		}
		/* Otherwise, dealer is one less than firstPlayer */
		else {
			currentPlayer = firstPlayer - 1;
		}
	}
	
	/******************************************************************
	 * Set the currentPlayer to be the same as the firstPlayer. This 
	 * needs to be done at the start of each trick.
	 *****************************************************************/
	public void setCurrentPlayerFirst() {
		currentPlayer = firstPlayer;
	}
		
	/******************************************************************
	 * Get the number of tricks won be a given team.
	 * 
	 * @param team The team to check the number of tricks for.
	 * @return The number of tricks the given team has won this hand.
	 *****************************************************************/
	public int getNumTricks(final int team) {
		return team == 0 ? team0Tricks : team1Tricks;
	}
	
	/******************************************************************
	 * Get the score of a given team.
	 * 
	 * @param team The team to check the score for.
	 * @return The score of the given team.
	 *****************************************************************/
	public int getScore(final int team) {
		return team == 0 ? team0Score : team1Score;
	}
	
	/*****************************************************************
	 * Set both teams scores and tricks back to zero.
	 ****************************************************************/
	public void resetScore() {
		team0Score = 0;
		team1Score = 0;
		team0Tricks = 0;
		team1Tricks = 0;
	}
	
	/******************************************************************
	 * Sets the trump to the specified suit. Also determines the team
	 * that selected trump for scoring purposes.
	 * 
	 * @param s The suit that trump should be set to.
	 *****************************************************************/
 	public void setTrump(final SUIT s) {
		currentTrump = s;
		trumpSelectingTeam = players[currentPlayer].getTeam();
	}
 	
 	/******************************************************************
	 * Gets the current trump.
	 * 
	 * @return The suit that is currently trump.
	 *****************************************************************/
	public SUIT getTrump() { 
		return currentTrump;
	}
 	
	/******************************************************************
	 * Get the current number of times a player has passed on picking
	 * up trump.
	 * 
	 * @return The number of times a player has passed on picking up
	 * 		   trump.
	 *****************************************************************/
	public int getNumPasses() {	
		return numPasses;
	}
	
	/******************************************************************
	 * Get the arrayList of cards that have been played in the current
	 * trick.
	 * 
	 * @return The arrayList of playedCards this trick.
	 *****************************************************************/
	public ArrayList<Card> getPlayedCards() {
		return playedCards;
	}
	
	/******************************************************************
	 * Get the topKitty card.
	 * 
	 * @return The card object of the topKitty.
	 *****************************************************************/
	public Card getTopKitty() {
		return topKitty;
	}
	
	
	
	
	//CHECKSTYLE:OFF
	/********************** Public Methods ***************************/
	//CHECKSTYLE:ON
	
	/******************************************************************
	 * Handles the dealing logic of the Euchre game.
	 *****************************************************************/
	public void deal() {
		
		/* Index within the deck of the random card to use */
		int randCard;
		
		/* Make sure the deck is filled before dealing */
		fillDeck();
		
		/* Clear each players hand */
		for (Player p : players) {
			p.clearHand();
		}
		
		/* Deal cards one-by-one to each player until all players have
		 * five cards */
		for (int i = 0; i < 5; i++) {
			for (int player = 0; player < 4; player++) {
				/* get a random integer between 0 and 23 (inclusive) */
				randCard = (randomNum.nextInt(deck.size()));
				
				/* add the card at this random index to the players
				 * hand, and remove it from the deck so it doesn't
				 * get dealt to another player */
				players[player].addCardToHand(i, deck.get(randCard));
				deck.remove(randCard);
			}
		}
		
		/* After dealing, no passes have happened yet */
		numPasses = 0;
		
		/* Set the topKitty to the card on the top of the deck
		 * after dealing to all of the players */
		topKitty = deck.get(0);
		
		/* Increment the dealer to the next player after dealing */
		incrementDealer();
	}
	
	/******************************************************************
	 * Increments the dealer by one and wraps if greater than 3 to 
	 * start back from 0.
	 *****************************************************************/
	public void incrementDealer() {
		firstPlayer = currentPlayer = (dealer + 1) % 4;
		dealer++;
		if (dealer >= 4) {
			dealer = 0;
		}
	}
	
	/******************************************************************
	 * Checks if the currentPlayer is also the dealer.
	 * 
	 * @return True if the currentPlayer is the dealer, else false.
	 *****************************************************************/
	public boolean isCurrentPlayerDealer() {
		return ((currentPlayer == firstPlayer - 1) 
				|| (currentPlayer == 0 && firstPlayer == 3));
	}
	
	/******************************************************************
	 * Play a card based on the index given. Plays the card at the 
	 * given index in the current players hand. After playing, removes
	 * the card from the players hand and increments the current player
	 * so that the next person can play.
	 * 
	 * @param index The index of the card within the current players
	 * 				hand that should be played.
	 *****************************************************************/
	public void makeMove(final int index) {
		/* Check if the move is valid */
		if (isValidMove(index)) {
			/* Add the played card from the users hand to playedCards */
			playedCards.add(players[currentPlayer].getCardFromHand(index));
			/* Remove the played card from the players hand */
			players[currentPlayer].removeCardFromHand(index);
			
			/* Increment the current player appropriately */
			currentPlayer = (currentPlayer + 1) % 4;
		}
	}
	
	/******************************************************************
	 * This function handles the player passing on calling trump (used
	 * for both passing on the kitty and passing on selecting a suit
	 * for trump).
	 *****************************************************************/
	public void playerPassed() {
		/* Increment the current player appropriately */
		currentPlayer = (currentPlayer + 1) % 4;
		/* Increment the number of passes */
		numPasses++;
	}
	
	/******************************************************************
	 * Handle the logic to swap the card at the given index in the 
	 * current players hand to the flipped over card on the kitty.
	 * 
	 * @param index The index (within the current players hand) of 
	 * 				the card that should be swapped with the topKitty
	 * 				card.
	 *****************************************************************/
	public void swapWithTopKitty(final int index) {
		/* Replace the players card at the index with the topKitty */
		players[currentPlayer].setCardInHand(index, topKitty);
		
		/* Set the currentPlayer to firstPlayer to begin the trick */
		setCurrentPlayerFirst();
	}
	
	/******************************************************************
	 * Clear the played cards arrayList. The arrayList should only 
	 * only clear if it is full (i.e. all cards for this trick have
	 * been played).
	 * 
	 * @return true if the playedCards are cleared, false otherwise.
	 *****************************************************************/
	public boolean clearPlayedCards() {
		/* If all cards for this trick have been played, clear */
		if (playedCards.size() >= 4) {
			playedCards.clear();
			return true;
		}
		return false;
	}
	
	/******************************************************************
	 * The evalTricks method evaluates a trick to determine who scored
	 * the most in a given trick. It increments the winning teams trick
	 * count and sets the firstPlayer for the next trick to the winner
	 * of the previous trick.
	 *****************************************************************/
	public void evalTricks() {
		/* Array of modified values used in scoring a trick */
		int[] modifiedValues = new int[4];
		/* Determine the suit that should be followed */
		SUIT followSuit = playedCards.get(0).getSuit();
		/* The index within modified value of the most valuable card */
		int maxIndex = 0;
		
		/* Loop through the played cards and determine the scoring
		 * scoring value for each played card. Save this value, 
		 * including any modifiers, into the modifiedValues array */
		for (int i = 0; i < 4; i++) {
			modifiedValues[i] = playedCards.get(i).getValue();
			/* If the played card followed suit, it receives a *2
			 * multiplier */
			if (playedCards.get(i).getSuit() == followSuit) {
				modifiedValues[i] *= 2;
			}
			/* If the played card followed trump, it receives a *4
			 * multiplier */
			if (playedCards.get(i).getSuit() == currentTrump) {		
				modifiedValues[i] *= 4;
			}	
			/* If the card is a jack, need to see if it is a bauer */
			if (playedCards.get(i).getValue() == 11) {
				/* If this jack is trump, it is the right bauer and is
				 * the most valuable card, so set it to 1000 */
				if (playedCards.get(i).getSuit() == currentTrump) {
					modifiedValues[i] = 1000;
				}
				/* If this jack is the same color as trump, it is the
				 * left bauer and is the second most valuable card, so
				 * set it to 999 to be slightly less than the right
				 * bauer */
				else if (playedCards.get(i).getSuit() == sameColor(currentTrump)) {
					modifiedValues[i] = 999;
				}
			}
		}
		
		/* Loop through the modified values to find the index of the 
		 * largest value */
		for (int i = 1; i < 4; i++) {
			if (modifiedValues[i] > modifiedValues[maxIndex]) {
				maxIndex = i;
			}
		}
		
		/* If the player that won the trick was on team 0, increment
		 * team 0 tricks */
		if (players[(firstPlayer + maxIndex) % 4].getTeam() == 0) {
			team0Tricks++;
			
		}
		/* If the player that won the trick was on team 1, increment
		 * team 1 tricks */
		else if (players[(firstPlayer + maxIndex) % 4].getTeam() == 1) {
			team1Tricks++;
		}
		
		/* Set the first and current players to the trick winner */
		firstPlayer = currentPlayer = ((firstPlayer + maxIndex) % 4);
	}
	
	/******************************************************************
	 * The evalScore method evaluates the number of tricks that have
	 * been won by both teams to see if the hand is over. If the hand
	 * is over, the score for the team that won the hand gets 
	 * incremented appropriately.
	 * 
	 * @return True if the hand was scored and the controller should
	 * 		   reset, false if the hand is not done yet.
	 *****************************************************************/
	public boolean evalScore() {
		/* The boolean used to determine if scoring happened and if
		 * a reset (re-deal) is necessary. */
		boolean reset = true;
		/* If one team has won 3 tricks, and the other has won 1 or 
		 * more, the hand should be considered done */
		if ((team0Tricks >= 3 && team1Tricks >= 1)) {
			/* If team 0 won more tricks and selected trump, they get
			 * 1 point */
			if (trumpSelectingTeam == 0) {
				team0Score += 1;
			}
			/* If team 0 won more tricks, but team 1 selected trump,
			 * team 0 gets 2 points (team 0 euchred team 1) */
			else {
				team0Score += 2;
			}
			/* Reset trick counts to 0 */
			team0Tricks = team1Tricks = 0;
		}
		/* If one team has won 3 tricks, and the other has won 1 or 
		 * more, the hand should be considered done */
		else if ((team0Tricks >= 1 && team1Tricks >= 3)) {
			/* If team 1 won more tricks and selected trump, they get
			 * 1 point */
			if (trumpSelectingTeam == 1) {
				team1Score += 1;
			}
			/* If team 1 won more tricks, but team 0 selected trump,
			 * team 1 gets 2 points (team 1 euchred team 0) */
			else {
				team1Score += 2;
			}	
			/* Reset trick counts to 0 */
			team0Tricks = team1Tricks = 0;
		}
		/* If the 5 tricks have been played, the hand is done */
		else if (team0Tricks + team1Tricks == 5) {
			if (team0Tricks == 5) {
				/* If team 0 won all 5 tricks and selected trump,
				 * they get 2 points */
				if (trumpSelectingTeam == 0) {
					team0Score += 2;
				}
				/* If team 0 won all 5 tricks, but team 1 selected
				 * trump, team 0 gets 4 points */
				else {
					team0Score += 4;
				}
				/* Reset trick counts to 0 */
				team0Tricks = team1Tricks = 0;
			}
			else if (team1Tricks == 5) {
				/* If team 1 won all 5 tricks and selected trump,
				 * they get 2 points */
				if (trumpSelectingTeam == 1) {
					team1Score += 2;
				}
				/* If team 1 won all 5 tricks, but team 0 selected
				 * trump, team 1 gets 4 points */
				else {
					team1Score += 4;
				}
				/* Reset trick counts to 0 */
				team0Tricks = team1Tricks = 0;
			}
		}
		/* If the hand is not complete and no scoring took place, 
		 * there is no need to reset, therefore set reset false */
		else {
			reset = false;
		}
		
		/* Return the status of reset (true if scored, else false) */
		return reset;		
	}
	
	/******************************************************************
	 * Handles which private method to call based on the code sent by
	 * the controller.
	 * 
	 * @param code describing what action the bot should take
	 * @return the BOTCODE describing what the resulting action of the
	 * bot play was
	 */
	public BOTCODE botPlay(final BOTCODE code) {
		switch (code) {
		case TRUMP:
			return botSelectTrump();
		case PLAY:
			return botPlayCard();
		case SWAP:
			return botSwapWithKitty();
		case HITKITTY:
			return botSelectKitty();
		default:
			return BOTCODE.DEFAULT;
		}
	}
	
	
	
	
	//CHECKSTYLE:OFF
	/********************** Private Methods ***************************/
	//CHECKSTYLE:ON
	
	/*******************************************************************
	 * Checks if the card at the index is a valid play based on the 
	 * first card played.
	 * 
	 * @param index of the card to be played
	 * @return True if the move was valid and False if not
	 ******************************************************************/
	private boolean isValidMove(final int index) {
		
		boolean result = true;
		
		if (playedCards.isEmpty()) {
			return result;
		}
		
		SUIT follow = playedCards.get(0).getSuit();
		Card cardSelected = players[currentPlayer].getCardFromHand(index);
		
		//Logic to set the follow equal to the same color suit of the left bauer when played first rather than the actual suit
		if (playedCards.get(0).getValue() == 11 
				&& currentTrump == sameColor(playedCards.get(0).getSuit())) {
			follow = currentTrump;
		}	
		
		//Loop through all cards in hand to see if there was a card that follows suit before allowing any move
		if (cardSelected.getSuit() != follow) {
			for (Card possiblePlay : players[currentPlayer].getHand()) {
				//Set left bauer's trump to the same color so logically it acts as its same color rather than its face suit.
				if (possiblePlay.getSuit() == sameColor(currentTrump) && possiblePlay.getValue() == 11) {
					possiblePlay.setSuit(sameColor(possiblePlay.getSuit()));
					//Check that left bauer isn't in hand if trump is to be followed
					if (possiblePlay.getSuit() == follow) {
						result = false;
					}
					possiblePlay.setSuit(sameColor(possiblePlay.getSuit()));
				}
				//Check that none of the cards in hand are the same suit as first played
				else if (possiblePlay.getSuit() == follow) {
					result = false;
				}
				
			}
			
			// Logic to allow you to play the left bauer in the case of following trump
			if (cardSelected.getSuit() == sameColor(currentTrump) && cardSelected.getValue() == 11 && follow == currentTrump) {
				result = true;
			}

		}
		return result;
	}
	
	/******************************************************************
	 * Based on the input suit, determine the other suit that is the 
	 * same color. For example, if clubs was passed in as suit, spades
	 * would be returned because it is the same color (black) as clubs.
	 * 
	 * @param suit The suit that you want the returned suit to be the
	 * 			   same color as.
	 * @return The suit that is the same color as the inputted suit.
	 *****************************************************************/
	private SUIT sameColor(final SUIT suit) {
		switch (suit) {
		/* If the suit is clubs, return spades */
		case CLUB:
			return SUIT.SPADE;
		/* If the suit is spades, return clubs */
		case SPADE:
			return SUIT.CLUB;
		/* If the suit is hearts, return diamonds */
		case HEART:
			return SUIT.DIAMOND;
		/* If the suit is diamonds, return hearts */
		case DIAMOND:
			return SUIT.HEART;
		/* Default should never be hit, but if it is, return spades */
		default:
			return SUIT.SPADE;
		}
	}
	
	/******************************************************************
	 * Fill a deck with all of the 24 cards used in Euchre. This is
	 * done before dealing to ensure that the deck is complete.
	 *****************************************************************/
	private void fillDeck() {
		
		/* Make sure the deck is empty before re-filling it */
		deck.clear();
		
		/* Add all cards to the deck, starting with 9 up to Ace for
		 * each of the four suits */
		for (int value = 9; value < 15; value++) {
			for (SUIT suit : SUIT.values()) {
				deck.add(new Card(value, suit));
			}
		}
	}
	
	/******************************************************************
	 * Bot trump selection function for selecting trump.
	 * 
	 * @return A botcode depending on whether the bot selected trump
	 * 		   or not.
	 *****************************************************************/
	private BOTCODE botSelectTrump() {
		for (SUIT s: SUIT.values()) {
			if (evalHandPotential(s) > 100) {
				currentTrump = s;
				setCurrentPlayerFirst();
				return BOTCODE.TRUMP_SELECTED;
			}	
		}
		playerPassed();
		return BOTCODE.TRUMP_NOTSELECTED;
	}
	
	/******************************************************************
	 * Bot function to determine if the bot should order the kitty up
	 * as trump.
	 * 
	 * @return A botcode depending on whether the bot ordered the kitty
	 * 		   as trump or not.
	 *****************************************************************/
	private BOTCODE botSelectKitty() {
		playerPassed();
		return BOTCODE.HITKITTY_NOHIT;
	}
	
	/******************************************************************
	 * Bot function to swap a card in their hand with the kitty if 
	 * they are the dealer and someone decides to order the kitty as
	 * trump.
	 * 
	 * @return A botcode stating that the swap occurred (there is no 
	 * 		   reason the swap would not occur successfully).
	 */
	private BOTCODE botSwapWithKitty() {
		/* Choose a random card in the bots hand */
//		int randCard = randomNum.nextInt(5);
//		/* Swap the random card with the kitty */
//		swapWithTopKitty(randCard);
		
		//Find the index of the lowest off suit card in hand
		int lowestOffSuit = 0;
		for (int i = 1; i < 5; i++) {
			if (players[currentPlayer].getCardFromHand(i).getSuit() == currentTrump) {
				break;
			}
			else if (players[currentPlayer].getCardFromHand(i).getSuit() == sameColor(currentTrump) && players[currentPlayer].getCardFromHand(i).getValue() == 11) {
				break;
			}
			else if (players[currentPlayer].getCardFromHand(i).getValue() < players[currentPlayer].getCardFromHand(lowestOffSuit).getValue()) {
				lowestOffSuit = i;
			}
		}
		swapWithTopKitty(lowestOffSuit);

		return BOTCODE.SWAP_SUCCESSFUL;
	}
	
	/******************************************************************
	 * Bot play card function. This function handles the logic for 
	 * determining a card to play. 
	 * 
	 * @return A botcode depending on whether the bot played the last
	 * 		   card in the trick or not.
	 *****************************************************************/
	private BOTCODE botPlayCard() {
		
		/* Check each card to see if it is a valid move (i.e. it
		 * follows suit) */
		for (Card card : players[currentPlayer].getHand()) {
			if (isValidMove(players[currentPlayer].getHand().indexOf(card))) {
				/* If there is a valid move, make the mode */
				makeMove(players[currentPlayer].getHand().indexOf(card));
				/* If all cards in trick have been played, 
				 * return the BOTCODE.PLAY_TRICKFINISHED botcode */
				if (playedCards.size() >= 4) {
					return BOTCODE.PLAY_TRICKFINISHED;
				}				
				/* If not all cards in trick have been played, 
				 * return the BOTCODE.PLAY_TRICKNOTFINISHED botcode */
				return BOTCODE.PLAY_TRICKNOTFINISHED;
			}
		}
		
		/* If no valid moves available, play the first card in hand */
		/* This is in place as a failsafe */
		makeMove(0);
		/* If all cards in trick have been played, 
		 * return the BOTCODE.PLAY_TRICKFINISHED botcode */
		if (playedCards.size() >= 4) {
			return BOTCODE.PLAY_TRICKFINISHED;
		}	
		/* If not all cards in trick have been played, 
		 * return the BOTCODE.PLAY_TRICKNOTFINISHED botcode */
		return BOTCODE.PLAY_TRICKNOTFINISHED;		
	}
	
	private int evalHandPotential(final SUIT trump) {
		
		int result = 0;
		
		for (Card card : players[currentPlayer].getHand()) {
			if (card.getSuit() == trump && card.getValue() == 11) {
				result += 30;
			}
			else if (card.getSuit() == sameColor(trump) && card.getValue() == 11) {
				result += 29;
			}
			else if (card.getSuit() == trump) {
				result += card.getValue() * 2;
			}
			else {
				result += card.getValue();
			}
		}
		return result;
	}
	
}
