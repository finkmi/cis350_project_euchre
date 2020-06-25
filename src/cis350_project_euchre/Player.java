package cis350_project_euchre;

public class Player {
	
	private int team;
	private Card[] hand;
	private boolean isBot;
	private int difficulty;
	
	public Player(int team) {
		this.setTeam(team);
		this.hand = new Card[5];
		this.difficulty = 0;
		this.isBot = false;
	}
	
	public Player(int team, boolean isBot, int difficulty) {
		this.setTeam(team);
		this.hand = new Card[5];
		this.difficulty = difficulty;
		this.isBot = isBot;
	}
	
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
	
	
	
	//TODO: Add bot logic
}
