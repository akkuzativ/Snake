package com.company;

import java.util.ArrayList;
import java.util.Random;

public class FruitsAndFrogGenerator {
    private final Board board;
    private final int fruitsNumber;

    FruitsAndFrogGenerator(Board board, int fruitsNumber) {
        this.board = board;
        this.fruitsNumber = fruitsNumber;
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

    public void generateFruitsAndFrog() throws TileOutOfBoundsException {
        if (this.board.getFruits().size() < fruitsNumber) {
            int missingFruitsNumber = fruitsNumber - this.board.getFruits().size();
            ArrayList<Fruit> missingFruits = new ArrayList<>();
            for (int i = 0; i < missingFruitsNumber; i++) {
                missingFruits.add(new Fruit(generateUnoccupiedCoordinates(0, this.board.getWidth(),
                                                                    0, this.board.getHeight())));
            }
            this.board.setFruits(missingFruits);
        }
        if (this.board.getFrog() == null) {
            board.setFrog(new Frog(generateUnoccupiedCoordinates(0, this.board.getWidth(),
                                                            0, this.board.getHeight())));
        }
    }
}
