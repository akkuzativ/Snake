package com.company;

import java.util.ArrayList;
import java.util.Arrays;

/***
 * Possible types of tiles on board
 */
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

/***
 * Exception thrown when a tiles gets outside of board's bounds
 */
class TileOutOfBoundsException extends Exception {}

/***
 * Class representing a board
 */
public class Board {
    /***
     * Wrapper for a list of game objects
     */
    public static class GameObjectArrayList {
        public ArrayList<Collidable> gameObjects = new ArrayList<>();
    }
    private final int width;
    private final int height;
    private Snake playerSnake;
    private Snake enemySnake;
    private final ArrayList<Coordinates> obstacles = new ArrayList<>();
    private ArrayList<Fruit> fruits = new ArrayList<>();
    private ArrayList<Frog> frogs = new ArrayList<>();
    private ArrayList<Frog> missingFrogs = new ArrayList<>();

    /***
     * Board constructor
     * @param width board's width (in tiles)
     * @param height board's height (in tiles)
     */
    public Board(int width, int height) {
        this.width = width;
        this.height = height;
    }

    /***
     * Returns the width of the board
     * @return width of the board
     */
    public int getWidth() {
        return width;
    }

    /***
     * Returns the height of the board
     * @return height of the board
     */
    public int getHeight() {
        return height;
    }

    /***
     * Checks if coordinates are in bounds
     * @param coordinates coordinates to check
     * @return true if out of bounds
     */
    private boolean IsOutOfBounds(Coordinates coordinates) {
        return coordinates.x > this.width || coordinates.x < 0 || coordinates.y > this.height || coordinates.y < 0;
    }

    /***
     * Returns the snake controlled by the player
     * @return player controlled snake
     */
    public Snake getPlayerSnake() {
        return playerSnake;
    }

    /***
     * Sets the snake controlled by the player
     * @param playerSnake snake to be set
     * @throws TileOutOfBoundsException if the snake is out of bounds
     */
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

    /***
     * Sets the snake controlled by AI
     * @param enemySnake snake to be set
     * @throws TileOutOfBoundsException if the snake is out of bounds
     */
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

    /***
     * Adds a frog to the list of frogs
     * @param frog frog to add
     * @throws TileOutOfBoundsException if the frog is out of bounds
     */
    public void addFrog(Frog frog) throws TileOutOfBoundsException{
        if (IsOutOfBounds(frog.getCoordinates())) {
            throw new TileOutOfBoundsException();
        }
        this.frogs.add(frog);
    }

    /***
     * Adds a fruit to the list of fruits
     * @param fruit fruit to add
     * @throws TileOutOfBoundsException if the fruit is out of bounds
     */
    public void addFruit(Fruit fruit) throws TileOutOfBoundsException{
        if (IsOutOfBounds(fruit.getCoordinates())) {
            throw new TileOutOfBoundsException();
        }
        this.fruits.add(fruit);
    }

    /***
     * Returns the list of missing frogs
     * @return missing frogs
     */
    public ArrayList<Frog> getMissingFrogs() {
        return this.missingFrogs;
    }

    /***
     * Sets the list of missing frogs
     * @param frogs missing frogs to set
     */
    public void setMissingFrogs(ArrayList<Frog> frogs) {
        this.missingFrogs = frogs;
        this.frogs.addAll(this.missingFrogs);
    }

    /***
     * Clears the list of missing frogs
     */
    public void clearMissingFrogs() {
        this.missingFrogs.clear();
    }

    /***
     * Removes a game object from board
     * @param gameObject game object to remove
     */
    public void removeGameObject(Collidable gameObject) {
        switch (gameObject.getName()) {
            case "Fruit":
                fruits.remove(((Fruit) gameObject));
                break;
            case "Frog":
                frogs.remove(((Frog) gameObject));
                break;
            case "Snake":
                if (gameObject == playerSnake) {
                    playerSnake = null;
                }
                else {
                    enemySnake = null;
                }
                break;
            default:
                break;
        }
    }

    /***
     * Returns the list of fruits on board
     * @return list of fruits on board
     */
    public ArrayList<Fruit> getFruits() {
        return this.fruits;
    }

    /***
     * Adds an obstacle to board
     * @param obstacle obstalce to add
     * @throws TileOutOfBoundsException if the obstacle is out of bounds
     */
    public void addObstacle(Coordinates obstacle) throws TileOutOfBoundsException{
        this.obstacles.add(obstacle);
    }

    /***
     * Returns the board as a 2D array of board tiles
     * @return 2D array of board tiles
     */
    public BoardTile[][] toTiles() {
        BoardTile[][] tiles = new BoardTile[this.width][this.height];

        for (BoardTile[] row: tiles) {
            Arrays.fill(row, BoardTile.EMPTY);
        }

        for (Frog frog: this.frogs) {
            tiles[frog.getCoordinates().x][frog.getCoordinates().y] = BoardTile.FROG;
        }

        for (Fruit fruit: fruits) {
            tiles[fruit.getCoordinates().x][fruit.getCoordinates().y] = BoardTile.FRUIT;
        }

        if (this.playerSnake != null) {
            ArrayList<Coordinates> snakeBody = playerSnake.getSnakeBody();
            for (Coordinates snakeTile: snakeBody) {
                tiles[snakeTile.x][snakeTile.y] = BoardTile.SNAKE;
            }
            tiles[playerSnake.getSnakeHead().x][playerSnake.getSnakeHead().y] = BoardTile.SNAKE_HEAD;
        }

        if (this.enemySnake != null) {
            ArrayList<Coordinates> enemySnakeBody = enemySnake.getSnakeBody();
            for (Coordinates enemySnakeTile: enemySnakeBody) {
                tiles[enemySnakeTile.x][enemySnakeTile.y] = BoardTile.ENEMY_SNAKE;
            }
            tiles[enemySnake.getSnakeHead().x][enemySnake.getSnakeHead().y] = BoardTile.ENEMY_SNAKE_HEAD;
        }

        if (!this.obstacles.isEmpty()){
            for (Coordinates obstacle : obstacles) {
                tiles[obstacle.x][obstacle.y] = BoardTile.OBSTACLE;
            }
        }

        return tiles;
    }

    /***
     * Returns a list of references to occupying game objects for every tile of a board
     * @param currentGameObject game object to ignore
     * @return 2D matrix of lists of game objects
     */
    public GameObjectArrayList[][] getReferenceMatrix(Collidable currentGameObject) {
        GameObjectArrayList[][] matrix = new GameObjectArrayList[this.width][this.height];


        for (int i = 0; i < this.width; i++) {
            for (int j = 0; j < this.height; j++) {
                matrix[i][j] = new GameObjectArrayList();
            }
        }

        if (this.playerSnake != null) {
            try {
                if (currentGameObject != playerSnake) {
                    matrix[playerSnake.getSnakeHead().x][playerSnake.getSnakeHead().y].gameObjects.add(playerSnake);
                }
                ArrayList<Coordinates> snakeBody = playerSnake.getSnakeBody();
                for (Coordinates snakeTile : snakeBody) {
                    matrix[snakeTile.x][snakeTile.y].gameObjects.add(playerSnake);
                }
            }
            catch (Exception e) { }
        }

        if (this.enemySnake != null) {
            try {
                if (currentGameObject != enemySnake) {
                    matrix[enemySnake.getSnakeHead().x][enemySnake.getSnakeHead().y].gameObjects.add(enemySnake);
                }
                ArrayList<Coordinates> enemySnakeBody = enemySnake.getSnakeBody();
                for (Coordinates enemySnakeTile : enemySnakeBody) {
                    matrix[enemySnakeTile.x][enemySnakeTile.y].gameObjects.add(enemySnake);
                }
            }
            catch (Exception e) { }
        }

        if (!this.frogs.isEmpty()){
            for (Frog frog: this.frogs) {
                try {
                    if (currentGameObject != frog) {
                        matrix[frog.getCoordinates().x][frog.getCoordinates().y].gameObjects.add(frog);
                    }
                }
                catch (Exception e) { }
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
