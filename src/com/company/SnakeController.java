package com.company;

import java.util.ArrayList;

public class SnakeController {
    private Board board;
    private Snake snake;

    SnakeController(Board board, Snake snake) {
        this.board = board;
        this.snake = snake;
    }

    public void move() {
        // TODO: sprawdzenie czy na następnym polu jest owoc
        if (true) {
            normalMove();
        } else {
            growingMove();
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
        for (int i = (snakeBody.size() - 1); i >= 0; i-- ) {
            if (i == (snakeBody.size() - 1)) {
                continue;
            }
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
}
