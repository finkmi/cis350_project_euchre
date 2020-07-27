package cis350_project_euchre;

//CHECKSTYLE:OFF
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.text.DefaultCaret;
//CHECKSTYLE:ON

public class EuchreController extends JPanel {

	/** Array of JButtons to make up the players hand. */
	private JButton[] hand;
	private JLabel[] leftBotHand;
	private JLabel[] topBotHand;
	private JLabel[] rightBotHand;

	/** Array of labels for the user to see. */
	private JLabel[] humanIndicator;
	private JLabel[] leftBotIndicator;
	private JLabel[] topBotIndicator;
	private JLabel[] rightBotIndicator;
	
	/** Array of two for tracking score in gui */
	private JLabel[] scores;

	/** Instance of the model class. */
	private EuchreModel model;

	/** JButton for passing on the kitty. */
	private JButton passBtn;

	/** JButton to represent the top card of the kitty. */
	private JButton topKitty;

	/** ImageIcon for 9 of clubs. */
	private ImageIcon club9;
	/** ImageIcon for 10 of clubs. */
	private ImageIcon club10;
	/** ImageIcon for Jack of clubs. */
	private ImageIcon club11;
	/** ImageIcon for Queen of clubs. */
	private ImageIcon club12;
	/** ImageIcon for King of clubs. */
	private ImageIcon club13;
	/** ImageIcon for Ace of clubs. */
	private ImageIcon club14;
	/** ImageIcon for 9 of diamonds. */
	private ImageIcon diamond9;
	/** ImageIcon for 10 of diamonds. */
	private ImageIcon diamond10;
	/** ImageIcon for Jack of diamonds. */
	private ImageIcon diamond11;
	/** ImageIcon for Queen of diamonds. */
	private ImageIcon diamond12;
	/** ImageIcon for King of diamonds. */
	private ImageIcon diamond13;
	/** ImageIcon for Ace of diamonds. */
	private ImageIcon diamond14;
	/** ImageIcon for 9 of hearts. */
	private ImageIcon heart9;
	/** ImageIcon for 10 of hearts. */
	private ImageIcon heart10;
	/** ImageIcon for Jack of hearts. */
	private ImageIcon heart11;
	/** ImageIcon for Queen of hearts. */
	private ImageIcon heart12;
	/** ImageIcon for King of hearts. */
	private ImageIcon heart13;
	/** ImageIcon for Ace of hearts. */
	private ImageIcon heart14;
	/** ImageIcon for 9 of spades. */
	private ImageIcon spade9;
	/** ImageIcon for 10 of spades. */
	private ImageIcon spade10;
	/** ImageIcon for Jack of spades. */
	private ImageIcon spade11;
	/** ImageIcon for Queen of spades. */
	private ImageIcon spade12;
	/** ImageIcon for King of spades. */
	private ImageIcon spade13;
	/** ImageIcon for Ace of spades. */
	private ImageIcon spade14;
	/** ImageIcon for a black joker. */
	// CHECKSTYLE:OFF
	private ImageIcon black_joker;
	/** ImageIcon for a card back. */
	private ImageIcon card_back;
	// CHECKSTYLE:ON
	/** ImageIcon for black card scoring */
	private ImageIcon black1;
	private ImageIcon black2;
	private ImageIcon black3;
	private ImageIcon black4;
	private ImageIcon black5;
	private ImageIcon black6;
	private ImageIcon black7;
	private ImageIcon black8;
	private ImageIcon black9;
	private ImageIcon black10;
	private ImageIcon red1;
	private ImageIcon red2;
	private ImageIcon red3;
	private ImageIcon red4;
	private ImageIcon red5;
	private ImageIcon red6;
	private ImageIcon red7;
	private ImageIcon red8;
	private ImageIcon red9;
	private ImageIcon red10;

	private ImageIcon side_card_back;

	private BufferedImage myCard = null;

	/** ImageIcon for green light to indicate current player. */
	private ImageIcon lightOn;
	/** ImageIcon for off light to indicate current player. */
	private ImageIcon lightOff;

	/** Array of JLabels for displaying the played cards for a trick. */
	private JLabel[] playedCards;

	/** Boolean for clearing the game (true if full hand was played. */
	private boolean clearFlag = false;

	/** Boolean to tell if we are in trump selecting mode (or not). */
	private boolean trumpSelect;

	/** Boolean to tell if the kitty has been pressed this hand. */
	private boolean kittyHasBeenPressed;

	/** Instance of timer used for bot play. */
	private Timer timer;

	/** The index of the current player. */
	private int currentPlayer;
		
	private JTextArea gameInfo;
	private JScrollPane gameInfoDisplay;
	private Font font = new Font("Times New Roman", Font.BOLD, 10);
	
	private boolean playerHasBeenToldToSelectTrump = false;

	/******************************************************************
	 * Constructor for the Euchre controller method. Handles the starting of the
	 * Euchre game.
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
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				panelArray[i][j] = new JPanel();
				panel.add(panelArray[i][j]);
			}
		}

		/*
		 * Set the layout for the panel that has the players hand, as well as the panel
		 * that has the buttons and kitty
		 */
		panelArray[2][1].setLayout(new GridLayout(2, 5));
		panelArray[2][2].setLayout(new GridBagLayout());

		/* Set up the bot players hands (Left to Right around the table) */
		panelArray[1][0].setLayout(new GridLayout(5, 2));
		panelArray[0][1].setLayout(new GridLayout(2, 5));
		panelArray[1][2].setLayout(new GridLayout(5, 2));
		
		/* Set up the grid for score tracking */
		GridBagLayout gridBag = new GridBagLayout();
		panelArray[2][0].setLayout(gridBag);

		/* Save icons from computer to be used for gameplay */
		createIcons();

		/* Get the proper size of the cards */
		int imageWidth = club9.getIconWidth();
		int imageHeight = club9.getIconHeight();

		/* Setup layered pane for played cards */
		JLayeredPane layeredPane = new JLayeredPane();
		layeredPane.setPreferredSize(new Dimension(5 * imageWidth, imageHeight));
		/* Instantiate the array of JLabels used for played cards */
		playedCards = new JLabel[4];
		/* Add layered pane to the middle panel */
		panelArray[1][1].add(layeredPane);
		/* Setup position, and add each JLabel to the panel */
		int xorigin = (imageWidth * 5 / 2) - ((imageWidth + 150) / 2);
		for (int i = 0; i < 4; i++) {
			playedCards[i] = new JLabel();
			/*
			 * Use valueOf to take advantage of caching. Values above 127 may not be cached,
			 * but anything between -128 and 127 will be cached which will lead to better
			 * performance
			 */
			layeredPane.add(playedCards[i], Integer.valueOf(i * 50));
			playedCards[i].setBounds(xorigin + (50 * i), 00, imageWidth, imageHeight);
		}

		/* Setup button listener */
		ButtonListener listener = new ButtonListener();

		/* Instantiate the model, have it deal */
		model = new EuchreModel();
		model.deal();

		/* Instantiate the buttons for the hand */
		hand = new JButton[5];
		leftBotHand = new JLabel[5];
		topBotHand = new JLabel[5];
		rightBotHand = new JLabel[5];

		/* Instantiate the labels for the player panel */
		humanIndicator = new JLabel[5];
		leftBotIndicator = new JLabel[5];
		topBotIndicator = new JLabel[5];
		rightBotIndicator = new JLabel[5];
		
		/* Instantiate the labels for card scoring */
		scores = new JLabel[2];

		/* Set up the players information panel */
		for (int i = 0; i <= 4; i++) {
			humanIndicator[i] = new JLabel(lightOn, JLabel.CENTER);
			humanIndicator[i].setVisible(false);
			if (i == 2) {
				humanIndicator[i].setIcon(lightOn);
				humanIndicator[i].setVisible(true);
			}
			panelArray[2][1].add(humanIndicator[i]);
		}

		/* Add the hand buttons to the GUI with their appropriate card image */
		for (int i = 0; i <= 4; i++) {
			hand[i] = new JButton(getCardIcon(model.getPlayer(0).getCardFromHand(i)));
			hand[i].setPreferredSize(new Dimension(imageWidth, imageHeight));
			hand[i].setVisible(true);

			hand[i].setOpaque(false);
			hand[i].setContentAreaFilled(false);
			hand[i].setBorderPainted(false);

			/* Add action listener to hand buttons */
			hand[i].addActionListener(listener);
			panelArray[2][1].add(hand[i]);
		}

		/* Set up left bots hand */
		for (int i = 0; i <= 4; i++) {
			for (int j = 0; j <= 1; j++) {
				if (j == 0) {
					leftBotHand[i] = new JLabel(side_card_back);
					panelArray[1][0].add(leftBotHand[i]);
				} else {
					leftBotIndicator[i] = new JLabel(lightOff, JLabel.CENTER);
					if (i != 2) {
						leftBotIndicator[i].setVisible(false);
					}
					panelArray[1][0].add(leftBotIndicator[i]);
				}
			}
		}

		
		/* Set up top bots hand */
		for (int i = 0; i <= 1; i++) {
			for (int j = 0; j <= 4; j++) {
				if (i == 0) {
					topBotHand[j] = new JLabel(card_back);
					panelArray[0][1].add(topBotHand[j]);
				} else {
					topBotIndicator[j] = new JLabel(lightOff, JLabel.CENTER);
					if (j != 2) {
						topBotIndicator[j].setVisible(false);
					}
					panelArray[0][1].add(topBotIndicator[j]);
				}
			}
		}

		/* Set up right bots hand */
		for (int i = 0; i <= 4; i++) {
			for (int j = 0; j <= 1; j++) {
				if (j == 1) {
					rightBotHand[i] = new JLabel(side_card_back);
					panelArray[1][2].add(rightBotHand[i]);
				} else {
					rightBotIndicator[i] = new JLabel(lightOff, JLabel.CENTER);
					if (i != 2) {
						rightBotIndicator[i].setVisible(false);
					}
					panelArray[1][2].add(rightBotIndicator[i]);
				}
			}
		}
		
		for (int i = 0; i < 2; i++) {
			scores[i] = new JLabel(black1);
			panelArray[0][2].add(scores[i]);
		}

		/* Instantiate the pass and topKitty buttons */
		passBtn = new JButton("Pass");
		topKitty = new JButton(getCardIcon(model.getTopKitty()));

		/* Size buttons appropriately */
		topKitty.setPreferredSize(new Dimension(imageWidth, imageHeight));
		passBtn.setPreferredSize(new Dimension((int) (imageWidth * .75), (int) (imageWidth * .5)));

		GridBagConstraints c = new GridBagConstraints();

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
		passBtn.setVisible(true);
		
		/* Setting up info text and scroll pane */
		gameInfo = new JTextArea(5, 20);
		
		/* Set the caret to white... This way it won't look editable to the user.
		 * Could set the textArea to not editable, but then it won't scroll 
		 * properly to show the most recent data entry at the bottom. */
		gameInfo.setCaretColor(Color.white);
		
		/* Get the caret and save it as a variable, allowing us
		 * to set it to always update no matter what thread the change has
		 * come from. This makes the caret always go to the bottom line of the 
		 * text area. New data entry occurs at the bottom, and old entries are
		 * scrolled upwards */
		DefaultCaret caret = (DefaultCaret) gameInfo.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);

		/* Set the font for our text panel */
		gameInfo.setFont(font);
		gameInfoDisplay = new JScrollPane(gameInfo);
		/* Add some new lines so that we start at the bottom of the text box */
		for (int i = 0; i < 4; i++) {
			gameInfo.append("\n");
		}
		
		c.fill = GridBagConstraints.BOTH;
		
		/* Add the text box, and scrolling pane to the bottom/left corner */
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 2;
		c.gridheight = 1;
		c.weightx = 1;
		gridBag.setConstraints(gameInfoDisplay, c);
		panelArray[2][0].add(gameInfoDisplay);
		
		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 1;
		c.gridheight = 1;
		gridBag.setConstraints(scores[0], c);
		panelArray[2][0].add(scores[0]);
		
		c.gridx = 1;
		c.gridy = 1;
		c.gridwidth = 1;
		c.gridheight = 1;
		gridBag.setConstraints(scores[1], c);
		panelArray[2][0].add(scores[1]);

		/* Add the panel to the gui */
		add(panel);
		/* Instantiate the timer for 1 sec intervals, and start it */
		timer = new Timer(1000, new TimerListener());
		timer.start();

	}

	/******************************************************************
	 * Creates all card icons from resource files.
	 *****************************************************************/
	private void createIcons() {		
		
		Dimension screenSz = Toolkit.getDefaultToolkit().getScreenSize();
		/* Width should be height / 15, scale height to match width (ratio is W:H, 125:182) */
		int newWidth = (screenSz.height - 200) / 15;
		int newHeight = (newWidth * 182) / 125;
		
		Image resizedImg;
		
		for (int i = 0; i < 4; i++) {
			for (int j = 9; j <= 14; j++) {				
				try {
					myCard = ImageIO.read(new File("cardImages/" + j + "_of_" + (SUIT.values()[i].toString().toLowerCase()) + "s.png"));
				} catch (IOException e) {
					e.printStackTrace();
				}
				resizedImg = myCard.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
				switch (i) {
			 	case 0:
			 		switch (j) {
		 			case 9:
		 				club9 = new ImageIcon(resizedImg);
		 				break;
		 			case 10:
		 				club10 = new ImageIcon(resizedImg);
		 				break;
					case 11:
						club11 = new ImageIcon(resizedImg);
						break;
					case 12:
						club12 = new ImageIcon(resizedImg);
						break;
					case 13:
						club13 = new ImageIcon(resizedImg);
						break;
					case 14:
						club14 = new ImageIcon(resizedImg);
						break;
					default:
						/* Do nothing... not valid */
						break;
			 		}
			 		break;
			 		
			 	case 1:
			 		switch (j) {
		 			case 9:
		 				diamond9 = new ImageIcon(resizedImg);
		 				break;
		 			case 10:
		 				diamond10 = new ImageIcon(resizedImg);
		 				break;
					case 11:
						diamond11 = new ImageIcon(resizedImg);
						break;
					case 12:
						diamond12 = new ImageIcon(resizedImg);
						break;
					case 13:
						diamond13 = new ImageIcon(resizedImg);
						break;
					case 14:
						diamond14 = new ImageIcon(resizedImg);
						break;
					default:
						/* Do nothing... not valid */
						break;
			 		}
			 		break;
			 		
			 	case 2:
			 		switch (j) {
		 			case 9:
		 				heart9 = new ImageIcon(resizedImg);
		 				break;
		 			case 10:
		 				heart10 = new ImageIcon(resizedImg);
		 				break;
					case 11:
						heart11 = new ImageIcon(resizedImg);
						break;
					case 12:
						heart12 = new ImageIcon(resizedImg);
						break;
					case 13:
						heart13 = new ImageIcon(resizedImg);
						break;
					case 14:
						heart14 = new ImageIcon(resizedImg);
						break;
					default:
						/* Do nothing... not valid */
						break;
			 		}
			 		break;
			 		
			 	case 3:
			 		switch (j) {
		 			case 9:
		 				spade9 = new ImageIcon(resizedImg);
		 				break;
		 			case 10:
		 				spade10 = new ImageIcon(resizedImg);
		 				break;
					case 11:
						spade11 = new ImageIcon(resizedImg);
						break;
					case 12:
						spade12 = new ImageIcon(resizedImg);
						break;
					case 13:
						spade13 = new ImageIcon(resizedImg);
						break;
					case 14:
						spade14 = new ImageIcon(resizedImg);
						break;
					default:
						/* Do nothing... not valid */
						break;
			 		}
			 		break;
			 		
			 	default:
			 		/* Do nothing... not valid */	
			 		break;
			 }
			}
		}
		
		/* Scan the black_joker card */
		try {
			myCard = ImageIO.read(new File("cardImages/black_joker.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		resizedImg = myCard.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
		black_joker = new ImageIcon(resizedImg);
		
		/* Scan the card back */
		try {
			myCard = ImageIO.read(new File("cardImages/card_back.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		resizedImg = myCard.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
		card_back = new ImageIcon(resizedImg);
		
		/* Scan the sideways card back */
		try {
			myCard = ImageIO.read(new File("images/card_back90.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		resizedImg = myCard.getScaledInstance(newHeight, newWidth, Image.SCALE_SMOOTH);
		side_card_back = new ImageIcon(resizedImg);
		
		/* Scan the on light indicator */
		try {
			myCard = ImageIO.read(new File("images/ongreen.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		resizedImg = myCard.getScaledInstance(newWidth / 2, newWidth / 2, Image.SCALE_SMOOTH);
		lightOn = new ImageIcon(resizedImg);
		
		/* Scan the off light indicator */
		try {
			myCard = ImageIO.read(new File("images/offgreen.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		resizedImg = myCard.getScaledInstance(newWidth / 2, newWidth / 2, Image.SCALE_SMOOTH);
		lightOff = new ImageIcon(resizedImg);
		
		/* Set images for black card scoring */
		black1 = new ImageIcon("images/black1.png");
		black2 = new ImageIcon("images/black2.png");
		black3 = new ImageIcon("images/black3.png");
		black4 = new ImageIcon("images/black4.png");
		black5 = new ImageIcon("images/black5.png");
		black6 = new ImageIcon("images/black6.png");
		black7 = new ImageIcon("images/black7.png");
		black8 = new ImageIcon("images/black8.png");
		black9 = new ImageIcon("images/black9.png");
		black10 = new ImageIcon("images/black10.png");
		
		red1 = new ImageIcon("images/red1.png");
		red2 = new ImageIcon("images/red2.png");
		red3 = new ImageIcon("images/red3.png");
		red4 = new ImageIcon("images/red4.png");
		red5 = new ImageIcon("images/red5.png");
		red6 = new ImageIcon("images/red6.png");
		red7 = new ImageIcon("images/red7.png");
		red8 = new ImageIcon("images/red8.png");
		red9 = new ImageIcon("images/red9.png");
		red10 = new ImageIcon("images/red10.png");
		
		
	}

	/******************************************************************
	 * Helper function to get the correct image for a card, based on the cards
	 * stored value and suit.
	 * 
	 * @param card The card the image is wanted for.
	 * @return The imageicon corresponding to the card.
	 *****************************************************************/
	private ImageIcon getCardIcon(final Card card) {
		/*
		 * This entire function is essentially just a big lookup table to get the
		 * imageicons based on the card
		 */
		switch (card.getSuit()) {
		case CLUB:
			switch (card.getValue()) {
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
			default:
				return black_joker;
			}

		case DIAMOND:
			switch (card.getValue()) {
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
			default:
				return black_joker;
			}

		case HEART:
			switch (card.getValue()) {
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
			default:
				return black_joker;
			}

		case SPADE:
			switch (card.getValue()) {
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
			default:
				return black_joker;
			}

		default:
			return black_joker;
		}
	}

	/******************************************************************
	 * Takes care of graphically updating the players hand. Called after the player
	 * makes a move, or if dealing has occured. The model takes care of actually
	 * changing the cards, this function just does the visual update.
	 *****************************************************************/
	private void updateHand() {
		/* Go through and update each cards icon based on the card */
		for (int i = 0; i < model.getPlayer(0).getHand().size(); i++) {
			hand[i].setIcon(getCardIcon(model.getPlayer(0).getCardFromHand(i)));
		}
	}

	/******************************************************************
	 * Takes care of graphically updating the card on the top of the kitty. During
	 * trump selection mode, it should be visible. However, after trump selection
	 * mode, it should be gone.
	 *****************************************************************/
	private void updateTopKitty() {
		if (trumpSelect) {
			topKitty.setIcon(getCardIcon(model.getTopKitty()));
		} else {
			topKitty.setIcon(card_back);
		}
	}

	/******************************************************************
	 * Takes care of graphically updating the cards that have been played. This
	 * needs to happen after a trick is over as well as any time a player (human or
	 * bot) plays a card.
	 *****************************************************************/
	private void updatePlayedCards() {
		for (Card card : model.getPlayedCards()) {
			playedCards[model.getPlayedCards().indexOf(card)].setIcon(getCardIcon(card));
		}
		/*
		 * If four cards have been played, or no cards have been played, set the
		 * clearFlag. It will ensure that the played cards get cleared next time the 1
		 * sec timer occurs
		 */
		if (model.clearPlayedCards() || model.getPlayedCards().size() == 0) {
			clearFlag = true;
		}
	}

	/******************************************************************
	 * Takes the index of the current player and sets their indicator
	 * to the 'ON' position and sets the other players to 'OFF'.
	 *
	 * @param indexCurPlayer is the currentplayer index of the players 
	 * array
	 ******************************************************************/
	private void updateIndicators(final int indexCurPlayer) {
		if (indexCurPlayer == 0) {
			humanIndicator[2].setIcon(lightOn);
			leftBotIndicator[2].setIcon(lightOff);
			topBotIndicator[2].setIcon(lightOff);
			rightBotIndicator[2].setIcon(lightOff);
		}
		else if (indexCurPlayer == 1) {
			humanIndicator[2].setIcon(lightOff);
			leftBotIndicator[2].setIcon(lightOn);
			topBotIndicator[2].setIcon(lightOff);
			rightBotIndicator[2].setIcon(lightOff);
		}
		else if (indexCurPlayer == 2) {
			humanIndicator[2].setIcon(lightOff);
			leftBotIndicator[2].setIcon(lightOff);
			topBotIndicator[2].setIcon(lightOn);
			rightBotIndicator[2].setIcon(lightOff);
		}
		else {
			humanIndicator[2].setIcon(lightOff);
			leftBotIndicator[2].setIcon(lightOff);
			topBotIndicator[2].setIcon(lightOff);
			rightBotIndicator[2].setIcon(lightOn);
		}
	}
	
	private void updatePlayersCards(final int indexCurPlayer) {
		if (indexCurPlayer == 1) {
			leftBotHand[model.getPlayer(indexCurPlayer).getHand().size() - 1].setVisible(false);
		}
		else if (indexCurPlayer == 2) {
			topBotHand[model.getPlayer(indexCurPlayer).getHand().size() - 1].setVisible(false);
		}
		else if (indexCurPlayer == 3) {
			rightBotHand[model.getPlayer(indexCurPlayer).getHand().size() - 1].setVisible(false);
		}
	}
	
	/******************************************************************
	 * This function handles clearing the icons. It also makes sure to update
	 * everything else on the GUI (such as the hand, the topKitty, and the score).
	 *****************************************************************/
	private void clearPlayedCardsIcons() {
		/* Set playedCards icons all to null to make them invisible */
		for (int i = 0; i < 4; i++) {
			playedCards[i].setIcon(null);
		}
		/* Update GUI info for user */
		updateHand();
		updateTopKitty();
		updateScore();
	}

	/******************************************************************
	 * Updates the buttons for passing and the topKitty based on if we
	 * are in trump selection mode or not.
	 *****************************************************************/
	private void updateButtons() {
		passBtn.setVisible(trumpSelect);
		topKitty.setEnabled(trumpSelect);
		/* Make sure we update topKitty too */
		updateTopKitty();
	}

	/******************************************************************
	 * Update the scoring when a team wins a trick or a hand. In release two this
	 * will be put into the GUI, but for now we are using print statments to see the
	 * info.
	 *****************************************************************/
	private void updateScore() {
//		System.out.println("Tricks0: " + model.getNumTricks(0));
//		System.out.println("Tricks1: " + model.getNumTricks(1));
//		System.out.println("Score0: " + model.getScore(0));
//		System.out.println("Score1: " + model.getScore(1));
		gameInfo.append("Team 1 has " + model.getNumTricks(0) + "\n");
		gameInfo.append("Team 2 has " + model.getNumTricks(1) + "\n\n");
	}

	/******************************************************************
	 * Set all of the cards in the players hand to visible. During play, the users
	 * cards are set to invisible, so it is necessary to make sure they are set back
	 * to visible again.
	 *****************************************************************/
	private void setHandVisible() {
		for (JButton cards : hand) {
			cards.setVisible(true);
		}

		for (JLabel cards : topBotHand) {
			cards.setVisible(true);
		}

		for (JLabel cards : rightBotHand) {
			cards.setVisible(true);
		}

		for (JLabel cards : leftBotHand) {
			cards.setVisible(true);
		}
	}
	

	/******************************************************************
	 * The timerlistener class is used in this project in order to give the bots a
	 * life-like feel. Rather than have them play immediately, they are set up on a
	 * one second timer.
	 *****************************************************************/
	private class TimerListener implements ActionListener {
		public void actionPerformed(final ActionEvent e) {
			/*
			 * If the clear flag as been set, we need to clear the played cards to get ready
			 * for a new trick/hand
			 */
			if (clearFlag) {
				clearPlayedCardsIcons();
				clearFlag = false;
			}

			/*
			 * If the current player is a bot, we need to process the bots play
			 */
			if (model.getPlayer(model.getCurrentPlayer()).getIsBot()) {
				/* If we are in trump selection mode */
				if (trumpSelect) {
					/* If the kitty has been pressed, the bot should swap */
					/*
					 * We set the current player to the dealer when the kitty is pressed, so it is
					 * known that the dealer is the current player and will therefore be able to
					 * swap
					 */
					if (kittyHasBeenPressed) {
						model.botPlay(BOTCODE.SWAP);
						/*
						 * We are no longer in trump selection mode, and the kitty therefore needs to be
						 * flipped over
						 */
						trumpSelect = false;
						updateTopKitty();
					}
					/*
					 * If there havent been four passes yet, the bot should decide to hit the kitty
					 * or not. Currently, the bots logic is to always pass, but this will be updated
					 * in release 2
					 */
					else if (model.getNumPasses() < 4) {
						model.botPlay(BOTCODE.HITKITTY);
					}
					/*
					 * If neither of the other 2 cases, then the bot should select trump. Currently,
					 * the bots logic is to always pass on trump selection, but this will be updated
					 * in release 2
					 */
					else {
						trumpSelect = !(BOTCODE.TRUMP_SELECTED == model.botPlay(BOTCODE.TRUMP));
						/*
						 * If this was the 8th pass, we don't screw the dealer so just redeal and reset
						 * everything
						 */
						if (model.getNumPasses() >= 8) {
							/* Re-deal, and update graphics for newly dealt cards */
							model.deal();
							updateHand();
							updateTopKitty();
							setHandVisible();
							/*
							 * Kitty hasn't been pressed for this deal, and now we are back in trump
							 * selection mode
							 */
							trumpSelect = true;
							kittyHasBeenPressed = false;
						}
					}
				}
				/*
				 * Else, if we are not in trump selection mode, then the bot needs to decide on
				 * a card to play
				 */
				else {
					
					updatePlayersCards(model.getCurrentPlayer());
					/* If the play results in a trick being finished */
					if (BOTCODE.PLAY_TRICKFINISHED == model.botPlay(BOTCODE.PLAY)) {
						/* The trick is done, so we should evaluate the trick count */
						model.evalTricks();
						gameInfo.append("Player " + (model.getCurrentPlayer()+1) + " Won the trick!\n");

						/*
						 * If the tricks are such that the hand can be scored, we should score the hand
						 */
						if (model.evalScore()) {
							hand[0].setIcon(card_back);
							/*
							 * If the hand is scored, redeal, set cards visible, back into trump selection
							 * mode, and the kitty has not been pressed for this new hand
							 */
							model.deal();
							setHandVisible();
							System.out.println("In trump selection mode");
							trumpSelect = true;
							kittyHasBeenPressed = false;
							updateButtons();
						}
					}
					/*
					 * Get the current player after the bot move. In release 2, we will be
					 * implementing a method of seeing the current player on the GUI, but as of now
					 * this is not being used
					 */
					currentPlayer = model.getCurrentPlayer();
					/* Update the played cards after any bot move */
					updatePlayedCards();

				}
				updateIndicators(model.getCurrentPlayer());
			}
			/* If it's not the bot turn (therefore a humans turn) */
			else {
				
				if (model.getNumPasses() < 4 && trumpSelect && !playerHasBeenToldToSelectTrump) {
					playerHasBeenToldToSelectTrump = true;
					JOptionPane.showConfirmDialog(
							null,
							"Tell dealer, player " + (((model.getDealer() + 3) % 4) + 1) + ", to pick up the kitty, or pass!",
							"You are selecting trump!",
							JOptionPane.DEFAULT_OPTION,
							JOptionPane.INFORMATION_MESSAGE);
				}
				
				/*
				 * If there have been at least 4 passes and we are in trump selection mode, we
				 * should be given the chance to choose the suit
				 */
				if (model.getNumPasses() >= 4 && trumpSelect) {

					JButton[] options = { new JButton("Club"), new JButton("Diamond"), new JButton("Heart"), new JButton("Spade"), new JButton("Pass") };
					
					
					options[model.getTopKitty().getSuit().ordinal()].setEnabled(false);

					
					options[0].addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent arg0) {
							model.setTrump(SUIT.CLUB);
							model.setCurrentPlayerFirst();
							trumpSelect = false;
							updateButtons();
							
							Window w = SwingUtilities.getWindowAncestor(options[0]);

						    if (w != null) {
						      w.dispose();						    }
			            }
					});
					options[1].addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent arg0) {
							model.setTrump(SUIT.DIAMOND);
							model.setCurrentPlayerFirst();
							trumpSelect = false;
							updateButtons();
							
							Window w = SwingUtilities.getWindowAncestor(options[0]);

						    if (w != null) {
						      w.dispose();//setVisible(false);
						    }
			            }
					});
					options[2].addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent arg0) {
							model.setTrump(SUIT.HEART);
							model.setCurrentPlayerFirst();
							trumpSelect = false;
							updateButtons();
							
							Window w = SwingUtilities.getWindowAncestor(options[0]);

						    if (w != null) {
						      w.dispose();//setVisible(false);
						    }
			            }
					});
					options[3].addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent arg0) {
							model.setTrump(SUIT.SPADE);
							model.setCurrentPlayerFirst();
							trumpSelect = false;
							updateButtons();
							
							Window w = SwingUtilities.getWindowAncestor(options[0]);

						    if (w != null) {
						      w.dispose();//setVisible(false);
						    }
			            }
					});
					options[4].addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent arg0) {
							model.playerPassed();
							/*
							 * If that was the eight pass, we need to re-deal and restart (this game does
							 * not do "screw the dealer"
							 */
							if (model.getNumPasses() >= 8) {
								/* Re-deal and update GUI */
								model.deal();
								updateHand();
								updateTopKitty();
								setHandVisible();

								/*
								 * Back to trump selection, and kitty has yet to be pressed
								 */
								trumpSelect = true;
								kittyHasBeenPressed = false;
							}
							Window w = SwingUtilities.getWindowAncestor(options[0]);

						    if (w != null) {
						      w.dispose();//setVisible(false);
						    }
			            }
					});
//					Object[] options = { "Club", "Diamond", "Heart", "Spade", "Pass" };
					JOptionPane.showOptionDialog(null, "Pick the suit you would like, or pass", "Choose a suit",
							JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, null);

					updateIndicators(model.getCurrentPlayer());
				}
			}
		}
	}

	/******************************************************************
	 * The button listener class is used in this project to determine when the human
	 * player has pressed a button.
	 *****************************************************************/
	private class ButtonListener implements ActionListener {

		public void actionPerformed(final ActionEvent e) {
			/*
			 * Verify we are the current player before doing anything... we can't play if
			 * its not our turn
			 */
			if (model.getCurrentPlayer() == 0) {
				/*
				 * If the pass button was pressed, the model needs to know that the player
				 * passed in order to make it the next players turn
				 */
				if (passBtn == e.getSource()) {
					playerHasBeenToldToSelectTrump = false;
					model.playerPassed();
				}
				/*
				 * If the topKitty was pressed, this means the human player would like the suit
				 * of the top kitty card to be trump. The dealer will need to pick this card up
				 * when it's their turn
				 */
				else if (topKitty == e.getSource()) {
					playerHasBeenToldToSelectTrump = false;
					/* Set flag true */
					kittyHasBeenPressed = true;
					/* Set trump accordingly */
					model.setTrump(model.getTopKitty().getSuit());
					/*
					 * Dealer should now be the current player so they can discard a card and swap
					 * for topKitty card
					 */
					model.setCurrentPlayerDealer();
					/* Set buttons accordingly */
					passBtn.setVisible(false);
					topKitty.setEnabled(false);
				}
				/*
				 * Check if the button pressed was any of the card button (the cards in the
				 * players hand)
				 */
				for (int i = 0; i < 5; i++) {
					if (hand[i] == e.getSource()) {
						/*
						 * If we aren't in trump selection mode, the player is trying to play a card
						 */
						if (!trumpSelect) {
							/* Play the card the user pressed */
							model.makeMove(i);
							/*
							 * Check if all cards for the trick have been played
							 */
							if (model.getPlayedCards().size() >= 4) {
								/*
								 * Evaluate tricks because we just finished a trick
								 */
								model.evalTricks();
								gameInfo.append("Player " + (model.getCurrentPlayer()+1) + " Won the trick!\n");
								/*
								 * Check to see if the current trick count warrants check the score. If it does,
								 * we need to reset
								 */
								if (model.evalScore()) {
									/*
									 * We finished the previous hand, redeal, and reset GUI for the next hand
									 */
									model.deal();
									setHandVisible();
									System.out.println("In trump selection mode");
									trumpSelect = true;
									kittyHasBeenPressed = false;
									updateButtons();
								}
							}
							/*
							 * If the player just played a card, Then we should show their hand has shrunk.
							 * The model takes care of logically removing the card from the hand, but we
							 * need to update the GUI to show that too. We always remove the last card
							 * because the arraylist will automatically shift everything down when we play a
							 * card
							 */
							if (model.getPlayer(0).getHand().size() != 5) {
								hand[model.getPlayer(0).getHand().size()].setVisible(false);
								hand[model.getPlayer(0).getHand().size()].setIcon(card_back);
							}
							/*
							 * Check the current player. This will be displayed on the GUI for release 2,
							 * but currently it is unused
							 */
							currentPlayer = model.getCurrentPlayer();
							updatePlayedCards();
						}
						/*
						 * Otherwise, if the player pressed a card but they're not playing, they must be
						 * the dealer and must have to discard a card (due to one of the other players
						 * deciding to order the kitty as trump)
						 */
						else if (trumpSelect && model.isCurrentPlayerDealer() && kittyHasBeenPressed) {
							/* Swap the card in the players hand with the topKitty */
							model.swapWithTopKitty(i);
							/* Visually update the hand */
							updateHand();
							/* Set buttons and flags appropriately */
							trumpSelect = false;
							topKitty.setIcon(card_back);
							passBtn.setVisible(false);
							updateTopKitty();
						}
					}
				}
				/*
				 * Update the players hand (graphically) after the player makes any moves. We
				 * want to make sure the GUI stays up to date
				 */
				updateHand();
				updateIndicators(model.getCurrentPlayer());
			}
		}
	}
}
