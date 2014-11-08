package core.model;

import core.controller.DirKey;

/**
 * Contains methods to define hero behavior
 * */
public class HeroManager {

    private Hero hero;

    public HeroManager(final Hero hero) {
        this.hero = hero;
    }

/*
    public void move(final int keyIndex, final int delta) {
        switch (keyIndex) {
            case 0: //right
                hero.setX(hero.getX() + hero.getSpeed() * delta);
                break;
            case 1: // down
                hero.setY(hero.getY() + hero.getSpeed() * delta);
                break;
            case 2: // left
                hero.setX(hero.getX() - hero.getSpeed() * delta);
                break;
            case 3: // up
                hero.setY(hero.getY() - hero.getSpeed() * delta);
                break;
        }
    }
*/

    public void move(final DirKey keyIndex, final int delta) {
        double length, lengthDirX, lengthDirY;
        length = hero.getSpeed() * delta;

        switch (keyIndex) {
            case RIGHT: //right
                lengthDirX = Math.cos(hero.getDirection() + Math.PI / 2) * length;
                lengthDirY = Math.sin(hero.getDirection() + Math.PI / 2) * length;
                hero.setX(hero.getX() + lengthDirX);
                hero.setY(hero.getY() + lengthDirY);
                break;
            case BOT: // back
                lengthDirX = Math.cos(hero.getDirection()) * length;
                lengthDirY = Math.sin(hero.getDirection()) * length;
                hero.setX(hero.getX() - lengthDirX);
                hero.setY(hero.getY() - lengthDirY);
                break;
            case LEFT: // left
                lengthDirX = Math.cos(hero.getDirection() - Math.PI / 2) * length;
                lengthDirY = Math.sin(hero.getDirection() - Math.PI / 2) * length;
                hero.setX(hero.getX() + lengthDirX);
                hero.setY(hero.getY() + lengthDirY);
                break;
            case TOP: // forward
                lengthDirX = Math.cos(hero.getDirection()) * length;
                lengthDirY = Math.sin(hero.getDirection()) * length;
                hero.setX(hero.getX() + lengthDirX);
                hero.setY(hero.getY() + lengthDirY);
                break;
        }
    }

    public void rotate(final int targetX, final int targetY) {
        double angle;
        /*if (targetX - hero.getX() >= 0) {
            angle = Math.atan((targetY - hero.getY()) / (targetX - hero.getX()));
        } else {
            angle = Math.atan((targetY - hero.getY())/(targetX - hero.getX())) + Math.PI;
        }*/
        angle = Math.atan2((targetY - hero.getY()), (targetX - hero.getX()));

        hero.setDirection(angle);
    }

    public void rotate(final double angleOffset) {
/*        if (angleOffset > 0.1) {
            angleOffset = 0.1;
        } else if (angleOffset < -0.1) {
            angleOffset = -0.1;
        }*/
        hero.setDirection(hero.getDirection() + angleOffset);
    }

}