package com.company;

public class Coordinates implements Collidable{
    public int x;
    public int y;

    Coordinates(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void print() {
        System.out.println("(" + this.x + ", " + this.y + ")");
    }

    public String getName() {
        return "Coordinates";
    }
}
