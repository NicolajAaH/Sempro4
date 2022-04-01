package dk.sdu.mmmi.cbse.tower;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.Types;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.LifePart;
import dk.sdu.mmmi.cbse.common.data.entityparts.MovingPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;
import dk.sdu.mmmi.cbse.commontower.Tower;

public class TowerPlugin implements IGamePluginService {

    private Entity tower;

    @Override
    public void start(GameData gameData, World world) {
    }

    @Override
    public void stop(GameData gameData, World world) {
        // Remove entities
        world.removeEntity(this.tower);
    }

    @Override
    public Entity create(SpriteBatch batch, GameData gameData, World world, Texture texture) {
        float deacceleration = 0;
        float acceleration = 0;
        float speed = 0;
        float rotationSpeed = 5;
        // TODO: get exact placement of x&y = center of tile
        float x = 50;
        float y = 50;
        float radians = 3.1415f / 2;

        Sprite sprite = new Sprite(texture);
        Entity tower = new Tower(sprite, Types.TOWER); //throws exception nulpointer
        tower.add(new MovingPart(deacceleration, acceleration, speed, rotationSpeed));
        tower.add(new PositionPart(x, y, radians));
        tower.add(new LifePart(1));
        world.addEntity(tower);

        return tower;
    }

    @Override
    public Types getType() {
        return Types.TOWER;
    }
}
