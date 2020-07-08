package cis350_project_euchre;

import java.util.ArrayList;

public class Player {
	
	private int team;
	private ArrayList<Card> hand;
	private boolean isBot;
	private int difficulty;
	private boolean renegeable;
	
	public Player(int team) {
		this.setTeam(team);
		hand = new ArrayList();
		this.difficulty = 0;
		this.isBot = false;
		this.renegeable = false;
	}
	
	public Player(int team, boolean isBot, int difficulty) {
		this.setTeam(team);
		hand = new ArrayList();
		this.difficulty = difficulty;
		this.isBot = isBot;
		this.renegeable = false;	}
	
	public void addCardToHand(int index, Card card) {
		hand.add(index, card);
	}
	
	public void setCardInHand(int index, Card card) {
		hand.set(index, card);
	}
	
	public Card getCardFromHand(int index) {
		return hand.get(index);
	}
	
	public void removeCardFromHand(int index) {
		hand.remove(index);
	}
	
	public void clearHand() {
		hand.clear();
	}
	
	public ArrayList<Card> getHand() {
		return hand;
	}

	public int getTeam() {
		return team;
	}

	public void setTeam(int team) {
		this.team = team;
	}

	public boolean isRenegeable() {
		return renegeable;
	}

	public void setRenegeable(boolean renegeable) {
		this.renegeable = renegeable;
	}
	
	public boolean getIsBot() {
		return isBot;
	}
	
	
	
	//TODO: Add bot logic
}
