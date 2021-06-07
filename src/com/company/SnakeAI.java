package com.company;

import java.util.ArrayList;

public class SnakeAI implements AI {
    private Board board;

    SnakeAI(Board board) {
        this.board = board;
    }

    public Direction getNextMoveDirection() {
        BFSalgorithm bfs = new BFSalgorithm(board);
        ArrayList<Coordinates> path = bfs.getShortestPathToFrogOrFruit(this.board.getEnemySnake().getSnakeHead());
        Coordinates nextMove = path.get(1);
        Coordinates currentPosition = board.getEnemySnake().getSnakeHead();
        int[] deltas = {nextMove.x - currentPosition.x, nextMove.y - currentPosition.y};
        return DirectionUtilities.getDirection(deltas);
    }
}
