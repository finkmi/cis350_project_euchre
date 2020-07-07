package cis350_project_euchre;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class EuchreController extends JPanel {

	static final String FILEPATH = "C:/Users/charl/eclipse-workspace/";
//	static final String FILEPATH = "/Users/michaelfink/workspace/";
	private JButton[] hand;

	private EuchreModel model;

	private JButton renegeBtn;
	private JButton passBtn;
	private JButton topKitty;

	private ImageIcon club9;
	private ImageIcon club10;
	private ImageIcon club11; // Jack
	private ImageIcon club12; // Queen
	private ImageIcon club13; // King
	private ImageIcon club14; // Ace
	private ImageIcon diamond9;
	private ImageIcon diamond10;
	private ImageIcon diamond11; // Jack
	private ImageIcon diamond12; // Queen
	private ImageIcon diamond13; // King
	private ImageIcon diamond14; // Ace
	private ImageIcon heart9;
	private ImageIcon heart10;
	private ImageIcon heart11; // Jack
	private ImageIcon heart12; // Queen
	private ImageIcon heart13; // King
	private ImageIcon heart14; // Ace
	private ImageIcon spade9;
	private ImageIcon spade10;
	private ImageIcon spade11; // Jack
	private ImageIcon spade12; // Queen
	private ImageIcon spade13; // King
	private ImageIcon spade14; // Ace
	private ImageIcon black_joker;
	private ImageIcon card_back;
	
	private JLabel[] playedCards;
	private int numCardsPlayed;
	
	private boolean trumpSelect;
	private boolean kittyHasBeenPressed;
	Timer timer;

	public EuchreController() {

		trumpSelect = true;
		kittyHasBeenPressed = false;
		Timer timer = new Timer(1000, new TimerListener());
		timer.start();
		
		/* Set up a panel with a 3x3 grid of JPanels */
		JPanel panel = new JPanel();		
		JPanel[][] panelArray = new JPanel[3][3];
		for(int i=0; i<3; i++) {
			for(int j=0; j<3; j++) {
				panelArray[i][j] = new JPanel();
				panel.add(panelArray[i][j]);
			}
		}
				
		createIcons();
		int imageWidth = club9.getIconWidth();
		int imageHeight = club9.getIconHeight();

		panel.setLayout(new GridLayout(3, 3, 10, 10));
		panelArray[2][1].setLayout(new GridLayout(1, 5));
		panelArray[2][2].setLayout(new GridBagLayout());
		
		/* Handling layered pane for played cards */
		/* Just use set testing[i].setIcon(black_joker) and the labels will appear */
		JLayeredPane layeredPane = new JLayeredPane();
		layeredPane.setPreferredSize(new Dimension(5*imageWidth,imageHeight));
		playedCards = new JLabel[4];
		numCardsPlayed = 0;
		panelArray[1][1].add(layeredPane);
		int xorigin = (imageWidth * 5/2) - ((imageWidth+150)/2);
		for(int i=0; i<4; i++) {
			playedCards[i] = new JLabel();
			layeredPane.add(playedCards[i], new Integer(i*50));
			playedCards[i].setBounds(xorigin+(50*i), 00, imageWidth, imageHeight);
		}
				
		GridBagConstraints c = new GridBagConstraints();
		
		
		ButtonListener listener = new ButtonListener();

		model = new EuchreModel();
		model.deal();

		hand = new JButton[5];
		
		for (int i = 0; i <= 4; i++) {
			hand[i] = new JButton(getCardIcon(model.getPlayer(0).getCardFromHand(i)));
			hand[i].setPreferredSize(new Dimension(imageWidth, imageHeight));
			hand[i].setVisible(true);
			hand[i].addActionListener(listener);
			panelArray[2][1].add(hand[i]);
		}

		renegeBtn = new JButton("Renege");
		passBtn = new JButton("Pass");
		topKitty = new JButton(getCardIcon(model.getTopKitty()));
		
		topKitty.setPreferredSize(new Dimension(imageWidth, imageHeight));
		renegeBtn.setPreferredSize(new Dimension((int)(imageWidth*.75),(int)(imageWidth*.5)));
		passBtn.setPreferredSize(new Dimension((int)(imageWidth*.75),(int)(imageWidth*.5)));
		
		renegeBtn.addActionListener(listener);
		c.gridx = 1;
		c.gridy = 1;
		panelArray[2][2].add(renegeBtn);
		
		passBtn.addActionListener(listener);
		c.gridx = 2;
		c.gridy = 1;
		panelArray[2][2].add(passBtn);
		
		topKitty.addActionListener(listener);
		c.weightx = 1;
		c.gridx = 3;
		c.gridy = 0;
		c.gridheight = 3;
		c.gridwidth = 2;
		panelArray[2][2].add(topKitty);
		
		topKitty.setVisible(true);
		renegeBtn.setVisible(false);
		passBtn.setVisible(true);
		
		add(panel);
		
		
	}

	/******************************************************************
	 * Creates all card icons from resource file
	 */
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
	
	private ImageIcon getCardIcon(Card card) {
		
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
		 		
	 		default:
	 			break;	
		 }
		return black_joker;
	}
	
	private void updateHand() {
		for(int i=0; i<model.getPlayer(0).getHand().size(); i++) {
			hand[i].setIcon(getCardIcon(model.getPlayer(0).getCardFromHand(i)));
		}
	}
	
	private void updateTopKitty() {
		if(trumpSelect)
			topKitty.setIcon(getCardIcon(model.getTopKitty()));
		else
			topKitty.setIcon(card_back);
	}
	
	private void updatePlayedCards() {
		if(model.getPlayedCards().isEmpty())
			return;
		
		for(Card card: model.getPlayedCards()) {
			playedCards[model.getPlayedCards().indexOf(card)].setIcon(getCardIcon(card));
		}
	}
	
	private class TimerListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			updateHand();
			updateTopKitty();
			updatePlayedCards();
			System.out.println("Current Player:" + model.getCurrentPlayer() + "\tgetNumPasses: " + model.getNumPasses());
			if(model.getPlayer(model.getCurrentPlayer()).getIsBot()) {				
				if(trumpSelect) {
					if(kittyHasBeenPressed) {
						model.botPlay(BOTCODE.SWAP);
						trumpSelect = false;
					}
					else if(model.getNumPasses() < 4) {
						model.botPlay(BOTCODE.HITKITTY);
					}
					else {
						trumpSelect = !model.botPlay(BOTCODE.TRUMP);
					}
				} 
				else {
					model.botPlay(BOTCODE.PLAY);
				}
			}
			else {
				if(model.getNumPasses() >= 4 && trumpSelect) {
				
					int n;
			        //Array that holds the names of all the options
					Object[] options = {"Club", "Diamond", "Heart", "Spade", "Pass"};
		            n = JOptionPane.showOptionDialog(null,
		                    "Pick the suit you would like, or pass","Choose a suit",
		                    JOptionPane.DEFAULT_OPTION,
		                    JOptionPane.PLAIN_MESSAGE,
		                    null,
		                    options,
		                    null);

		            //If the user clicks the "Custom" option
		            if(n == 0) {
		                model.setTrump(SUIT.CLUB);
		                model.setCurrentPlayerFirst();
		                trumpSelect = false;
		            }

		            //If the user clicks the "Medium" option set board to 16x16
		            //set numMines to 40
		            else if(n == 1) {
		            	model.setTrump(SUIT.DIAMOND);
		            	model.setCurrentPlayerFirst();
		                trumpSelect = false;

		            }

		            //If the user clicks "Hard" option set board to 16x30
		            //set numMines to 99
		            else if(n == 2) {
		            	model.setTrump(SUIT.HEART);
		            	model.setCurrentPlayerFirst();
		                trumpSelect = false;

		            }

		            //If the user clicks "Easy" option set board to 9x9
		            //set numMines to 9
		            else if (n == 3){
		            	model.setTrump(SUIT.SPADE);
		            	model.setCurrentPlayerFirst();
		                trumpSelect = false;

		            }
		            else {
		            	model.playerPassed();
//		            	for(int i=0; i<3; i++) {
//		            		model.botPlay(BOTCODE.TRUMP);
//		            	}
		            }
		        }
			}
		}
	}

	private class ButtonListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			if(model.getCurrentPlayer() == 0) {
				if(passBtn == e.getSource()) {
					model.playerPassed();					
				}
				else if(renegeBtn == e.getSource()) {
	//				System.out.println("renegeBtn pressed");
				}
				else if(topKitty == e.getSource()) {
					kittyHasBeenPressed = true;
					model.setTrump(model.getTopKitty().getSuit());
					model.setCurrentPlayerDealer();
					passBtn.setVisible(false);
					renegeBtn.setVisible(true);
					
					topKitty.setEnabled(false);
				}
				for(int i=0; i<5; i++) {
					if(hand[i] == e.getSource()) {
						if(!trumpSelect) {
							model.makeMove(i);
							hand[model.getPlayer(0).getHand().size()].setVisible(false);
						}
						else if(trumpSelect && model.isCurrentPlayerDealer() && kittyHasBeenPressed) {
							model.swapWithTopKitty(i);
							updateHand();
							trumpSelect = false;
							topKitty.setIcon(card_back);
							passBtn.setVisible(false);
							renegeBtn.setVisible(true);
						}
						else {
							/* never enter here */
						}
					}
				}
				updateHand();
			}
			
		}

	}

}
