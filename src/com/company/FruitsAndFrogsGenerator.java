package com.company;

import java.util.ArrayList;
import java.util.Random;

public class FruitsAndFrogsGenerator {
    private final Board board;
    private final int fruitCount;
    private final int frogCount;
    private final Random rand;

    FruitsAndFrogsGenerator(Board board, int fruitCount, int frogCount) {
        this.board = board;
        this.fruitCount = fruitCount;
        this.frogCount = frogCount;
        this.rand = new Random();
    }

    /***
     * Generates random unoccupied coordinates in given bounds
     * @param minX minimum width bound
     * @param maxX maximum width bound
     * @param minY minimum height bound
     * @param maxY maximum height bound
     * @return generated random unoccupied coordinates in given bounds
     */
    private Coordinates generateUnoccupiedCoordinates(int minX, int maxX, int minY, int maxY) {
        int xCoordinate = minX + rand.nextInt(maxX - minX);
        int yCoordinate = minY + rand.nextInt(maxY - minY);
        BoardTile[][] boardTiles = this.board.toTiles();

        while (boardTiles[xCoordinate][yCoordinate] != BoardTile.EMPTY) {
            xCoordinate = minX + rand.nextInt(maxX - minX);
            yCoordinate = minY + rand.nextInt(maxY - minY);
        }
        return new Coordinates(xCoordinate, yCoordinate);
    }

    /***
     * Generates a list of fruits missing from the board
     * @return list of fruits missing from the board
     */
    public ArrayList<Fruit> generateMissingFruitsList() {
        int missingFruitsNumber = this.fruitCount - this.board.getFruits().size();
        ArrayList<Fruit> missingFruits = new ArrayList<>();
        for (int i = 0; i < this.rand.nextInt(Math.max(0, missingFruitsNumber + 1)); i++) {
            missingFruits.add(new Fruit(generateUnoccupiedCoordinates(0, this.board.getWidth(),
                    0, this.board.getHeight())));
        }
        return missingFruits;
    }

    /***
     * Generates a list of frogs missing from the board
     * @return list of frogs missing from the board
     */
    public ArrayList<Frog> generateMissingFrogsList() {
        int missingFrogsNumber = this.frogCount - this.board.getFrogs().size();
        ArrayList<Frog> missingFrogs = new ArrayList<>();
        for (int i = 0; i < this.rand.nextInt(Math.max(0, missingFrogsNumber + 1)); i++) {
            missingFrogs.add(new Frog(generateUnoccupiedCoordinates(0, this.board.getWidth(),
                    0, this.board.getHeight())));
        }
        return missingFrogs;
    }

    /***
     * Adds missing fruits and frogs to the board
     * @param missingFruits list of fruits missing from the board
     * @param missingFrogs list of frogs missing from the board
     */
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
