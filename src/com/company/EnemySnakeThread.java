package com.company;

import java.util.ArrayList;

public class EnemySnakeThread extends Thread implements GameObjectThread{
    private Board board;
    private SnakeAI snakeAI;
    private SnakeController snakeController;
    private boolean canCalculateNextAction = false;
    private Direction nextAction;
    private ArrayList<Collidable> gameObjectsToRemove = new ArrayList<>();
    private boolean killed = false;

    EnemySnakeThread(Board board) {
        this.board = board;
        this.snakeAI = new SnakeAI(board, board.getEnemySnake());
        this.snakeController = new SnakeController(board, board.getEnemySnake());
    }

    /***
     * Calculates next enemy snake action using BFS algorithm when the flag is set
     */
    @Override
    public void run() {
        while (true) {
            if (board.getEnemySnake() == null || killed) {
                break;
            }
            if (this.canCalculateNextAction) {
                this.nextAction = this.snakeAI.getNextMoveDirection();
                this.canCalculateNextAction = false;
            }
            try {
                Thread.sleep(1);
            } catch (Exception e) {
            }
        }
    }

    /***
     * Sets the flag that allows the thread to calculate its next action
     */
    @Override
    public void startCalculatingNextAction() {
        this.canCalculateNextAction = true;
    }

    /***
     * Sets next move direction for the enemy snake and handles possible collisions
     */
    @Override
    public void performNextAction() {
        try {
            this.board.getEnemySnake().setMoveDirection(this.nextAction);
        } catch (IncorrectDirectionException ignored) {
        }
        this.snakeController.move();
        gameObjectsToRemove = snakeController.handleCollisions();
    }

    /***
     * Returns game object threads to be removed
     * @return game object threads to be removed
     */
    @Override
    public ArrayList<Collidable> getGameObjectsToRemove() {
        return gameObjectsToRemove;
    }

    /***
     * Returns the enemy snake
     * @return enemy snake
     */
    @Override
    public Collidable getRelatedGameObject() {
        return board.getEnemySnake();
    }

    /***
     * Sets thread kill flag to true
     */
    @Override
    public void forceKill() {
        killed = true;
    }
}
