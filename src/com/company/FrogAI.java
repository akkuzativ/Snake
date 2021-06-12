package com.company;

import java.util.ArrayList;

public class FrogAI implements AI {
    private Board board;
    private Frog frog;

    FrogAI(Board board, Frog frog) {
        this.board = board;
        this.frog = frog;
    }

    public Direction getNextMoveDirection() {
        BFSalgorithm bfs = new BFSalgorithm(board);
        Direction nextDirection;
        try {
            ArrayList<Coordinates> path = bfs.getLongestFrogPath(bfs.createFrogMatrix(this.frog), this.frog);
            Coordinates nextMove = path.get(1);
            Coordinates currentPosition = this.frog.getCoordinates();
            int[] deltas = {nextMove.x - currentPosition.x, nextMove.y - currentPosition.y};
            nextDirection = DirectionUtilities.getDirection(deltas);
        } catch (Exception e) {
            nextDirection = DirectionUtilities.getRandomDirection();
        }
        return nextDirection;
    }
}
