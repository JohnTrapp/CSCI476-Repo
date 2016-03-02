/*
 * This code is written by John Trapp and Brendan Bellows
 * for the express purpose to be used in CSCI476 at Montana State University
 * Spring 2016. Please do not use elsewhere.
 */

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JOptionPane;

/**
 *
 * @author John Trapp
 */
public class Computer extends JButton {
    //A class that extends JButton to move the individual logic to a neater place.

    private boolean vulnerable;
    private int infected = 0, id;

    public Computer(int idIn) {  //Constructor
        super();
        infected = 0;
        id = idIn;
        super.setText("" + infected);
        super.setBackground(Color.WHITE);
        super.addActionListener(new ListenerOMatic());
    }

    public void infect() {  //Checks if it is vulnerable or not
        if (vulnerable) {
            infected++;
            super.setText("" + infected);
            if (infected == 1) {                    //Normally infected
                super.setBackground(Color.ORANGE);
            } else if (infected < 100) {            //Reinfected
                super.setBackground(Color.RED);
            } else if (infected >= 100) {           //Overloaded
                super.setBackground(Color.MAGENTA);
            }
        }
    }

    public void setVulnerable(boolean input) {
        if (input) {  //If setting as vulnerable
            vulnerable = true;
            super.setBackground(Color.BLACK);  //Make it black
        } else {  //If removing vulnerablility (a feature probably not needed)
            vulnerable = false;
            infected--;
            infect();  //Best way to recolor it without redoing the whole infect class
        }
    }

    public int getInfected() {
        return infected;
    }

    public String prettyPrint() {
        String result = "";
        if (infected < 100) {
            result = "Computer Number: " + id
                    + "\nComputer Vulnerability: " + vulnerable
                    + "\nThis computer has been infected " + infected + " times.";
        } else {
            result = "Computer Number: " + id
                    + "\nComputer Vulnerability: " + vulnerable
                    + "\nThis computer has been infected " + infected + " times."
                    + "\nTHIS COMPUTER HAS BEEN OVERLOADED!!";
        }
        return result;
    }

    private class ListenerOMatic implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            JOptionPane.showMessageDialog(null, "" + prettyPrint());
        }
    }
}
