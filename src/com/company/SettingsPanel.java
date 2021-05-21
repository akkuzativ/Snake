package com.company;

import javax.swing.*;
import java.awt.event.ActionListener;

public class SettingsPanel extends JPanel {
    JButton menuButton = new JButton("Back to Menu");

    SettingsPanel(ActionListener actionListener) {
        menuButton.setActionCommand("MENU");
        menuButton.addActionListener(actionListener);
        menuButton.setVisible(true);
        add(menuButton);
    }
}