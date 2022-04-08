package dk.sdu.mmmi.cbse.commontower;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.Types;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;

import java.util.Random;

public class Tower extends Entity {

    private int range;

    public Tower(Sprite sprite, Types type) {
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

        float radians = positionPart.getRadians();

        Random r = new Random();

        // should rotate
        int shouldRotate = r.nextInt(100);
        if (shouldRotate < 20) {
            radians +=1;
        }

        positionPart.setRadians(radians);

        this.setRotation(positionPart.getRadians());

    }

    public void setRange(int range) {
        this.range = range;
    }

    public int getRange(){
        return range;
    }
}
