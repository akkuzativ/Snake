package com.company;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class SnakeAI {
    private Board board;
    private BoardTile[][] boardTiles;

    SnakeAI(Board board) {
        this.board = board;
        this.boardTiles = this.board.toTiles();
    }

    private ArrayList<Coordinates> BFS() {
        Coordinates targetCoordinates = new Coordinates(-1,-1);
        boolean[][] visitedTiles = new boolean[board.getWidth()][board.getHeight()];
        Coordinates[][] previousCoordinates = new Coordinates[board.getWidth()][board.getHeight()];
        Queue<Coordinates> boardTileQueue = new LinkedList<>();
        Coordinates snakeHead = board.getEnemySnake().getSnakeHead();
        boardTileQueue.add(snakeHead);
        visitedTiles[snakeHead.x][snakeHead.y] = true;
        previousCoordinates[snakeHead.x][snakeHead.y] = snakeHead;

        while (!boardTileQueue.isEmpty()) {
            Coordinates currentTile = boardTileQueue.poll();
            if (boardTiles[currentTile.x][currentTile.y] == BoardTile.FROG ||
                boardTiles[currentTile.x][currentTile.y] == BoardTile.FRUIT) {
                targetCoordinates = currentTile;
                break;
            }
            for (Coordinates coordinates : getNeighboringCoordinates(currentTile)) {
                if (!visitedTiles[coordinates.x][coordinates.y]) {
                    visitedTiles[coordinates.x][coordinates.y] = true;
                    boardTileQueue.add(coordinates);
                    previousCoordinates[coordinates.x][coordinates.y] = currentTile;
                }
            }
        }
        return recreatePath(previousCoordinates, board.getEnemySnake().getSnakeHead(), targetCoordinates);
    }

    private ArrayList<Coordinates> getNeighboringCoordinates(Coordinates coordinates) {
        ArrayList<Coordinates> potentialNeighbors = new ArrayList<>();
        ArrayList<Coordinates> neighbors = new ArrayList<>();
        potentialNeighbors.add(new Coordinates(coordinates.x , coordinates.y - 1));
        potentialNeighbors.add(new Coordinates(coordinates.x , coordinates.y + 1));
        potentialNeighbors.add(new Coordinates(coordinates.x - 1, coordinates.y));
        potentialNeighbors.add(new Coordinates(coordinates.x + 1, coordinates.y));

        for (Coordinates potentialNeighbor : potentialNeighbors) {
            if (areCoordinatesInBounds(potentialNeighbor)) {
                neighbors.add(potentialNeighbor);
            }
        }
        return neighbors;
    }

    private boolean areCoordinatesInBounds(Coordinates coordinates) {
        return coordinates.x >= 0 && coordinates.y >= 0 &&
                coordinates.x < this.board.getWidth() && coordinates.y < this.board.getHeight() &&
                this.boardTiles[coordinates.x][coordinates.y] != BoardTile.SNAKE &&
                this.boardTiles[coordinates.x][coordinates.y] != BoardTile.SNAKE_HEAD &&
                this.boardTiles[coordinates.x][coordinates.y] != BoardTile.ENEMY_SNAKE &&
                this.boardTiles[coordinates.x][coordinates.y] != BoardTile.ENEMY_SNAKE_HEAD &&
                this.boardTiles[coordinates.x][coordinates.y] != BoardTile.OBSTACLE;
    }

    private ArrayList<Coordinates> recreatePath(Coordinates[][] previous, Coordinates startingCoordinates, Coordinates targetCoordinates) {
        ArrayList<Coordinates> path = new ArrayList<>();
        Coordinates currentCoordinates = targetCoordinates;
        while (currentCoordinates != startingCoordinates) {
            currentCoordinates = previous[currentCoordinates.x][currentCoordinates.y];
            currentCoordinates.print();
            path.add(currentCoordinates);
        }
        return path;
    }

    public Direction getNextMoveDirection() {
        ArrayList<Coordinates> path = BFS();
        Coordinates nextMove = path.get(path.size() - 2);
        Coordinates currentPosition = board.getEnemySnake().getSnakeHead();
        int[] deltas = {nextMove.x - currentPosition.x, nextMove.y - currentPosition.y};

        if (deltas[0] == 0 && deltas[1] == -1) {
            return Direction.UP;
        } else if (deltas[0] == 0 && deltas[1] == 1) {
            return Direction.DOWN;
        } else if (deltas[0] == -1 && deltas[1] == 0) {
            return Direction.LEFT;
        } else {
            return Direction.RIGHT;
        }
    }
}
