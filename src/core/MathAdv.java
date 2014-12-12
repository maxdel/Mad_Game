package core;

public class MathAdv {

    public static double lengthDirX(double direction, double length) {
        return Math.cos(direction) * length;
    }

    public static double lengthDirY(double direction, double length) {
        return Math.sin(direction) * length;
    }

}