/*
 * This program has been written by Brendan Bellows and should
 * probably be executed with care as he is a student, not a trained
 * professional. Yet.
 */
package config;

import java.util.ArrayList;

/**
 *
 * @author Brendan
 */
public class Policy {
    private String type;
    private String proto;
    private String host_port;
    private String attacker_port;
    private String attacker;
    private ArrayList<SubPolicy> subPolicies;
    
    public Policy(){
        subPolicies = new ArrayList();
    }
    
    public void stateless(){
        type = "stateless";
    }
    
    public void stateful(){
        type = "stateful";
    }
    
    public void setProto(String inProto){
        proto = inProto;
    }
    
    public void setHostPort(String inHostPort){
        host_port = inHostPort;
    }
    
    public void setAttackerPort(String inAttackerPort){
        attacker_port = inAttackerPort;
    }
    
    public void setAttacker(String inAttacker){
        attacker = inAttacker;
    }
    
    public void addSubPolicy(String direction, String regexp){
        SubPolicy temp = new SubPolicy();
        temp.setDirection(direction);
        temp.setRegexp(regexp);
    }
    
    public void addSubPolicy(String direction, String regexp, ArrayList<String> flags){
        SubPolicy temp = new SubPolicy();
        temp.setDirection(direction);
        temp.setRegexp(regexp);
        temp.setFlags(flags);
    }
    
    public String getType(){
        return this.type;
    }
    public String getHostPort(){
        return this.host_port;
    }
    public String getAttackerPort(){
        return this.attacker_port;
    }
    public String getAttacker(){
        return this.attacker;
    }
    public String getProto(){
        return this.proto;
    }
    public ArrayList getSubPolicies(){
        return this.subPolicies;
    }
}
