package dk.sdu.mmmi.cbse.common.data.entityparts;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.commonmap.IMap;
import lombok.Getter;
import lombok.Setter;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

@Getter
@Setter
public class MovingPart implements EntityPart {

    public MovingPart() {
    }

    private float dx, dy;
    private float speed;

    private boolean moving = false;

    static IMap map;

    public MovingPart(float speed) {
        this.speed = speed;
    }

    public MovingPart(float speed, boolean moving) {
        this.speed = speed;
        this.moving = moving;
    }

    @Override
    public void process(GameData gameData, Entity entity) {
        if (!moving) return;

        PositionPart positionPart = entity.getPart(PositionPart.class);
        float x = positionPart.getX();
        float y = positionPart.getY();
        int radians = positionPart.getAngle();

        float radians2 = (float) Math.toRadians(radians);
        dx = (float) cos(radians2) * speed;
        dy = (float) sin(radians2) * speed;


        // calculating center of a tile
        float centerX = x -5 + map.getTileSize()/2f;
        float centerY = y -5  + map.getTileSize()/2f;
        float radius = entity.getRadius();

        // set position if new position is inside map
        if ( map.isInsideMap(centerX+dx+radius, centerY+dy+radius) && map.isInsideMap(centerX+dx-radius, centerY+dy-radius) ) {
            x += dx;
            y += dy;
            positionPart.setPosition(x, y);
            positionPart.setAngle(radians);
        }
    }

    public void setIMap(IMap map) {
        this.map = map;
    }

    public void removeIMap(IMap map) {
        this.map = null;
    }
}
