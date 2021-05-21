package com.company;

import java.awt.*;
import java.awt.event.ActionListener;
import javax.swing.*;

public class MenuPanel extends JPanel{
    JButton startButton = new JButton("Start");
    JButton settingsButton = new JButton("Settings");
    JButton highScoreButton = new JButton("High-Score");
    JButton quitButton = new JButton("Quit");

    MenuPanel(ActionListener actionListener) {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setAlignmentY(CENTER_ALIGNMENT);
        buttonPanel.setAlignmentX(CENTER_ALIGNMENT);
        startButton.setActionCommand("START");
        settingsButton.setActionCommand("SETTINGS");
        highScoreButton.setActionCommand("HIGH_SCORE");
        quitButton.setActionCommand("QUIT");
        JButton[] buttons = {startButton, settingsButton, highScoreButton, quitButton};
        for (JButton button: buttons) {
            button.setAlignmentX(CENTER_ALIGNMENT);
            button.setVisible(true);
            buttonPanel.add(button);
            button.addActionListener(actionListener);
        }
        buttonPanel.setVisible(true);
        add(buttonPanel);
        setVisible(true);
    }
}
