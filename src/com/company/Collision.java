package com.company;

public class Collision {
    private final Collidable actorEntity;
    private final Collidable targetEntity;

    Collision(Collidable firstEntity, Collidable secondEntity) {
        this.actorEntity = firstEntity;
        this.targetEntity = secondEntity;
    }

    public Collidable getActorEntity() {
        return actorEntity;
    }

    public Collidable getTargetEntity() {
        return targetEntity;
    }

}
