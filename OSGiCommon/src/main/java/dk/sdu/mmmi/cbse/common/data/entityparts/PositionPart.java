package dk.sdu.mmmi.cbse.common.data.entityparts;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PositionPart implements EntityPart {

    public static final int right = 0;
    public static final int up = 90;
    public static final int left = 180;
    public static final int down = 270;
    private float x;
    private float y;
    private int radians = 0; // direction of movement
    private String lastChange;
    private float originX, originY;

    public PositionPart(float x, float y, int radians) {
        this.x = x;
        this.y = y;
        this.originX = x;
        this.originY = y;
        this.radians = radians;
    }

    public void setPosition(float newX, float newY) {
        this.x = newX;
        this.y = newY;
    }


    @Override
    public void process(GameData gameData, Entity entity) {
    }

    public float getDistanceFromOrigin(){
        return (float) Math.sqrt((originX - x) * (originX - x) + (originY - y) * (originY -y));
    }
}
