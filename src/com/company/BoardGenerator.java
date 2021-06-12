package com.company;

import java.util.ArrayList;
import java.util.Random;

public class BoardGenerator {
    private final boolean[][] occupiedTiles;
    private final int boardWidth;
    private final int boardHeight;
    private final int fruitCount;
    private final int frogCount;
    private final int obstaclesCount;
    private final int snakeLength;
    private final int minObstacleLength;
    private final int maxObstacleLength;
    private final Random rand;

    BoardGenerator(int boardWidth, int boardHeight, int fruitCount, int frogCount, int obstaclesCount,
                   int minObstacleLength, int maxObstacleLength, int snakeLength) {
        this.rand = new Random();
        this.occupiedTiles = new boolean[boardWidth][boardHeight];
        this.boardWidth = boardWidth;
        this.boardHeight = boardHeight;
        this.fruitCount = fruitCount;
        this.frogCount = frogCount;
        this.obstaclesCount = obstaclesCount;
        this.minObstacleLength = minObstacleLength;
        this.maxObstacleLength = maxObstacleLength;
        this.snakeLength = snakeLength;
    }

    private void markCoordinatesAsOccupied(Coordinates coordinate) {
        this.occupiedTiles[coordinate.x][coordinate.y] = true;
    }

    private Coordinates generateUnoccupiedCoordinates(int minX, int maxX, int minY, int maxY) {
        int xCoordinate = minX + this.rand.nextInt(maxX - minX);
        int yCoordinate = minY + this.rand.nextInt(maxY - minY);

        while (this.occupiedTiles[xCoordinate][yCoordinate]) {
            xCoordinate = minX + this.rand.nextInt(maxX - minX);
            yCoordinate = minY + this.rand.nextInt(maxY - minY);
        }
        return new Coordinates(xCoordinate, yCoordinate);
    }

    private Coordinates generateUnoccupiedCoordinates() {
        return generateUnoccupiedCoordinates(0, this.boardWidth, 0, this.boardHeight);
    }

    private Coordinates generateUnoccupiedCoordinatesAndMarkAsOccupied(int minX, int maxX, int minY, int maxY) {
        Coordinates coordinates = generateUnoccupiedCoordinates(minX, maxX, minY, maxY);
        markCoordinatesAsOccupied(coordinates);
        return coordinates;
    }

    private Coordinates generateUnoccupiedCoordinatesAndMarkAsOccupied() {
        return generateUnoccupiedCoordinatesAndMarkAsOccupied(0, this.boardWidth, 0, this.boardHeight);
    }

    private Snake generateSnake() {
        Direction snakeDirection = DirectionUtilities.getRandomDirection();
        ArrayList<Coordinates> snakeSegment = generateLineSegment(this.snakeLength, snakeDirection);
        Coordinates snakeHead = snakeSegment.get(0);
        for (Coordinates segmentCoordinate : snakeSegment) {
            this.occupiedTiles[segmentCoordinate.x][segmentCoordinate.y] = true;
        }
        snakeSegment.remove(0);
        return new Snake(snakeSegment, snakeHead, DirectionUtilities.getOppositeDirection(snakeDirection));
    }

    public Board generateBoard() throws TileOutOfBoundsException {
        Board generatedBoard = new Board(this.boardWidth, this.boardHeight);

        for (int i = 0; i < this.fruitCount; i++) {
            Fruit fruit = new Fruit(generateUnoccupiedCoordinatesAndMarkAsOccupied());
            generatedBoard.addFruit(fruit);
        }
        for (int i = 0; i < this.frogCount; i++) {
            Frog frog = new Frog(generateUnoccupiedCoordinatesAndMarkAsOccupied());
            generatedBoard.addFrog(frog);
        }
        for (int i = 0; i < this.obstaclesCount; i++) {
            int segmentLength = this.minObstacleLength + rand.nextInt(this.maxObstacleLength - this.minObstacleLength);
            ArrayList<Coordinates> obstacleSegment = generateLineSegment(segmentLength, DirectionUtilities.getRandomDirection());
            for (Coordinates segmentCoordinate : obstacleSegment) {
                generatedBoard.addObstacle(segmentCoordinate);
                this.occupiedTiles[segmentCoordinate.x][segmentCoordinate.y] = true;
            }
        }
        generatedBoard.setPlayerSnake(generateSnake());
        generatedBoard.setEnemySnake(generateSnake());
        return generatedBoard;
    }

    private boolean areCoordinatesNotInBounds(Coordinates coordinates, int margin) {
        return coordinates.x < margin || coordinates.x >= this.boardWidth - margin ||
                coordinates.y < margin || coordinates.y >= this.boardHeight - margin;
    }

    private boolean areAllCoordinatesFree(Coordinates startingPoint, Coordinates endPoint, int margin) {
        if (areCoordinatesNotInBounds(startingPoint, margin) || areCoordinatesNotInBounds(endPoint, margin)) {
            return false;
        }

        for (int i = Math.min(startingPoint.x, endPoint.x) - margin; i <= Math.max(startingPoint.x, endPoint.x) + margin; i++) {
            for (int j = Math.min(startingPoint.y, endPoint.y) - margin; j <= Math.max(startingPoint.y, endPoint.y) + margin; j++) {
                if (this.occupiedTiles[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    private ArrayList<Coordinates> generateLineSegment(int segmentLength, Direction direction) {
        ArrayList<Coordinates> outputCoordinates = new ArrayList<>();
        int[] deltas = DirectionUtilities.getDeltas(direction);
        int deltaX = deltas[0];
        int deltaY = deltas[1];
        boolean isRoomForSegment = false;

        while (!isRoomForSegment) {
            Coordinates startingPoint = generateUnoccupiedCoordinates();
            Coordinates endPoint = new Coordinates(startingPoint.x + deltaX * (segmentLength - 1),
                                                   startingPoint.y + deltaY * (segmentLength - 1));

            isRoomForSegment = areAllCoordinatesFree(startingPoint, endPoint, 10);

            if (isRoomForSegment) {
                for (int i = 0; i < segmentLength; i++) {
                    Coordinates coordinates = new Coordinates(startingPoint.x + deltaX * i, startingPoint.y + deltaY * i);
                    outputCoordinates.add(coordinates);
                }
            }
        }
        return outputCoordinates;
    }
}
