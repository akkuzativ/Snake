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

    public ArrayList<Fruit> generateMissingFruitsList() {
        int missingFruitsNumber = fruitCount - this.board.getFruits().size();
        ArrayList<Fruit> missingFruits = new ArrayList<>();
        for (int i = 0; i < missingFruitsNumber; i++) {
            missingFruits.add(new Fruit(generateUnoccupiedCoordinates(0, this.board.getWidth(),
                    0, this.board.getHeight())));
        }
        return missingFruits;
    }

    public ArrayList<Frog> generateMissingFrogsList() {
        int missingFrogsNumber = frogCount - this.board.getFrogs().size();
        ArrayList<Frog> missingFrogs = new ArrayList<>();
        for (int i = 0; i < missingFrogsNumber; i++) {
            missingFrogs.add(new Frog(generateUnoccupiedCoordinates(0, this.board.getWidth(),
                    0, this.board.getHeight())));
        }
        return missingFrogs;
    }

    public void generateFruitsAndFrogs(ArrayList<Fruit> missingFruits, ArrayList<Frog> missingFrogs) {
        if (this.board.getFruits().size() < fruitCount) {
            try {
                for (Fruit missingFruit : missingFruits) {
                    this.board.addFruit(missingFruit);
                }
            } catch (TileOutOfBoundsException ignored) {

            }
        }
        if (this.board.getFrogs().size() < frogCount) {
            try {
                for (Frog missingFrog : missingFrogs) {
                    this.board.addFrog(missingFrog);
                }
            } catch (TileOutOfBoundsException ignored) {

            }
        }
    }
}
