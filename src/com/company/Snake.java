package com.company;

import java.util.ArrayList;

public class Snake implements Collidable{
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
        if (this.moveDirection == DirectionUtilities.getOppositeDirection(moveDirection)) {
            throw new IncorrectDirectionException();
        }
        this.moveDirection = moveDirection;
    }

    public Direction getMoveDirection() {
        return moveDirection;
    }
}
