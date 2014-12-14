package core.model.gameplay.gameobjects.ai;

import core.MathAdv;
import core.model.gameplay.CollisionManager;
import core.model.gameplay.gameobjects.Bullet;
import core.model.gameplay.gameobjects.GameObjInstanceKind;
import core.model.gameplay.gameobjects.GameObjectSolid;
import core.model.gameplay.gameobjects.Unit;
import org.newdawn.slick.geom.Point;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class AStar {

    private List<Cell> path;
    private int GRID_STEP = 30;

    public AStar() {
        path = new ArrayList<>();
    }

    protected boolean buildPath(GameObjectSolid target, Unit owner) {
        List<Cell> openedSet = new ArrayList<>();
        List<Cell> closedSet = new ArrayList<>();
        Cell start = new Cell(0, 0, 0, owner.getX(), owner.getY());
        Cell goal = new Cell(0, 0, 0, target.getX(), target.getY());
        openedSet.add(start);
        start.g = 0;
        start.h = MathAdv.getDistance(goal.x, goal.y, start.x, start.y);
        start.f = start.g + start.h;
        while (!openedSet.isEmpty()) {
            Cell x = getLowestFCell(openedSet);
            if (MathAdv.getDistance(x.x, x.y, goal.x, goal.y) <= GRID_STEP) {
                System.out.println("RECONSTR x.f: " + x.f);
                path = reconstructPath(start, x);
                return true;
            }

            openedSet.remove(x);
            closedSet.add(x);

            for (Cell y : neighborCells(owner, x, GRID_STEP)) {
                if (contains(closedSet, y)) {
                    continue;
                }

                double tentativeGScore = x.g + MathAdv.getDistance(x.x, x.y, y.x, y.y);
                boolean tentativeIsBetter;

                if (!contains(openedSet, y)) {
                    openedSet.add(y);
                    tentativeIsBetter = true;
                } else {
                    if (tentativeGScore < y.g) {
                        tentativeIsBetter = true;
                    } else {
                        tentativeIsBetter = false;
                    }
                }

                if (tentativeIsBetter) {
                    y.cameFrom = x;
                    y.g = tentativeGScore;
                    y.h = calculateH(owner, target, y.x, y.y, goal.x, goal.y);
                    y.f = y.g + y.h;
                }
            }
        }
        return false;
    }

    private double calculateH(Unit owner, GameObjectSolid target, int x, int y, int goalX, int goalY) {
        if (CollisionManager.getInstance().collidesWith(owner, x, y) == null ||
                CollisionManager.getInstance().collidesWith(owner, x, y) == target) {
            return MathAdv.getDistance(x, y, goalX, goalY);
        } else {
            return 1000;
        }
    }

    private boolean contains(List<Cell> list, Cell cell) {
        for (Cell currentCell : list) {
            if (currentCell.x == cell.x && currentCell.y == cell.y) {
                return true;
            }
        }
        return false;
    }

    /*private List<Cell> neighborCells(Unit owner, Cell cell, int step) {
        List<Cell> list = new ArrayList<>();
        if (CollisionManager.getInstance().isPlaceFreeAdv(owner, cell.x + step, cell.y)) {
            list.add(new Cell(0, 0, 0, cell.x + step, cell.y));
        }
        if (CollisionManager.getInstance().isPlaceFreeAdv(owner, cell.x + step, cell.y + step)) {
            list.add(new Cell(0, 0, 0, cell.x + step, cell.y + step));
        }
        if (CollisionManager.getInstance().isPlaceFreeAdv(owner, cell.x, cell.y + step)) {
            list.add(new Cell(0, 0, 0, cell.x, cell.y + step));
        }
        if (CollisionManager.getInstance().isPlaceFreeAdv(owner, cell.x - step, cell.y + step)) {
            list.add(new Cell(0, 0, 0, cell.x - step, cell.y + step));
        }
        if (CollisionManager.getInstance().isPlaceFreeAdv(owner, cell.x - step, cell.y)) {
            list.add(new Cell(0, 0, 0, cell.x - step, cell.y));
        }
        if (CollisionManager.getInstance().isPlaceFreeAdv(owner, cell.x - step, cell.y - step)) {
            list.add(new Cell(0, 0, 0, cell.x - step, cell.y - step));
        }
        if (CollisionManager.getInstance().isPlaceFreeAdv(owner, cell.x, cell.y - step)) {
            list.add(new Cell(0, 0, 0, cell.x, cell.y - step));
        }
        if (CollisionManager.getInstance().isPlaceFreeAdv(owner, cell.x + step, cell.y - step)) {
            list.add(new Cell(0, 0, 0, cell.x + step, cell.y - step));
        }
        return list;
    }*/

    private List<Cell> neighborCells(Unit owner, Cell cell, int step) {
        List<Cell> list = new ArrayList<>();
        list.add(new Cell(0, 0, 0, cell.x + step, cell.y));
        list.add(new Cell(0, 0, 0, cell.x + step, cell.y + step));
        list.add(new Cell(0, 0, 0, cell.x, cell.y + step));
        list.add(new Cell(0, 0, 0, cell.x - step, cell.y + step));
        list.add(new Cell(0, 0, 0, cell.x - step, cell.y));
        list.add(new Cell(0, 0, 0, cell.x - step, cell.y - step));
        list.add(new Cell(0, 0, 0, cell.x, cell.y - step));
        list.add(new Cell(0, 0, 0, cell.x + step, cell.y - step));
        return list;
    }

    private List<Cell> reconstructPath(Cell first, Cell last) {
        List<Cell> path = new ArrayList<>();
        Cell currentCell = last;
        while (currentCell != null) {
            path.add(currentCell);
            currentCell = currentCell.cameFrom;
        }
        return path;
    }

    private Cell getLowestFCell(List<Cell> list) {
        if (list.isEmpty()) {
            return null;
        } else {
            Cell result = list.get(0);
            for (Cell cell : list) {
                if (cell.f < result.f) {
                    result = cell;
                }
            }
            return result;
        }
    }

    public void removeFrom(Point point) {
        boolean startRemove = false;
        for(Iterator<Cell> it = path.iterator(); it.hasNext();) {
            Cell currentCell = it.next();
            Point currentPoint = new Point(currentCell.x, currentCell.y);
            if (startRemove == false && currentPoint.getX() == point.getX() && currentPoint.getY() == point.getY()) {
                startRemove = true;
            }
            if (startRemove) {
                it.remove();
                System.out.print("*");
            }
        }
        System.out.println("|");
    }

    public Point getNextPoint() {
        if (path.size() > 0) {
            return new Point((float) path.get(path.size() - 1).x, (float) path.get(path.size() - 1).y);
        } else {
            return null;
        }
    }

    public Point getFirstReachablePoint(Unit owner) {
        if (path.isEmpty()) {
            return null;
        } else {
            Point currentPoint = null;
            Point previousPoint = new Point(path.get(0).x, path.get(0).y);
            for (int i = path.size() - 1; i >= 0; i--) {
                currentPoint = new Point(path.get(i).x, path.get(i).y);
                if (!isWayFree(owner, currentPoint)) {
                    return previousPoint;
                }
                previousPoint = currentPoint;
            }
            return currentPoint;
        }
    }

    private boolean isWayFree(Unit owner, Point target) {
        int step = 5;
        double currentDirection = Math.atan2(target.getY() - owner.getY(), target.getX() - owner.getX());
        for (int j = 0; j < MathAdv.getDistance(owner.getX(), owner.getY(), target.getX(), target.getY()) / step; ++j) {
            GameObjectSolid collisionObject = CollisionManager.getInstance().collidesWith(owner,
                    owner.getX() + MathAdv.lengthDirX(currentDirection, step * j),
                    owner.getY() + MathAdv.lengthDirY(currentDirection, step * j));
            if (collisionObject != owner && collisionObject != null && !(collisionObject instanceof Bullet)) {
                return false;
            }
        }
        return true;
    }

    protected Point getGoalPoint() {
        if (path.isEmpty()) {
            return null;
        } else {
            return new Point((float) path.get(0).x, (float) path.get(0).y);
        }
    }

    protected int getGridStep() {
        return GRID_STEP;
    }

    public List<Cell> getPath() {
        return path;
    }
}
