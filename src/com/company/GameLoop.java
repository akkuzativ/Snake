package com.company;

public class GameLoop {
    final Board board;
    final BoardPanel boardPanel;
    boolean notPaused;
    boolean gameOver;

    GameLoop(Board board, BoardPanel boardPanel){
        this.board = board;
        this.boardPanel = boardPanel;
        this.gameOver = false;
        this.notPaused = true;
    }

    void updateState() {

    }

    void updateGraphics() {
        this.boardPanel.setCurrentBoard(board);
        this.boardPanel.paintImmediately(this.boardPanel.getBounds());
    }

    void processInput() {

    }

    void run() {
        this.updateGraphics();
        while (true) {

            processInput();

            if (this.notPaused) {
                this.updateState();
                this.updateGraphics();
            }

            if (this.gameOver) {
                break;
            }

        }
    }
}
