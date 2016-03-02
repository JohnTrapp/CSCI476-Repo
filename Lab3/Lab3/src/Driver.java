/*
 * This code is written by John Trapp and Brendan Bellows
 * for the express purpose to be used in CSCI476 at Montana State University
 * Spring 2016. Please do not use elsewhere.
 */

import javax.swing.JFrame;
import javax.swing.*;

/**
 *
 * @author John Trapp
 */
public class Driver {

    //Edit these fields to taylor the driver.
    private static final String TITLE = "Lab 3: Worm Simulator";
    private static final ImageIcon ICON = new ImageIcon("images/icon.jpg");
    
    public static void main(String[] args) {
        JFrame.setDefaultLookAndFeelDecorated(false);
        JFrame frame = new JFrame(TITLE);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setIconImage(ICON.getImage());
        frame.setSize(1300,850);
        frame.setExtendedState(frame.getExtendedState() | JFrame.MAXIMIZED_BOTH);
        frame.setContentPane(new Lab3Panel());
        frame.setVisible(true);
        
        //frame.pack();
    }
}
