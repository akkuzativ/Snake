package com.company;

import java.util.ArrayList;
import java.util.Random;

public class FrogAI implements AI {
    private Board board;
    private Frog frog;
    private final Random rand;

    FrogAI(Board board, Frog frog) {
        this.board = board;
        this.frog = frog;
        this.rand = new Random();
    }

    public Direction getNextMoveDirection() {
        BFSalgorithm bfs = new BFSalgorithm(board);
        if (this.rand.nextInt(20) <= 1) {
            Direction direction = DirectionUtilities.getRandomDirection();
            int[] deltas = DirectionUtilities.getDeltas(direction);
//            TODO: sprawdzanie czy następny to wąż
            int nextXCoordinate = this.frog.getCoordinates().x + deltas[0];
            int nextYCoordinate = this.frog.getCoordinates().y + deltas[1];
            if (nextXCoordinate < 0 || nextXCoordinate > this.board.getWidth() ||
                nextYCoordinate < 0 || nextYCoordinate > this.board.getHeight()) {
                return DirectionUtilities.getOppositeDirection(direction);
            }
            return direction;
        }
        Direction nextDirection;
        try {
            ArrayList<Coordinates> path = bfs.getLongestFrogPath(bfs.createFrogMatrix(this.frog), this.frog);
            Coordinates nextMove = path.get(1);
            Coordinates currentPosition = this.frog.getCoordinates();
            int[] deltas = {nextMove.x - currentPosition.x, nextMove.y - currentPosition.y};
            nextDirection = DirectionUtilities.getDirection(deltas);
        } catch (Exception e) {
//            TODO: losowy ale legalny ruch
            nextDirection = DirectionUtilities.getRandomDirection();
        }
        return nextDirection;
    }
}
