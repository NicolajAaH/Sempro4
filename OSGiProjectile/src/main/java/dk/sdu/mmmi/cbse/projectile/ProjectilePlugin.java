package dk.sdu.mmmi.cbse.projectile;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.Types;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.LifePart;
import dk.sdu.mmmi.cbse.common.data.entityparts.MovingPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;
import dk.sdu.mmmi.cbse.commonprojectile.Projectile;


public class ProjectilePlugin implements IGamePluginService {
    public ProjectilePlugin() {
    }

    @Override
    public void start(GameData gameData, World world) {

    }

    @Override
    public void stop(GameData gameData, World world) {
        // Remove entities
        for (Entity entity : world.getEntities(Projectile.class)) {
            world.removeEntity(entity);
        }
    }

    @Override
    public Entity create(GameData gameData, World world) {
        float deacceleration = 10;
        float acceleration = 20000000;
        float speed = 50;
        float rotationSpeed = 5;
        float x = gameData.getDisplayWidth() / 2;
        float y = gameData.getDisplayHeight() / 2;
        float radians = 3.1415f / 2;

        Texture texture = world.getTextureHashMap().get(Types.PROJECTILE);
        Sprite sprite = new Sprite(texture);
        Entity projectile = new Projectile(sprite, Types.PROJECTILE);
        projectile.add(new MovingPart(deacceleration, acceleration, speed, rotationSpeed));
        projectile.add(new PositionPart(x, y, radians));
        projectile.add(new LifePart(1));
        world.addEntity(projectile);
        return projectile;
    }

    @Override
    public Types getType() {
        return Types.PROJECTILE;
    }
}
