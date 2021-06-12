package com.company;

import org.w3c.dom.events.Event;

import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

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
        if (snake != null) {
            currentScore = snake.getSnakeBody().size() + 1;
        }
        if (board.getPlayerSnake() == null) {
            gameOver = true;
        }
    }

    private void render() {
        this.boardPanel.setCurrentBoard(board);
        this.gameFrame.revalidate();
        this.gameFrame.repaint();
    }

    private void removeGameObjects() {
        Set<Collidable> gameObjectsToRemove = new HashSet<Collidable>();
        for (GameObjectThread gameObjectThread : this.gameObjectThreads) {
            gameObjectsToRemove.addAll(gameObjectThread.getGameObjectsToRemove());
        }
        gameObjectsToRemove.removeIf(Objects::isNull);
        if (!gameObjectsToRemove.isEmpty()) {
            for (Collidable gameObject: gameObjectsToRemove) {
                board.removeGameObject(gameObject);
            }
        }
        gameObjectThreads.removeIf(gameObjectThread -> gameObjectThread.getRelatedGameObject() == null);
    }

    public void run() {
        boardPanel.setCurrentBoard(board);
        // TODO: Dodać pozostałe wątki
        //this.gameObjectThreads.add(new EnemySnakeThread(this.board));
        this.gameObjectThreads.add(new PlayerSnakeThread(this.board, this.keyboardHandler));
        for (Frog frog : this.board.getFrogs()) {
            this.gameObjectThreads.add(new FrogThread(this.board, frog));
        }
        //this.gameObjectThreads.add(new FruitsAndFrogsGeneratorThread(this.board));

        for (GameObjectThread gameObjectThread : this.gameObjectThreads) {
            gameObjectThread.start();
        }

        //render();
        while (!gameOver) {
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
            updateState();
            removeGameObjects();
            render();
        }
        //gameFrame.dispatchEvent(new Event());
    }
}
