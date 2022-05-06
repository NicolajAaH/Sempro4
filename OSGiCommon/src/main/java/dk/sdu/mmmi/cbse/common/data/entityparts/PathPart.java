package dk.sdu.mmmi.cbse.common.data.entityparts;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;

import java.awt.*;
import java.util.Stack;


public class PathPart implements EntityPart{

    private int xGoal;
    private int yGoal;

    private final Stack<Point> explored = new Stack<>();

    public int getxGoal() {
        return xGoal;
    }

    public void addPosition(Point position){
        explored.push(position);
    }

    public boolean isExplored(Point position){
        return explored.stream().anyMatch(in -> in.x == position.x && in.y == position.y);
    }

    public Point getCurrentTile(){
        return explored.peek();
    }

    public void setxGoal(int xGoal) {
        this.xGoal = xGoal;
    }

    public int getyGoal() {
        return yGoal;
    }

    public void setyGoal(int yGoal) {
        this.yGoal = yGoal;
    }

    @Override
    public void process(GameData gameData, Entity entity) {

    }
}
