package com.company;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        Board board = new Board(40, 40);
        Fruit fruit1 = new Fruit(new Coordinates(20, 20));
        Fruit fruit2 = new Fruit(new Coordinates(30, 30));
        Snake snake = new Snake(new ArrayList<>(), new Coordinates(10, 12), Direction.UP);
        try {
            board.addFruit(fruit1);
            board.addFruit(fruit2);
            board.setSnake(snake);
        }
        catch (TileOutOfBoundsException e) {
            System.out.println("Out of boards bounds");
            System.exit(-1);
        }
        JFrame gameFrame = new JFrame();
        gameFrame.setSize(new Dimension(800, 800));
        gameFrame.setVisible(true);
        BoardPanel boardPanel = new BoardPanel(true);
        boardPanel.setCurrentBoard(board);
        boardPanel.repaint();
        gameFrame.setContentPane(boardPanel);
    }
}
