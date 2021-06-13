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

    /***
     * Marks given coordinates as occupied on the occupiedTiles list
     * @param coordinates coordinates to be marked as occupied
     */
    private void markCoordinatesAsOccupied(Coordinates coordinates) {
        this.occupiedTiles[coordinates.x][coordinates.y] = true;
    }

    /***
     * Generates random unoccupied coordinates in given bounds
     * @param minX minimum width bound
     * @param maxX maximum width bound
     * @param minY minimum height bound
     * @param maxY maximum height bound
     * @return random unoccupied coordinates in given bounds
     */
    private Coordinates generateUnoccupiedCoordinates(int minX, int maxX, int minY, int maxY) {
        int xCoordinate = minX + this.rand.nextInt(maxX - minX);
        int yCoordinate = minY + this.rand.nextInt(maxY - minY);

        while (this.occupiedTiles[xCoordinate][yCoordinate]) {
            xCoordinate = minX + this.rand.nextInt(maxX - minX);
            yCoordinate = minY + this.rand.nextInt(maxY - minY);
        }
        return new Coordinates(xCoordinate, yCoordinate);
    }

    /***
     * Generates random unoccupied coordinates in board bounds
     * @return random unoccupied coordinates in board bounds
     */
    private Coordinates generateUnoccupiedCoordinates() {
        return generateUnoccupiedCoordinates(0, this.boardWidth, 0, this.boardHeight);
    }

    /***
     * Generates random unoccupied coordinates in given bounds and marks them as occupied on the occupiedTiles list
     * @param minX minimum width bound
     * @param maxX maximum width bound
     * @param minY minimum height bound
     * @param maxY maximum height bound
     * @return random unoccupied coordinates in given bounds
     */
    private Coordinates generateUnoccupiedCoordinatesAndMarkAsOccupied(int minX, int maxX, int minY, int maxY) {
        Coordinates coordinates = generateUnoccupiedCoordinates(minX, maxX, minY, maxY);
        markCoordinatesAsOccupied(coordinates);
        return coordinates;
    }

    /***
     * Generates random unoccupied coordinates in board bounds and marks them as occupied on the occupiedTiles list
     * @return random unoccupied coordinates in board bounds
     */
    private Coordinates generateUnoccupiedCoordinatesAndMarkAsOccupied() {
        return generateUnoccupiedCoordinatesAndMarkAsOccupied(0, this.boardWidth, 0, this.boardHeight);
    }

    /***
     * Generates a new snake on an unoccupied board tile
     * @return generated snake
     */
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

    /***
     * Generates the whole starting board with fruits, frogs, obstacles and snakes
     * @return generated starting board
     * @throws TileOutOfBoundsException when added game object are out of board bounds
     */
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

    /***
     * Checks whether given coordinates are not in board bounds with margins
     * @param coordinates the coordinates being checked
     * @param margin given margin
     * @return true if given coordinates are not in bounds and true if they are
     */
    private boolean areCoordinatesNotInBounds(Coordinates coordinates, int margin) {
        return coordinates.x < margin || coordinates.x >= this.boardWidth - margin ||
                coordinates.y < margin || coordinates.y >= this.boardHeight - margin;
    }

    /***
     * Checks if all line segment coordinates are in board bounds with margins
     * @param startingPoint starting point of generated line segment
     * @param endPoint end point of generated line segment
     * @param margin given margin
     * @return true if all line segment coordinates are in board bounds with margins and false if they aren't
     */
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

    /***
     * Generates a line segment of given length on an unoccupied board space
     * @param segmentLength generated line segment length
     * @param direction generated line segment direction
     * @return generated line segment
     */
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

            isRoomForSegment = areAllCoordinatesFree(startingPoint, endPoint, 3);

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
