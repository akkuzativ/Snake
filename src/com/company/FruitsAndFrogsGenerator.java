package com.company;

import java.util.ArrayList;
import java.util.Random;

public class FruitsAndFrogsGenerator {
    private final Board board;
    private final int fruitCount;
    private final int frogCount;

    FruitsAndFrogsGenerator(Board board, int fruitCount, int frogCount) {
        this.board = board;
        this.fruitCount = fruitCount;
        this.frogCount = frogCount;
    }

    private Coordinates generateUnoccupiedCoordinates(int minX, int maxX, int minY, int maxY) {
        Random rand = new Random();
        int xCoordinate = minX + rand.nextInt(maxX - minX);
        int yCoordinate = minY + rand.nextInt(maxY - minY);
        BoardTile[][] boardTiles = this.board.toTiles();

        while (boardTiles[xCoordinate][yCoordinate] != BoardTile.EMPTY) {
            xCoordinate = minX + rand.nextInt(maxX - minX);
            yCoordinate = minY + rand.nextInt(maxY - minY);
        }
        return new Coordinates(xCoordinate, yCoordinate);
    }

    public void generateFruitsAndFrogs() throws TileOutOfBoundsException {
        if (this.board.getFruits().size() < fruitCount) {
            int missingFruitsNumber = fruitCount - this.board.getFruits().size();
            ArrayList<Fruit> missingFruits = new ArrayList<>();
            for (int i = 0; i < missingFruitsNumber; i++) {
                missingFruits.add(new Fruit(generateUnoccupiedCoordinates(0, this.board.getWidth(),
                                                                    0, this.board.getHeight())));
            }
            this.board.setFruits(missingFruits);
        }
        if (this.board.getFrogs().size() < frogCount) {
            int missingFrogsNumber = frogCount - this.board.getFrogs().size();
            ArrayList<Frog> missingFrogs = new ArrayList<>();
            for (int i = 0; i < missingFrogsNumber; i++) {
                missingFrogs.add(new Frog(generateUnoccupiedCoordinates(0, this.board.getWidth(),
                                                                    0, this.board.getHeight())));
            }
            this.board.setFrogs(missingFrogs);
        }
    }
}
