package com.company;

import java.util.ArrayList;

public class PlayerSnakeThread extends Thread implements GameObjectThread {
    private Board board;
    private SnakeAI snakeAI;
    private SnakeController snakeController;
    private boolean canCalculateNextAction = false;
    private Direction nextAction;
    private KeyboardHandler keyboardHandler;
    private ArrayList<Collidable> gameObjectsToRemove = new ArrayList<>();
    private boolean killed = false;

    PlayerSnakeThread(Board board, KeyboardHandler keyboardHandler) {
        this.board = board;
        this.snakeAI = new SnakeAI(board, board.getPlayerSnake());
        this.snakeController = new SnakeController(board, board.getPlayerSnake());
        this.keyboardHandler = keyboardHandler;
    }

    /***
     * Handles keyboard input when the flag is set
     */
    @Override
    public void run() {
        while (true) {
            if (board.getPlayerSnake() == null || killed) {
                break;
            }
            if (this.canCalculateNextAction) {
                this.nextAction = this.board.getPlayerSnake().getMoveDirection();
                switch (this.keyboardHandler.getRecentlyPressedKey()) {
                    case UP:
                        this.nextAction = Direction.UP;
                        break;
                    case DOWN:
                        this.nextAction = Direction.DOWN;
                        break;
                    case LEFT:
                        this.nextAction = Direction.LEFT;
                        break;
                    case RIGHT:
                        this.nextAction = Direction.RIGHT;
                        break;
                    default:
                        break;
                }
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
     * Sets next move direction for the player snake and handles possible collisions
     */
    @Override
    public void performNextAction() {
        try {
            this.board.getPlayerSnake().setMoveDirection(this.nextAction);
        }
        catch (IncorrectDirectionException ignored) {
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
     * Returns the player snake
     * @return player snake
     */
    @Override
    public Collidable getRelatedGameObject() {
        return board.getPlayerSnake();
    }

    /***
     * Sets thread kill flag to true
     */
    @Override
    public void forceKill() {
        killed = true;
    }
}
