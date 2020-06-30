package cis350_project_euchre;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


public class EuchreController extends JPanel{
	
    private JButton[] hand;
    
    private EuchreModel model;
    
    private JButton renegeBtn;
    private JButton passBtn;
    private JButton potentialTrumpBtn;
    
    private ImageIcon club9;
    private ImageIcon club10;
    private ImageIcon club11;		//Jack
    private ImageIcon club12;		//Queen
    private ImageIcon club13;		//King
    private ImageIcon club14;		//Ace
    private ImageIcon diamond9;
    private ImageIcon diamond10;
    private ImageIcon diamond11;	//Jack
    private ImageIcon diamond12;	//Queen
    private ImageIcon diamond13;	//King
    private ImageIcon diamond14;	//Ace
    private ImageIcon heart9;
    private ImageIcon heart10;
    private ImageIcon heart11;		//Jack
    private ImageIcon heart12;		//Queen
    private ImageIcon heart13;		//King
    private ImageIcon heart14;		//Ace
    private ImageIcon spade9;
    private ImageIcon spade10;
    private ImageIcon spade11;		//Jack
    private ImageIcon spade12;		//Queen
    private ImageIcon spade13;		//King
    private ImageIcon spade14;		//Ace

    
    public EuchreController() {
    	
    	JPanel panel = new JPanel();
    	JPanel centerPanel = new JPanel();
    	JPanel handPanel = new JPanel();

    	panel.setLayout(new BorderLayout());
    	handPanel.setLayout(new GridLayout(1,5));
    	centerPanel.setLayout(new GridLayout(1,3));
    	panel.add(handPanel, BorderLayout.SOUTH);
    	panel.add(centerPanel, BorderLayout.NORTH);

		ButtonListener listener = new ButtonListener();
		
		model = new EuchreModel();
		
		hand = new JButton[5];
		for(int i = 0; i<=4; i++) {
			hand[i] = new JButton("" + i);
			hand[i].setVisible(true);
			hand[i].addActionListener(listener);
			handPanel.add(hand[i]);
		}
		
		renegeBtn = new JButton("Renege");
		passBtn = new JButton("Pass");
		potentialTrumpBtn = new JButton("trump");
		renegeBtn.addActionListener(listener);
		passBtn.addActionListener(listener);
		potentialTrumpBtn.addActionListener(listener);
		centerPanel.add(renegeBtn);
		centerPanel.add(passBtn);
		centerPanel.add(potentialTrumpBtn);
		
//		createIcons();
				
		add(panel);
    }
    
    /******************************************************************
     * Creates all card icons from resource file
     */
//    private void createIcons() {
//        // Sets the Image for clubs
//        club9 = new ImageIcon("/Users/michaelfink/IdeaProjects" +
//                "/fink_projects163/resources/wRook.png");
//        club10 = new ImageIcon("/Users/michaelfink/IdeaProjects" +
//                "/fink_projects163/resources/wBishop.png");
//        club11 = new ImageIcon("/Users/michaelfink/IdeaProjects" +
//                "/fink_projects163/resources/wQueen.png");
//        club12 = new ImageIcon("/Users/michaelfink/IdeaProjects" +
//                "/fink_projects163/resources/wKing.png");
//        club13 = new ImageIcon( "/Users/michaelfink/IdeaProjects" +
//                "/fink_projects163/resources/wPawn.png");
//        club14 = new ImageIcon("/Users/michaelfink/IdeaProjects" +
//                "/fink_projects163/resources/wKnight.png");
//        
//        // Sets the Image for diamond
//        club9 = new ImageIcon("/Users/michaelfink/IdeaProjects" +
//                "/fink_projects163/resources/wRook.png");
//        club10 = new ImageIcon("/Users/michaelfink/IdeaProjects" +
//                "/fink_projects163/resources/wBishop.png");
//        club11 = new ImageIcon("/Users/michaelfink/IdeaProjects" +
//                "/fink_projects163/resources/wQueen.png");
//        club12 = new ImageIcon("/Users/michaelfink/IdeaProjects" +
//                "/fink_projects163/resources/wKing.png");
//        club13 = new ImageIcon( "/Users/michaelfink/IdeaProjects" +
//                "/fink_projects163/resources/wPawn.png");
//        club14 = new ImageIcon("/Users/michaelfink/IdeaProjects" +
//                "/fink_projects163/resources/wKnight.png");
//        
//        // Sets the Image for heart
//        club9 = new ImageIcon("/Users/michaelfink/IdeaProjects" +
//                "/fink_projects163/resources/wRook.png");
//        club10 = new ImageIcon("/Users/michaelfink/IdeaProjects" +
//                "/fink_projects163/resources/wBishop.png");
//        club11 = new ImageIcon("/Users/michaelfink/IdeaProjects" +
//                "/fink_projects163/resources/wQueen.png");
//        club12 = new ImageIcon("/Users/michaelfink/IdeaProjects" +
//                "/fink_projects163/resources/wKing.png");
//        club13 = new ImageIcon( "/Users/michaelfink/IdeaProjects" +
//                "/fink_projects163/resources/wPawn.png");
//        club14 = new ImageIcon("/Users/michaelfink/IdeaProjects" +
//                "/fink_projects163/resources/wKnight.png");
//        
//        // Sets the Image for spade
//        club9 = new ImageIcon("/Users/michaelfink/IdeaProjects" +
//                "/fink_projects163/resources/wRook.png");
//        club10 = new ImageIcon("/Users/michaelfink/IdeaProjects" +
//                "/fink_projects163/resources/wBishop.png");
//        club11 = new ImageIcon("/Users/michaelfink/IdeaProjects" +
//                "/fink_projects163/resources/wQueen.png");
//        club12 = new ImageIcon("/Users/michaelfink/IdeaProjects" +
//                "/fink_projects163/resources/wKing.png");
//        club13 = new ImageIcon( "/Users/michaelfink/IdeaProjects" +
//                "/fink_projects163/resources/wPawn.png");
//        club14 = new ImageIcon("/Users/michaelfink/IdeaProjects" +
//                "/fink_projects163/resources/wKnight.png");     
//    }
    
    private class ButtonListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {

		}

	}

}
