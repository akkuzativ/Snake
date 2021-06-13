package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/***
 * Panel displayed when the player dies
 */
public class GameOverPanel extends JPanel implements ActionListener{
    JButton menuButton = new JButton("Back to Menu");
    JButton quitButton = new JButton("Quit");
    JButton saveButton = new JButton("Save");
    JLabel gameOverLabel = new JLabel();
    JLabel infoLabel = new JLabel();
    JLabel scoreLabel = new JLabel();
    JTextField nameTextField = new JTextField();
    private int score = 0;
    private HighScore records;

    /***
     * GameOverPanel constructor
     * @param actionListener Listener to button events Listener to button events (class GameFrame required because of type cast inside)
     */
    GameOverPanel(ActionListener actionListener) {
        menuButton.setActionCommand("MENU");
        quitButton.setActionCommand("QUIT");
        saveButton.setActionCommand("SAVE");
        gameOverLabel.setText("Game Over");
        infoLabel.setText("Enter your name (optionally) and save your score");

        GameFrame gameFrame = ((GameFrame)actionListener);

        JLabel[] jlabels = {gameOverLabel, scoreLabel, infoLabel};
        for (JLabel jlabel: jlabels) {
            jlabel.setHorizontalAlignment(JLabel.CENTER);
            add(jlabel);
            jlabel.setVisible(true);
        }

        nameTextField.setVisible(true);
        add(nameTextField);
        nameTextField.setHorizontalAlignment(JTextField.CENTER);

        JButton[] buttons = {saveButton, menuButton, quitButton};
        for (JButton button: buttons) {
            button.setHorizontalAlignment(JButton.CENTER);
            button.setVisible(true);
            if (button != saveButton) {
                button.addActionListener(actionListener);
            }
            add(button);
        }
        saveButton.addActionListener(this);

        gameOverLabel.setFont(new Font("default", Font.BOLD, (int)(gameFrame.getWidth()*0.065)));

        setLayout(new GridLayout(7, 1, 0, (int)(gameFrame.getHeight()*0.05)));
        setBorder(BorderFactory.createEmptyBorder((int)(gameFrame.getHeight()*0.05), (int)(gameFrame.getWidth()*0.1),
                (int)(gameFrame.getHeight()*0.05), (int)(gameFrame.getWidth()*0.1)));
    }

    /***
     * Handles reactions to events (only clear button click)
     * @param e Heard event
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("SAVE")) {
            String name = "Noname";
            if (!nameTextField.getText().equals("")) {
                name = nameTextField.getText();
            }
            name = name.replaceAll("\\s+", "");
            HighScore.HighScoreRecord record = new HighScore.HighScoreRecord(name, score);
            records.addRecord(record);
            records.writeToFile();
            saveButton.setEnabled(false);
            menuButton.setEnabled(true);
            quitButton.setEnabled(true);
        }
    }

    /***
     * Allows GameOverPanel to access player's score
     * @param score score to access
     */
    public void setScore(int score) {
        this.score = score;
        scoreLabel.setText("Score: " + score);
    }

    /***
     * Allows GameOverPanel to access common high score records in order to add a new record
     * @param records High score records to access
     */
    public void setHighScoreRecords(HighScore records) {
        this.records = records;

    }

    /***
     * Resets the state of GameOverPanel for next use
     */
    public void reset() {
        saveButton.setEnabled(true);
        saveButton.setEnabled(true);
        menuButton.setEnabled(false);
        quitButton.setEnabled(false);
        nameTextField.setText("");
    }

}
