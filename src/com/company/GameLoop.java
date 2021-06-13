package com.company;

import org.w3c.dom.events.Event;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;
import java.sql.Timestamp;
import java.util.*;

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
            if (gameObjectThread.getRelatedGameObject() != null) {
                gameObjectThread.performNextAction();
            }
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
                gameObjectThreads.removeIf( gameObjectThread -> gameObjectThread.getRelatedGameObject() == gameObject );
                board.removeGameObject(gameObject);
            }
        }
        gameObjectThreads.removeIf( gameObjectThread -> gameObjectThread.getRelatedGameObject() == null );
    }

    private void cleanUpAndExit() {
        for (GameObjectThread gameObjectThread: gameObjectThreads) {
            gameObjectThread.forceKill();
        }
        gameObjectThreads.clear();
    }

    public void run() {
        boardPanel.setCurrentBoard(board);
        this.gameObjectThreads.add(new EnemySnakeThread(this.board));
        this.gameObjectThreads.add(new PlayerSnakeThread(this.board, this.keyboardHandler));
        this.gameObjectThreads.add(new FruitsAndFrogsGeneratorThread(this.board));
        for (Frog frog : this.board.getFrogs()) {
            this.gameObjectThreads.add(new FrogThread(this.board, frog));
        }
        for (GameObjectThread gameObjectThread : this.gameObjectThreads) {
            gameObjectThread.start();
        }
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        long startTime = timestamp.getTime();
        long stopTime;
        //render();
        while (!gameOver) {
            for (Frog frog : this.board.getMissingFrogs()) {
                GameObjectThread gameObjectThread = new FrogThread(this.board, frog);
                this.gameObjectThreads.add(gameObjectThread);
                gameObjectThread.start();
            }
            this.board.clearMissingFrogs();

            for (GameObjectThread gameObjectThread : this.gameObjectThreads) {
                gameObjectThread.startCalculatingNextAction();
            }

            stopTime = timestamp.getTime();
            long timeDifference = startTime - stopTime;
            try {
                Thread.sleep(Math.max(0, 100 - timeDifference));
            } catch (Exception e) { }
            startTime = timestamp.getTime();

            //removeGameObjects();
            updateState();
            removeGameObjects();
            render();
        }
        cleanUpAndExit();


        //ActionEvent gameOverEvent = new ActionEvent(this, ActionEvent.ACTION_FIRST, "GAME_OVER");
        //gameFrame.event
    }
}
