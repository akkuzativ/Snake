package com.company;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class FrogAI implements AI {
    private Board board;
    private Frog frog;
    private final Random rand;
    private BFSalgorithm bfs;

    FrogAI(Board board, Frog frog) {
        this.board = board;
        this.frog = frog;
        this.rand = new Random();
        this.bfs = new BFSalgorithm(board);
    }

    public Direction getNextMoveDirection() {
        Direction nextDirection;

        if (this.rand.nextInt(30) <= 1) {
            nextDirection = getRandomLegalDirection();
        } else {
            try {
                ArrayList<Coordinates> path = this.bfs.getLongestFrogPath(this.bfs.createFrogMatrix(this.frog), this.frog);
                Coordinates nextMove = path.get(1);
                Coordinates currentPosition = this.frog.getCoordinates();
                int[] deltas = {nextMove.x - currentPosition.x, nextMove.y - currentPosition.y};
                nextDirection = DirectionUtilities.getDirection(deltas);
            } catch (Exception e) {
                nextDirection = getRandomLegalDirection();
            }
        }
        return nextDirection;
    }

    private Direction getRandomLegalDirection() {
        ArrayList<Direction> directions = new ArrayList<>(List.of(Direction.UP, Direction.DOWN, Direction.LEFT, Direction.RIGHT));
        Collections.shuffle(directions);
        for (Direction direction : directions) {
            int[] deltas = DirectionUtilities.getDeltas(direction);
            int nextXCoordinate = this.frog.getCoordinates().x + deltas[0];
            int nextYCoordinate = this.frog.getCoordinates().y + deltas[1];
            if (this.bfs.areCoordinatesUnoccupiedAndInBoundsForFrog(new Coordinates(nextXCoordinate, nextYCoordinate))) {
                return direction;
            }
        }
        return DirectionUtilities.getRandomDirection();
    }
}
