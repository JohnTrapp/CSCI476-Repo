
import com.sun.org.apache.xalan.internal.xsltc.compiler.util.Type;
import config.Policy;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import org.jnetpcap.Pcap;
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
    
    public static ArrayList<ScanPolicy> host_port;
    public static ArrayList<ScanPolicy> attacker_port;
    public static ArrayList<ScanPolicy> attacker;
    public static ArrayList<ScanPolicy> payload;
    
    
    public static void main(String[] args) throws IOException {
        /*if(args.length != 2){
         System.err.println("Incorrect amount of arguments used. "
         + "Please correct and try again."
         + "\nCorrect Usage: (policy file) (pcap file)");
         System.exit(0);
         }*/
        isInputCorrect(args);

        config.ConfigParser parser = new config.ConfigParser(args[0]);
        parser.parseThisShit();
        //arraylist policys = parse(args[0]);

        createPolicies(parser.getPolicies());
        scan(args[1]);
    }

    private static void createPolicies(ArrayList<Policy> inPolicies) {
        Policy curPol;
        for(int i = 0; i < inPolicies.size(); i++){
            curPol = inPolicies.get(i);
            if(!curPol.getHostPort().equals("any")){
                host_port.add(new ScanPolicy(curPol.getHostPort(), curPol));
            } else if (!curPol.getAttacker().equals("any")){
                attacker.add(new ScanPolicy(curPol.getAttacker(), curPol));
            } else if (!curPol.getAttackerPort().equals("any")){
                attacker_port.add(new ScanPolicy(curPol.getAttackerPort(), curPol));
            } else {
                payload.add(new ScanPolicy("regexp", curPol));
            }
        }
    }

    private static void scan(String fileIn) {
        final StringBuilder errbuf = new StringBuilder();   //For error messages
        final String file = fileIn;

        Pcap pcap = Pcap.openOffline(file, errbuf);
        if (pcap == null) {
            System.err.println("Error opening pcap file!\nError from scan class." + errbuf.toString());
            System.exit(1);
        }
        
        PcapPacketHandler<String> jpacketHandler = new PcapPacketHandler<String>(){
            @Override
            public void nextPacket(PcapPacket packet, String user) {
                
            }
        };
    }

    private static void isInputCorrect(String[] args) throws IOException {
        boolean policy = true;
        boolean pcap = new File(args[1]).isFile();

        if (!args[0].contains(".txt")) {
            policy = false;
        }
        if (!args[1].contains(".pcap")) {
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

    private static class ScanPolicy {

        private String scanRule;
        private Policy policy;
        public ScanPolicy(String inAttrib, Policy inPolicy) {
            this.scanRule = inAttrib;
            this.policy = inPolicy;
        }
        
        public String getRule(){
            return this.scanRule;
        }
        
        public Policy getPolicy(){
            return this.policy;
        }
        
    }

}
