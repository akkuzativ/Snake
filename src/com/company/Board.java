package com.company;

import java.util.ArrayList;
import java.util.Arrays;

enum BoardTile {
    EMPTY,
    SNAKE,
    SNAKE_HEAD,
    ENEMY_SNAKE,
    ENEMY_SNAKE_HEAD,
    FROG,
    FRUIT,
    OBSTACLE
}

class TileOutOfBoundsException extends Exception {}

public class Board {
    public class GameObjectArrayList {
        public ArrayList<Collidable> gameObjects = new ArrayList<>();
    }
    private final int width;
    private final int height;
    private Snake playerSnake;
    private Snake enemySnake;
    private ArrayList<Fruit> fruits = new ArrayList<>();
    private final ArrayList<Coordinates> obstacles = new ArrayList<>();
    private ArrayList<Frog> frogs = new ArrayList<>();

    public Board(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    private boolean IsOutOfBounds(Coordinates coordinates) {
        return coordinates.x > this.width || coordinates.x < 0 || coordinates.y > this.height || coordinates.y < 0;
    }

    public Snake getPlayerSnake() {
        return playerSnake;
    }

    public void setPlayerSnake(Snake playerSnake) throws TileOutOfBoundsException {
        if (IsOutOfBounds(playerSnake.getSnakeHead())) {
            throw new TileOutOfBoundsException();
        }
        for (Coordinates snakeTile: playerSnake.getSnakeBody()) {
            if (IsOutOfBounds(snakeTile)) {
                throw new TileOutOfBoundsException();
            }
        }

        this.playerSnake = playerSnake;
    }

    public Snake getEnemySnake() {
        return enemySnake;
    }

    public void setEnemySnake(Snake enemySnake) throws TileOutOfBoundsException {
        if (IsOutOfBounds(enemySnake.getSnakeHead())) {
            throw new TileOutOfBoundsException();
        }
        for (Coordinates enemySnakeTile: enemySnake.getSnakeBody()) {
            if (IsOutOfBounds(enemySnakeTile)) {
                throw new TileOutOfBoundsException();
            }
        }

        this.enemySnake = enemySnake;
    }

    public ArrayList<Frog> getFrogs() {
        return this.frogs;
    }

    public void setFrogs(ArrayList<Frog> frogs) throws TileOutOfBoundsException{
        for (Frog frog: frogs) {
            if (IsOutOfBounds(frog.getCoordinates())) {
                throw new TileOutOfBoundsException();
            }
        }
        this.frogs = frogs;
    }

    public void addFrog(Frog frog) throws TileOutOfBoundsException{
        if (IsOutOfBounds(frog.getCoordinates())) {
            throw new TileOutOfBoundsException();
        }
        this.frogs.add(frog);
    }

    public void addFruit(Fruit fruit) throws TileOutOfBoundsException{
        if (IsOutOfBounds(fruit.getCoordinates())) {
            throw new TileOutOfBoundsException();
        }
        this.fruits.add(fruit);
    }

    public void removeFruit(Fruit fruit) {
        this.fruits.remove(fruit);
    }

    public ArrayList<Fruit> getFruits() {
        return this.fruits;
    }

    public void setFruits(ArrayList<Fruit> fruits) throws TileOutOfBoundsException{
        for (Fruit fruit: fruits) {
            if (IsOutOfBounds(fruit.getCoordinates())) {
                throw new TileOutOfBoundsException();
            }
        }
        this.fruits = fruits;
    }

    public ArrayList<Coordinates> getObstacles() {
        return this.obstacles;
    }

    public void addObstacle(Coordinates obstacle) throws TileOutOfBoundsException{
        this.obstacles.add(obstacle);
    }

    public BoardTile[][] toTiles() {
        BoardTile[][] tiles = new BoardTile[this.width][this.height];

        for (BoardTile[] row: tiles) {
            Arrays.fill(row, BoardTile.EMPTY);
        }

        if (this.playerSnake != null) {
            tiles[playerSnake.getSnakeHead().x][playerSnake.getSnakeHead().y] = BoardTile.SNAKE_HEAD;
            ArrayList<Coordinates> snakeBody = playerSnake.getSnakeBody();
            for (Coordinates snakeTile: snakeBody) {
                tiles[snakeTile.x][snakeTile.y] = BoardTile.SNAKE;
            }
        }

        if (this.enemySnake != null) {
            tiles[enemySnake.getSnakeHead().x][enemySnake.getSnakeHead().y] = BoardTile.ENEMY_SNAKE_HEAD;
            ArrayList<Coordinates> enemySnakeBody = enemySnake.getSnakeBody();
            for (Coordinates enemySnakeTile: enemySnakeBody) {
                tiles[enemySnakeTile.x][enemySnakeTile.y] = BoardTile.ENEMY_SNAKE;
            }
        }

        if (!this.frogs.isEmpty()){
            for (Frog frog: this.frogs) {
                tiles[frog.getCoordinates().x][frog.getCoordinates().y] = BoardTile.FROG;
            }
        }

        if (!this.fruits.isEmpty()){
            for (Fruit fruit: fruits) {
                tiles[fruit.getCoordinates().x][fruit.getCoordinates().y] = BoardTile.FRUIT;
            }
        }

        if (!this.obstacles.isEmpty()){
            for (Coordinates obstacle : obstacles) {
                tiles[obstacle.x][obstacle.y] = BoardTile.OBSTACLE;
            }
        }

        return tiles;
    }

    public GameObjectArrayList[][] getReferenceMatrix(Collidable currentGameObject) {
        GameObjectArrayList[][] matrix = new GameObjectArrayList[this.width][this.height];


        for (int i = 0; i < this.width; i++) {
            for (int j = 0; j < this.height; j++) {
                matrix[i][j] = new GameObjectArrayList();
            }
        }

        if (this.playerSnake != null) {
            if (currentGameObject != playerSnake) {
                matrix[playerSnake.getSnakeHead().x][playerSnake.getSnakeHead().y].gameObjects.add(playerSnake);
            }
            ArrayList<Coordinates> snakeBody = playerSnake.getSnakeBody();
            for (Coordinates snakeTile: snakeBody) {
                matrix[snakeTile.x][snakeTile.y].gameObjects.add(playerSnake);
            }
        }

        if (this.enemySnake != null) {
            if (currentGameObject != enemySnake) {
                matrix[enemySnake.getSnakeHead().x][enemySnake.getSnakeHead().y].gameObjects.add(enemySnake);
            }
            ArrayList<Coordinates> enemySnakeBody = enemySnake.getSnakeBody();
            for (Coordinates enemySnakeTile: enemySnakeBody) {
                matrix[enemySnakeTile.x][enemySnakeTile.y].gameObjects.add(enemySnake);
            }
        }

        if (!this.frogs.isEmpty()){
            for (Frog frog: this.frogs) {
                if (currentGameObject != frog) {
                    matrix[frog.getCoordinates().x][frog.getCoordinates().y].gameObjects.add(frog);
                }
            }
        }

        if (!this.fruits.isEmpty()){
            for (Fruit fruit: fruits) {
                matrix[fruit.getCoordinates().x][fruit.getCoordinates().y].gameObjects.add(fruit);
            }
        }

        if (!this.obstacles.isEmpty()){
            for (Coordinates obstacle : obstacles) {
                matrix[obstacle.x][obstacle.y].gameObjects.add(obstacle);
            }
        }

        return matrix;
    }
}
