package core.view.gameplay.ui;

import java.util.List;

import org.newdawn.slick.Image;

import core.MathAdv;
import core.model.Timer;

public class SpinnerView {

    private int radius;
    private List<Image> imageList;
    private int elementsNumber;
    private double direction;
    private int spinTime;

    private double targetDirection;
    private Timer timer;
    private double spinDelta;

    public SpinnerView(int radius, List<Image> imageList, int elementsNumber, double initialDirection, int spinTime) {
        this.radius = radius;
        this.imageList = imageList;
        this.elementsNumber = elementsNumber;
        this.direction = initialDirection;
        this.spinTime = spinTime;

        this.targetDirection = direction;
        this.timer = new Timer();
        this.spinDelta = targetDirection - direction;
    }

    protected void update(int delta) {timer.update(delta);
        if (timer.isActive()) {
            direction += spinDelta * delta / spinTime;
        } else {
            direction = targetDirection;
        }
    }

    protected void render(int x, int y) {
        double currentItemDirection = direction;
        for (int i = 0; i < elementsNumber; ++i) {
            imageList.get(i).draw((float) (x + MathAdv.lengthDirX(currentItemDirection, radius) - imageList.get(i).getWidth() / 2),
                    (float) (y + MathAdv.lengthDirY(currentItemDirection, radius) - imageList.get(i).getHeight() / 2));
            currentItemDirection += 2 * Math.PI / elementsNumber;
        }
    }

    protected void spinForward() {
        timer.activate(spinTime);
        targetDirection += 2 * Math.PI / elementsNumber;
        spinDelta = targetDirection - direction;
    }

    protected void spinBackward() {
        timer.activate(spinTime);
        targetDirection -= 2 * Math.PI / elementsNumber;
        spinDelta = targetDirection - direction;
    }

    // Setters

    protected void setElementsNumber(int elementsNumber) {
        this.elementsNumber = elementsNumber;
    }

    protected void setImages(List<Image> imageList) {
        this.imageList = imageList;
    }

}