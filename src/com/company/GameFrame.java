package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

public class GameFrame extends JFrame implements ActionListener {
    final MenuPanel menuPanel = new MenuPanel(this);
    final BoardPanel boardPanel = new BoardPanel(true);
    final SettingsPanel settingsPanel = new SettingsPanel(this);
    final GameOverPanel gameOverPanel = new GameOverPanel(this);
    final HighScorePanel highScorePanel = new HighScorePanel(this);
    Board board;
    BoardGenerator boardGenerator = new BoardGenerator(30, 30, 1,2,
            10, 20, 3);

    GameFrame(Dimension size) {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(size);
        setVisible(true);
        JPanel[] panels = {menuPanel, boardPanel, settingsPanel, gameOverPanel, highScorePanel};
        for (JPanel panel: panels) {
            panel.setVisible(true);
        }
        setContentPane(menuPanel);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()){
            case "START":
                System.out.println("start");
                try {
                    board = boardGenerator.generateBoard();
                    boardPanel.setCurrentBoard(board);

                    FrogAI frogAI = new FrogAI(board);
                    System.out.println(frogAI.getNextMoveDirection());

                    SnakeAI snakeAI = new SnakeAI(board);
                    System.out.println(snakeAI.getNextMoveDirection());

                } catch (TileOutOfBoundsException exception) {
                    System.out.println("Invalid board generated");
                    return;
                }
                setContentPane(boardPanel);
                revalidate();
                repaint();
                break;
            case "SETTINGS":
                System.out.println("settings");
                setContentPane(settingsPanel);
                revalidate();
                repaint();
                break;
            case "HIGH_SCORE":
                System.out.println("high_score");
                setContentPane(highScorePanel);
                revalidate();
                repaint();
                break;
            case "QUIT":
                System.out.println("quit");
                dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
                break;
            case "MENU":
                System.out.println("menu");
                setContentPane(menuPanel);
                revalidate();
                repaint();
                break;
            default:
        }
    }
}
