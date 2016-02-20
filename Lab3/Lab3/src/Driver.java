/*
 * This code is written by John Trapp and Brendan Bellows
 * for the express purpose to be used in CSCI476 at Montana State University
 * Spring 2016. Please do not use elsewhere.
 */

import javax.swing.JFrame;
import java.awt.Image;
import java.awt.*;
import javax.swing.*;

/**
 *
 * @author john.trapp
 */
public class Driver {

    //Edit these fields to taylor the driver.
    private static final String TITLE = "Lab 3: Worm Simulator";
    private static final ImageIcon ICON = new ImageIcon("icon.jpg");
    
    //Do not modify the code below unless you do not want to include something. 
    //In which case, you need to comment out.
    public static void main(String[] args) {
        JFrame.setDefaultLookAndFeelDecorated(false);
        JFrame frame = new JFrame(TITLE);
        //frame.setLocation(200, 200);
        //frame.setSize(1000, 1000);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setIconImage(ICON.getImage());
        frame.setContentPane(new Lab3Panel());
        frame.setVisible(true);
        frame.pack();
    }
}
