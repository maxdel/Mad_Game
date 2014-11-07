package core.model;

public class HeroManager {

    private Hero hero;

    public HeroManager(Hero hero) {
        this.hero = hero;
    }

    public void move(final int keyIndex, final int delta) {
        switch (keyIndex) {
            case 0: //rigth
                hero.setX(hero.getX() + hero.getSpeed() * delta);
                hero.setLastDirection('r');
                break;
            case 1: // up
                hero.setY(hero.getY() - hero.getSpeed() * delta);
                hero.setLastDirection('u');
                break;
            case 2: // left
                hero.setX(hero.getX() - hero.getSpeed() * delta);
                hero.setLastDirection('l');
                break;
            case 3: // down
                hero.setY(hero.getY() + hero.getSpeed() * delta);
                hero.setLastDirection('d');
                break;
        }
    }

    public void rotate(final int targetX, int targetY) {
        double angle;
/*        if (targetX - hero.getX() >= 0) {
            angle = Math.atan((targetY - hero.getY()) / (targetX - hero.getX()));
        } else {
            angle = Math.atan((targetY - hero.getY())/(targetX - hero.getX())) + Math.PI;
        }*/
        angle = Math.atan2((targetY - hero.getY()), (targetX - hero.getX()));

        hero.setDirection(angle);
    }

    public char getLastDirection() {
        return hero.getLastDirection();
    }
}