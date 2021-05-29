package com.company;

import java.util.ArrayList;

public class SnakeController {

    SnakeController() {
    }

    private int[] getDeltas(Direction direction) {
        int[] deltas = new int[2];
        switch (direction) {
            case UP -> deltas[1] = -1;
            case DOWN -> deltas[1] = 1;
            case LEFT -> deltas[0] = -1;
            case RIGHT -> deltas[0] = 1;
        }
        return deltas;
    }

    public void normalMove(Snake snake) {
        Coordinates snakeHead = snake.getSnakeHead();
        ArrayList<Coordinates> snakeBody = snake.getSnakeBody();
        Direction moveDirection = snake.getMoveDirection();
        for (int i = 0; i < snakeBody.size(); i++ ) {
            if (i == 0) {
                snakeBody.set(i, snakeHead);
            }
            else {
                snakeBody.set(i, snakeBody.get(i - 1));
            }
        }
        snakeHead.x += getDeltas(moveDirection)[0];
        snakeHead.y += getDeltas(moveDirection)[1];
    }

    public void growingMove(Snake snake) {
        Coordinates snakeHead = snake.getSnakeHead();
        ArrayList<Coordinates> snakeBody = snake.getSnakeBody();
        Direction moveDirection = snake.getMoveDirection();
        for (int i = 0; i < snakeBody.size(); i++ ) {
            if (i == 0) {
                snakeBody.set(i, snakeHead);
            }
            else if (i != snakeBody.size() - 1) {
                snakeBody.set(i, snakeBody.get(i - 1));
            }
        }
        snakeHead.x += getDeltas(moveDirection)[0];
        snakeHead.y += getDeltas(moveDirection)[1];
    }

}
