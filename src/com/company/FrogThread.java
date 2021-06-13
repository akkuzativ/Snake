package com.company;

import java.util.ArrayList;

public class FrogThread extends Thread implements GameObjectThread {
    private Board board;
    private FrogAI frogAI;
    private boolean canCalculateNextAction = false;
    private Direction nextAction;
    private Frog frog;
    private ArrayList<Collidable> gameObjectsToRemove = new ArrayList<>();
    private boolean killed = false;

    FrogThread(Board board, Frog frog) {
        this.board = board;
        this.frogAI = new FrogAI(board, frog);
        this.frog = frog;
    }

    /***
     * Calculates next frog action using BFS algorithm when the flag is set
     */
    @Override
    public void run() {
        while (true) {
            if (!board.getFrogs().contains(frog) || killed) {
                frog = null;
                break;
            }
            if (this.canCalculateNextAction) {
                this.nextAction = this.frogAI.getNextMoveDirection();
                this.canCalculateNextAction = false;
            }
            try {
                Thread.sleep(1);
            } catch (Exception e) {
            }
        }
    }

    /***
     * Sets the flag that allows the thread to calculate its next action
     */
    @Override
    public void startCalculatingNextAction() {
        this.canCalculateNextAction = true;
    }

    /***
     * Sets next move direction for the frog and handles possible collisions
     */
    @Override
    public void performNextAction() {
        this.frog.setMoveDirection(this.nextAction);
        FrogController.move(this.frog);
        gameObjectsToRemove = FrogController.handleCollisions(frog, board);
    }

    /***
     * Returns game object threads to be removed
     * @return game object threads to be removed
     */
    @Override
    public ArrayList<Collidable> getGameObjectsToRemove() {
        return gameObjectsToRemove;
    }

    /***
     * Returns current frog
     * @return current frog
     */
    @Override
    public Collidable getRelatedGameObject() {
        return frog;
    }

    /***
     * Sets thread kill flag to true
     */
    @Override
    public void forceKill() {
        killed = true;
    }
}
