
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
//import org.jnetpcap.Pcap;  
import org.jnetpcap.packet.PcapPacket;  
import org.jnetpcap.packet.PcapPacketHandler;  

/*
 * This code is written by John Trapp and Brendan Bellows
 * for the express purpose to be used in CSCI476 at Montana State University
 * Spring 2016. Please do not use elsewhere.
 */
/**
 *
 * @author John Trapp and Brendan Bellows
 */
public class ids {

    //arraylist of policies
    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws IOException {
        /*if(args.length != 2){
            System.err.println("Incorrect amount of arguments used. "
                    + "Please correct and try again."
                    + "\nCorrect Usage: (policy file) (pcap file)");
            System.exit(0);
        }*/
        isInputCorrect(args);
        
        config.ConfigParser parser = new config.ConfigParser();
        //arraylist policys = parse(args[0]);

        
        createPolicies();
        scan(args[1]);
    }

    private static void createPolicies() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private static void scan(String fileIn) {
        final StringBuilder errbuf = new StringBuilder();   //For error messages
        final String file = fileIn;
        
        Pcap pcap = Pcap.openOffline(file, errbuf);
    }

    private static void isInputCorrect(String[] args) throws IOException {
        boolean policy = true;
        boolean pcap = true;
        
        if(!args[0].contains(".txt")){
            policy = false;
        }
        if(!args[1].contains(".pcap")){
            pcap = false;
        }
        
        if (!policy || !pcap) { //if not verified
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            System.err.print("Unable to verify files! Please check your arguments. "
                    + "\nVerification Results:"
                    + "\n\tPolicy file:\t" + policy 
                    + "\n\tPCAP File:\t" + pcap 
                    + "\nContinue? (Y/N) ");
            String in = reader.readLine();
            if (!in.equalsIgnoreCase("Y")) {
                System.exit(1);
            }
        }
    }

}
