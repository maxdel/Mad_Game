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

}