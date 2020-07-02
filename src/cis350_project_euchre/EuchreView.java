package cis350_project_euchre;
import java.awt.Dimension;
import javax.swing.*;


public class EuchreView {
	
    public static void main (String[] args) {
    	
    	JFrame frame = new JFrame ("Euchre");
        frame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
        EuchreController controller = new EuchreController();
        
        frame.getContentPane().add(controller);
        frame.setResizable(true);
        //frame.setPreferredSize(new Dimension(800, 637));
        frame.pack();
        frame.setVisible(true);
    }
}
