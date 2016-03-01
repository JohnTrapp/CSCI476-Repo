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
    private int[] probability;

    //Not actually used, but added just in case
    public WormSimulator() {
    }

    //Main constructor used
    public WormSimulator(int nIn, int dIn, double pIn, Computer[] boardIn) {
        n = nIn;
        d = dIn;
        p = pIn;
        board = boardIn;
        //create a lookup table to implement weighted reinfection
        probability = new int[10];
        for (int i = 0; i < probability.length; i++){
            if(i < (p * 10)){
                probability[i] = 1;
            }else{
                probability[i] = 0;
            }
        }
    }

    //Waits for external okay to start, just to be safe
    public void startSimulation() {
        makeVulnerable();  //INCREASE!!
        infection();  //INFECT!!
        int startInf;
        //randomly assign a start point for the infection to begin
        do{
        startInf = (int) (Math.random() * board.length);
        board[startInf].infect();
        } while(board[startInf].getInfected()==0);
    }
    
    //each time this method is called, the infection will spread
    public void spread(){
        //create an array and copy over the CURRENT infections
        int[] infected = new int[board.length];
        for(int i = 0; i < board.length; i++){
        if (board[i].getInfected() > 0){
            infected[i] = 1;
        }else{
            infected[i] = 0;
        }
    }
        //iterate through the arrays and infect once for each originally infected computer
        for(int i = 0; i < board.length; i++){
            if (infected[i] != 0){
                for(int j = 0; j < d; j++){
                    //select a computer to infect
                    int selected = (int) (Math.random() * board.length);
                    //if the computer has already been infected
                    if(board[selected].getInfected() > 0){
                        //reinfect according to the reinfection probability
                        if(probability[(int) (Math.random() * probability.length)] ==1){
                            board[selected].infect();
                        }
                        //otherwise, infect the computer
                    }else{
                        board[selected].infect();
                    }
                    
                }
            }
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
