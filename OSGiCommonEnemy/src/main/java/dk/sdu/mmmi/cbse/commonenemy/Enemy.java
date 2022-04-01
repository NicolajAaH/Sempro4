package dk.sdu.mmmi.cbse.commonenemy;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.Types;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;

public class Enemy extends Entity {
    private float speed = 60*2;

    public Enemy(Sprite sprite, Types type) {
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

    public void update(float delta){
        PositionPart positionPart = this.getPart(PositionPart.class);
        this.setPosition(positionPart.getX(), positionPart.getY());
        if (positionPart.getLastChange() == "Up") this.setRotation(0);
        if (positionPart.getLastChange() == "Down") this.setRotation(180);
        if (positionPart.getLastChange() == "Left") this.setRotation(90);
        if (positionPart.getLastChange() == "Right") this.setRotation(270);
    }
}
