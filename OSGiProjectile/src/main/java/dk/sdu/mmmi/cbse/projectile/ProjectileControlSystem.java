package dk.sdu.mmmi.cbse.projectile;

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

            movingPart.setUp(true);
            if (timerPart.getExpiration() < 0) {
                world.removeEntity(projectile);
            }

            timerPart.process(gameData, projectile);
            movingPart.process(gameData, projectile);
            positionPart.process(gameData, projectile);
        }
    }

    public void draw(SpriteBatch batch, World world){
        for (Entity projectile : world.getEntities(Projectile.class)) {
            projectile.draw(batch);
        }
    }

    @Override
    public void createProjectile(Entity shooter, GameData gameData, World world) {

        PositionPart shooterPos = shooter.getPart(PositionPart.class);
        MovingPart shooterMovingPart = shooter.getPart(MovingPart.class);

        float x = shooterPos.getX();
        float y = shooterPos.getY();
        float radians = shooterPos.getRadians();
        float dt = gameData.getDelta();
        float speed = 350;

        Entity projectile = new Projectile(new Sprite (world.getTextureHashMap().get(Types.PROJECTILE)), Types.PROJECTILE);
        projectile.setRadius(2);

        // getting direction of projectile
        float bx = (float) cos(radians) * shooter.getRadius() * projectile.getRadius();
        float by = (float) sin(radians) * shooter.getRadius() * projectile.getRadius();

        projectile.add(new PositionPart(bx + x, by + y, radians));
        projectile.add(new LifePart(1));
        projectile.add(new MovingPart(0, 5000000, speed, 5));
        projectile.add(new TimerPart(1));

    }
}
