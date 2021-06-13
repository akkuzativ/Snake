package com.company;

import java.util.*;

public class BFSalgorithm {
    private Board board;
    private BoardTile[][] boardTiles;

    BFSalgorithm(Board board) {
        this.board = board;
        this.boardTiles = this.board.toTiles();
    }

    public ArrayList<Coordinates> getShortestPathToFrogOrFruit(Coordinates startingPoint) {
        Coordinates targetCoordinates = null;
        boolean[][] visitedCoordinates = new boolean[board.getWidth()][board.getHeight()];
        Coordinates[][] previousCoordinates = new Coordinates[board.getWidth()][board.getHeight()];
        Queue<Coordinates> boardCoordinatesQueue = new LinkedList<>();

        boardCoordinatesQueue.add(startingPoint);
        visitedCoordinates[startingPoint.x][startingPoint.y] = true;

        while (!boardCoordinatesQueue.isEmpty()) {
            Coordinates currentCoordinates = boardCoordinatesQueue.poll();
            if (boardTiles[currentCoordinates.x][currentCoordinates.y] == BoardTile.FRUIT) {
                targetCoordinates = currentCoordinates;
                break;
            } else if (boardTiles[currentCoordinates.x][currentCoordinates.y] == BoardTile.FROG) {
                targetCoordinates = currentCoordinates;
                break;
            }
            for (Coordinates neighbor : getNeighboringCoordinates(currentCoordinates)) {
                if (!visitedCoordinates[neighbor.x][neighbor.y]) {
                    visitedCoordinates[neighbor.x][neighbor.y] = true;
                    boardCoordinatesQueue.add(neighbor);
                    previousCoordinates[neighbor.x][neighbor.y] = currentCoordinates;
                }
            }
        }
        return recreatePath(previousCoordinates, this.board.getEnemySnake().getSnakeHead(), targetCoordinates);
    }

    int[][] getDistancesMatrix(ArrayList<Coordinates> startingPoints) {
        boolean[][] visitedCoordinates = new boolean[this.board.getWidth()][this.board.getHeight()];
        Queue<Coordinates> boardCoordinatesQueue = new LinkedList<>();
        int[][] distancesMatrix = new int[this.board.getWidth()][this.board.getHeight()];

        for (Coordinates startingPoint : startingPoints) {
            boardCoordinatesQueue.add(startingPoint);
            visitedCoordinates[startingPoint.x][startingPoint.y] = true;
        }
        while (!boardCoordinatesQueue.isEmpty()) {
            Coordinates currentCoordinates = boardCoordinatesQueue.poll();
            for (Coordinates neighbor : getNeighboringCoordinates(currentCoordinates)) {
                if (!visitedCoordinates[neighbor.x][neighbor.y]) {
                    visitedCoordinates[neighbor.x][neighbor.y] = true;
                    distancesMatrix[neighbor.x][neighbor.y] = distancesMatrix[currentCoordinates.x][currentCoordinates.y] + 1;
                    boardCoordinatesQueue.add(neighbor);
                }
            }
        }
//        for (int i=0; i < this.board.getWidth(); i++) {
//            for (int j=0; j < this.board.getHeight(); j++) {
//                if (visitedCoordinates[j][i]) {
//                    System.out.printf("%3d", distancesMatrix[j][i]);
//                } else {
//                    System.out.print("  x");
//                }
//            }
//            System.out.println();
//        }
//        System.out.println();
        return distancesMatrix;
    }

    public ArrayList<Coordinates> getLongestFrogPath(int[][] frogMatrix, Frog frog) {
        Coordinates targetCoordinates = null;
        boolean[][] visitedCoordinates = new boolean[board.getWidth()][board.getHeight()];
        Coordinates[][] previousCoordinates = new Coordinates[board.getWidth()][board.getHeight()];
        Queue<Coordinates> boardCoordinatesQueue = new LinkedList<>();
        Coordinates startingPoint = frog.getCoordinates();
        boardCoordinatesQueue.add(startingPoint);
        visitedCoordinates[startingPoint.x][startingPoint.y] = true;
        previousCoordinates[startingPoint.x][startingPoint.y] = startingPoint;

        while (!boardCoordinatesQueue.isEmpty()) {
            Coordinates currentCoordinates = boardCoordinatesQueue.poll();
            for (Coordinates neighbor : getNeighboringCoordinates(currentCoordinates)) {
                if (!visitedCoordinates[neighbor.x][neighbor.y] && frogMatrix[neighbor.x][neighbor.y] > 0) {
                    visitedCoordinates[neighbor.x][neighbor.y] = true;
                    boardCoordinatesQueue.add(neighbor);
                    previousCoordinates[neighbor.x][neighbor.y] = currentCoordinates;
                }
            }
            targetCoordinates = currentCoordinates;
        }
        return recreatePath(previousCoordinates, startingPoint, targetCoordinates);
    }

    private ArrayList<Coordinates> getNeighboringCoordinates(Coordinates coordinates) {
        ArrayList<Coordinates> potentialNeighbors = new ArrayList<>();
        ArrayList<Coordinates> neighbors = new ArrayList<>();
        potentialNeighbors.add(new Coordinates(coordinates.x , coordinates.y - 1));
        potentialNeighbors.add(new Coordinates(coordinates.x , coordinates.y + 1));
        potentialNeighbors.add(new Coordinates(coordinates.x - 1, coordinates.y));
        potentialNeighbors.add(new Coordinates(coordinates.x + 1, coordinates.y));

        for (Coordinates potentialNeighbor : potentialNeighbors) {
            if (areCoordinatesUnoccupiedAndInBounds(potentialNeighbor)) {
                neighbors.add(potentialNeighbor);
            }
        }
        return neighbors;
    }

    private boolean areCoordinatesUnoccupiedAndInBounds(Coordinates coordinates) {
        ArrayList<BoardTile> obstacles = new ArrayList<>(List.of(
                BoardTile.SNAKE, BoardTile.SNAKE_HEAD,
                BoardTile.ENEMY_SNAKE, BoardTile.ENEMY_SNAKE_HEAD, BoardTile.OBSTACLE));
        return coordinates.x >= 0 && coordinates.y >= 0 &&
                coordinates.x < this.board.getWidth() && coordinates.y < this.board.getHeight() &&
                !obstacles.contains(this.boardTiles[coordinates.x][coordinates.y]);
    }

    private ArrayList<Coordinates> recreatePath(Coordinates[][] previous, Coordinates startingCoordinates,
                                                Coordinates targetCoordinates) {
        ArrayList<Coordinates> path = new ArrayList<>();
        Coordinates currentCoordinates = targetCoordinates;
        path.add(currentCoordinates);
        while (currentCoordinates != startingCoordinates) {
            currentCoordinates = previous[currentCoordinates.x][currentCoordinates.y];
//            currentCoordinates.print();
            path.add(currentCoordinates);
        }
        Collections.reverse(path);
        return path;
    }

    public int[][] createFrogMatrix(Frog frog) {
        ArrayList<Coordinates> snakeHeads = new ArrayList<>();
        try {
            snakeHeads.add(this.board.getEnemySnake().getSnakeHead());
            snakeHeads.add(this.board.getPlayerSnake().getSnakeHead());
        } catch (Exception e) {
            snakeHeads.add(this.board.getPlayerSnake().getSnakeHead());
        }
        int[][] snakeDistances = getDistancesMatrix(snakeHeads);

        ArrayList<Coordinates> frogList = new ArrayList<>();
        frogList.add(frog.getCoordinates());
        int[][] frogDistances = getDistancesMatrix(frogList);
        int[][] outputMatrix = new int[this.board.getWidth()][this.board.getHeight()];

        for (int i=0; i < this.board.getWidth(); i++) {
            for (int j=0; j < this.board.getHeight(); j++) {
                outputMatrix[j][i] = snakeDistances[j][i] - frogDistances[j][i];
                if (this.boardTiles[j][i] == BoardTile.FROG || this.boardTiles[j][i] == BoardTile.FRUIT) {
                    outputMatrix[j][i] = 0;
                }
//                System.out.printf("%4d", outputMatrix[j][i]);
            }
//            System.out.println();
        }
//        System.out.println();
        return outputMatrix;
    }
}
