package com.company;

public class PlayerSnakeThread extends Thread implements GameObjectThread {
    private Board board;
    private SnakeAI snakeAI;
    private SnakeController snakeController;
    private boolean canCalculateNextAction = false;
    private Direction nextAction;
    private KeyboardHandler keyboardHandler;

    PlayerSnakeThread(Board board, KeyboardHandler keyboardHandler) {
        this.board = board;
        this.snakeAI = new SnakeAI(board, board.getPlayerSnake());
        this.snakeController = new SnakeController(board, board.getPlayerSnake());
        this.keyboardHandler = keyboardHandler;
    }

    @Override
    public void run() {
        while (true) {
            if (this.canCalculateNextAction) {
                this.nextAction = this.board.getPlayerSnake().getMoveDirection();
                System.out.println("recently pressed key: " + keyboardHandler.getRecentlyPressedKey());
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
                System.out.println("nextAction: " + nextAction);
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
}
