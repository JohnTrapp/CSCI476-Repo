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

    private int n, d;
    private double p;
    public Computer[] board;

    //Not actually used, but added just in case
    public WormSimulator() {
    }

    //Main constructor used
    public WormSimulator(int nIn, int dIn, double pIn, Computer[] boardIn) {
        //Note that constructor will take in whatever is used to display the worms
        n = nIn;
        d = dIn;
        p = pIn;
        board = boardIn;
        
        board[2].infect();
    }

    //Waits for external okay to start, just to be safe
    public void startSimulation() {

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
