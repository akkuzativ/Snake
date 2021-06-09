package com.company;

import java.util.ArrayList;

public class FrogController {
    public static void move(Frog frog) {
        Coordinates newCoordinates = new Coordinates(frog.getCoordinates().x, frog.getCoordinates().y);
        newCoordinates.x += DirectionUtilities.getDeltas(frog.getMoveDirection())[0];
        newCoordinates.y += DirectionUtilities.getDeltas(frog.getMoveDirection())[1];
        frog.setCoordinates(newCoordinates);
    }
}
