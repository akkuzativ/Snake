package com.company;

import java.util.ArrayList;

public interface GameObjectThread {
    void start();
    void startCalculatingNextAction();
    void performNextAction();
    ArrayList<Collidable> getGameObjectsToRemove();
    Collidable getRelatedGameObject();
    void forceKill();
}
