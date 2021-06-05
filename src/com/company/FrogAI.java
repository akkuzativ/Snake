package com.company;

import java.util.ArrayList;

public class FrogAI {
    private Board board;

    FrogAI(Board board) {
        this.board = board;
    }

    public Direction getNextMoveDirection() {
        BFSalgorithm bfs = new BFSalgorithm(board);
        ArrayList<Coordinates> path = bfs.getLongestFrogPath(bfs.createFrogMatrix());
        Coordinates nextMove = path.get(1);
        Coordinates currentPosition = board.getFrog().getCoordinates();
        int[] deltas = {nextMove.x - currentPosition.x, nextMove.y - currentPosition.y};
        System.out.println("Frog deltas: " + deltas[0] + " " + deltas[1]);
        return DirectionConverter.getDirection(deltas);
    }
}
