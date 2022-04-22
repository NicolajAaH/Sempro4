package dk.sdu.mmmi.cbse.projectile;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.Types;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.*;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import dk.sdu.mmmi.cbse.commonmap.IMap;
import dk.sdu.mmmi.cbse.commonprojectile.Projectile;
import dk.sdu.mmmi.cbse.commonprojectile.ProjectileSPI;

import static java.lang.Math.*;

public class ProjectileControlSystem implements IEntityProcessingService, ProjectileSPI {

    private IMap map;

    @Override
    public void process(GameData gameData, World world) {
        for (Entity projectile : world.getEntities(Projectile.class)) {

            PositionPart positionPart = projectile.getPart(PositionPart.class);
            MovingPart movingPart = projectile.getPart(MovingPart.class);
            WeaponPart weaponPart = projectile.getPart(WeaponPart.class);

            movingPart.process(gameData, projectile);
            positionPart.process(gameData, projectile);

            // checks if it has reached it's range
            if (positionPart.getDistanceFromOrigin() > weaponPart.getRange()){
                world.removeEntity(projectile);
            }
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
        // setting position of sprite
        entity.setPosition(positionPart.getX(), positionPart.getY());
        // setting rotation of sprite TODO: rotate image 90' degress and delete +90
        entity.setRotation(positionPart.getRadians()+90);
    }

    @Override
    public void createProjectile(Entity shooter, GameData gameData, World world) {

        PositionPart shooterPos = shooter.getPart(PositionPart.class);
        WeaponPart weaponPart = shooter.getPart(WeaponPart.class);

        // shooter entity parameteres
        float x = shooterPos.getX();
        float y = shooterPos.getY();
        float radians = shooterPos.getRadians();
        float dt = gameData.getDelta();

        // parameters of projectile
        float deacceleration = 0;
        float acceleration = 0;
        float speed = 5;
        float rotationSpeed = 0;


        // calculating offset from shooter (start coordinates of projectile)
        float bx = (float) cos(toRadians(radians)) * shooter.getRadius();
        float by = (float) sin(toRadians(radians)) * shooter.getRadius();
        float tileSize = (float) map.getTileSize();
        float projX = (x + tileSize / 2) + bx;
        float projY = (y-4 + tileSize / 2) + by;

        // getting sprite for projectile
        Texture texture = world.getTextureHashMap().get(Types.PROJECTILE);
        Sprite sprite = new Sprite(texture);

        // creating new projectile with entity parts and add to world
        Entity projectile = new Projectile(sprite, Types.PROJECTILE);
        MovingPart projMovingPart = new MovingPart(deacceleration, acceleration, speed, rotationSpeed);
        projMovingPart.setIsProjectile(true);
        projectile.add(projMovingPart);
        projectile.add(new PositionPart(projX, projY, radians));
        projectile.add(new LifePart(1));
        projectile.add(new TimerPart(1));
        projectile.add(weaponPart);
        world.addEntity(projectile);
    }

    public void setIMap(IMap map) {
        this.map = map;
    }

    public void removeIMap(IMap map){
        this.map = null;
    }
}
