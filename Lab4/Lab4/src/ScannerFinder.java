
import java.util.ArrayList;

/*
 * This code is written by John Trapp and Brendan Bellows
 * for the express purpose to be used in CSCI476 at Montana State University
 * Spring 2016. Please do not use elsewhere.
 */
/**
 *
 * @author John Trapp and Brendan Bellows
 */
public class ScannerFinder {

    ArrayList<String> naughtyIPs;

    /**
     * @param args the command line arguments
     */
    public void main(String[] args) {
        // TODO code application logic here
        if (args.length != 1) { //Input checking
            if (args.length == 0) {  //No arguments
                System.out.println("Please specify a pcap file!");
                System.exit(1);
            } else {  //Too many arguments
                System.out.println("You entered too many arguments!");
                System.exit(1);
            }
        }

        openPCAP(args[0]);
        //Scan for attackers
        printResults();
    }

    public void openPCAP(String inputFile) {
        //Open PCAP file
    }

    public void scan() {

    }

    public void printResults() {
        for (String naughtyIP : naughtyIPs) {
            System.out.println(naughtyIP);
        }
    }

}
