package cis350_project_euchre;

/**********************************************************************
 * Represents a playing card.
 * 
 * @author Michael Fink, Charlie Dorn
 *********************************************************************/
public class Card {
	
	/** 
	 * The value of the playing card (9 - 14) where 11=Jack, 12=Queen,
	 * 13=King, and 14=Ace.
	 */
	private int value;
	
	/** The suit of the card using the SUIT enum class. */
	private SUIT suit;
	
	/******************************************************************
	 * Creates a new card with the given value and suit. The value 
	 * should be from 9-14.
	 * 
	 * @param value This cards value (9, 10, Jack=11, Queen=12, 
	 * 				King=13, Ace=14).
	 * @param s This cards suit.
	 *****************************************************************/
	public Card(int value, SUIT s) {
		this.value = value;
		this.suit = s;
	}

	/******************************************************************
	 * Getter method for the value of the card.
	 * 
	 * @return This cards value.
	 *****************************************************************/
	public int getValue() {
		return value;
	}

	/******************************************************************
	 * Getter method for the suit of the card.
	 * 
	 * @return This cards suit.
	 *****************************************************************/
	public SUIT getSuit() {
		return suit;
	}
}
