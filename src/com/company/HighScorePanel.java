package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/***
 * Panel used to display (and clear) saved high score records.
 */
public class HighScorePanel extends JPanel implements ActionListener{
    JButton menuButton = new JButton("Back to Menu");
    JButton clearButton = new JButton("Clear records");
    JList<String> highScoreRecordList = new JList<>();
    HighScore records;
    GameFrame gameFrame;

    /***
     * Highscore panel constructor
     * @param actionListener Listener to button events (class GameFrame required because of type cast inside)
     */
    HighScorePanel(ActionListener actionListener) {
        gameFrame = ((GameFrame)actionListener);

        JPanel upperPanel = new JPanel();
        JPanel lowerPanel = new JPanel();
        lowerPanel.setBorder(BorderFactory.createEmptyBorder((int)(gameFrame.getHeight()*0.05),
                (int)(gameFrame.getWidth()*0.1), (int)(gameFrame.getHeight()*0.05), (int)(gameFrame.getWidth()*0.1)));
        lowerPanel.setVisible(true);
        upperPanel.setVisible(true);
        lowerPanel.setLayout(new GridLayout(2, 1, 0, (int)(gameFrame.getHeight()*0.05)));

        setLayout(new GridLayout(2, 1, 0, (int)(gameFrame.getHeight()*0.05)));
        setBorder(BorderFactory.createEmptyBorder((int)(gameFrame.getHeight()*0.05), (int)(gameFrame.getWidth()*0.1),
                (int)(gameFrame.getHeight()*0.05), (int)(gameFrame.getWidth()*0.1)));


        highScoreRecordList.setVisible(true);
        upperPanel.add(highScoreRecordList);

        menuButton.setActionCommand("MENU");
        menuButton.addActionListener(actionListener);
        menuButton.setVisible(true);
        clearButton.addActionListener(this);
        clearButton.setActionCommand("CLEAR");
        clearButton.setVisible(true);
        lowerPanel.add(clearButton);
        lowerPanel.add(menuButton);
        add(upperPanel);
        add(lowerPanel);
    }

    /***
     * Handles reactions to events (only clear button click)
     * @param e Heard event
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("CLEAR")) {
            records.getRecords().clear();
            records.writeToFile();
            clearButton.setEnabled(false);
            gameFrame.actionPerformed(new ActionEvent(gameFrame, ActionEvent.ACTION_PERFORMED, "HIGH_SCORE"));
        }
    }

    /***
     * Allows HighScorePanel to access common highscore records
     * @param records records to access
     */
    public void setHighScoreRecords(HighScore records) {
        this.records = records;
        if (records != null) {
            DefaultListModel<String> listModel = new DefaultListModel<>();
            for (HighScore.HighScoreRecord record: records.getRecords()) {
                listModel.addElement(record.toString());
            }
            highScoreRecordList.setModel(listModel);
        }
    }

    /***
     * Resets the state of panel for next use
     */
    public void reset() {
        clearButton.setEnabled(true);
    }
}
