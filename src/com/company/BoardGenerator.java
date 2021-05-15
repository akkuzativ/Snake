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

    BoardGenerator(int boardWidth, int boardHeight, int fruitCount, int obstaclesCount, int snakeLength) {
        this.occupiedTiles = new boolean[boardWidth][boardHeight];
        this.boardWidth = boardWidth;
        this.boardHeight = boardHeight;
        this.fruitCount = fruitCount;
        this.obstaclesCount = obstaclesCount;
        this.snakeLength = snakeLength;
    }

    Coordinates generateUnoccupiedCoordinates(int minX, int maxX, int minY, int maxY) {
        Random rand = new Random();

        int xCoordinate = minX + rand.nextInt(maxX - minX);
        int yCoordinate = minY + rand.nextInt(maxY - minY);

        while (this.occupiedTiles[xCoordinate][yCoordinate]) {
            xCoordinate = minX + rand.nextInt(maxX - minX);
            yCoordinate = minY + rand.nextInt(maxY - minY);
        }
        this.occupiedTiles[xCoordinate][yCoordinate] = true;

        return new Coordinates(xCoordinate, yCoordinate);
    }

    Coordinates generateUnoccupiedCoordinates() {
        return generateUnoccupiedCoordinates(0, this.boardWidth, 0, this.boardHeight);
    }

    Board generateBoard() {
        Board generatedBoard = new Board(this.boardWidth, this.boardHeight);

        Frog frog = new Frog(generateUnoccupiedCoordinates());
        Snake enemySnake = new Snake(new ArrayList<>(), generateUnoccupiedCoordinates(), Direction.UP);
        Snake snake = new Snake(new ArrayList<>(), generateUnoccupiedCoordinates(), Direction.UP);
        try {
            for (int i = 0; i < fruitCount; i++) {
                Fruit fruit = new Fruit(generateUnoccupiedCoordinates());
                generatedBoard.addFruit(fruit);
            }
            for (int i = 0; i < obstaclesCount; i++) {
                generatedBoard.addObstacle(generateUnoccupiedCoordinates(2, this.boardWidth - 2, 2, this.boardHeight - 2));
            }
            generatedBoard.setSnake(snake);
            generatedBoard.setFrog(frog);
            generatedBoard.setEnemySnake(enemySnake);
        }
        catch (TileOutOfBoundsException e) {
            System.out.println("Out of boards bounds");
            System.exit(-1);
        }

        return generatedBoard;
    }
}
