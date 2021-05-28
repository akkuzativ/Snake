package com.company;

public class Settings {
    private final int boardWidth;
    private final int boardHeight;
    private final int fruitCount;
    private final int obstaclesCount;
    private final int snakeLength;
    private final int minObstacleLength;
    private final int maxObstacleLength;

    private final int gameSpeed;

    Settings(){
        this.boardWidth = 0;
        this.boardHeight = 0;
        this.fruitCount = 0;
        this.obstaclesCount = 0;
        this.snakeLength = 0;
        this.minObstacleLength = 0;
        this.maxObstacleLength = 0;

        this.gameSpeed = 0;
    }
}
