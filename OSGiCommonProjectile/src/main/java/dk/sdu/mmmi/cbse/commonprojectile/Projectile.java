package dk.sdu.mmmi.cbse.commonprojectile;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.Types;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;


public class Projectile extends Entity {

    public Projectile(Sprite sprite, Types type){
        super(sprite);
        this.type = type;
    }
}
