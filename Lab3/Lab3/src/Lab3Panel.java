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
    private JTextArea stats1Label, stats2Label;

    public Lab3Panel() {
        //setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

        //add(Box.createRigidArea(new Dimension(0, 10)));
        add(createControlPanel(), BorderLayout.PAGE_START);
        //add(Box.createRigidArea(new Dimension(0, 10)));
        JPanel display = createDisplayPanel();
        JScrollPane scrollDisplay = new JScrollPane(display);
        scrollDisplay.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollDisplay.setPreferredSize(new Dimension(1100, 600));
        add(scrollDisplay, BorderLayout.CENTER);
    }

    private JPanel createControlPanel() {
        startButton = new JButton("Start");
        startButton.setMnemonic(KeyEvent.VK_S);
        startButton.addActionListener(new startListener());

        ////////////////////////////////////////////////////////////////////////
        //Stats 1 area
        stats1Label = new JTextArea("Infected: 0" + "/" + n
                + "\nOverloaded: 0" + "/" + n
                + "\nNumber of reinfections: 0             ");
        stats1Label.setEditable(false);
        //stats1Label.setColumns(28);
        //stats1Label.setRows(3);

        JPanel stats1 = new JPanel();
        stats1.setBorder(BorderFactory.createTitledBorder("Stats"));
        stats1.add(stats1Label);
        stats1.validate();
        stats1.setPreferredSize(stats1.getPreferredSize());
        ////////////////////////////////////////////////////////////////////////
        ////////////////////////////////////////////////////////////////////////
        //Stats 2 area
        stats2Label = new JTextArea("Please start the simulation to view results.");
        stats2Label.setEditable(false);
        //stats2Label.setColumns(28);
        //stats2Label.setRows(3);

        JPanel stats2 = new JPanel();
        stats2.setBorder(BorderFactory.createTitledBorder("Lab Checker"));
        stats2.add(stats2Label);
        stats2.validate();
        stats2.setPreferredSize(stats2.getPreferredSize());
        ////////////////////////////////////////////////////////////////////////
        ////////////////////////////////////////////////////////////////////////
        //N area
        nChooser = new JSlider(0, 9999, 5000);
        nChooser.setMajorTickSpacing(100);
        nChooser.setPaintTicks(true);
        nChooser.addChangeListener(new nListener());

        nLabel = new JLabel("5000");

        JPanel nArea = new JPanel();
        nArea.add(nLabel);
        nArea.add(nChooser);
        nArea.setBorder(BorderFactory.createTitledBorder("Vulnerable Computers (N)"));
        nArea.validate();
        nArea.setPreferredSize(nArea.getPreferredSize());
        ////////////////////////////////////////////////////////////////////////
        ////////////////////////////////////////////////////////////////////////
        //D area
        dChooser = new JSlider(0, 9999, 5000);
        dChooser.setMajorTickSpacing(100);
        dChooser.setPaintTicks(true);
        dChooser.addChangeListener(new dListener());

        dLabel = new JLabel("5000");

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
        c.gridheight = 3;
        controls.add(stats1, c);

        c.gridx = 8;
        controls.add(stats2, c);

        c.gridx = 2;
        c.gridy = 0;
        c.gridwidth = 2;
        c.gridheight = 2;
        c.fill = GridBagConstraints.BOTH;
        controls.add(nArea, c);

        c.gridx = 4;
        controls.add(dArea, c);

        c.gridx = 6;
        controls.add(pArea, c);

        c.gridx = 2;
        c.gridy = 2;
        c.gridheight = 1;
        c.gridwidth = 6;
        controls.add(startButton, c);

        return controls;
    }

    private JPanel createDisplayPanel() {
        JPanel display = new JPanel();
        display.setLayout(new GridLayout(400, 25));

        board = new Computer[10000];
        for (int i = 0; i < board.length; i++) {
            board[i] = new Computer(i);
            display.add(board[i]);
        }

        display.validate();
        //Dimension dimension = display.getPreferredSize();
        //int width = (getWidth()) - 20;
        //int height = dimension.height;
        //dimension.setSize(width, height);
        //display.setBorder(BorderFactory.createTitledBorder("Computers"));
        //display.setPreferredSize(dimension);
        return display;
    }

    private class startListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            nChooser.setEnabled(false);
            dChooser.setEnabled(false);
            pChooser.setEnabled(false);
            startButton.setEnabled(false);

            wormSimulator = new WormSimulator(nChooser.getValue(), dChooser.getValue(),
                    ((double) (pChooser.getValue())) / 10, board);
            wormSimulator.startSimulation();
            //run ten steps of the simulator
            for (int i = 0; i < 10; i++) {
                wormSimulator.spread();
            }
            stats1Label.setText(wormSimulator.stats1Return());
            stats2Label.setText(wormSimulator.stats2Return());
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
