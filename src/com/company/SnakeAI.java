package com.company;

import java.util.ArrayList;

public class SnakeAI {
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

        if (deltas[0] == 0 && deltas[1] == -1) {
            return Direction.UP;
        } else if (deltas[0] == 0 && deltas[1] == 1) {
            return Direction.DOWN;
        } else if (deltas[0] == -1 && deltas[1] == 0) {
            return Direction.LEFT;
        } else {
            return Direction.RIGHT;
        }
    }
}
