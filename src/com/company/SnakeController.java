package com.company;

import java.util.ArrayList;

public class SnakeController {
    private Board board;
    private Snake snake;
    private boolean nextMoveGrows;
    private boolean isDead;

    SnakeController(Board board, Snake snake) {
        this.board = board;
        this.snake = snake;
        this.nextMoveGrows = false;
        this.isDead = false;
    }

    public void move() {
        // TODO: sprawdzenie czy na następnym polu jest owoc
        handleCollisions();
        if (!nextMoveGrows) {
            normalMove();
        } else {
            growingMove();
            nextMoveGrows = false;
        }
    }

    public void handleCollisions() {
        Coordinates head = snake.getSnakeHead();
        if (head.x < 0 || head.x > board.getWidth() || head.y < 0 || head.y > board.getHeight()) {
            this.snake = null;
            this.isDead = true;
        }
        else {
            Board.GameObjectArrayList[][] references = board.getReferenceMatrix(snake);
            ArrayList<Collidable> gameObjects = references[head.x][head.y].gameObjects;
            if (!gameObjects.isEmpty()) {
                for (Collidable gameObject: gameObjects) {
                    System.out.println(gameObject.getClass().getSimpleName());
                    switch (gameObject.getClass().getSimpleName()) {
                        case "Coordinates": case "Snake":
                            this.snake = null;
                            this.isDead = true;
                            break;
                        case "Frog": case "Fruit":
                            this.nextMoveGrows = true;
                            gameObject = null;
                            break;
                        default:
                            break;
                    }
                }
            }
        }
    }

    private void normalMove() {
        Coordinates snakeHead = snake.getSnakeHead();
        ArrayList<Coordinates> snakeBody = snake.getSnakeBody();
        Direction moveDirection = snake.getMoveDirection();
        for (int i = (snakeBody.size() - 1); i >= 0; i-- ) {
            if (i == 0) {
                snakeBody.get(i).x = snakeHead.x;
                snakeBody.get(i).y = snakeHead.y;
            }
            else {
                snakeBody.get(i).x = snakeBody.get(i - 1).x;
                snakeBody.get(i).y = snakeBody.get(i - 1).y;
            }
        }
        snakeHead.x += DirectionUtilities.getDeltas(moveDirection)[0];
        snakeHead.y += DirectionUtilities.getDeltas(moveDirection)[1];
    }

    private void growingMove() {
        Coordinates snakeHead = snake.getSnakeHead();
        ArrayList<Coordinates> snakeBody = snake.getSnakeBody();
        Direction moveDirection = snake.getMoveDirection();
        snakeBody.add(new Coordinates(snakeHead.x, snakeHead.y));
        snakeHead.x += DirectionUtilities.getDeltas(moveDirection)[0];
        snakeHead.y += DirectionUtilities.getDeltas(moveDirection)[1];
    }
}
