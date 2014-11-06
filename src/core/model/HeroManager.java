package core.model;

public class HeroManager {

    private Hero hero;

    public HeroManager(Hero hero) {
        this.hero = hero;
    }

    public void move(int key) {
        switch (key) {
            case 0:
                hero.setX(hero.getX() + hero.getSpeed());
                break;
            case 1:
                hero.setY(hero.getY() - hero.getSpeed());
                break;
            case 2:
                hero.setX(hero.getX() - hero.getSpeed());
                break;
            case 3:
                hero.setY(hero.getY() + hero.getSpeed());
                break;
        }
    }

    

}