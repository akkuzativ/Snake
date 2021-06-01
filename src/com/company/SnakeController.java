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
                snakeBody.get(i).x = snakeHead.x;
                snakeBody.get(i).y = snakeHead.y;
            }
            else {
                snakeBody.get(i).x = snakeBody.get(i - 1).x;
                snakeBody.get(i).y = snakeBody.get(i - 1).y;
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
                snakeBody.get(i).x = snakeHead.x;
                snakeBody.get(i).y = snakeHead.y;
            }
            else if (i != snakeBody.size() - 1) {
                snakeBody.get(i).x = snakeBody.get(i - 1).x;
                snakeBody.get(i).y = snakeBody.get(i - 1).y;
            }
        }
        snakeHead.x += getDeltas(moveDirection)[0];
        snakeHead.y += getDeltas(moveDirection)[1];
    }
}
