package core.model.gameplay.gameobjects.ai;

import core.MathAdv;
import core.model.gameplay.CollisionManager;
import core.model.gameplay.gameobjects.GameObjectSolid;
import core.model.gameplay.gameobjects.Unit;
import org.newdawn.slick.geom.Point;

import java.util.ArrayList;
import java.util.List;

public class AStar {

    private List<Cell> path;
    private int GRID_STEP = 32;

    public AStar() {
        path = new ArrayList<>();
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
                path = reconstructPath(start, x);
                return true;
            }

            openedSet.remove(x);
            closedSet.add(x);

            for (Cell y : neighborCells(x, GRID_STEP)) {
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
        return false;
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

    public void pop() {
        if (path.size() > 0) {
            path.remove(path.size() - 1);
        }
    }

    public Point getNextPoint() {
        if (path.size() > 0) {
            return new Point((float) path.get(path.size() - 1).x, (float) path.get(path.size() - 1).y);
        } else {
            return null;
        }
    }

}
