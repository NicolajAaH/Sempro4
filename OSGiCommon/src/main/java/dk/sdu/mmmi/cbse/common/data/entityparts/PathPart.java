package dk.sdu.mmmi.cbse.common.data.entityparts;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.GameKeys;

public class PathPart implements EntityPart{

    GameKeys direction;
    int xGoal;
    int yGoal;

    public GameKeys getDirection() {
        return direction;
    }

    public void setDirection(GameKeys direction) {
        this.direction = direction;
    }

    public int getxGoal() {
        return xGoal;
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
