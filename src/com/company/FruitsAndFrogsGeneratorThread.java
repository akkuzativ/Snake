package com.company;

import java.util.ArrayList;

public class FruitsAndFrogsGeneratorThread extends Thread implements GameObjectThread {
    private Board board;
    private FruitsAndFrogsGenerator fruitsAndFrogsGenerator;
    private boolean canCalculateNextAction = false;
    private ArrayList<Fruit> missingFruits;
    private ArrayList<Frog> missingFrogs;
    private boolean killed = false;

    FruitsAndFrogsGeneratorThread(Board board) {
        this.board = board;
        this.fruitsAndFrogsGenerator = new FruitsAndFrogsGenerator(board, 3, 3);
    }

    @Override
    public void run() {
        while(true) {
            if (killed) {
                break;
            }
            if (this.canCalculateNextAction) {
                this.missingFruits = fruitsAndFrogsGenerator.generateMissingFruitsList();
                this.missingFrogs = fruitsAndFrogsGenerator.generateMissingFrogsList();
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
        this.board.setMissingFrogs(this.missingFrogs);
        this.fruitsAndFrogsGenerator.generateFruitsAndFrogs(this.missingFruits, this.missingFrogs);
    }

    @Override
    public ArrayList<Collidable> getGameObjectsToRemove() {
        return new ArrayList<>();
    }

    @Override
    public Collidable getRelatedGameObject() {
        return new Collidable() {
            @Override
            public String getName() {
                return "dummy";
            }
        };
    }

    @Override
    public void forceKill() {
        killed = true;
    }

}
