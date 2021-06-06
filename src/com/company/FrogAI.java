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
        ArrayList<Coordinates> path = bfs.getLongestFrogPath(bfs.createFrogMatrix(this.frog), this.frog);
        Coordinates nextMove = path.get(1);
        Coordinates currentPosition = this.frog.getCoordinates();
        int[] deltas = {nextMove.x - currentPosition.x, nextMove.y - currentPosition.y};
        return DirectionUtilities.getDirection(deltas);
    }
}
