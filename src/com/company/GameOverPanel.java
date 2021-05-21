package com.company;

import javax.swing.*;
import java.awt.event.ActionListener;

public class GameOverPanel extends JPanel {
    JButton menuButton = new JButton("Back to Menu");

    GameOverPanel(ActionListener actionListener) {
        menuButton.setActionCommand("MENU");
        menuButton.addActionListener(actionListener);
        menuButton.setVisible(true);
        add(menuButton);
    }
}
