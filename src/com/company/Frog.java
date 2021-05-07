package com.company;

public class Frog {
    private Coordinates coordinates;

    Frog(Coordinates coordinates) {
        this.coordinates = new Coordinates(coordinates.x, coordinates.y);
    }

    public Coordinates getCoordinates() {
        return this.coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates.x = coordinates.x;
        this.coordinates.y = coordinates.y;
    }
}
