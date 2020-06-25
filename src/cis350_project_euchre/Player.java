package cis350_project_euchre;

public class Player {
	
	private int team;
	private Card[] hand;
	private boolean isBot;
	private int difficulty;
	private boolean renegeable;
	
	public Player(int team) {
		this.setTeam(team);
		this.hand = new Card[5];
		this.difficulty = 0;
		this.isBot = false;
		this.renegeable = false;
	}
	
	public Player(int team, boolean isBot, int difficulty) {
		this.setTeam(team);
		this.hand = new Card[5];
		this.difficulty = difficulty;
		this.isBot = isBot;
		this.renegeable = false;	}
	
	public void setCardInHand(int place, Card card) {
		this.hand[place] = card;
	}
	
	public Card getCardFromHand(int place) {
		return this.hand[place];
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
	
	
	
	//TODO: Add bot logic
}
