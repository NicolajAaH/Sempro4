package dk.sdu.mmmi.cbse.common.data.entityparts;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;

import java.awt.*;
import java.util.HashSet;


public class PathPart implements EntityPart{

    private int xGoal;
    private int yGoal;

    private final HashSet<Point> explored = new HashSet<>();

    public int getxGoal() {
        return xGoal;
    }

    public void addPosition(Point position){
        explored.add(position);
    }

    public boolean isExplored(Point position){
        return explored.contains(position);
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
