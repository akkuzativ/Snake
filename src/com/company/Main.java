package com.company;

import javax.swing.*;
import java.awt.*;

public class Main {

    public static void main(String[] args) {
        BoardGenerator boardGenerator = new BoardGenerator(20, 20, 5, 10, 1);
        Board board = boardGenerator.generateBoard();

        JFrame gameFrame = new JFrame();
        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameFrame.setSize(new Dimension(800, 800));
        BoardPanel boardPanel = new BoardPanel(true);
        gameFrame.setContentPane(boardPanel);
        boardPanel.setCurrentBoard(board);
        gameFrame.setVisible(true);
    }
}
