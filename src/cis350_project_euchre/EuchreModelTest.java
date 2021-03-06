//CHECKSTYLE:OFF
package cis350_project_euchre;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class EuchreModelTest {

	@Test
	// Ensures that there are 4 players 1 of which is human the rest of which are bots
	// and ensures that they are on the correct teams
	void test_constructorMakesAllPlayers() {
		EuchreModel model = new EuchreModel();
		
		assertFalse(model.getPlayer(0).getIsBot());
		assertTrue(model.getPlayer(1).getIsBot());
		assertTrue(model.getPlayer(2).getIsBot());
		assertTrue(model.getPlayer(3).getIsBot());

		assertEquals(model.getPlayer(0).getTeam(), 0);
		assertEquals(model.getPlayer(2).getTeam(), 0);
		assertEquals(model.getPlayer(0).getTeam(), model.getPlayer(2).getTeam());

		assertEquals(model.getPlayer(1).getTeam(), 1);
		assertEquals(model.getPlayer(3).getTeam(), 1);
		assertEquals(model.getPlayer(1).getTeam(), model.getPlayer(3).getTeam());
	}
	
	@Test
	// Ensures the getCurrentPlayer method is functional
	void test_getCurrentPlayer() {
		EuchreModel model = new EuchreModel();

		assertEquals(model.getCurrentPlayer(), 0);
		model.playerPassed();
		assertEquals(model.getCurrentPlayer(), 1);

	}
	
	@Test
	// Ensure that setCurrentPlayerDealer function is functional
	void test_setCurrentPlayerDealer() {
		EuchreModel model = new EuchreModel();
		model.deal();
		assertEquals(model.getCurrentPlayer(), 0);
		model.setCurrentPlayerDealer();
		assertEquals(model.getCurrentPlayer(), 3);
	}
	
	@Test
	// Ensure that setCurrentPlayerFirst is functional
	void test_setCurrentPlayerFirst() {
		EuchreModel model = new EuchreModel();
		
		assertEquals(model.getCurrentPlayer(), 0);
		model.setCurrentPlayerFirst();
		assertEquals(model.getCurrentPlayer(), 1);
	}
	
	@Test
	// Ensures we can get the number of tricks from model for both teams
	void test_getNumTricks() {
		EuchreModel model = new EuchreModel();

		assertEquals(model.getNumTricks(0), 0);
		assertEquals(model.getNumTricks(1), 0);
	}
	
	@Test
	//Ensures we can get the score from model for both teams
	void test_getScore() {
		EuchreModel model = new EuchreModel();

		assertEquals(model.getScore(0), 0);
		assertEquals(model.getScore(1), 0);
	}
	
	@Test
	// Ensures we can reset scores to zero
	void test_resetScore() {
		EuchreModel model = new EuchreModel();

		assertEquals(model.getScore(0), 0);
		assertEquals(model.getScore(1), 0);
		model.resetScore();
		assertEquals(model.getScore(0), 0);
		assertEquals(model.getScore(1), 0);
	}
	
	@Test
	//Test trump getters/setters
	void test_getAndSetTrump() {
		EuchreModel model = new EuchreModel();

		assertNull(model.getTrump());
		model.setTrump(SUIT.CLUB);
		assertEquals(model.getTrump(), SUIT.CLUB);
	}
	
	@Test
	//test getNumPasses functionality and that playerPassed() increments
	void test_getAndIncrementPasses() {
		EuchreModel model = new EuchreModel();
		
		assertEquals(model.getNumPasses(), 0);
		model.playerPassed();
		assertEquals(model.getNumPasses(), 1);
	}
	
	@Test
	// Ensure that making a move adds cards to playedcards as well as testing
	// the getter method
	void test_getPlayedCards() {
		EuchreModel model = new EuchreModel();
		
		assertEquals(model.getPlayedCards().size(), 0);
		model.deal();
		model.makeMove(0);
		assertEquals(model.getPlayedCards().size(), 1);
	}
	
	@Test
	//Ensure that dealing sets the top kitty and test the getter
	void test_getTopKitty() {
		EuchreModel model = new EuchreModel();
		
		assertNull(model.getTopKitty());
		model.deal();
		assertNotNull(model.getTopKitty());

	}
	
	@Test
	//Ensure that dealing fills the player hand
	void test_deal1() {
		EuchreModel model = new EuchreModel();
		
		assertEquals(model.getPlayer(model.getCurrentPlayer()).getHand().size(),0);
		model.deal();
		assertEquals(model.getPlayer(model.getCurrentPlayer()).getHand().size(),5);

	}
	
	@Test
	//Ensure that dealing resets the number of passes
	void test_deal2() {
		EuchreModel model = new EuchreModel();
		
		model.deal();
		model.playerPassed();
		assertEquals(model.getNumPasses(), 1);
		model.deal();
		assertEquals(model.getNumPasses(), 0);

	}
		
	@Test
	//Ensure that as the first player you are not also the dealer
	void test_isCurrentPlayerDealer() {
		EuchreModel model = new EuchreModel();
		model.setCurrentPlayerFirst();
		
		assertFalse(model.isCurrentPlayerDealer());
		

	}
	
	@Test
	//Ensure that making a move adds to the played cards arraylist
	void test_makeMove() {
		EuchreModel model = new EuchreModel();
		model.deal();
		assertEquals(model.getPlayedCards().size(), 0);
		model.makeMove(0);
		assertEquals(model.getPlayedCards().size(), 1);
	}
	
	@Test
	//Ensure that passing increments the num passes
	void test_playerPassed1() {
		EuchreModel model = new EuchreModel();
		assertEquals(model.getNumPasses(), 0);
		model.playerPassed();
		assertEquals(model.getNumPasses(), 1);
	}
	
	@Test
	//Ensure that passing increments the currentPlayer
	void test_playerPassed2() {
		EuchreModel model = new EuchreModel();
		assertEquals(model.getCurrentPlayer(), 0);
		model.playerPassed();
		assertEquals(model.getCurrentPlayer(), 1);
	}
	
	@Test
	//
	void test_swapWithTopKitty() {
		EuchreModel model = new EuchreModel();
		model.deal();
		Card testCard = model.getTopKitty();
		model.swapWithTopKitty(0);
		assertEquals(model.getPlayer(0).getCardFromHand(0), testCard);	
	}
	
	@Test
	//Ensure that the default code for botplay works
	void test_botPlayDefault() {
		EuchreModel model = new EuchreModel();
		assertEquals(model.botPlay(BOTCODE.DEFAULT), BOTCODE.DEFAULT);
	}
}
