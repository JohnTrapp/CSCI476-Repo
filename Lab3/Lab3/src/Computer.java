
import java.awt.Color;
import javax.swing.JButton;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author John
 */
public class Computer extends JButton {

    private boolean vulnerable;
    private int infected = 0;

    public Computer() {
        super();
        infected = 0;
        super.setText("" + infected);
        super.setBackground(Color.WHITE);
    }

    public void infect() {
        infected++;
        super.setText("" + infected);
        if (infected == 1) {
            super.setBackground(Color.ORANGE);
        } else {
            super.setBackground(Color.RED);
        }
    }
}
