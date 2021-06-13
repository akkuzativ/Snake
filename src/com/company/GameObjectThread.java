package com.company;

import java.util.ArrayList;

public interface GameObjectThread {
    /***
     * Starts to run the thread
     */
    void start();

    /***
     * Calculates next game object action
     */
    void startCalculatingNextAction();

    /***
     * Sets the flag that allows the thread to calculate its next action
     */
    void performNextAction();

    /***
     * Returns game object threads to be removed
     * @return game object threads to be removed
     */
    ArrayList<Collidable> getGameObjectsToRemove();

    /***
     * Returns current game object
     * @return current game object
     */
    Collidable getRelatedGameObject();

    /***
     * Sets thread kill flag to true
     */
    void forceKill();
}
