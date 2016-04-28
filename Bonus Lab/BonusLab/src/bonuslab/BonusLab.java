/*
 * This code is written by John Trapp and Brendan Bellows
 * for the express purpose to be used in CSCI476 at Montana State University
 * Spring 2016. Please do not use elsewhere.
 */
package bonuslab;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author John Trapp and Brendan Bellows
 */
public class BonusLab {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        byte[] plaintext = null; 
        try { //Get user input
            plaintext = getPlaintext();
        } catch (IOException ex) {  //Error catching stuff...
            Logger.getLogger(BonusLab.class.getName()).log(Level.SEVERE, null, ex);
            System.err.println("Error in input! Check input and try again!\n" + ex);
            System.exit(1);
        }

        String s = new String(plaintext);  //Get the string.
        System.out.println("\nThe plaintext is:\n" + s);  //Print it
        System.out.println("------------------------------------");

        Encryptor encryptor = new Encryptor();
        encryptor.encrypt(plaintext, 10);  //Encrypt with 10 rounds

        System.out.println("\n*****Encryption complete!*****\n");  //Encryption done!
    }

    private static byte[] getPlaintext() throws IOException {
        byte[] temp;
        do {  //Ask for the input
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

            System.out.print("Please enter 16 byte plaintext. (Leave blank for default) ");
            String in = reader.readLine();
            if (in.equals("")) {  //The default, lab supplied case.
                in = "abcdefghijklmnop";
            }
            temp = in.getBytes();

            if (temp.length != 16) {  //Yell at user for not suppling correct input
                System.out.println("\nPlaintext not 16 bytes! Please try again.\n");
            }
        } while (temp.length != 16);  //Have user try again

        return temp;  //Return user input
    }

}
