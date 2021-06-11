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
    SnakeAI snakeAI;
    FrogAI frogAI;

    Snake snake;
    Snake enemySnake;
    ArrayList<Frog> frogs;
    ArrayList<Fruit> fruits;
    ArrayList<Coordinates> obstacles;
    ArrayList<GameObjectThread> gameObjectThreads = new ArrayList<>();

    GameLoop(Board board, BoardPanel boardPanel, GameFrame gameFrame){
        this.board = board;
        this.boardPanel = boardPanel;
        this.gameFrame = gameFrame;

        this.gameOver = false;
        this.notPaused = true;

        this.keyboardHandler = new KeyboardHandler();
        this.gameFrame.addKeyListener(keyboardHandler);

        this.gameFrame.setFocusable(true);


        this.snake = board.getPlayerSnake();
        this.enemySnake = board.getEnemySnake();
        this.frogs = board.getFrogs();
        this.fruits = board.getFruits();

        this.snakeAI = new SnakeAI(board, this.enemySnake);
        this.frogAI = new FrogAI(board, frogs.get(0));
    }

    private void updateState() {
        for (GameObjectThread gameObjectThread : this.gameObjectThreads) {
            gameObjectThread.performNextAction();
        }
    }

    private void render() {
        this.boardPanel.setCurrentBoard(board);
        this.gameFrame.revalidate();
        this.gameFrame.repaint();
    }

    public void run() {
        boardPanel.setCurrentBoard(board);
        // TODO: Dodać pozostałe wątki
//        this.gameObjectThreads.add(new EnemySnakeThread(this.board));
        this.gameObjectThreads.add(new PlayerSnakeThread(this.board, this.keyboardHandler));
        for (Frog frog : this.board.getFrogs()) {
            this.gameObjectThreads.add(new FrogThread(this.board, frog));
        }
        this.gameObjectThreads.add(new FruitsAndFrogsGeneratorThread(this.board));

        for (GameObjectThread gameObjectThread : this.gameObjectThreads) {
            gameObjectThread.start();
        }

        while (true) {
            if (this.notPaused) {
                this.render();

                for (GameObjectThread gameObjectThread : this.gameObjectThreads) {
                    gameObjectThread.startCalculatingNextAction();
                }

                // !!!!!!!!!!!!!!!!!!!
                // TODO
                try {
                    Thread.sleep(160);
                } catch (Exception e) {
                }
                // !!!!!!!!!!!!!!!!!!!

//                processInput();

                updateState();

                if (this.gameOver) {
                    break;
                }
            }
        }
    }
}
