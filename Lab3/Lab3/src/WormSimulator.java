/*
 * This code is written by John Trapp and Brendan Bellows
 * for the express purpose to be used in CSCI476 at Montana State University
 * Spring 2016. Please do not use elsewhere.
 */

import javax.swing.JButton;

/**
 *
 * @author John Trapp and Brendan Bellows
 */
class WormSimulator {

    private int n, d, amountInfections = 0, amountReInfections = 0, amountOverLoaded = 0;
    private double p;
    public Computer[] board;

    //Not actually used, but added just in case
    public WormSimulator() {
    }

    //Main constructor used
    public WormSimulator(int nIn, int dIn, double pIn, Computer[] boardIn) {
        n = nIn;
        d = dIn;
        p = pIn;
        board = boardIn;
    }

    //Waits for external okay to start, just to be safe
    public void startSimulation() {
        makeVulnerable();  //INCREASE!!
        infection();  //INFECT!!
        
        board[2].setVulnerable(true);
        for (int i = 0; i < 101; i++) {
            board[2].infect();
        }

    }

    public void makeVulnerable() {
        for (int i = 0; i < n; i++) {
            board[i].setVulnerable(true);
        }
    }

    public void infection() {
        
    }

    public String stats1Return() {

        for (Computer board1 : board) {
            if (board1.getInfected() > 0) {
                amountInfections++;
            }
            if (board1.getInfected() > 1) {
                amountReInfections += (board1.getInfected() - 1);
            }
            if (board1.getInfected() > 99) {
                amountOverLoaded++;
            }
        }

        String result = "Infected: " + amountInfections + "/" + n
                + "\nOverloaded: " + amountOverLoaded + "/" + n
                + "\nNumber of reinfections: " + amountReInfections;

        return result;
    }

    //Please call stats1Return first!
    public String stats2Return() {
        String result = "";

        if (amountInfections < n) {
            result = "Some computers were not infected!";
        } else if (amountOverLoaded >= n) {
            result = "Every computer was overloaded!";
        } else {
            result = "Paramaters did not enable lab requirements.";
        }

        return result;
    }

    //Various get and set methods that good little coders have for their classes
    public int getN() {
        return n;
    }

    public int getD() {
        return d;
    }

    public double getP() {
        return p;
    }

    public void setN(int nIn) {
        n = nIn;
    }

    public void setD(int dIn) {
        d = dIn;
    }

    public void setP(double pIn) {
        p = pIn;
    }
}
