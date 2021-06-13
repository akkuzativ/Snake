package com.company;

import java.util.*;

public class BFSalgorithm {
    private final Board board;
    private final BoardTile[][] boardTiles;

    BFSalgorithm(Board board) {
        this.board = board;
        this.boardTiles = this.board.toTiles();
    }

    /***
     * Creates shortest snake path to nearest frog or fruit using BFS algorithm
     * @param startingPoint AI snake head coordinates
     * @return full path from snake head to nearest frog or fruit
     */
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
            for (Coordinates neighbor : getNeighboringCoordinates(currentCoordinates,BoardTile.ENEMY_SNAKE_HEAD)) {
                if (!visitedCoordinates[neighbor.x][neighbor.y]) {
                    visitedCoordinates[neighbor.x][neighbor.y] = true;
                    boardCoordinatesQueue.add(neighbor);
                    previousCoordinates[neighbor.x][neighbor.y] = currentCoordinates;
                }
            }
        }
        return recreatePath(previousCoordinates, this.board.getEnemySnake().getSnakeHead(), targetCoordinates);
    }

    /***
     * Creates a 2D matrix representing number of moves to reach every board tile by the snake or frog using BFS algorithm
     * @param startingPoints list of starting points to calculate the number of moves from
     * @param boardTile indicates whether the starting points are snakes or frogs
     * @return 2D distances matrix
     */
    int[][] getDistancesMatrix(ArrayList<Coordinates> startingPoints, BoardTile boardTile) {
        boolean[][] visitedCoordinates = new boolean[this.board.getWidth()][this.board.getHeight()];
        Queue<Coordinates> boardCoordinatesQueue = new LinkedList<>();
        int[][] distancesMatrix = new int[this.board.getWidth()][this.board.getHeight()];

        for (Coordinates startingPoint : startingPoints) {
            boardCoordinatesQueue.add(startingPoint);
            visitedCoordinates[startingPoint.x][startingPoint.y] = true;
        }
        while (!boardCoordinatesQueue.isEmpty()) {
            Coordinates currentCoordinates = boardCoordinatesQueue.poll();
            for (Coordinates neighbor : getNeighboringCoordinates(currentCoordinates, boardTile)) {
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

    /***
     * Creates longest possible frog escape path using BFS algorithm
     * @param frogMatrix 2D matrix of differences between snake and frog distances
     * @param frog the frog for which the matrix is calculated
     * @return longest possible frog escape path
     */
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
            for (Coordinates neighbor : getNeighboringCoordinates(currentCoordinates, BoardTile.FROG)) {
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

    /***
     * Creates list of possible neighbors of a game object
     * @param coordinates coordinates of the game object
     * @param boardTile indicates the type of game object
     * @return list of possible neighbors of a game object
     */
    private ArrayList<Coordinates> getNeighboringCoordinates(Coordinates coordinates, BoardTile boardTile) {
        ArrayList<Coordinates> potentialNeighbors = new ArrayList<>();
        ArrayList<Coordinates> neighbors = new ArrayList<>();
        potentialNeighbors.add(new Coordinates(coordinates.x , coordinates.y - 1));
        potentialNeighbors.add(new Coordinates(coordinates.x , coordinates.y + 1));
        potentialNeighbors.add(new Coordinates(coordinates.x - 1, coordinates.y));
        potentialNeighbors.add(new Coordinates(coordinates.x + 1, coordinates.y));

        for (Coordinates potentialNeighbor : potentialNeighbors) {
            if (areCoordinatesUnoccupiedAndInBounds(potentialNeighbor, boardTile)) {
                neighbors.add(potentialNeighbor);
            }
        }
        return neighbors;
    }

    /***
     * Checks if given coordinates meet the requirements to become snake/frog neighbors
     * @param coordinates the coordinates being checked
     * @param boardTile indicates whether we are looking for snake or frog neighbors
     * @return true if the coordinates meet the requirements to become snake/frog neighbors and false if they don't
     */
    public boolean areCoordinatesUnoccupiedAndInBounds(Coordinates coordinates, BoardTile boardTile) {
        boolean output = false;
        if (boardTile == BoardTile.ENEMY_SNAKE_HEAD) {
            output = areCoordinatesUnoccupiedAndInBoundsForSnake(coordinates);
        } else if (boardTile == BoardTile.FROG) {
            output = areCoordinatesUnoccupiedAndInBoundsForFrog(coordinates);
        }
        return output;
    }

    /***
     * Checks if given coordinates meet the requirements to become snake neighbors
     * @param coordinates the coordinates being checked
     * @return true if the coordinates meet the requirements to become snake neighbors and false if they don't
     */
    public boolean areCoordinatesUnoccupiedAndInBoundsForSnake(Coordinates coordinates) {
        ArrayList<BoardTile> obstacles = new ArrayList<>(List.of(
                BoardTile.SNAKE, BoardTile.SNAKE_HEAD,
                BoardTile.ENEMY_SNAKE, BoardTile.ENEMY_SNAKE_HEAD, BoardTile.OBSTACLE));
        return coordinates.x >= 0 && coordinates.y >= 0 &&
                coordinates.x < this.board.getWidth() && coordinates.y < this.board.getHeight() &&
                !obstacles.contains(this.boardTiles[coordinates.x][coordinates.y]);
    }

    /***
     * Checks if given coordinates meet the requirements to become frog neighbors
     * @param coordinates the coordinates being checked
     * @return true if the coordinates meet the requirements to become frog neighbors and false if they don't
     */
    public boolean areCoordinatesUnoccupiedAndInBoundsForFrog(Coordinates coordinates) {
        ArrayList<BoardTile> obstacles = new ArrayList<>(List.of(
                BoardTile.SNAKE, BoardTile.SNAKE_HEAD,
                BoardTile.ENEMY_SNAKE, BoardTile.ENEMY_SNAKE_HEAD, BoardTile.OBSTACLE, BoardTile.FRUIT, BoardTile.FROG));
        return coordinates.x >= 0 && coordinates.y >= 0 &&
                coordinates.x < this.board.getWidth() && coordinates.y < this.board.getHeight() &&
                !obstacles.contains(this.boardTiles[coordinates.x][coordinates.y]);
    }

    /***
     * Recreates path from a list of previous coordinates
     * @param previous list of previous coordinates
     * @param startingCoordinates starting coordinates of the calculated path
     * @param targetCoordinates target coordinates of the calculated path
     * @return recreated path
     */
    private ArrayList<Coordinates> recreatePath(Coordinates[][] previous, Coordinates startingCoordinates,
                                                Coordinates targetCoordinates) {
        ArrayList<Coordinates> path = new ArrayList<>();
        Coordinates currentCoordinates = targetCoordinates;
        path.add(currentCoordinates);
        while (currentCoordinates != startingCoordinates) {
            currentCoordinates = previous[currentCoordinates.x][currentCoordinates.y];
            path.add(currentCoordinates);
        }
        Collections.reverse(path);
        return path;
    }

    /***
     * Creates a 2D matrix of differences between snake and frog distances
     * @param frog the frog for which the matrix is calculated
     * @return 2D matrix of differences between snake and frog distances
     */
    public int[][] createFrogMatrix(Frog frog) {
        ArrayList<Coordinates> snakeHeads = new ArrayList<>();
        try {
            snakeHeads.add(this.board.getEnemySnake().getSnakeHead());
            snakeHeads.add(this.board.getPlayerSnake().getSnakeHead());
        } catch (Exception e) {
            snakeHeads.add(this.board.getPlayerSnake().getSnakeHead());
        }
        int[][] snakeDistances = getDistancesMatrix(snakeHeads, BoardTile.ENEMY_SNAKE_HEAD);

        ArrayList<Coordinates> frogList = new ArrayList<>();
        frogList.add(frog.getCoordinates());
        int[][] frogDistances = getDistancesMatrix(frogList, BoardTile.FROG);
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
