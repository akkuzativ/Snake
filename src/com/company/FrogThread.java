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

    @Override
    public void run() {
        while (true) {
            if (!board.getFrogs().contains(frog) || killed) {
                frog = null;
                break;
            }
            gameObjectsToRemove = FrogController.handleCollisions(frog, board);
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

    @Override
    public void startCalculatingNextAction() {
        this.canCalculateNextAction = true;
    }

    @Override
    public void performNextAction() {
        this.frog.setMoveDirection(this.nextAction);
        //FrogController.move(this.frog);

    }

    @Override
    public ArrayList<Collidable> getGameObjectsToRemove() {
        return gameObjectsToRemove;
    }

    @Override
    public Collidable getRelatedGameObject() {
        return frog;
    }

    @Override
    public void forceKill() {
        killed = true;
    }
}
