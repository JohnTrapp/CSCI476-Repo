/*
 * This code is written by John Trapp and Brendan Bellows
 * for the express purpose to be used in CSCI476 at Montana State University
 * Spring 2016. Please do not use elsewhere.
 */

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.PopupMenu;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 *
 * @author John Trapp and Brendan Bellows
 */
class Lab3Panel extends JPanel {

    private WormSimulator wormSimulator;
    public Computer[] board;  //Public because fuck that

    private int n, d;
    private double p;

    private JButton startButton;
    private JSlider nChooser, dChooser, pChooser;
    private JLabel nLabel, dLabel, pLabel;

    public Lab3Panel() {
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

        //add(Box.createRigidArea(new Dimension(0, 10)));
        add(createControlPanel());
        add(Box.createRigidArea(new Dimension(0, 10)));
        add(createDisplayPanel());
    }

    private JPanel createControlPanel() {
        startButton = new JButton("Start");
        startButton.setMnemonic(KeyEvent.VK_S);
        startButton.addActionListener(new startListener());

        ////////////////////////////////////////////////////////////////////////
        //N area
        nChooser = new JSlider(0, 999, 500);
        nChooser.setMajorTickSpacing(100);
        nChooser.setPaintTicks(true);
        nChooser.addChangeListener(new nListener());

        nLabel = new JLabel("500");

        JPanel nArea = new JPanel();
        nArea.add(nLabel);
        nArea.add(nChooser);
        nArea.setBorder(BorderFactory.createTitledBorder("Vulnerable Computers (N)"));
        nArea.validate();
        nArea.setPreferredSize(nArea.getPreferredSize());
        ////////////////////////////////////////////////////////////////////////
        ////////////////////////////////////////////////////////////////////////
        //D area
        dChooser = new JSlider(0, 999, 500);
        dChooser.setMajorTickSpacing(100);
        dChooser.setPaintTicks(true);
        dChooser.addChangeListener(new dListener());

        dLabel = new JLabel("500");

        JPanel dArea = new JPanel();
        dArea.add(dLabel);
        dArea.add(dChooser);
        dArea.setBorder(BorderFactory.createTitledBorder("Spread Rate (D)"));
        dArea.validate();
        dArea.setPreferredSize(dArea.getPreferredSize());
        ////////////////////////////////////////////////////////////////////////
        ////////////////////////////////////////////////////////////////////////
        //P area
        pChooser = new JSlider(0, 10, 5);
        pChooser.setMajorTickSpacing(1);
        pChooser.setMinorTickSpacing(1);
        pChooser.setPaintTicks(true);
        pChooser.addChangeListener(new pListener());

        pLabel = new JLabel("0.5");

        JPanel pArea = new JPanel();
        pArea.add(pLabel);
        pArea.add(pChooser);
        pArea.setBorder(BorderFactory.createTitledBorder("Random Reinfected Probability (P)"));
        pArea.validate();
        pArea.setPreferredSize(pArea.getPreferredSize());
        ////////////////////////////////////////////////////////////////////////

        JPanel controls = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 2;
        c.gridheight = 2;
        c.fill = GridBagConstraints.BOTH;
        controls.add(nArea, c);

        c.gridx = 2;
        controls.add(dArea, c);

        c.gridx = 4;
        controls.add(pArea, c);

        c.gridx = 0;
        c.gridy = 2;
        c.gridheight = 1;
        c.gridwidth = 6;
        controls.add(startButton, c);
        return controls;
    }

    private JPanel createDisplayPanel() {
        JPanel display = new JPanel();
        display.setLayout(new GridLayout(32, 32));

        board = new Computer[1000];
        for (int i = 0; i < board.length; i++) {
            board[i] = new Computer();
            display.add(board[i]);
        }

        display.setBorder(BorderFactory.createTitledBorder("Computers"));
        display.validate();
        display.setPreferredSize(display.getPreferredSize());
        return display;
    }

    private class startListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            wormSimulator = new WormSimulator(n, d, p, board);
            nChooser.setEnabled(false);
            dChooser.setEnabled(false);
            pChooser.setEnabled(false);
            startButton.setEnabled(false);
        }
    }

    private class nListener implements ChangeListener {

        @Override
        public void stateChanged(ChangeEvent e) {
            n = nChooser.getValue();
            nLabel.setText("" + n);
        }
    }

    private class dListener implements ChangeListener {

        @Override
        public void stateChanged(ChangeEvent e) {
            d = dChooser.getValue();
            dLabel.setText("" + d);
        }
    }

    private class pListener implements ChangeListener {

        @Override
        public void stateChanged(ChangeEvent e) {
            p = ((double) (pChooser.getValue())) / 10;
            pLabel.setText("" + p);
        }
    }
}
