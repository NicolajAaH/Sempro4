package dk.sdu.mmmi.cbse.common.data.entityparts;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.PathDirection;
import lombok.Getter;
import lombok.Setter;

import java.awt.*;
import java.util.Stack;

@Getter
@Setter
public class PathPart implements EntityPart{

    private Point goal;
    private Stack<PathDirection> path;

    public PathPart(Stack<PathDirection> path){
        this.path = path;
    }

    @Override
    public void process(GameData gameData, Entity entity) {

    }
}
