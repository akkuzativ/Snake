package com.company;

import java.awt.*;
import java.awt.event.ActionListener;
import javax.swing.*;

public class MenuPanel extends JPanel{
    JLabel titleLabel = new JLabel("Snake");
    JButton startButton = new JButton("Start");
    JButton highScoreButton = new JButton("High-Score");
    JButton quitButton = new JButton("Quit");

    MenuPanel(ActionListener actionListener) {
        GameFrame gameFrame = (GameFrame) actionListener;

        titleLabel.setFont(new Font("default", Font.BOLD, (int)(gameFrame.getWidth()*0.065)));
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        add(titleLabel);

        startButton.setActionCommand("START");
        highScoreButton.setActionCommand("HIGH_SCORE");
        quitButton.setActionCommand("QUIT");
        JButton[] buttons = {startButton, highScoreButton, quitButton};
        for (JButton button: buttons) {
            button.setVisible(true);
            add(button);
            button.addActionListener(actionListener);
        }

        setLayout(new GridLayout(7, 1, 0, (int)(gameFrame.getHeight()*0.05)));
        setBorder(BorderFactory.createEmptyBorder((int)(gameFrame.getHeight()*0.05), (int)(gameFrame.getWidth()*0.1),
                (int)(gameFrame.getHeight()*0.05), (int)(gameFrame.getWidth()*0.1)));

        this.setAlignmentY(CENTER_ALIGNMENT);
    }
}
