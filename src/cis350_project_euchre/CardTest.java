package cis350_project_euchre;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class CardTest {

	@Test
	/* Tests that a card of each differnt suit and varying values can be constructed */
	void test_cardConstructor() {
		Card card1 = new Card(9, SUIT.CLUB);
		Card card2 = new Card(10, SUIT.DIAMOND);
		Card card3 = new Card(11, SUIT.HEART);
		Card card4 = new Card(12, SUIT.SPADE);

		
		assertEquals(card1.getValue(), 9);
		assertEquals(card1.getSuit(), SUIT.CLUB);
		
		assertEquals(card2.getValue(), 10);
		assertEquals(card2.getSuit(), SUIT.DIAMOND);
		
		assertEquals(card3.getValue(), 11);
		assertEquals(card3.getSuit(), SUIT.HEART);
		
		assertEquals(card4.getValue(), 12);
		assertEquals(card4.getSuit(), SUIT.SPADE);
	}

}
