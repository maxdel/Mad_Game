package core.controller;

import core.model.World;
import core.view.Renderer;

public class Controller {

    World world;
    Renderer renderer;

    public Controller(World world, Renderer renderer) {
        this.world = world;
        this.renderer = renderer;
    }

}
