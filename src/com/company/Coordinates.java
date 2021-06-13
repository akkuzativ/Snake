package com.company;

/***
 * Class representing single location on board
 */
public class Coordinates implements Collidable{
    public int x;
    public int y;

    /***
     * Coordinates constructor
     * @param x Horizontal axis coordinate
     * @param y Vertical axis coordinate
     */
    Coordinates(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /***
     * Prints coordinates as formatted string
     */
    public void print() {
        System.out.println("(" + this.x + ", " + this.y + ")");
    }

    /***
     * Gets string representation of snake type game object
     * @return String object "Coordinates"
     */
    public String getName() {
        return "Coordinates";
    }
}
