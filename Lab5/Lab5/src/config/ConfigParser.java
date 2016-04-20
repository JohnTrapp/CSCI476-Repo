/*
 * This program has been written by Brendan Bellows and should
 * probably be executed with care as he is a student, not a trained
 * professional. Yet.
 */
package config;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author Brendan
 */
public class ConfigParser {
    
    private File configFile;
    private String host;
    private ArrayList<Policy> policies;
    private Scanner sc;
    private ArrayList<String> rawPolicies;
    
    public ConfigParser(String inFileName){
        policies = new ArrayList();
        rawPolicies = new ArrayList();
        try{
            configFile = new File(inFileName);
            sc = new Scanner(configFile);
            System.out.println("Found Config File: Ready to Parse!\n\n");
        } catch (FileNotFoundException e){
            System.err.println("Config File Not Found!\n");
        }
    }
    
    public void parseThisShit(){
        boolean done = false;
        String rawString;
        String temp;
        Policy tempPol;
        while(!done){
            rawString = sc.nextLine();
            temp = rawString.substring(0, rawString.indexOf("="));
            switch(temp){
                case "host":
                    this.setHost(rawString.substring((rawString.indexOf("=")+1)));
                    sc.nextLine();
                    break;
                case "name":
                    this.rawPolicies.add(rawString);
                    break;
                case "type":
                    this.rawPolicies.add(rawString);
                    break;
                case "proto":
                    this.rawPolicies.add(rawString);
                    break;
                case "host_port":
                    this.rawPolicies.add(rawString);
                    break;
                case "attacker_port":
                    this.rawPolicies.add(rawString);
                    break;
                case "attacker":
                    this.rawPolicies.add(rawString);
                    break;
                case "to_host":
                    this.rawPolicies.add(rawString);
                    break;
                case "from_host":
                    this.rawPolicies.add(rawString);
                    break;
                default:
                    break;
            }
            if (!sc.hasNext()){
                done = true;
            }
        }
        this.createPolicies();
    }

    private void setHost(String rawString) {
        this.host = rawString.substring((rawString.indexOf("=") + 1));
    }

    private void createPolicies() {
        Policy newPol = null;
        String curString;
        String type;
        String data;
        String flags;
        String direction;
        String regexp;
        int wFlags;
        
        for(int i = 0; i < rawPolicies.size(); i++){
            curString = rawPolicies.get(i);
            int equalSign = curString.indexOf("=");
            type = curString.substring(0, equalSign);
            data = curString.substring(equalSign + 1);
            switch (type){
                case "name":
                    if(newPol != null){
                        policies.add(newPol);
                    }
                    newPol = new Policy();
                    newPol.setName(data);
                    break;
                case "type":
                    newPol.state(data);
                    break;
                case "proto":
                    newPol.setProto(data);
                    break;
                case "host_port":
                    newPol.setHostPort(data);
                    break;
                case "attacker_port":
                    newPol.setAttackerPort(data);
                    break;
                case "attacker":
                    newPol.setAttacker(data);
                    break;
                case "to_host":
                    flags = "";
                    direction = type;
                    regexp = data;
                    wFlags = curString.indexOf("with flags");
                    if(wFlags > 0){
                        flags = curString.substring(wFlags + 10);
                        regexp = curString.substring((equalSign + 1), wFlags);
                        newPol.addSubPolicy(direction, regexp, flags);
                    } else {
                        newPol.addSubPolicy(direction, regexp);
                    }
                    break;
                case "from_host":
                    flags = "";
                    direction = type;
                    regexp = data;
                    wFlags = curString.indexOf("with flags");
                    if(wFlags > 0){
                        flags = curString.substring(wFlags + 10);
                        regexp = curString.substring((equalSign + 1), wFlags);
                        newPol.addSubPolicy(direction, regexp, flags);
                    } else {
                        newPol.addSubPolicy(direction, regexp);
                    }
                    break;
                default:
                    System.err.println("There may be a problem with the config file"
                            + " proceed with caution!");
                    break;
            }
        }
        if(newPol != null){
            policies.add(newPol);
        }
    }
    
    public String getHost(){
        return this.host;
    }
    
    public ArrayList getPolicies(){
        return this.policies;
    }
    
}
