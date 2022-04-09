package dk.sdu.mmmi.cbse.commonprojectile;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.Types;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;

import java.util.Random;

public class Projectile extends Entity {

    public Projectile(Sprite sprite, Types type){
        super(sprite);
        this.type = type;
    }

    /*
    @Override
    public void draw(Batch batch) {
        batch.begin();
        update(Gdx.graphics.getDeltaTime());
        super.draw(batch);
        batch.end();
    }

     */

    public void update(float delta){
        // setting position of Sprite from position part
        PositionPart positionPart = this.getPart(PositionPart.class);
        this.setPosition(positionPart.getX(), positionPart.getY());
        this.setRotation(positionPart.getRadians());
    }
}
