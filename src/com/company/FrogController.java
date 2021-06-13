package com.company;

import java.util.ArrayList;

/***
 * Used to control movement of frogs by modifying their coordinates
 */
public class FrogController {
    /***
     * Static method moving a specified frog once
     * @param frog frog to move
     */
    public static void move(Frog frog) {
        Coordinates newCoordinates = new Coordinates(frog.getCoordinates().x, frog.getCoordinates().y);
        newCoordinates.x += DirectionUtilities.getDeltas(frog.getMoveDirection())[0];
        newCoordinates.y += DirectionUtilities.getDeltas(frog.getMoveDirection())[1];
        frog.setCoordinates(newCoordinates);
    }

    /***
     * Tries to find objects colliding with the specified frog and returns objects to be removed from the board based on the type of the collision
     * @param frog handled frog
     * @param board board housing the rest of game objects
     * @return objects to be removed as a consequence of the collision
     */
    public static ArrayList<Collidable> handleCollisions(Frog frog, Board board) {
        ArrayList<Collidable> gameObjectsToRemove = new ArrayList<>();
        Coordinates frogCoordinates = frog.getCoordinates();
        if (frogCoordinates.x < 0 || frogCoordinates.x >= board.getWidth() ||
                frogCoordinates.y < 0 || frogCoordinates.y >= board.getHeight()) {
            gameObjectsToRemove.add(frog);
        } else {
            Board.GameObjectArrayList[][] references = board.getReferenceMatrix(frog);
            ArrayList<Collidable> gameObjects =
                    references[frogCoordinates.x][frogCoordinates.y].gameObjects;
            if (!gameObjects.isEmpty()) {
                for (Collidable gameObject : gameObjects) {
                    switch (gameObject.getName()) {
                        case "Coordinates":
                        case "Snake":
                        case "Frog":
                            gameObjectsToRemove.add(frog);
                            break;
                        case "Fruit":
                            gameObjectsToRemove.add(gameObject);
                            break;
                        default:
                            break;
                    }
                }
            }
        }
        return gameObjectsToRemove;
    }

}
