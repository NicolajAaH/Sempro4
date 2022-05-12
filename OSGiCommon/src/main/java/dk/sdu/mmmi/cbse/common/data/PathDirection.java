package dk.sdu.mmmi.cbse.common.data;

import dk.sdu.mmmi.cbse.common.data.Enums.Direction;

import java.awt.*;

public class PathDirection {

    private int direction;
    private Point goal;
    public PathDirection(int direction, Point goal){
        this.direction = direction;
        this.goal = goal;
    }

    public int getDirection() {
        return direction;
    }

    public Point getGoal() {
        return goal;
    }

    @Override
    public String toString() {
        return "Direction: " + direction + " goal: " + goal;
    }
}
