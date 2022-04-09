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
            TimerPart timerPart = projectile.getPart(TimerPart.class);

            if (timerPart.getExpiration() < 0) {
                world.removeEntity(projectile);
            }

            timerPart.process(gameData, projectile);
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
        PositionPart positionPart = entity.getPart(PositionPart.class);
        // setting posistoin of sprite
        entity.setPosition(positionPart.getX(), positionPart.getY());
        // setting rotation of sprite
        entity.setRotation(positionPart.getRadians());
    }

    @Override
    public void createProjectile(Entity shooter, GameData gameData, World world) {

        PositionPart shooterPos = shooter.getPart(PositionPart.class);

        // shooter entity parameteres
        float x = shooterPos.getX();
        float y = shooterPos.getY();
        float radians = shooterPos.getRadians();
        //radians = (float) Math.PI / 2;
        float dt = gameData.getDelta();

        // parameters of projectile
        float deacceleration = 0;
        float acceleration = 0;
        float speed = 5;
        float rotationSpeed = 0;

        // calculating offset from shooter
        float bx = (float) cos(radians) * 20;
        float by = (float) sin(radians) * 20;
        float projX = x + bx + 26;
        float projY = y + by + 23;

        // getting sprite for projectile
        Texture texture = world.getTextureHashMap().get(Types.PROJECTILE);
        Sprite sprite = new Sprite(texture);

        MovingPart projMovingPart = new MovingPart(deacceleration, acceleration, speed, rotationSpeed);
        projMovingPart.setIsProjectile(true);

        // creating new projectile with entity parts and add to world
        Entity projectile = new Projectile(sprite, Types.PROJECTILE);
        projectile.add(projMovingPart);
        projectile.add(new PositionPart(projX, projY, radians));
        projectile.add(new LifePart(1));
        projectile.add(new TimerPart(1));
        world.addEntity(projectile);
    }
}
