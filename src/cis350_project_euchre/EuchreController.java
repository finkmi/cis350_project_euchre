package cis350_project_euchre;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class EuchreController extends JPanel {

	static final String FILEPATH = "C:/Users/charl/eclipse-workspace/";
	private JButton[] hand;

	private EuchreModel model;

	private JButton renegeBtn;
	private JButton passBtn;
	private JButton potentialTrumpBtn;

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

	public EuchreController() {

		JPanel panel = new JPanel();
		JPanel centerPanel = new JPanel();
		JPanel handPanel = new JPanel();
		BorderLayout layout = new BorderLayout();
		layout.setVgap(10); /* vertical gap between components in panel */
		
		createIcons();

		panel.setLayout(layout);
		handPanel.setLayout(new GridLayout(1, 5));
		centerPanel.setLayout(new GridLayout(1, 3));

		panel.add(handPanel, BorderLayout.SOUTH);
		panel.add(centerPanel, BorderLayout.NORTH);

		ButtonListener listener = new ButtonListener();

		model = new EuchreModel();

		hand = new JButton[5];
		
		int imageWidth = club9.getIconWidth();
		int imageHeight = club9.getIconHeight();
		
		for (int i = 0; i <= 4; i++) {
			hand[i] = new JButton();
			hand[i].setPreferredSize(new Dimension(imageWidth, imageHeight));
			hand[i].setVisible(true);
			hand[i].addActionListener(listener);
			handPanel.add(hand[i]);
		}

		renegeBtn = new JButton("Renege");
		passBtn = new JButton("Pass");
		potentialTrumpBtn = new JButton("Trump");
		renegeBtn.addActionListener(listener);
		passBtn.addActionListener(listener);
		potentialTrumpBtn.addActionListener(listener);
		centerPanel.add(renegeBtn);
		centerPanel.add(passBtn);
		centerPanel.add(potentialTrumpBtn);

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
	}

	private class ButtonListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {

		}

	}

}
