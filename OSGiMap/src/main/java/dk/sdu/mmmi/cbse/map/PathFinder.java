package dk.sdu.mmmi.cbse.map;

import dk.sdu.mmmi.cbse.commonmap.IMap;

import java.awt.*;
import java.util.ArrayList;

public class PathFinder {
    IMap map;
    private final Point goal;
    private final ArrayList<CalcPoint> open = new ArrayList<>();
    private final ArrayList<CalcPoint> closed = new ArrayList<>();

    PathFinder(IMap map) {
        this.map = map;
        Point start = map.getStartTileCoor();
        goal = map.getEndTileCoor();
        addToFringe(start, 0, null);
    }

    public ArrayList<Point> calculatePath() {
        CalcPoint goal = null;

        while (open.size() != 0) {
            CalcPoint lowest = null;
            for (CalcPoint calcPoint : open) {
                if (lowest == null || (calcPoint.dist + calcPoint.pathDist) < (lowest.dist + lowest.pathDist)) lowest = calcPoint;
            }

            open.remove(lowest);
            closed.add(lowest);

            if (lowest.type.equals("End")) {
                goal = lowest;
                break;
            }
            if (!lowest.type.equals("Path") && !lowest.type.equals("Start")) continue;

            addSurroundingToFringe(lowest, lowest.pathDist + 1);
        }

        if (goal == null) return null;

        ArrayList<Point> path = new ArrayList<>();


        do {
            path.add(goal.point);
            goal = goal.parent;
        }
        while (goal != null);

        return path;
    }

    private double calculateEuclideanDistance(Point a, Point b) {
        return Math.sqrt(Math.pow((b.x - a.x), 2) + Math.pow((b.y - a.y), 2));
    }

    private void addToFringe(Point point, int pathDist, CalcPoint parent) {

        String type = map.getTileType(point.x, point.y);

        CalcPoint calcPoint = new CalcPoint(
                calculateEuclideanDistance(point, goal),
                point,
                type,
                pathDist + 1,
                parent
        );

        for(CalcPoint openPoint : open){
            if(openPoint.point.x == point.x && openPoint.point.y == point.y){
                if(openPoint.pathDist <= calcPoint.pathDist) return;
                open.remove(openPoint);
            }
        }

        if (closed.stream().anyMatch((closedPoint -> closedPoint.point.x == point.x && closedPoint.point.y == point.y && closedPoint.pathDist <= calcPoint.pathDist)))
            return;

        open.add(calcPoint);
    }

    private void addSurroundingToFringe(CalcPoint calcPoint, int pathDist) {
        addToFringe(new Point(calcPoint.point.x, calcPoint.point.y + 1), pathDist, calcPoint);
        addToFringe(new Point(calcPoint.point.x, calcPoint.point.y - 1), pathDist, calcPoint);
        addToFringe(new Point(calcPoint.point.x + 1, calcPoint.point.y), pathDist, calcPoint);
        addToFringe(new Point(calcPoint.point.x - 1, calcPoint.point.y), pathDist, calcPoint);
    }

    private class CalcPoint {
        Double dist;
        int pathDist;
        Point point;
        String type;
        CalcPoint parent;

        CalcPoint(Double dist, Point point, String type, int pathDist, CalcPoint parent) {
            this.dist = dist;
            this.point = point;
            this.type = type;
            this.pathDist = pathDist;
            this.parent = parent;
        }
    }
}
