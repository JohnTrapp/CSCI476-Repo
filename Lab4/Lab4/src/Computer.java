/*
 * This code is written by John Trapp and Brendan Bellows
 * for the express purpose to be used in CSCI476 at Montana State University
 * Spring 2016. Please do not use elsewhere.
 */

/**
 *
 * @author John Trapp and Brendan Bellows
 */
public class Computer {

    private String ip;
    private int sentSYN, receivedSYN, sentACK, receivedACK;

    public Computer(String ipIn) {
        ip = ipIn;
    }

    public void increase(String arg) {
        if (arg.equalsIgnoreCase("sentSYN")) {
            sentSYN++;
        } else if (arg.equalsIgnoreCase("receivedSYN")) {
            receivedSYN++;
        } else if (arg.equalsIgnoreCase("sentACK")) {
            sentACK++;
        } else if (arg.equalsIgnoreCase("receivedACK")) {
            receivedACK++;
        }
    }

    public boolean isNaughty() {
        return sentSYN > (receivedACK * 3);
    }

    public String getIP() {
        return ip;
    }

    public void setIP(String input) {
        ip = input;
    }

    public int getSentSYN() {
        return sentSYN;
    }

    public int getReceivedSYN() {
        return receivedSYN;
    }

    public int getSentACK() {
        return sentACK;
    }

    public int getReceivedACK() {
        return receivedACK;
    }

    public void setSentSYN(int input) {
        sentSYN = input;
    }

    public void setReceivedSYN(int input) {
        receivedSYN = input;
    }

    public void setSentACK(int input) {
        sentACK = input;
    }

    public void setReceivedACK(int input) {
        receivedACK = input;
    }
}
