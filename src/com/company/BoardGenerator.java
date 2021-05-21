package com.company;

import java.util.ArrayList;
import java.util.Random;

public class BoardGenerator {
    private final boolean[][] occupiedTiles;
    private final int boardWidth;
    private final int boardHeight;
    private final int fruitCount;
    private final int obstaclesCount;
    private final int snakeLength;
    private final int minObstacleLength;
    private final int maxObstacleLength;
    private final Random rand;

    BoardGenerator(int boardWidth, int boardHeight, int fruitCount, int obstaclesCount,
                   int minObstacleLength, int maxObstacleLength, int snakeLength) {
        this.rand = new Random();
        this.occupiedTiles = new boolean[boardWidth][boardHeight];
        this.boardWidth = boardWidth;
        this.boardHeight = boardHeight;
        this.fruitCount = fruitCount;
        this.obstaclesCount = obstaclesCount;
        this.minObstacleLength = minObstacleLength;
        this.maxObstacleLength = maxObstacleLength;
        this.snakeLength = snakeLength;
    }

    void markCoordinatesAsOccupied(Coordinates coordinate) {
        this.occupiedTiles[coordinate.x][coordinate.y] = true;
    }

    Coordinates generateUnoccupiedCoordinates(int minX, int maxX, int minY, int maxY) {
        int xCoordinate = minX + this.rand.nextInt(maxX - minX);
        int yCoordinate = minY + this.rand.nextInt(maxY - minY);

        while (this.occupiedTiles[xCoordinate][yCoordinate]) {
            xCoordinate = minX + this.rand.nextInt(maxX - minX);
            yCoordinate = minY + this.rand.nextInt(maxY - minY);
        }
        return new Coordinates(xCoordinate, yCoordinate);
    }

    Coordinates generateUnoccupiedCoordinates() {
        return generateUnoccupiedCoordinates(0, this.boardWidth, 0, this.boardHeight);
    }

    Coordinates generateUnoccupiedCoordinatesAndMarkAsOccupied(int minX, int maxX, int minY, int maxY) {
        Coordinates coordinates = generateUnoccupiedCoordinates(minX, maxX, minY, maxY);
        markCoordinatesAsOccupied(coordinates);
        return coordinates;
    }

    Coordinates generateUnoccupiedCoordinatesAndMarkAsOccupied() {
        return generateUnoccupiedCoordinatesAndMarkAsOccupied(0, this.boardWidth, 0, this.boardHeight);
    }

    Snake generateSnake() {
        Direction snakeDirection = getRandomDirection();
        ArrayList<Coordinates> snakeSegment = generateLineSegment(this.snakeLength, snakeDirection);
        Coordinates snakeHead = snakeSegment.get(0);
        snakeSegment.remove(0);
        return new Snake(snakeSegment, snakeHead, snakeDirection);
    }

    Board generateBoard() throws TileOutOfBoundsException {
        Board generatedBoard = new Board(this.boardWidth, this.boardHeight);
        Frog frog = new Frog(generateUnoccupiedCoordinatesAndMarkAsOccupied());

        for (int i = 0; i < this.fruitCount; i++) {
            Fruit fruit = new Fruit(generateUnoccupiedCoordinatesAndMarkAsOccupied());
            generatedBoard.addFruit(fruit);
        }
        for (int i = 0; i < this.obstaclesCount; i++) {
            int segmentLength = this.minObstacleLength + rand.nextInt(this.maxObstacleLength - this.minObstacleLength);
            ArrayList<Coordinates> obstacleSegment = generateLineSegment(segmentLength, getRandomDirection());
            for (Coordinates segmentCoordinate : obstacleSegment) {
                generatedBoard.addObstacle(segmentCoordinate);
                this.occupiedTiles[segmentCoordinate.x][segmentCoordinate.y] = true;
            }
        }

        generatedBoard.setSnake(generateSnake());
        generatedBoard.setEnemySnake(generateSnake());
        generatedBoard.setFrog(frog);
        return generatedBoard;
    }

    Direction getRandomDirection() {
        Direction[] directions = Direction.values();
        return directions[this.rand.nextInt(directions.length)];
    }

    int[] getDeltas(Direction direction) {
        int[] deltas = new int[2];
        switch (direction) {
            case UP -> deltas[1] = -1;
            case DOWN -> deltas[1] = 1;
            case LEFT -> deltas[0] = -1;
            case RIGHT -> deltas[0] = 1;
        }
        return deltas;
    }


    boolean areCoordinatesNotInBounds(Coordinates coordinates, int margin) {
        return coordinates.x < margin || coordinates.x >= this.boardWidth - margin ||
                coordinates.y < margin || coordinates.y >= this.boardHeight - margin;
    }

    boolean areAllCoordinatesFree(Coordinates startingPoint, Coordinates endPoint, int margin) {
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

    ArrayList<Coordinates> generateLineSegment(int segmentLength, Direction direction) {
        ArrayList<Coordinates> outputCoordinates = new ArrayList<>();
        int[] deltas = getDeltas(direction);
        int deltaX = deltas[0];
        int deltaY = deltas[1];
        boolean isRoomForSegment = false;

        while (!isRoomForSegment) {
            Coordinates startingPoint = generateUnoccupiedCoordinates();
            Coordinates endPoint = new Coordinates(startingPoint.x + deltaX * (segmentLength - 1),
                                                   startingPoint.y + deltaY * (segmentLength - 1));

            isRoomForSegment = areAllCoordinatesFree(startingPoint, endPoint, 2);

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
