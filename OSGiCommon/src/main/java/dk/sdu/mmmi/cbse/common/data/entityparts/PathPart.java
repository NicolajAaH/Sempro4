package dk.sdu.mmmi.cbse.common.data.entityparts;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.PathDirection;

import java.awt.*;
import java.util.Stack;


public class PathPart implements EntityPart{

    private Point goal;
    private Stack<PathDirection> path;

    public PathPart(Stack<PathDirection> path){
        this.path = path;
    }

    public Point getGoal() {
        return goal;
    }

    public Stack<PathDirection> getPath() {
        return path;
    }

    public void setGoal(Point goal) {
        this.goal = goal;
    }

    @Override
    public void process(GameData gameData, Entity entity) {

    }
}
