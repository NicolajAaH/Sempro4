package dk.sdu.mmmi.cbse.common.data;

import lombok.Getter;
import lombok.Setter;

import java.awt.*;

@Getter
@Setter
public class PathDirection {

    private int direction;
    private Point goal;
    public PathDirection(int direction, Point goal){
        this.direction = direction;
        this.goal = goal;
    }

    @Override
    public String toString() {
        return "Direction: " + direction + " goal: " + goal;
    }
}
