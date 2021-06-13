package com.company;

import java.util.ArrayList;

/***
 * Class representing a snake game object, consisting of a head, a list of segments (body) and a current move direction
 */
public class Snake implements Collidable{
    private ArrayList<Coordinates> snakeBody;
    private Coordinates snakeHead;
    private Direction moveDirection;

    /***
     * Snake constructor
     * @param snakeBody a list of coordinates (body segments)
     * @param snakeHead single coordinates representing snake's head
     * @param direction current move direction
     */
    Snake(ArrayList<Coordinates> snakeBody, Coordinates snakeHead, Direction direction) {
        this.snakeBody = new ArrayList<>();
        for (Coordinates coordinates : snakeBody) {
            this.snakeBody.add(new Coordinates(coordinates.x, coordinates.y));
        }
        this.snakeHead = new Coordinates(snakeHead.x, snakeHead.y);
        this.moveDirection = direction;
    }

    /***
     * Returns the snake's body
     * @return list of body segments
     */
    public ArrayList<Coordinates> getSnakeBody() {
        return this.snakeBody;
    }

    /***
     * Returns snake's head
     * @return snake's head
     */
    public Coordinates getSnakeHead() {
        return this.snakeHead;
    }

    /***
     * Tries to set current move direction
     * @param moveDirection direction to set
     * @throws IncorrectDirectionException if a move caused by the direction would not be legal
     */
    public void setMoveDirection(Direction moveDirection) throws IncorrectDirectionException {
        if (this.moveDirection == DirectionUtilities.getOppositeDirection(moveDirection)) {
            throw new IncorrectDirectionException();
        }
        this.moveDirection = moveDirection;
    }

    /***
     * Returns current move direction
     * @return current move direction
     */
    public Direction getMoveDirection() {
        return moveDirection;
    }

    /***
     * Gets string representation of snake type game object
     * @return string object of value "Snake"
     */
    public String getName() {
        return "Snake";
    }
}
