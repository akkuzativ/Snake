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
    private final int width;
    private final int height;
    private Snake snake;
    private Snake enemySnake;
    private ArrayList<Fruit> fruits = new ArrayList<>();
    private final ArrayList<Coordinates> obstacles = new ArrayList<>();
    private Frog frog;

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

    public Snake getSnake() {
        return snake;
    }

    public void setSnake(Snake snake) throws TileOutOfBoundsException {
        if (IsOutOfBounds(snake.getSnakeHead())) {
            throw new TileOutOfBoundsException();
        }
        for (Coordinates snakeTile: snake.getSnakeBody()) {
            if (IsOutOfBounds(snakeTile)) {
                throw new TileOutOfBoundsException();
            }
        }

        this.snake = snake;
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

    public Frog getFrog() {
        return frog;
    }

    public void setFrog(Frog frog) throws TileOutOfBoundsException{
        if (IsOutOfBounds(frog.getCoordinates())) {
            throw new TileOutOfBoundsException();
        }

        this.frog = frog;
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
        return fruits;
    }

    public void setFruits(ArrayList<Fruit> fruits) throws TileOutOfBoundsException{
        for (Fruit fruit: fruits) {
            if (IsOutOfBounds(fruit.getCoordinates())) {
                throw new TileOutOfBoundsException();
            }
        }
        this.fruits = fruits;
    }

    public void addObstacle(Coordinates obstacle) throws TileOutOfBoundsException{
        this.obstacles.add(obstacle);
    }

    public BoardTile[][] toTiles() {
        BoardTile[][] tiles = new BoardTile[this.width][this.height];

        for (BoardTile[] row: tiles) {
            Arrays.fill(row, BoardTile.EMPTY);
        }

        if (this.snake != null) {
            tiles[snake.getSnakeHead().x][snake.getSnakeHead().y] = BoardTile.SNAKE_HEAD;
            ArrayList<Coordinates> snakeBody = snake.getSnakeBody();
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

        if (this.frog != null){
            tiles[frog.getCoordinates().x][frog.getCoordinates().y] = BoardTile.FROG;
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
}
