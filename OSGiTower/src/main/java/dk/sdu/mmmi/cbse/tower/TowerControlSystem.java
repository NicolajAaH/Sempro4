package dk.sdu.mmmi.cbse.tower;

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
import dk.sdu.mmmi.cbse.common.data.entityparts.WeaponPart;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import dk.sdu.mmmi.cbse.commonplayer.Player;
import dk.sdu.mmmi.cbse.commonprojectile.ProjectileSPI;
import dk.sdu.mmmi.cbse.commontower.Tower;
import dk.sdu.mmmi.cbse.commontower.TowerSPI;
import dk.sdu.mmmi.cbse.commonmap.IMap;

import java.awt.*;
import java.util.Random;

public class TowerControlSystem implements IEntityProcessingService, TowerSPI {

    private ProjectileSPI projectileLauncher;
    private IMap map;

    @Override
    public void process(GameData gameData, World world) {

        for (Entity tower : world.getEntities(Tower.class)) {
            PositionPart positionPart = tower.getPart(PositionPart.class);

            Random r = new Random();

            // random shooting
            int shouldShoot = r.nextInt(100);
            if (shouldShoot < 1) {
                projectileLauncher.createProjectile(tower, gameData, world);
            }

            // random rotation
            int shouldRotate = r.nextInt(100);
            if (shouldRotate < 20) {
                Integer radians = positionPart.getRadians();
                radians +=1;
                if (360 < radians) {
                    radians = 0;
                }
                positionPart.setRadians(radians);
            }

            //experiment with shooting towards an entity

            // getting position of player
            Entity player = world.getEntities(Player.class).get(0);
            positionPart.setRadians((int) (getAngleBetweenEntities(player, tower)));

            // TODO: SET DIRECTION OF SHOOTING- AI!
        }
    }
    
    // returning angle in degrees
    private float getAngleBetweenEntities(Entity entity1, Entity entity2) {
        PositionPart positionPart1 = entity1.getPart(PositionPart.class);
        PositionPart positionPart2 = entity2.getPart(PositionPart.class);
        float deltaY = positionPart1.getY() - positionPart2.getY();
        float deltaX = positionPart1.getX() - positionPart2.getX();
        // calculating angle
        float angle = (float) Math.atan2(deltaY, deltaX);
        return (float) Math.toDegrees(angle);
    }

    @Override
    public void draw(SpriteBatch batch, World world) {
       for (Entity tower : world.getEntities(Tower.class)) {
           tower.draw(batch);
       }
    }

    @Override
    public Entity createTower(World world, int xTile, int yTile) {

        // Checking tileProperties if tower can be created
        if (!map.getTileType(xTile, yTile).equals("Grass")) {
            // System.out.println("can not place tower here");
            return null;
        }

        // creating a tower entity
        Sprite sprite = new Sprite(world.getTextureHashMap().get(Types.TOWER));
        Entity tower = new Tower(sprite, Types.TOWER); //throws exception nulpointer

        // Replacing af tile on the map at pos (x,y) with tower tile.
        map.changeTileType(xTile, yTile, "Tower");

        // setting variables
        float deacceleration = 0;
        float acceleration = 0;
        float speed = 0;
        float rotationSpeed = 5;

        Point coordinate = map.tileCoorToMapCoor(xTile, yTile);
        float x = (float) coordinate.x - map.getTileSize() / 2;
        float y = (float) coordinate.y - map.getTileSize() / 2;
        float radians = 3.1415f / 2;

        tower.add(new MovingPart(deacceleration, acceleration, speed, rotationSpeed));
        tower.add(new PositionPart(x, y, radians));
        tower.add(new LifePart(1));
        tower.setRadius(20);
        tower.add(new WeaponPart(300, 5, 10));
        return tower;
    }

    public void setProjectileSPI(ProjectileSPI projectileSPI) {
        this.projectileLauncher = projectileSPI;
    }

    public void removeProjectileSPI(ProjectileSPI projectileSPI) {
        this.projectileLauncher = null;
    }

    public void setIMap(IMap map) {
        this.map = map;
    }

    public void removeIMap(IMap map){
        this.map = null;
    }
}