package core.controller;

import core.view.HeroRepresentation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;

import core.model.HeroManager;

public class HeroController {

    private HeroManager heroManager;
    private HeroRepresentation heroRepresentation;

    public HeroController(HeroManager heroManager, HeroRepresentation heroRepresentation) {
        this.heroManager = heroManager;
        this.heroRepresentation = heroRepresentation;
    }

    public void update(GameContainer gc, final int delta) {
        if (gc.getInput().isKeyDown(Input.KEY_D)) {
            heroManager.move(0, delta);
            heroRepresentation.setHero(heroRepresentation.getMovementRight());
        }
        if (gc.getInput().isKeyDown(Input.KEY_W)) {
            heroManager.move(1, delta);
            heroRepresentation.setHero(heroRepresentation.getMovementUp());
        }
        if (gc.getInput().isKeyDown(Input.KEY_A)) {
            heroManager.move(2, delta);
            heroRepresentation.setHero(heroRepresentation.getMovementLeft());
        }
        if (gc.getInput().isKeyDown(Input.KEY_S)) {
            heroManager.move(3, delta);
            heroRepresentation.setHero(heroRepresentation.getMovementDown());

        } else {
            switch (heroManager.getLastDirection()) {
                case 'd':
                    heroRepresentation.setHero(heroRepresentation.getStillDown());
                    break;
                case 'u':
                    heroRepresentation.setHero(heroRepresentation.getStillUp());
                    break;
                case 'l':
                    heroRepresentation.setHero(heroRepresentation.getStillLeft());
                    break;
                case 'r':
                    heroRepresentation.setHero(heroRepresentation.getStillRight());
                    break;
            }
        }
        heroManager.rotate(gc.getInput().getMouseX(), gc.getInput().getMouseY());

    }
}



