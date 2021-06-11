package com.company;

public class FrogThread extends Thread implements GameObjectThread {
    private Board board;
    private FrogAI frogAI;
    private boolean canCalculateNextAction = false;
    private Direction nextAction;
    private Frog frog;

    FrogThread(Board board, Frog frog) {
        this.board = board;
        this.frogAI = new FrogAI(board, frog);
        this.frog = frog;
    }

    @Override
    public void run() {
        while (true) {
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
        FrogController.move(this.frog);
    }
}
