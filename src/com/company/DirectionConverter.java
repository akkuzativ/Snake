package com.company;

enum Direction {
    UP,
    DOWN,
    LEFT,
    RIGHT
}

 public class DirectionConverter {
    public static int[] getDeltas(Direction direction) {
        int[] deltas = new int[2];
        switch (direction) {
            case UP -> deltas[1] = -1;
            case DOWN -> deltas[1] = 1;
            case LEFT -> deltas[0] = -1;
            case RIGHT -> deltas[0] = 1;
        }
        return deltas;
    }

    public static Direction getDirection(int[] deltas) {
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
