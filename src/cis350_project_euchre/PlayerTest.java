package cis350_project_euchre;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class PlayerTest {

	@Test
	/* This tests that the default constructor is not a bot */
	void test_defaultConstructorIsNotABot() {
		Player p1 = new Player(0);
		assertFalse(p1.getIsBot());
	}
	
	@Test
	/* This tests that the secondary constructor can create a bot */
	void test_secondaryConstructorSetsBotStatusTrue() {
		Player p1 = new Player(0, true, 1);		
		assertTrue(p1.getIsBot());
	}
	
	@Test
	/* This tests that the secondary constructor can not create a bot */
	void test_secondaryConstructorSetsBotStatusFalse() {
		Player p1 = new Player(0, false, 1);		
		assertFalse(p1.getIsBot());
	}
	
	@Test
	/* This tests that the default constructor sets the team correctly */
	void test_defaultConstructorSetsTeamCorrectly() {
		Player p1 = new Player(0);
		Player p2 = new Player(1);
		
		assertEquals(p1.getTeam(), 0);
		assertEquals(p2.getTeam(), 1);
	}
	
	@Test
	/* This tests that the secondary constructor sets the team correctly */
	void test_secondaryConstructorSetsTeamCorrectly() {
		Player p1 = new Player(0, true, 1);
		Player p2 = new Player(1, false, 1);
		
		assertEquals(p1.getTeam(), 0);
		assertEquals(p2.getTeam(), 1);
	}
	
	@Test
	/* This tests that the player can have cards added to their hand */
	void test_playerCanAddCardsToHand() {
		Player p1 = new Player(0, true, 1);
		Card c1 = new Card(9, SUIT.CLUB);
		
		assertTrue(p1.getHand().isEmpty());
		p1.addCardToHand(0, c1);
		assertFalse(p1.getHand().isEmpty());
	}
	
	@Test
	/* This tests that the correct cards are added to the players hand */
	void test_playerCanAddCorrectCardsToHand() {
		Player p1 = new Player(0, false, 1);
		Card c1 = new Card(14, SUIT.HEART);
		
		assertTrue(p1.getHand().isEmpty());
		p1.addCardToHand(0,  c1);
		
		assertEquals(p1.getCardFromHand(0), c1);
	}
	
	@Test 
	/* This tests that a card in the players hand can be changed */
	void test_playerCanChangeCardInHand() {
		Player p1 = new Player(0);
		Card c1 = new Card(11, SUIT.SPADE);
		Card c2 = new Card(13, SUIT.DIAMOND);
		
		assertTrue(p1.getHand().isEmpty());
		p1.addCardToHand(0, c1);
		assertEquals(p1.getCardFromHand(0), c1);
		
		p1.setCardInHand(0, c2);
		assertEquals(p1.getCardFromHand(0), c2);
	}
	
	@Test 
	/* This tests that a card in the players hand can be removed */
	void test_playerCanRemoveCardInHand() {
		Player p1 = new Player(0);
		Card c1 = new Card(11, SUIT.SPADE);
		Card c2 = new Card(13, SUIT.DIAMOND);
		
		assertTrue(p1.getHand().isEmpty());
		p1.addCardToHand(0, c1);
		assertEquals(p1.getCardFromHand(0), c1);
		
		p1.addCardToHand(1, c2);
		assertEquals(p1.getCardFromHand(1), c2);
		
		assertEquals(p1.getHand().size(), 2);
		p1.removeCardFromHand(0);
		assertEquals(p1.getHand().size(), 1);
	}
	
	@Test
	/* This tests that all the cards in the players hand can be removed */
	void test_playerCanRemoveAllCards() {
		Player p1 = new Player(0);
		Card c1 = new Card(12, SUIT.CLUB);
		Card c2 = new Card(10, SUIT.HEART);
		
		assertTrue(p1.getHand().isEmpty());
		p1.addCardToHand(0, c1);
		p1.addCardToHand(1, c2);
		assertEquals(p1.getHand().size(), 2);
		
		p1.clearHand();
		assertTrue(p1.getHand().isEmpty());
	}
	
	@Test
	/* This tests that the Renegeable boolean can be set */
	void test_playerSetsRenegeable() {
		Player p1 = new Player(0);
		p1.setRenegeable(true);
		assertTrue(p1.isRenegeable());
		
		p1.setRenegeable(false);
		assertFalse(p1.isRenegeable());
	}

}
