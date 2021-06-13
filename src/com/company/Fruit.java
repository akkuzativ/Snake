package com.company;

/***
 * Class representing a fruit game object consisting of single coordinates
 */
public class Fruit implements Collidable{
    private Coordinates coordinates;

    /**
     * Fruit constructor
     * @param coordinates coordinates used as a location of the fruit
     */
    Fruit(Coordinates coordinates) {
        this.coordinates = new Coordinates(coordinates.x, coordinates.y);
    }

    /***
     * Returns coordinates representing the current location of the fruit
     * @return
     */
    public Coordinates getCoordinates() {
        return this.coordinates;
    }

    /***
     * Sets current location of the fuit
     * @param coordinates location to set
     */
    public void setCoordinates(Coordinates coordinates) {
        this.coordinates.x = coordinates.x;
        this.coordinates.y = coordinates.y;
    }

    /***
     * Gets string representation of snake type game object
     * @return String object "Fruit"
     */
    public String getName() {
        return "Fruit";
    }
}
