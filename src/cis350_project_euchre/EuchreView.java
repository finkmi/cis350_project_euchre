package cis350_project_euchre;
//CHECKSTYLE:OFF
import javax.swing.*;
//CHECKSTYLE:ON

/**********************************************************************
 * The view class for the Euchre program.
 * 
 * @author Michael Fink, Charlie Dorn
 *********************************************************************/
public class EuchreView {
	
	/******************************************************************
	 * The main method for the Euchre program. Sets up the GUI for the
	 * Euchre game and creates an instance of the euchreController
	 * used to run the euchre game.
	 * 
	 * @param args
	 *****************************************************************/
    public static void main(final String[] args) {
    	
    	/* Create the JFrame for the game, make it exit when the close
    	 * button is pressed */
    	JFrame frame = new JFrame("Euchre");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        EuchreController controller = new EuchreController();
        
        /* Add the controller to the JFrame created for the program */
        frame.getContentPane().add(controller);
        frame.setResizable(true);
        
        /* Pack to good dimensions for the game */
        frame.pack();
        /* Set the frame visible so it can be interacted with */
        frame.setVisible(true);
    }
}
