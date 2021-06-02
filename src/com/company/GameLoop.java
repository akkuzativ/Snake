package com.company;

import java.util.ArrayList;

public class GameLoop extends Thread{
    final Board board;
    final BoardPanel boardPanel;
    int targetFPS = 60;
    boolean notPaused;
    boolean gameOver;
    int currentScore;
    KeyboardHandler keyboardHandler;
    GameFrame gameFrame;
    SnakeController snakeController;

    Snake snake;
    Snake enemySnake;
    Frog frog;
    ArrayList<Fruit> fruits;
    ArrayList<Coordinates> obstacles;

    GameLoop(Board board, BoardPanel boardPanel, GameFrame gameFrame){
        this.board = board;
        this.boardPanel = boardPanel;
        this.gameFrame = gameFrame;

        this.gameOver = false;
        this.notPaused = true;

        this.keyboardHandler = new KeyboardHandler();
        this.gameFrame.addKeyListener(keyboardHandler);

        this.gameFrame.setFocusable(true);

        this.snakeController = new SnakeController();

        this.snake = board.getSnake();
        this.enemySnake = board.getEnemySnake();
        this.frog = board.getFrog();
        this.fruits = board.getFruits();
    }

    private void processInput() {
        Direction newDirection = snake.getMoveDirection();
        switch (keyboardHandler.getRecentlyPressedKey()) {
            case UP:
                newDirection = Direction.UP;
                break;
            case DOWN:
                newDirection = Direction.DOWN;
                break;
            case LEFT:
                newDirection = Direction.LEFT;
                break;
            case RIGHT:
                newDirection = Direction.RIGHT;
                break;
            case ESC:
                notPaused = !notPaused;
                break;
            default:
                break;
        }
        if (notPaused) {
            try {
                snake.setMoveDirection(newDirection);
            }
            catch (IncorrectDirectionException e) {
            }
        }
        keyboardHandler.flushRecentlyPressedKey();
    }

    private void updateState() {
        snakeController.normalMove(snake);
        //snakeController.normalMove(enemySnake);
    }

    private void render() {
        this.boardPanel.setCurrentBoard(board);

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
