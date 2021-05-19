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

    Board generateBoard() throws TileOutOfBoundsException {
        Board generatedBoard = new Board(this.boardWidth, this.boardHeight);

        try {
            for (int i = 0; i < this.obstaclesCount; i++) {
                int segmentLength = this.minObstacleLength + rand.nextInt(this.maxObstacleLength - this.minObstacleLength);
                ArrayList<Coordinates> obstacleSegment = generateLineSegment(segmentLength, getRandomDirection());
                for (Coordinates segmentCoordinate : obstacleSegment) {
                    generatedBoard.addObstacle(segmentCoordinate);
                    this.occupiedTiles[segmentCoordinate.x][segmentCoordinate.y] = true;
                }
            }
            for (int i = 0; i < this.fruitCount; i++) {
                Fruit fruit = new Fruit(generateUnoccupiedCoordinatesAndMarkAsOccupied());
                generatedBoard.addFruit(fruit);
            }
        }
        catch (TileOutOfBoundsException e) {
            System.out.println("Out of boards bounds");
            System.exit(-1);
        }

        Frog frog = new Frog(generateUnoccupiedCoordinatesAndMarkAsOccupied());

        Direction snakeDirection = getRandomDirection();
        ArrayList<Coordinates> snakeSegment = generateLineSegment(this.snakeLength, snakeDirection);
        Coordinates snakeHead = snakeSegment.get(0);
        snakeSegment.remove(0);
        Snake snake = new Snake(snakeSegment, snakeHead, snakeDirection);

        Direction enemySnakeDirection = getRandomDirection();
        ArrayList<Coordinates> enemySnakeSegment = generateLineSegment(this.snakeLength, enemySnakeDirection);
        Coordinates enemySnakeHead = enemySnakeSegment.get(0);
        enemySnakeSegment.remove(0);
        Snake enemySnake = new Snake(enemySnakeSegment, enemySnakeHead, enemySnakeDirection);

        generatedBoard.setSnake(snake);
        generatedBoard.setFrog(frog);
        generatedBoard.setEnemySnake(enemySnake);
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

    ArrayList<Coordinates> generateLineSegment(int segmentLength, Direction direction) throws TileOutOfBoundsException {
        ArrayList<Coordinates> outputCoordinates = new ArrayList<>();
        Coordinates startingPoint;
        int[] deltas;
        deltas = getDeltas(direction);
        int deltaX = deltas[0];
        int deltaY = deltas[1];
        int[] segmentXBounds = {2, this.boardWidth - 2};
        int[] segmentYBounds = {2, this.boardHeight - 2};
        boolean isRoomForSegment = false;

        while (!isRoomForSegment) {
            int segmentCount = 0;
            startingPoint = generateUnoccupiedCoordinates(segmentXBounds[0], segmentXBounds[1], segmentYBounds[0], segmentYBounds[1]);
            int endingX = startingPoint.x + deltaX * (segmentLength - 1);
            int endingY = startingPoint.y + deltaY * (segmentLength - 1);

            if (endingX < segmentXBounds[0] || endingX >= segmentXBounds[1] ||
                endingY < segmentYBounds[0] || endingY >= segmentYBounds[1]) {
                continue;
            }

            for (int i = 0; i < segmentLength; i++) {
                if (!this.occupiedTiles[startingPoint.x + deltaX * i][startingPoint.y + deltaY * i]) {
                    segmentCount++;
                }
            }

            if (segmentCount == segmentLength) {
                isRoomForSegment = true;
                for (int i = 0; i < segmentLength; i++) {
                    Coordinates coordinates = new Coordinates(startingPoint.x + deltaX * i, startingPoint.y + deltaY * i);
                    outputCoordinates.add(coordinates);
                }
            }
        }
        return outputCoordinates;
    }
}
