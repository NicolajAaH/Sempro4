package dk.sdu.mmmi.cbse.commonenemy;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.Types;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;

public class Enemy extends Entity {

    public Enemy(Sprite sprite, Types type) {
        super(sprite, type);
    }

    @Override
    public void draw(Batch batch) {
        batch.begin();
        update(Gdx.graphics.getDeltaTime());
        super.draw(batch);
        batch.end();
    }

    public void update(float delta) {
        PositionPart positionPart = this.getPart(PositionPart.class);
        this.setPosition(positionPart.getX(), positionPart.getY());
        this.setRotation((positionPart.getAngle() + 270) % 360);
    }
}
