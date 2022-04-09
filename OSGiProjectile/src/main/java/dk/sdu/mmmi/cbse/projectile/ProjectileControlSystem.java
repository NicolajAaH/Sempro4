package dk.sdu.mmmi.cbse.projectile;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.Types;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.LifePart;
import dk.sdu.mmmi.cbse.common.data.entityparts.MovingPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.TimerPart;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import dk.sdu.mmmi.cbse.commonprojectile.Projectile;
import dk.sdu.mmmi.cbse.commonprojectile.ProjectileSPI;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

public class ProjectileControlSystem implements IEntityProcessingService, ProjectileSPI {

    @Override
    public void process(GameData gameData, World world) {
        for (Entity projectile : world.getEntities(Projectile.class)) {

            PositionPart positionPart = projectile.getPart(PositionPart.class);
            MovingPart movingPart = projectile.getPart(MovingPart.class);
            //TimerPart timerPart = projectile.getPart(TimerPart.class);

            movingPart.setUp(true);
            /*
            if (timerPart.getExpiration() < 0) {
                world.removeEntity(projectile);
            }
             */

            // timerPart.process(gameData, projectile);
            movingPart.process(gameData, projectile);
            positionPart.process(gameData, projectile);
        }
    }

    @Override
    public void draw(SpriteBatch batch, World world) {
        for (Entity projectile : world.getEntities(Projectile.class)) {
            update(projectile);
            batch.begin();
            projectile.draw(batch);
            batch.end();
        }
    }

    public void update(Entity entity){
        // setting position of Sprite from position part
        PositionPart positionPart = entity.getPart(PositionPart.class);
        entity.setPosition(positionPart.getX(), positionPart.getY());
        entity.setRotation(positionPart.getRadians());
    }

    @Override
    public void createProjectile(Entity shooter, GameData gameData, World world) {
        System.out.println("create projectile in Projectile control system");

        PositionPart shooterPos = shooter.getPart(PositionPart.class);
        MovingPart shooterMovingPart = shooter.getPart(MovingPart.class);

        // shooter entity parameteres
        float x = shooterPos.getX();
        float y = shooterPos.getY();
        float radians = shooterPos.getRadians();
        float dt = gameData.getDelta();

        // parameters of projectile
        float deacceleration = 10;
        float acceleration = 20000000;
        float speed = 50;
        float rotationSpeed = 5;
        float projX = x- 50;
        float projY = y- 50;

        // getting sprite for projectile
        Texture texture = world.getTextureHashMap().get(Types.PROJECTILE);
        Sprite sprite = new Sprite(texture);

        // creating new projectile
        Entity projectile = new Projectile(sprite, Types.PROJECTILE);
        projectile.add(new MovingPart(deacceleration, acceleration, speed, rotationSpeed));
        projectile.add(new PositionPart(projX, projY, radians));
        projectile.add(new LifePart(1));
        world.addEntity(projectile);
    }
}
