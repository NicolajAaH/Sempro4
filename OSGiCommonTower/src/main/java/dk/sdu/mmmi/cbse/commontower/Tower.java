package dk.sdu.mmmi.cbse.commontower;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.Types;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;


public class Tower extends Entity {

    private int buildCost;

    public Tower(Sprite sprite, Types type) {
        super(sprite);
        this.type = type;
        this.buildCost = 100;
    }

    @Override
    public void draw(Batch batch) {

            batch.begin();
            update(Gdx.graphics.getDeltaTime());
            super.draw(batch);
            batch.end();
    }

    public void update(float delta){
        PositionPart positionPart = this.getPart(PositionPart.class);
        // setting posistion of sprite
        this.setPosition(positionPart.getX(), positionPart.getY());
        // setting rotation of sprite (using degrees NOT radians)
        this.setRotation(positionPart.getRadians());
    }

    public int getBuildCost() {
        return buildCost;
    }

    public void setBuildCost(int buildCost) {
        this.buildCost = buildCost;
    }
}
