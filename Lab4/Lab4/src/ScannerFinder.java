
import java.util.ArrayList;
import org.jnetpcap.Pcap;

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

    public static ArrayList<String> naughtyIPs;
    public static Pcap pcap;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        if (args.length != 1) { //Input checking
            if (args.length == 0) {  //No arguments
                System.err.printf("Please specify a pcap file!\n");
                System.exit(1);
            } else {  //Too many arguments
                System.err.printf("You entered too many arguments!\n");
                System.exit(1);
            }
        }

        openPCAP(args[0]);
        scan();
        printResults();
    }

    public static void openPCAP(String inputFile) {
        //Open PCAP file
        StringBuilder errbuf = new StringBuilder();
        pcap = Pcap.openOffline(inputFile, errbuf);
        if (pcap == null) {
            System.err.printf("Error while opening file for capture: " + errbuf.toString());
            System.exit(2);
        }
    }

    public static void scan() {
        //Scan the PCAP file.
        //TODO application logic
    }

    public static void printResults() {
        //Print the IP's
        for (String naughtyIP : naughtyIPs) {
            System.out.println(naughtyIP);
        }
    }

}
