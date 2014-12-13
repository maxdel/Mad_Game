package core;

public class MathAdv {

    public static double lengthDirX(double direction, double length) {
        return Math.cos(direction) * length;
    }

    public static double lengthDirY(double direction, double length) {
        return Math.sin(direction) * length;
    }

    public static double getDistance(double x1, double y1, double x2, double y2) {
        return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
    }

}