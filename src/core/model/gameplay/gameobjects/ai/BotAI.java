package core.model.gameplay.gameobjects.ai;

import core.MathAdv;
import core.model.gameplay.CollisionManager;
import core.model.gameplay.World;
import core.model.gameplay.gameobjects.Bot;
import core.model.gameplay.gameobjects.GameObjectSolid;
import org.newdawn.slick.geom.Point;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class BotAI {

    protected Bot owner;
    protected Map<BotAIState, AIState> stateMap;
    protected BotAIState currentState;
    private BotAIState previousState;

    protected List<Cell> path;

    public BotAI() {
        this(null);
    }

    public BotAI(Bot bot) {
        this.owner = bot;
        stateMap = new HashMap<>();
        path = new ArrayList<>();
        init();
    }

    public interface BotAIState {

    }

    public void run(int delta) {
        if (currentState != previousState) {
            stateMap.get(currentState).enter();
            previousState = currentState;
        }
        stateMap.get(currentState).run(delta);
        stateMap.get(currentState).update(delta);
    }

    protected abstract void init();

    // AI methods

    protected double getDistanceToHero() {
        return MathAdv.getDistance(World.getInstance().getHero().getX(), World.getInstance().getHero().getY(),
                owner.getX(), owner.getY());
    }

    protected Point getRandomTarget() {
        Point target = new Point(0, 0);
        int attemptNumber = 0;
        while (attemptNumber < 5) {
            double randomDistance = 100 + Math.random() * 10;
            double randomAngle = Math.random() * 2 * Math.PI;
            double tmpX = owner.getX() + MathAdv.lengthDirX(randomAngle, randomDistance);
            double tmpY = owner.getY() + MathAdv.lengthDirY(randomAngle, randomDistance);
            if (CollisionManager.getInstance().isPlaceFreeAdv(owner, tmpX, tmpY)) {
                target.setX((float) tmpX);
                target.setY((float) tmpY);
                return target;
            }
            attemptNumber++;
        }
        target.setX((float)owner.getX());
        target.setY((float)owner.getY());
        return target;
    }

    protected void followTarget(Point target) {
        double direction = Math.atan2(target.getY() - owner.getY(), target.getX() - owner.getX());
        owner.setDirection(direction);
        owner.move();
    }

    protected void followHero() {
        if (path.size() > 0) {
            followTarget(new Point((float)path.get(path.size() - 1).x, (float)path.get(path.size() - 1).y));
            if (MathAdv.getDistance(owner.getX(), owner.getY(), path.get(path.size() - 1).x, path.get(path.size() - 1).y) < 2) {
                path.remove(path.size() - 1);
                System.out.println(path.size());
            }
        } else {
            followTarget(new Point((float) World.getInstance().getHero().getX(), (float) World.getInstance().getHero().getY()));
        }
    }

    protected double getDistanceToTarget(Point target) {
        return MathAdv.getDistance(target.getX(), target.getY(), owner.getX(), owner.getY());
    }

    private class Cell {
        public double f;
        public double g;
        public double h;
        public int x;
        public int y;
        public Cell cameFrom;

        public Cell(double f, double g, double h, double x, double y) {
            this(f, g, h, x, y, null);
        }

        public Cell(double f, double g, double h, double x, double y, Cell cameFrom) {
            this.f = f;
            this.g = g;
            this.h = h;
            this.x = (int) x;
            this.y = (int) y;
            this.cameFrom = cameFrom;
        }
    }

    protected List<Cell> buildPath(GameObjectSolid target) {
        int step = 32;
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
            if (MathAdv.getDistance(x.x, x.y, goal.x, goal.y) <= step) {
                return reconstructPath(start, x);
            }

            openedSet.remove(x);
            closedSet.add(x);

            for (Cell y : neighborCells(x, step)) {
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
                    y.h = CollisionManager.getInstance().isPlaceFreeAdv(owner, y.x, y.y) ? MathAdv.getDistance(goal.x, goal.y, y.x, y.y) : 999;
                    y.f = y.g + y.h;
                }
            }
        }
        return null;
    }

    private boolean contains(List<Cell> list, Cell cell) {
        for (Cell currentCell : list) {
            if (currentCell.x == cell.x && currentCell.y == cell.y) {
                return true;
            }
        }
        return false;
    }

    private List<Cell> neighborCells(Cell cell, int step) {
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

    // Getters and setters

    public BotAIState getCurrentState() {
        return currentState;
    }

    public void setOwner(Bot owner) {
        this.owner = owner;
    }

}