package dk.sdu.mmmi.cbse;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import dk.sdu.mmmi.cbse.commonmap.IMap;

import java.awt.*;
import java.util.*;

public class PathFinder {


    IMap map;
    private Point start;
    private Point goal;

    private ArrayList<CalcPoint> open = new ArrayList<>();
    private ArrayList<CalcPoint> closed = new ArrayList<>();
    private ArrayList<CalcPoint> path = new ArrayList<>();

    PathFinder(IMap map){
        this.map = map;
        start = map.getStartTileCoor();
        goal = map.getEndTileCoor();
        addToFringe(start, 0, null);
    }

    public ArrayList<Point> calculatePath() {
        CalcPoint goal = null;

        while (open.size() != 0){
            CalcPoint lowest = null;
            System.out.println("while start");
            for(CalcPoint calcPoint : open){
                if(lowest == null || calcPoint.dist < lowest.dist) lowest = calcPoint;
            }
            System.out.println("lowest set: " + open.size());

            open.remove(lowest);
            closed.add(lowest);

            System.out.println("lowest removed: " + open.size());

            if(lowest.type.equals("End")) {
                goal = lowest;
                break;
            }
            if(!lowest.type.equals("Path") && !lowest.type.equals("Start")) continue;

            System.out.println("breaks done");

            addSurroundingToFringe(lowest, lowest.pathDist + 1);

            System.out.println("open: " + open.size());
        }

        System.out.println("search over");

        if(goal == null) return null;

        ArrayList<Point> path = new ArrayList<>();

        System.out.println("do while start");

        do {
            path.add(goal.point);
            goal = goal.parent;
        }
        while(goal.parent != null);

        System.out.println("do while over");

        Collections.reverse(path);

        System.out.println("collection over");

        return path;
    }

    private double calculateEuclideanDistance(Point a, Point b){
        return Math.sqrt(Math.pow((b.x - a.x), 2) + Math.pow((b.y - a.y), 2));
    }

    private void addToFringe(Point point, int pathDist, CalcPoint parent){
        String type = map.getTileType(point.x, point.y);

        if(type == null) return;
        if(open.stream().anyMatch((calcPoint -> calcPoint.point.x == point.x && calcPoint.point.y == point.y))) return;
        if(closed.stream().anyMatch((calcPoint -> calcPoint.point.x == point.x && calcPoint.point.y == point.y))) return;

        System.out.println("addToFringe: " + point.x + " " + point.y);
        open.add(
                new CalcPoint(
                        calculateEuclideanDistance(point,goal),
                        point,
                        map.getTileType(point.x, point.y),
                        pathDist + 1,
                        parent
                )
        );
        System.out.println("addToFringe closed");
    }

    private void addSurroundingToFringe(CalcPoint calcPoint, int pathDist){
        addToFringe(new Point(calcPoint.point.x, calcPoint.point.y+1), pathDist, calcPoint);
        addToFringe(new Point(calcPoint.point.x, calcPoint.point.y-1), pathDist, calcPoint);
        addToFringe(new Point(calcPoint.point.x+1,calcPoint. point.y), pathDist, calcPoint);
        addToFringe(new Point(calcPoint.point.x-1,calcPoint. point.y), pathDist, calcPoint);
    }

    private class CalcPoint{
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
