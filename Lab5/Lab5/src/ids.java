
import config.Policy;
import config.SubPolicy;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import org.jnetpcap.Pcap;
import org.jnetpcap.packet.Payload;
import org.jnetpcap.packet.PcapPacket;
import org.jnetpcap.packet.PcapPacketHandler;
import org.jnetpcap.protocol.network.Ip4;
import org.jnetpcap.protocol.tcpip.Tcp;
import org.jnetpcap.protocol.tcpip.Udp;

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
    public static ArrayList<ScanPolicy> host_port = new ArrayList();
    public static ArrayList<ScanPolicy> attacker_port = new ArrayList();
    public static ArrayList<ScanPolicy> attacker = new ArrayList();
    public static ArrayList<ScanPolicy> payload = new ArrayList();
    public static String hostIp;

    public static void main(String[] args) throws IOException {
        if(args.length != 2){
         System.err.println("Incorrect amount of arguments used. "
         + "Please correct and try again."
         + "\nCorrect Usage: (policy file) (pcap file)");
         System.exit(0);
         }
        isInputCorrect(args);

        config.ConfigParser parser = new config.ConfigParser(args[0]);
        parser.parseThisShit();
        //arraylist policys = parse(args[0]);
        hostIp = parser.getHost();

        createPolicies(parser.getPolicies());
        scan(args[1]);
    }

    private static void createPolicies(ArrayList<Policy> inPolicies) {
        Policy curPol;
        for (int i = 0; i < inPolicies.size(); i++) {
            curPol = inPolicies.get(i);
            if (!curPol.getHostPort().equals("any")) {
                host_port.add(new ScanPolicy(curPol.getHostPort(), curPol));
            } else if (!curPol.getAttacker().equals("any")) {
                attacker.add(new ScanPolicy(curPol.getAttacker(), curPol));
            } else if (!curPol.getAttackerPort().equals("any")) {
                attacker_port.add(new ScanPolicy(curPol.getAttackerPort(), curPol));
            } else {
                payload.add(new ScanPolicy("regexp", curPol));
            }
        }
    }

    private static void scan(String fileIn) {
        System.out.println("SCAN STARTED!");
        System.out.println("ALERTS FOUND:");
        final StringBuilder errbuf = new StringBuilder();   //For error messages
        final String file = fileIn;

        Pcap pcap;
        pcap = Pcap.openOffline(file, errbuf);
        if (pcap == null) {
            System.err.println("Error opening pcap file!\nError from scan class." + errbuf.toString());
            System.exit(1);
        }

        PcapPacketHandler<String> jpacketHandler = new PcapPacketHandler<String>() {
            @Override

            public void nextPacket(PcapPacket packet, String user) {
                Ip4 ip = new Ip4();
                Tcp tcp = new Tcp();
                Udp udp = new Udp();
                Payload payloadHeader = new Payload();
                boolean fromhostFlag = false;
                boolean tohostFlag = false;

                String sourceIp, destinationIp, payload;
                int sourcePort = 0, destinationPort = 0;
                if (packet.hasHeader(ip)) {
                    sourceIp = Arrays.toString(packet.getHeader(ip).source());
                    destinationIp = Arrays.toString(packet.getHeader(ip).destination());
                    if (sourceIp.equals(hostIp)) {
                        fromhostFlag = true;
                    }

                    if (destinationIp.equals(hostIp)) {
                        tohostFlag = true;
                    }
                }
                if (packet.hasHeader(tcp)) {
                    sourcePort = (packet.getHeader(tcp).source());
                    destinationPort = (packet.getHeader(tcp).destination());
                }
                if (packet.hasHeader(udp)) {
                    sourcePort = (packet.getHeader(udp).source());
                    destinationPort = (packet.getHeader(udp).destination());
                }

                payload = Arrays.toString(packet.getHeader(payloadHeader).data());

                for (int i = 0; i < host_port.size(); i++) {

                    if (Integer.parseInt(host_port.get(i).getRule()) == destinationPort) {
                        ArrayList<SubPolicy> temp = host_port.get(i).getPolicy().getSubPolicies();
                        for (int j = 0; j < temp.size(); j++) {
                            if(temp.get(j).matchRule(payload)){
                                System.out.println("\tRule: " + host_port.get(i).getPolicy().getName());
                            }
                        }
                    }
                }
            }
        };

        pcap.loop(-1, jpacketHandler, "test");
        System.out.println("Scan complete!");
    }

    private static void isInputCorrect(String[] args) throws IOException {
        boolean policy = true;
        boolean pcap = new File(args[1]).isFile();

        if (!args[0].contains(".conf")) {
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

        public String getRule() {
            return this.scanRule;
        }

        public Policy getPolicy() {
            return this.policy;
        }

    }

}
