package com.company;

import java.util.ArrayList;

public class SnakeAI implements AI {
    private Board board;
    private Snake snake;

    SnakeAI(Board board, Snake snake) {
        this.board = board;
        this.snake = snake;
    }

    public Direction getNextMoveDirection() {
        BFSalgorithm bfs = new BFSalgorithm(board);
        Direction nextDirection;
        try {
            ArrayList<Coordinates> path = bfs.getShortestPathToFrogOrFruit(snake.getSnakeHead());
            Coordinates nextMove = path.get(1);
            Coordinates currentPosition = snake.getSnakeHead();
            int[] deltas = {nextMove.x - currentPosition.x, nextMove.y - currentPosition.y};
            nextDirection = DirectionUtilities.getDirection(deltas);
        } catch (Exception e) {
            nextDirection = DirectionUtilities.getRandomDirection();
        }
        return nextDirection;
    }
}
