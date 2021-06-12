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

    PlayerSnakeThread(Board board, KeyboardHandler keyboardHandler) {
        this.board = board;
        this.snakeAI = new SnakeAI(board, board.getPlayerSnake());
        this.snakeController = new SnakeController(board, board.getPlayerSnake());
        this.keyboardHandler = keyboardHandler;
    }

    @Override
    public void run() {
        while (true) {
            if (board.getPlayerSnake() == null) {
                break;
            }

            if (this.canCalculateNextAction) {
                gameObjectsToRemove = snakeController.handleCollisions();
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
                Thread.sleep(5);
            } catch (Exception e) {
            }
        }
    }

    @Override
    public void startCalculatingNextAction() {
        this.canCalculateNextAction = true;
    }

    @Override
    public void performNextAction() {
        try {
            this.board.getPlayerSnake().setMoveDirection(this.nextAction);
        }
        catch (IncorrectDirectionException ignored) {
        }
        this.snakeController.move();
    }

    public ArrayList<Collidable> getGameObjectsToRemove() {
        return gameObjectsToRemove;
    }

    public void clearGameObjectsToRemove() {
        gameObjectsToRemove.clear();
    }
}
