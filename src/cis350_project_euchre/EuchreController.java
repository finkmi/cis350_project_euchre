package cis350_project_euchre;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class EuchreController extends JPanel {

	/** Filepath defined for images on each teammates computer */
	static final String FILEPATH = "C:/Users/charl/eclipse-workspace/";
//	static final String FILEPATH = "/Users/michaelfink/workspace/";
	
	/** Array of JButtons to make up the players hand */
	private JButton[] hand;

	/** Instance of the model class */
	private EuchreModel model;

	/** JButton for renege */
	private JButton renegeBtn;
	
	/** JButton for passing on the kitty */
	private JButton passBtn;
	
	/** JButton to represent the top card of the kitty */
	private JButton topKitty;

	/** ImageIcon for 9 of clubs */
	private ImageIcon club9;
	/** ImageIcon for 10 of clubs */
	private ImageIcon club10;
	/** ImageIcon for Jack of clubs */
	private ImageIcon club11;
	/** ImageIcon for Queen of clubs */
	private ImageIcon club12;
	/** ImageIcon for King of clubs */
	private ImageIcon club13;
	/** ImageIcon for Ace of clubs */
	private ImageIcon club14;
	/** ImageIcon for 9 of diamonds */
	private ImageIcon diamond9;
	/** ImageIcon for 10 of diamonds */
	private ImageIcon diamond10;
	/** ImageIcon for Jack of diamonds */
	private ImageIcon diamond11;
	/** ImageIcon for Queen of diamonds */
	private ImageIcon diamond12;
	/** ImageIcon for King of diamonds */
	private ImageIcon diamond13;
	/** ImageIcon for Ace of diamonds */
	private ImageIcon diamond14; 
	/** ImageIcon for 9 of hearts */
	private ImageIcon heart9;
	/** ImageIcon for 10 of hearts */
	private ImageIcon heart10;
	/** ImageIcon for Jack of hearts */
	private ImageIcon heart11;
	/** ImageIcon for Queen of hearts */
	private ImageIcon heart12;
	/** ImageIcon for King of hearts */
	private ImageIcon heart13;
	/** ImageIcon for Ace of hearts */
	private ImageIcon heart14;
	/** ImageIcon for 9 of spades */
	private ImageIcon spade9;
	/** ImageIcon for 10 of spades */
	private ImageIcon spade10;
	/** ImageIcon for Jack of spades */
	private ImageIcon spade11;
	/** ImageIcon for Queen of spades */
	private ImageIcon spade12;
	/** ImageIcon for King of spades */
	private ImageIcon spade13;
	/** ImageIcon for Ace of spades */
	private ImageIcon spade14;
	/** ImageIcon for a black joker */
	private ImageIcon black_joker;
	/** ImageIcon for a card back */
	private ImageIcon card_back;
	
	/** Array of JLabels for displaying the played cards for a trick */
	private JLabel[] playedCards;
	
	/** Boolean for clearing the game (true if full hand was played */
	private boolean clearFlag = false;
	
	/** Boolean to tell if we are in trump selecting mode (or not) */
	private boolean trumpSelect;
	
	/** Boolean to tell if the kitty has been pressed this hand */
	private boolean kittyHasBeenPressed;
	
	/** Instance of timer used for bot play */
	Timer timer;
	
	/** The index of the current player */
	private int currentPlayer;

	/******************************************************************
	 * Constructor for the Euchre controller method. Handles the 
	 * starting of the Euchre game.
	 *****************************************************************/
	public EuchreController() {

		/* We start in trump selection mode, and kitty not pressed */
		trumpSelect = true;
		kittyHasBeenPressed = false;
		
		/* Set up a panel with a 3x3 grid of JPanels */
		JPanel panel = new JPanel();
		/* Set up all of the JPanels in a grid layout */
		panel.setLayout(new GridLayout(3, 3, 10, 10));
		JPanel[][] panelArray = new JPanel[3][3];
		for(int i=0; i<3; i++) {
			for(int j=0; j<3; j++) {
				panelArray[i][j] = new JPanel();
				panel.add(panelArray[i][j]);
			}
		}
		
		/* Set the layout for the panel that has the players hand,
		 * as well as the panel that has the buttons and kitty */
		panelArray[2][1].setLayout(new GridLayout(1, 5));
		panelArray[2][2].setLayout(new GridBagLayout());
				
		/* Save icons from computer to be used for gameplay */
		createIcons();
		
		/* Get the proper size of the cards */
		int imageWidth = club9.getIconWidth();
		int imageHeight = club9.getIconHeight();
		
		/* Setup layered pane for played cards */
		JLayeredPane layeredPane = new JLayeredPane();
		layeredPane.setPreferredSize(new Dimension(5*imageWidth,imageHeight));
		/* Instantiate the array of JLabels used for played cards */
		playedCards = new JLabel[4];
		/* Add layered pane to the middle panel */
		panelArray[1][1].add(layeredPane);
		/* Setup position, and add each JLabel to the panel */
		int xorigin = (imageWidth * 5/2) - ((imageWidth+150)/2);
		for(int i=0; i<4; i++) {
			playedCards[i] = new JLabel();
			layeredPane.add(playedCards[i], new Integer(i*50));
			playedCards[i].setBounds(xorigin+(50*i), 00, imageWidth, imageHeight);
		}
		
		/* Setup button listener */
		ButtonListener listener = new ButtonListener();

		/* Instantiate the model, have it deal */
		model = new EuchreModel();
		model.deal();

		/* Instantiate the buttons for the hand */
		hand = new JButton[5];
		
		/* Add the hand buttons to the GUI with their appropriate card image */
		for (int i = 0; i <= 4; i++) {
			hand[i] = new JButton(getCardIcon(model.getPlayer(0).getCardFromHand(i)));
			hand[i].setPreferredSize(new Dimension(imageWidth, imageHeight));
			hand[i].setVisible(true);
			/* Add action listener to hand buttons */
			hand[i].addActionListener(listener);
			panelArray[2][1].add(hand[i]);
		}

		/* Instantiate the renege, pass, and topKitty buttons */
		renegeBtn = new JButton("Renege");
		passBtn = new JButton("Pass");
		topKitty = new JButton(getCardIcon(model.getTopKitty()));

		/* Size buttons appropriately */
		topKitty.setPreferredSize(new Dimension(imageWidth, imageHeight));
		renegeBtn.setPreferredSize(new Dimension((int)(imageWidth*.75),(int)(imageWidth*.5)));
		passBtn.setPreferredSize(new Dimension((int)(imageWidth*.75),(int)(imageWidth*.5)));
		
		/* Add action listener to renege button, and set spacing */
		renegeBtn.addActionListener(listener);
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 1;
		c.gridy = 1;
		panelArray[2][2].add(renegeBtn);
		
		/* Add action listener to pass button, and set spacing */
		passBtn.addActionListener(listener);
		c.gridx = 2;
		c.gridy = 1;
		panelArray[2][2].add(passBtn);
		
		/* Add action listener to topKitty button, and set spacing */
		topKitty.addActionListener(listener);
		c.weightx = 1;
		c.gridx = 3;
		c.gridy = 0;
		c.gridheight = 3;
		c.gridwidth = 2;
		panelArray[2][2].add(topKitty);
		
		/* Set the buttons visible/invisible to prepare to start the game */
		topKitty.setVisible(true);
		renegeBtn.setVisible(false);
		passBtn.setVisible(true);
		
		/* Add the panel to the gui */
		add(panel);
		/* Instantiate the timer for 1 sec intervals, and start it */
		Timer timer = new Timer(1000, new TimerListener());
		timer.start();
		
	}

	/******************************************************************
	 * Creates all card icons from resource files.
	 *****************************************************************/
	private void createIcons() {
		/* Sets Images for clubs */
		club9 = new ImageIcon(FILEPATH + "/cis350_project_euchre/cardImages/9_of_clubs.png");
		club10 = new ImageIcon(FILEPATH + "/cis350_project_euchre/cardImages/10_of_clubs.png");
		club11 = new ImageIcon(FILEPATH + "/cis350_project_euchre/cardImages/jack_of_clubs.png");
		club12 = new ImageIcon(FILEPATH + "/cis350_project_euchre/cardImages/queen_of_clubs.png");
		club13 = new ImageIcon(FILEPATH + "/cis350_project_euchre/cardImages/king_of_clubs.png");
		club14 = new ImageIcon(FILEPATH + "/cis350_project_euchre/cardImages/ace_of_clubs.png");

		/* Set Images for diamonds */
		diamond9 = new ImageIcon(FILEPATH + "/cis350_project_euchre/cardImages/9_of_diamonds.png");
		diamond10 = new ImageIcon(FILEPATH + "/cis350_project_euchre/cardImages/10_of_diamonds.png");
		diamond11 = new ImageIcon(FILEPATH + "/cis350_project_euchre/cardImages/jack_of_diamonds.png");
		diamond12 = new ImageIcon(FILEPATH + "/cis350_project_euchre/cardImages/queen_of_diamonds.png");
		diamond13 = new ImageIcon(FILEPATH + "/cis350_project_euchre/cardImages/king_of_diamonds.png");
		diamond14 = new ImageIcon(FILEPATH + "/cis350_project_euchre/cardImages/ace_of_diamonds.png");

		/* Set Images for hearts */
		heart9 = new ImageIcon(FILEPATH + "/cis350_project_euchre/cardImages/9_of_hearts.png");
		heart10 = new ImageIcon(FILEPATH + "/cis350_project_euchre/cardImages/10_of_hearts.png");
		heart11 = new ImageIcon(FILEPATH + "/cis350_project_euchre/cardImages/jack_of_hearts.png");
		heart12 = new ImageIcon(FILEPATH + "/cis350_project_euchre/cardImages/queen_of_hearts.png");
		heart13 = new ImageIcon(FILEPATH + "/cis350_project_euchre/cardImages/king_of_hearts.png");
		heart14 = new ImageIcon(FILEPATH + "/cis350_project_euchre/cardImages/ace_of_hearts.png");

		/* Set Images for spades */
		spade9 = new ImageIcon(FILEPATH + "/cis350_project_euchre/cardImages/9_of_spades.png");
		spade10 = new ImageIcon(FILEPATH + "/cis350_project_euchre/cardImages/10_of_spades.png");
		spade11 = new ImageIcon(FILEPATH + "/cis350_project_euchre/cardImages/jack_of_spades.png");
		spade12 = new ImageIcon(FILEPATH + "/cis350_project_euchre/cardImages/queen_of_spades.png");
		spade13 = new ImageIcon(FILEPATH + "/cis350_project_euchre/cardImages/king_of_spades.png");
		spade14 = new ImageIcon(FILEPATH + "/cis350_project_euchre/cardImages/ace_of_spades.png");
		
		black_joker = new ImageIcon(FILEPATH + "/cis350_project_euchre/cardImages/black_joker.png");
		card_back = new ImageIcon(FILEPATH + "/cis350_project_euchre/cardImages/card_back.png");
	}
	
	/******************************************************************
	 * Helper function to get the correct image for a card, based
	 * on the cards stored value and suit.
	 * 
	 * @param card The card the image is wanted for.
	 * @return The imageicon corresponding to the card.
	 *****************************************************************/
	private ImageIcon getCardIcon(Card card) {
		/* This entire function is essentially just a big lookup 
		 * table to get the imageicons based on the card */
		 switch (card.getSuit()) {
		 	case CLUB:
		 		switch(card.getValue()) {
	 			case 9:
	 				return club9;
	 			case 10:
	 				return club10;
				case 11:
					return club11;	
				case 12:
					return club12;	
				case 13:
					return club13;	
				case 14:
					return club14;	
		 		}	 		
		 		break;
		 		
		 	case DIAMOND:
		 		switch(card.getValue()) {
	 			case 9:
	 				return diamond9;
	 			case 10:
	 				return diamond10;
				case 11:
					return diamond11;	
				case 12:
					return diamond12;	
				case 13:
					return diamond13;	
				case 14:
					return diamond14;	
		 		}
		 		break;
		 		
		 	case HEART:
		 		switch(card.getValue()) {
	 			case 9:
	 				return heart9;
	 			case 10:
	 				return heart10;
				case 11:
					return heart11;	
				case 12:
					return heart12;	
				case 13:
					return heart13;	
				case 14:
					return heart14;	
		 		}
		 		break;
		 		
		 	case SPADE:
		 		switch(card.getValue()) {
	 			case 9:
	 				return spade9;
	 			case 10:
	 				return spade10;
				case 11:
					return spade11;	
				case 12:
					return spade12;	
				case 13:
					return spade13;	
				case 14:
					return spade14;	
		 		}
		 		break;
		 }
		 /* A default return is needed, but should never be hit */
		 return black_joker;
	}
	
	/******************************************************************
	 * Takes care of graphically updating the players hand. Called 
	 * after the player makes a move, or if dealing has occured. The
	 * model takes care of actually changing the cards, this function
	 * just does the visual update.
	 *****************************************************************/
	private void updateHand() {
		/* Go through and update each cards icon based on the card */
		for(int i=0; i<model.getPlayer(0).getHand().size(); i++) {
			hand[i].setIcon(getCardIcon(model.getPlayer(0).getCardFromHand(i)));
		}
	}
	
	/******************************************************************
	 * Takes care of graphically updating the card on the top of the
	 * kitty. During trump selection mode, it should be visible. 
	 * However, after trump selection mode, it should be gone.
	 *****************************************************************/
	private void updateTopKitty() {
		if(trumpSelect)
			topKitty.setIcon(getCardIcon(model.getTopKitty()));
		else
			topKitty.setIcon(card_back);
	}
	
	/******************************************************************
	 * Takes care of graphically updating the cards that have been
	 * played. This needs to happen after a trick is over as well as
	 * any time a player (human or bot) plays a card.
	 *****************************************************************/
	private void updatePlayedCards() {
		for(Card card: model.getPlayedCards()) {
			playedCards[model.getPlayedCards().indexOf(card)].setIcon(getCardIcon(card));
		}
		/* If four cards have been played, or no cards have been played,
		 * set the clearFlag. It will ensure that the played cards get
		 * cleared next time the 1 sec timer occurs */
		if(model.clearPlayedCards() || model.getPlayedCards().size() == 0) {
			clearFlag = true;
		} 
	}
	
	/******************************************************************
	 * This function handles clearing the icons. It also makes sure to
	 * update everything else on the GUI (such as the hand, the 
	 * topKitty, and the score).
	 *****************************************************************/
	private void clearPlayedCardsIcons() {
		/* Set playedCards icons all to null to make them invisible */
		for(int i=0; i<4; i++) {
			playedCards[i].setIcon(null);
		}
		/* Update GUI info for user */
		updateHand();
		updateTopKitty();
		updateScore();
	}
	
	/******************************************************************
	 * Updates the buttons for passing, renegeing, and the topKitty
	 * based on if we are in trump selection mode or not.
	 *****************************************************************/
	private void updateButtons() {
		passBtn.setVisible(trumpSelect);
		renegeBtn.setVisible(!trumpSelect);
		topKitty.setEnabled(trumpSelect);
		/* Make sure we update topKitty too */
		updateTopKitty();
	}
	
	/******************************************************************
	 * Update the scoring when a team wins a trick or a hand. In 
	 * release two this will be put into the GUI, but for now we 
	 * are using print statments to see the info.
	 *****************************************************************/
	private void updateScore() {
		System.out.println("Tricks0: " + model.getNumTricks(0));
		System.out.println("Tricks1: " + model.getNumTricks(1));
		System.out.println("Score0: " + model.getScore(0));
		System.out.println("Score1: " + model.getScore(1));	
	}
	
	/******************************************************************
	 * Set all of the cards in the players hand to visible. During
	 * play, the users cards are set to invisible, so it is necessary
	 * to make sure they are set back to visible again.
	 *****************************************************************/
	private void setHandVisible() {
		for(JButton cards : hand)
			cards.setVisible(true);
	}
	
	/******************************************************************
	 * The timerlistener class is used in this project in order
	 * to give the bots a life-like feel. Rather than have them play
	 * immediately, they are set up on a one second timer.
	 *****************************************************************/
	private class TimerListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			/* If the clear flag as been set, we need to clear the 
			 * played cards to get ready for a new trick/hand */
			if(clearFlag) {
				clearPlayedCardsIcons();
				clearFlag = false;
			}
			
			/* If the current player is a bot, we need to process
			 * the bots play */
			if(model.getPlayer(model.getCurrentPlayer()).getIsBot()) {
				/* If we are in trump selection mode */
				if(trumpSelect) {
					/* If the kitty has been pressed, the bot should swap */
					/* We set the current player to the dealer when the kitty
					 * is pressed, so it is known that the dealer is the
					 * current player and will therefore be able to swap */
					if(kittyHasBeenPressed) {
						model.botPlay(BOTCODE.SWAP);
						/* We are no longer in trump selection mode, and the
						 * kitty therefore needs to be flipped over */
						trumpSelect = false;
						updateTopKitty();
					}
					/* If there havent been four passes yet, the bot should
					 * decide to hit the kitty or not. Currently, the bots
					 * logic is to always pass, but this will be updated in
					 * release 2 */
					else if(model.getNumPasses() < 4) {
						model.botPlay(BOTCODE.HITKITTY);
					}
					/* If neither of the other 2 cases, then the bot should
					 * select trump. Currently, the bots logic is to always
					 * pass on trump selection, but this will be updated in
					 * release 2 */
					else {
						trumpSelect = !(BOTCODE.TRUMP_SELECTED == model.botPlay(BOTCODE.TRUMP));
						/* If this was the 8th pass, we don't screw the dealer
						 * so just redeal and reset everything */
						if(model.getNumPasses() >= 8) {
		            		/* Re-deal, and update graphics for newly dealt cards */
							model.deal();
		            		updateHand();
		            		updateTopKitty();
		            		setHandVisible();
		            		/* Kitty hasn't been pressed for this deal, and 
		            		 * now we are back in trump selection mode */
		            		trumpSelect = true;
		            		kittyHasBeenPressed = false;
		        		}
					}
				} 
				/* Else, if we are not in trump selection mode, then the bot
				 * needs to decide on a card to play */
				else {
					/* If the play results in a trick being finished */
					if (BOTCODE.PLAY_TRICKFINISHED == model.botPlay(BOTCODE.PLAY)) {
						/* The trick is done, so we should evaluate the trick count */
						model.evalTricks();
						/* If the tricks are such that the hand can be scored, we
						 * should score the hand */
						if(model.evalScore()) {
							/* If the hand is scored, redeal, set cards visible,
							 * back into trump selection mode, and the kitty
							 * has not been pressed for this new hand */
							model.deal();
							setHandVisible();
							System.out.println("In trump selection mode");
							trumpSelect = true;
							kittyHasBeenPressed = false;
							updateButtons();
						}
					}
					/* Get the current player after the bot move.
					 * In release 2, we will be implementing a method
					 * of seeing the current player on the GUI, but as 
					 * of now this is not being used */
					currentPlayer = model.getCurrentPlayer();
					/* Update the played cards after any bot move */
					updatePlayedCards();
					
				}
			}
			/* If it's not the bot turn (therefore a humans turn) */
			else {
				/* If there have been at least 4 passes and we
				 * are in trump selection mode, we should be 
				 * given the chance to choose the suit */
				if(model.getNumPasses() >= 4 && trumpSelect) {
				
					int n;
			        /* Array that holds the names of all the options */
					Object[] options = {"Club", "Diamond", "Heart", "Spade", "Pass"};
		            n = JOptionPane.showOptionDialog(null,
		                    "Pick the suit you would like, or pass","Choose a suit",
		                    JOptionPane.DEFAULT_OPTION,
		                    JOptionPane.PLAIN_MESSAGE,
		                    null,
		                    options,
		                    null);

		            /* If the user clicks the "Club" option */
		            if(n == 0) {
		                model.setTrump(SUIT.CLUB);
		                model.setCurrentPlayerFirst();
		                trumpSelect = false;
		                updateButtons();
		            }

		            /* If the user clicks the "Diamond" option */ 
		            else if(n == 1) {
		            	model.setTrump(SUIT.DIAMOND);
		            	model.setCurrentPlayerFirst();
		                trumpSelect = false;
		                updateButtons();
		            }

		            /* If the user clicks the "Heart" option */
		            else if(n == 2) {
		            	model.setTrump(SUIT.HEART);
		            	model.setCurrentPlayerFirst();
		                trumpSelect = false;
		                updateButtons();
		            }

		            /* If the user clicks the "Spade" option */
		            else if (n == 3){
		            	model.setTrump(SUIT.SPADE);
		            	model.setCurrentPlayerFirst();
		                trumpSelect = false;
		                updateButtons();
		            }
		            /* Else, either the player clicked the "pass"
		             * option or exited out of the pop-up, either way,
		             * it counts as a pass */
		            else {
		            	model.playerPassed();
		            	/* If that was the eight pass, we need
		            	 * to re-deal and restart (this game
		            	 * does not do "screw the dealer" */
		            	if(model.getNumPasses() >= 8) {
		            		/* Re-deal and update GUI */
		            		model.deal();
		            		updateHand();
		            		updateTopKitty();
		            		setHandVisible();
		            		
		            		/* Back to trump selection, and kitty
		            		 * has yet to be pressed */
		            		trumpSelect = true;
		            		kittyHasBeenPressed = false;
		        		}
		            }
		        }
			}
		}
	}

	/******************************************************************
	 * The button listener class is used in this project to determine
	 * when the human player has pressed a button.
	 *****************************************************************/
	private class ButtonListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			/* Verify we are the current player before doing
			 * anything... we can't play if its not our turn */
			if(model.getCurrentPlayer() == 0) {
				/* If the pass button was pressed, the model needs
				 * to know that the player passed in order to make 
				 * it the next players turn */
				if(passBtn == e.getSource()) {
					model.playerPassed();					
				}
				/* If the renege button was pressed, do nothing yet.
				 * This functionality will be added in release 2 */
				else if(renegeBtn == e.getSource()) {
	//				System.out.println("renegeBtn pressed");
				}
				/* If the topKitty was pressed, this means the human
				 * player would like the suit of the top kitty card
				 * to be trump. The dealer will need to pick this card
				 * up when it's their turn */
				else if(topKitty == e.getSource()) {
					/* Set flag true */
					kittyHasBeenPressed = true;
					/* Set trump accordingly */
					model.setTrump(model.getTopKitty().getSuit());
					/* Dealer should now be the current player so they 
					 * can discard a card and swap for topKitty card */
					model.setCurrentPlayerDealer();
					/* Set buttons accordingly */
					passBtn.setVisible(false);
					renegeBtn.setVisible(true);
					topKitty.setEnabled(false);
				}
				/* Check if the button pressed was any of the card 
				 * button (the cards in the players hand) */
				for(int i=0; i<5; i++) {
					if(hand[i] == e.getSource()) {
						/* If we aren't in trump selection mode, the 
						 * player is trying to play a card */
						if(!trumpSelect) {
							/* Play the card the user pressed */
							model.makeMove(i);
							/* Check if all cards for the trick have
							 * been played */
							if(model.getPlayedCards().size() >= 4) {
								/* Evaluate tricks because we just 
								 * finished a trick */
								model.evalTricks();
								/* Check to see if the current trick
								 * count warrants check the score. If
								 * it does, we need to reset */
								if(model.evalScore()) {
									/* We finished the previous hand,
									 * redeal, and reset GUI for the
									 * next hand */
									model.deal();
									setHandVisible();
									System.out.println("In trump selection mode");
									trumpSelect = true;
									kittyHasBeenPressed = false;
									updateButtons();
								}
							}
							/* If the player just played a card, Then
							 * we should show their hand has shrunk. 
							 * The model takes care of logically removing
							 * the card from the hand, but we need to update
							 * the GUI to show that too. We always remove the 
							 * last card because the arraylist will 
							 * automatically shift everything down when
							 * we play a card */
							if(model.getPlayer(0).getHand().size() != 5) {
								hand[model.getPlayer(0).getHand().size()].setVisible(false);
								hand[model.getPlayer(0).getHand().size()].setIcon(card_back);
							}
							/* Check the current player. This will be displayed
							 * on the GUI for release 2, but currently it is unused */
							currentPlayer = model.getCurrentPlayer();
							updatePlayedCards();
						}
						/* Otherwise, if the player pressed a card but they're not playing,
						 * they must be the dealer and must have to discard a card
						 * (due to one of the other players deciding to order the kitty
						 * as trump) */
						else if(trumpSelect && model.isCurrentPlayerDealer() && kittyHasBeenPressed) {
							/* Swap the card in the players hand with the topKitty */
							model.swapWithTopKitty(i);
							/* Visually update the hand */
							updateHand();
							/* Set buttons and flags appropriately */
							trumpSelect = false;
							topKitty.setIcon(card_back);
							passBtn.setVisible(false);
							renegeBtn.setVisible(true);
							updateTopKitty();
						}
						/* This should never be entered, but was added just
						 * in case we at somepoint need to use this option */
						else {
							/* never enter here */
						}
					}
				}
				/* Update the players hand (graphically) after the player makes
				 * any moves. We want to make sure the GUI stays up to date */
				updateHand();
			}
			
		}

	}

}
