package com.company;

import java.util.Locale;

public class GameLoop extends Thread{
    final Board board;
    final BoardPanel boardPanel;
    int targetFPS = 60;
    boolean notPaused;
    boolean gameOver;
    int currentScore;
    KeyboardHandler keyboardHandler;
    GameFrame gameFrame;

    GameLoop(Board board, BoardPanel boardPanel, GameFrame gameFrame){
        this.board = board;
        this.boardPanel = boardPanel;
        this.gameFrame = gameFrame;

        this.gameOver = false;
        this.notPaused = true;

        this.keyboardHandler = new KeyboardHandler();
        this.gameFrame.addKeyListener(keyboardHandler);

        this.gameFrame.setFocusable(true);
    }

    private void processInput() {
        switch (keyboardHandler.getRecentlyPressedKey()) {
            case UP:
                break;
            case DOWN:
                break;
            case LEFT:
                break;
            case RIGHT:
                break;
            case ESC:
                break;
            default:
                break;
        }
        System.out.println(keyboardHandler.getRecentlyPressedKey().toString().toLowerCase(Locale.ROOT));
        keyboardHandler.flushRecentlyPressedKey();
    }

    void updateState() {

    }

    private void render() {
        this.boardPanel.revalidate();
        this.boardPanel.repaint();

        this.gameFrame.revalidate();
        this.gameFrame.repaint();

    }

    public void run() {
        boardPanel.setCurrentBoard(board);

        while (true) {

            processInput();

            if (this.notPaused) {
                this.updateState();
                this.render();
            }

            if (this.gameOver) {
                break;
            }


            // !!!!!!!!!!!!!!!!!!!
            // TODO
            try {
                Thread.sleep(160);
            }
            catch (Exception e) {
            }
            // !!!!!!!!!!!!!!!!!!!


        }
    }
}
