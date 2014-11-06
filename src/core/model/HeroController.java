package core.model;

public class HeroController {

    private Hero hero;

    public HeroController(Hero hero) {
        this.hero = hero;
    }

    public void move(int direction) {
        switch (direction) {
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
