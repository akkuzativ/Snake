package com.company;

import java.util.ArrayList;

public class SnakeController {
    private Board board;
    private Snake snake;
    private boolean nextMoveGrows;

    SnakeController(Board board, Snake snake) {
        this.board = board;
        this.snake = snake;
        this.nextMoveGrows = false;
    }

    public void move() {
        System.out.println(snake.getMoveDirection());
        if (!nextMoveGrows) {
            normalMove();
        } else {
            growingMove();
            nextMoveGrows = false;
        }
    }

    public ArrayList<Collidable> handleCollisions() {
        Coordinates head = snake.getSnakeHead();
        ArrayList<Collidable> gameObjectsToRemove = new ArrayList<>();
        int[] deltas = DirectionUtilities.getDeltas(snake.getMoveDirection());
        if ( (head.x + deltas[0]) < 0 || (head.x + deltas[0]) >= board.getWidth() ||
                (head.y + deltas[1]) < 0 || (head.y + deltas[1]) >= board.getHeight()) {
            gameObjectsToRemove.add(snake);
        }
        else {
            Board.GameObjectArrayList[][] references = board.getReferenceMatrix(snake);
            ArrayList<Collidable> gameObjects = references[head.x + deltas[0]][head.y + deltas[1]].gameObjects;
            if (!gameObjects.isEmpty()) {
                for (Collidable gameObject: gameObjects) {
                    switch (gameObject.getName()) {
                        case "Coordinates": case "Snake":
                            gameObjectsToRemove.add(snake);
                            break;
                        case "Frog": case "Fruit":
                            System.out.flush();
                            System.out.println("hit Fruit");
                            this.nextMoveGrows = true;
                            gameObjectsToRemove.add(gameObject);
                            break;
                        default:
                            break;
                    }
                }
            }
        }
        return gameObjectsToRemove;
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
        Coordinates lastSegment = snakeBody.get(snakeBody.size() - 1);
        Coordinates segmentToAdd = new Coordinates(lastSegment.x, lastSegment.y);
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
        snakeBody.add(segmentToAdd);
        snakeHead.x += DirectionUtilities.getDeltas(moveDirection)[0];
        snakeHead.y += DirectionUtilities.getDeltas(moveDirection)[1];
    }
}
