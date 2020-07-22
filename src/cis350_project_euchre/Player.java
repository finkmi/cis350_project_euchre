package cis350_project_euchre;

import java.util.ArrayList;

/**********************************************************************
 * Represents a player in the Euchre game.
 * 
 * @author Michael Fink, Charlie Dorn
 *********************************************************************/
public class Player {
	
	/** The team of a given player (represented by 0 and 1). */
	private int team;
	
	/** The cards currently in a players hand. */
	private ArrayList<Card> hand;
	
	/** Specifies if the player is a bot or a human player. */
	private boolean isBot;


	/******************************************************************
	 * Constructor for a player. This constructor makes the assumption
	 * that the player is not a bot, and therefore only takes a team
	 * (0 or 1) as input.
	 * 
	 * @param team The team the player is on (0 or 1).
	 *****************************************************************/
	public Player(final int team) {
		/* Set the team of the new player, and instantiate the hand */
		setTeam(team);
		hand = new ArrayList<Card>();
		
		/* This player is not a bot, and has made no illegal moves */
		isBot = false;
	}
	
	/******************************************************************
	 * Constructor for a player. This constructor takes the team, and
	 * the status of isBot as well as a difficulty level for a bot.
	 * 
	 * @param team The team the player is on (0 or 1).
	 * @param isBot True if the player is a bot, else false.
	 *****************************************************************/
	public Player(final int team, final boolean isBot) {
		/* Set the team of the new player, and instantiate the hand */
		this.setTeam(team);
		hand = new ArrayList<Card>();
		
		/* Set bot statuses accordingly */
		this.isBot = isBot;
	}
	
	/******************************************************************
	 * Adds a card to the users hand arrayList at the specified index.
	 * 
	 * @param index The spot in the hand to place this card.
	 * @param card The card to be placed in the hand.
	 *****************************************************************/
	public void addCardToHand(final int index, final Card card) {
		hand.add(index, card);
	}
	
	/******************************************************************
	 * Changes a card already in the users hand at a specific index to 
	 * the new card.
	 * 
	 * @param index The spot in the hand to swap with this card.
	 * @param card The card to be set in the hand.
	 *****************************************************************/
	public void setCardInHand(final int index, final Card card) {
		hand.set(index, card);
	}
	
	/******************************************************************
	 * Returns the card in the players hand at the given index.
	 * 
	 * @param index The index within the hand that the card should be 
	 *  			retrieved from.
	 * @return The card at the specified index in the hand.
	 *****************************************************************/
	public Card getCardFromHand(final int index) {
		return hand.get(index);
	}
	
	/******************************************************************
	 * Removes the card at the given index from the players hand.
	 * 
	 * @param index The index within the hand that the card should be
	 * 				removed from.
	 *****************************************************************/
	public void removeCardFromHand(final int index) {
		hand.remove(index);
	}
	
	/******************************************************************
	 * Removes all cards from the players hand, making the hand empty.
	 *****************************************************************/
	public void clearHand() {
		hand.clear();
	}
	
	/******************************************************************
	 * Gets the entire arrayList for this players hand.
	 * 
	 * @return The players hand.
	 *****************************************************************/
	public ArrayList<Card> getHand() {
		return hand;
	}

	/******************************************************************
	 * Gets the team of the given player.
	 * 
	 * @return The integer representing the team (0 or 1).
	 *****************************************************************/
	public int getTeam() {
		return team;
	}

	/******************************************************************
	 * Sets the team of the current player.
	 * 
	 * @param team An integer representing the team (0 or 1).
	 *****************************************************************/
	public void setTeam(final int team) {
		this.team = team;
	}
	
	/******************************************************************
	 * Gets the status of isBot for the given player.
	 * 
	 * @return True if this player is a bot, else false. 
	 *****************************************************************/
	public boolean getIsBot() {
		return isBot;
	}
}
