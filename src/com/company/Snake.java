package com.company;

import java.util.ArrayList;

class IncorrectDirectionException extends Exception {}

public class Snake {
    private ArrayList<Coordinates> snakeBody;
    private Coordinates snakeHead;
    private Direction moveDirection;

    Snake(ArrayList<Coordinates> snakeBody, Coordinates snakeHead, Direction direction) {
        this.snakeBody = new ArrayList<>();
        for (Coordinates coordinates : snakeBody) {
            this.snakeBody.add(new Coordinates(coordinates.x, coordinates.y));
        }
        this.snakeHead = new Coordinates(snakeHead.x, snakeHead.y);
        this.moveDirection = direction;
    }

    public ArrayList<Coordinates> getSnakeBody() {
        return this.snakeBody;
    }

    public Coordinates getSnakeHead() {
        return this.snakeHead;
    }

    public void setMoveDirection(Direction moveDirection) throws IncorrectDirectionException {
        if (this.moveDirection == getOppositeDirection(moveDirection)) {
            throw new IncorrectDirectionException();
        }
        this.moveDirection = moveDirection;
    }

    public Direction getMoveDirection() {
        return moveDirection;
    }

    private Direction getOppositeDirection(Direction direction) {
        return switch (direction) {
            case UP -> Direction.DOWN;
            case DOWN -> Direction.UP;
            case LEFT -> Direction.RIGHT;
            case RIGHT -> Direction.LEFT;
        };
    }
}
