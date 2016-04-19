
import java.util.ArrayList;
import org.jnetpcap.Pcap;
import org.jnetpcap.PcapHeader;
import org.jnetpcap.protocol.*;
import org.jnetpcap.packet.JPacket;
import org.jnetpcap.packet.JPacketHandler;
import org.jnetpcap.packet.JScanner;
import org.jnetpcap.packet.PcapPacket;
import org.jnetpcap.protocol.network.Ip4;
import org.jnetpcap.protocol.tcpip.Tcp;

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

    public static ArrayList<Computer> computers;
    public static Pcap pcap;
    public static StringBuilder errbuf = new StringBuilder();

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
        //openPCAP("pcap.pcap");
        scan();
        printResults();
    }

    public static void openPCAP(String inputFile) {
        //Open PCAP file

        pcap = Pcap.openOffline(inputFile, errbuf);
        if (pcap == null) {
            System.err.printf("Error while opening file for capture: " + errbuf.toString());
            System.exit(2);
        }
    }

    public static void scan() {
        //This is supposed to loop through the whole pcap file
        pcap.loop(-1, new JPacketHandler<StringBuilder>() {
            private Ip4 ip = new Ip4();
            private Tcp tcp = new Tcp();

            //This is supposed to handle each incoming packet
            public void nextPacket(JPacket packet, String user) {
                //If a packet has an ip header, then
                if (packet.hasHeader(ip)) {
                    ip = packet.getHeader(ip);
                    byte[] ipSrc = ip.source();
                    byte[] ipDest = ip.destination();
                    String ip = new String(ipSrc);
                    String dest = new String(ipDest);
                    //see if we have already stored the computer data, and if so, increment the computer
                    for (int i = 0; i < computers.size(); i++) {
                        if (computers.get(i).getIP() == ip) {
                            if (packet.hasHeader(tcp)) {
                                tcp = packet.getHeader(tcp);
                                if (tcp.flags_SYN()) {
                                    computers.get(i).increase("sentSYN");
                                } else if (tcp.flags_ACK()) {
                                    computers.get(i).increase("sentACK");
                                }
                            }
                        }

                        if (computers.get(i).getIP() == dest) {
                            if (packet.hasHeader(tcp)) {
                                tcp = packet.getHeader(tcp);
                                if (tcp.flags_SYN()) {
                                    computers.get(i).increase("receivedSYN");
                                } else if (tcp.flags_ACK()) {
                                    computers.get(i).increase("receivedACK");
                                }
                            }
                        }
                    }
                }
            }

            @Override
            public void nextPacket(JPacket packet, StringBuilder user) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        }, errbuf);
    }

    public static void printResults() {
        //Print the IP's
        for (int i = 0; i < computers.size(); i++) {
            if (computers.get(i).isNaughty()) {
                System.out.println(computers.get(i).getIP());
            }
        }
    }
}
