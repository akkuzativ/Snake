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
        ArrayList<Coordinates> path = bfs.getShortestPathToFrogOrFruit(snake.getSnakeHead());
        Coordinates nextMove = path.get(1);
        Coordinates currentPosition = snake.getSnakeHead();
        int[] deltas = {nextMove.x - currentPosition.x, nextMove.y - currentPosition.y};
        return DirectionUtilities.getDirection(deltas);
    }
}
