package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

/***
 * JFrame which serves the purpose of a main window and entry point of the game. Displayes different JPanels depending on the handled event.
 */
public class GameFrame extends JFrame implements ActionListener {
    final MenuPanel menuPanel;
    final BoardPanel boardPanel;
    final GameOverPanel gameOverPanel;
    final HighScorePanel highScorePanel;
    Board board;
    GameLoop gameLoop;
    HighScore highScoreRecords = new HighScore("HighScore.txt");

    /***
     * GameFrame constructor
     * @param size size of the JFrame (and the window)
     */
    GameFrame(Dimension size) {
        setSize(size);
        setMinimumSize(size);

        setTitle("Snake");

        menuPanel = new MenuPanel(this);
        boardPanel = new BoardPanel(true);
        gameOverPanel = new GameOverPanel(this);
        highScorePanel = new HighScorePanel(this);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
        JPanel[] panels = {menuPanel, boardPanel, gameOverPanel, highScorePanel};
        for (JPanel panel: panels) {
            panel.setVisible(true);
        }
        setContentPane(menuPanel);
        highScoreRecords.readFromFile();
    }

    /***
     * Handles global events from a currently displayed JPanel
     * @param e heard event
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        JPanel panelToDisplay = null;
        switch (e.getActionCommand()){
            case "START":
                try {
                    int fruitCount = 3;
                    int frogCount = 3;
                    BoardGenerator boardGenerator = new BoardGenerator(50, 50, fruitCount, frogCount,2,
                                                          10, 20, 3);
                    board = boardGenerator.generateBoard();
                    boardPanel.setCurrentBoard(board);
                    gameLoop = new GameLoop(board, boardPanel, this);
                    setContentPane(boardPanel);
                    gameLoop.start();
                } catch (TileOutOfBoundsException exception) {
                    System.out.println("Invalid board generated");
                    return;
                }
                panelToDisplay = boardPanel;
                break;
            case "HIGH_SCORE":
                panelToDisplay = highScorePanel;
                highScoreRecords.readFromFile();
                highScorePanel.reset();
                highScorePanel.setHighScoreRecords(highScoreRecords);
                break;
            case "QUIT":
                dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
                break;
            case "MENU":
                panelToDisplay = menuPanel;
                break;
            case "GAME_OVER":
                panelToDisplay = gameOverPanel;
                gameOverPanel.reset();
                gameOverPanel.setHighScoreRecords(highScoreRecords);
                gameOverPanel.setScore(gameLoop.getLastScore());
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
