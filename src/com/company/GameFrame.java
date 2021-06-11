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
    GameLoop gameLoop;

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
        JPanel panelToDisplay = null;
        switch (e.getActionCommand()){
            case "START":
                try {
                    int fruitCount = 3;
                    int frogCount = 2;
                    BoardGenerator boardGenerator = new BoardGenerator(30, 30, fruitCount, frogCount,2,
                                                          10, 20, 3);
                    board = boardGenerator.generateBoard();
                    gameLoop = new GameLoop(board, boardPanel, this);
                    setContentPane(boardPanel);
                    gameLoop.start();
                    boardPanel.setCurrentBoard(board);
                    for (Frog frog: this.board.getFrogs()) {
                        FrogAI frogAI = new FrogAI(board, frog);
//                        System.out.println("frogAI: " + frogAI.getNextMoveDirection());
                    }
                    SnakeAI snakeAI = new SnakeAI(board, board.getEnemySnake());
                    System.out.println("snakeAI: " + snakeAI.getNextMoveDirection());
                } catch (TileOutOfBoundsException exception) {
                    System.out.println("Invalid board generated");
                    return;
                }
                panelToDisplay = boardPanel;

                break;
            case "SETTINGS":
                panelToDisplay = settingsPanel;
                break;
            case "HIGH_SCORE":
                panelToDisplay = highScorePanel;
                break;
            case "QUIT":
                dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
                break;
            case "MENU":
                panelToDisplay = menuPanel;
                break;
            default:
        }
        if (panelToDisplay != null)
        {
            setContentPane(panelToDisplay);
            revalidate();
            repaint();
        }
    }
}
