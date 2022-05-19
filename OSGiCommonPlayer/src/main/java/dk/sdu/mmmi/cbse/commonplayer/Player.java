package dk.sdu.mmmi.cbse.commonplayer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.Types;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;

public class Player extends Entity {
    public Player(Sprite sprite, Types type) {
        super(sprite);
        this.type = type;
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
