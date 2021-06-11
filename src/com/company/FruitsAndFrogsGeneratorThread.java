package com.company;

import java.util.ArrayList;

public class FruitsAndFrogsGeneratorThread extends Thread implements GameObjectThread {
    FruitsAndFrogsGenerator fruitsAndFrogsGenerator;
    private boolean canCalculateNextAction = false;
    private ArrayList<Fruit> missingFruits;
    private ArrayList<Frog> missingFrogs;

    FruitsAndFrogsGeneratorThread(Board board) {
        this.fruitsAndFrogsGenerator = new FruitsAndFrogsGenerator(board, 2, 3);
    }

    @Override
    public void run() {
        while(true) {
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
        this.fruitsAndFrogsGenerator.generateFruitsAndFrogs(this.missingFruits, this.missingFrogs);
    }
}
