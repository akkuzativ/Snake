package com.company;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        Board board = new Board(25, 25);
        Fruit fruit = new Fruit(new Coordinates(20, 20));
        Frog frog = new Frog(new Coordinates(10, 10));
        Snake enemySnake = new Snake(new ArrayList<>(), new Coordinates(20, 12), Direction.UP);
        Snake snake = new Snake(new ArrayList<>(), new Coordinates(10, 12), Direction.UP);
        try {
            board.addFruit(fruit);
            board.setSnake(snake);
            board.setFrog(frog);
            board.setEnemySnake(enemySnake);
        }
        catch (TileOutOfBoundsException e) {
            System.out.println("Out of boards bounds");
            System.exit(-1);
        }
        JFrame gameFrame = new JFrame();
        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameFrame.setSize(new Dimension(800, 800));
        BoardPanel boardPanel = new BoardPanel(true);
        gameFrame.setContentPane(boardPanel);
        boardPanel.setCurrentBoard(board);
        gameFrame.setVisible(true);
    }
}
