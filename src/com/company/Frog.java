package com.company;

/***
 * Class representing a frog game object, consisting of a single coordinates
 */
public class Frog implements Collidable{
    private Coordinates coordinates;
    private Direction moveDirection;

    /***
     * Frog constructor
     * @param coordinates location of the frog
     */
    Frog(Coordinates coordinates) {
        this.coordinates = new Coordinates(coordinates.x, coordinates.y);
    }

    /***
     * Returns current location of the frog
     * @return current location as coordinates
     */
    public Coordinates getCoordinates() {
        return this.coordinates;
    }

    /***
     * Sets current location
     * @param coordinates location to set
     */
    public void setCoordinates(Coordinates coordinates) {
        this.coordinates.x = coordinates.x;
        this.coordinates.y = coordinates.y;
    }

    /***
     * Gets the move direction of the frog
     * @return current move direction
     */
    public Direction getMoveDirection() {
        return moveDirection;
    }

    /***
     * Sets current move direction
     * @param moveDirection direction to set
     */
    public void setMoveDirection(Direction moveDirection) {
        this.moveDirection = moveDirection;
    }

    /***
     * Gets string representation of snake type game object
     * @return String object "Frog"
     */
    public String getName() {
        return "Frog";
    }
}
