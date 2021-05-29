package com.company;

public class GameLoop extends Thread{
    final Board board;
    final BoardPanel boardPanel;
    int maxFPS;
    boolean notPaused;
    boolean gameOver;
    int currentScore;
    Settings settings;
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
                System.out.println("up");
                break;
            case DOWN:
                System.out.println("down");
                break;
            case LEFT:
                System.out.println("left");
                break;
            case RIGHT:
                System.out.println("right");
                break;
            case ESC:
                System.out.println("esc");
                break;
            default:
                break;
        }
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
