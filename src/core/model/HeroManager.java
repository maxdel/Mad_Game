package core.model;

public class HeroManager {

    private Hero hero;

    public HeroManager(Hero hero) {
        this.hero = hero;
    }

    public void move(final int keyIndex, final int delta) {
        switch (keyIndex) {
            case 0:
                hero.setX(hero.getX() + hero.getSpeed() * delta);
                break;
            case 1:
                hero.setY(hero.getY() - hero.getSpeed() * delta);
                break;
            case 2:
                hero.setX(hero.getX() - hero.getSpeed() * delta);
                break;
            case 3:
                hero.setY(hero.getY() + hero.getSpeed() * delta);
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

}