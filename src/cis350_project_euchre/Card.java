package cis350_project_euchre;

public class Card {
	
	public enum SUIT {
		CLUB,
		DIAMOND,
		HEART,
		SPADE
	}
	
	private int value;
	private SUIT suit;
	
	public Card(int value, SUIT s) {
		this.value = value;
		this.suit = s;
	}

	public int getValue() {
		return value;
	}

	public SUIT getSuit() {
		return suit;
	}
}
