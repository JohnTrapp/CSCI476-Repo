/*
 * This program has been written by Brendan Bellows and should
 * probably be executed with care as he is a student, not a trained
 * professional. Yet.
 */
package config;

import java.util.ArrayList;


public class SubPolicy {
    private String direction;
    private String regexp;
    private ArrayList<String> flags;
    
    public SubPolicy(){
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public void setRegexp(String regexp) {
        this.regexp = regexp;
    }

    public void setFlags(ArrayList<String> flags) {
        this.flags = flags;
    }
    
}
