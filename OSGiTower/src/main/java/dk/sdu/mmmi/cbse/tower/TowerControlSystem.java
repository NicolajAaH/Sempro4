package dk.sdu.mmmi.cbse.tower;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.Types;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.LifePart;
import dk.sdu.mmmi.cbse.common.data.entityparts.MovingPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.WeaponPart;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import dk.sdu.mmmi.cbse.commonenemy.Enemy;
import dk.sdu.mmmi.cbse.commonprojectile.ProjectileSPI;
import dk.sdu.mmmi.cbse.commontower.Tower;
import dk.sdu.mmmi.cbse.commontower.TowerSPI;
import dk.sdu.mmmi.cbse.commonmap.IMap;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.List;

public class TowerControlSystem implements IEntityProcessingService, TowerSPI {

    private ProjectileSPI projectileLauncher;
    private IMap map;
    private Random r = new Random();
    @Override
    public void process(GameData gameData, World world) {

        // iterating through all towers
        for (Entity tower : world.getEntities(Tower.class)) {
            PositionPart positionPart = tower.getPart(PositionPart.class);
            WeaponPart weaponPart = tower.getPart(WeaponPart.class);

            // creating list of enemies within range of tower
            List<Entity> reachableEnemies = new ArrayList<>();
            List<Entity> enemies = world.getEntities(Enemy.class);

            if (enemies != null) {
                for (Entity enemy : enemies) {
                   int distance = getDistanceBetweenEntities(enemy, tower);
                   if (distance < weaponPart.getRange()){
                      reachableEnemies.add(enemy);
                  }
                }
            }

            // if no enemies is within range - rotate tower
            if (reachableEnemies.size() == 0) {
                int shouldRotate = r.nextInt(100);
                if (shouldRotate < 20) {
                    int radians = positionPart.getRadians();
                    radians +=1;
                    if (360 < radians) {
                        radians = 0;
                    }
                    positionPart.setRadians(radians);
                }
            } else {
                // SELECT ENEMY TO TARGET
                Entity selectedEnemy = null;

                // iterating reachable enemies
                for (Entity enemy : reachableEnemies) {
                    //System.out.println("distance to end enemy 1" + getDistanceToEnd(reachableEnemies.get(0)));

                    // selecting the closest enemy
                    int min_distance = 1000000;
                    int distance = getDistanceBetweenEntities(enemy, tower);
                    if (distance < min_distance) {
                        selectedEnemy = enemy;
                    }
                }


                // mindste life, (tættest på mål, længst væk fra start), tættest på tårn.

                // shoot
                // check if projectile launceher is null!
                positionPart.setRadians((getAngleBetweenEntities(tower, selectedEnemy) + 180) % 360);
                int shouldShoot = r.nextInt(50);
                if (shouldShoot < 1) {
                    //projectileLauncher.createProjectile(tower, gameData, world);
                }
            }

            // TODO: SET DIRECTION OF SHOOTING- AI!
        }
    }



    // TODO: evt refactor til Entity? getAngleToPoint

    private float closestToGoalHeuristic(){
        return 0;
    }

    private float getDistanceToEnd(Entity enemy) {
        Point endTileCoordinat = map.getEndTileCoor();
        Point endMapCoordinates = map.tileCoorToMapCoor((float) endTileCoordinat.x, (float) endTileCoordinat.y);
        PositionPart positionPart = enemy.getPart(PositionPart.class);
        float deltaY = positionPart.getY() - (float) endMapCoordinates.getY();
        float deltaX = positionPart.getX() - (float) endMapCoordinates.getX();
        return (float) Math.sqrt( ((deltaX * deltaX) + (deltaY * deltaY)));
    }

    private float getDistanceToStart(Entity enemy) {
        Point startTileCoordinat = map.getStartTileCoor();
        Point endMapCoordinates = map.tileCoorToMapCoor((float) startTileCoordinat.x, (float) startTileCoordinat.y);
        PositionPart positionPart = enemy.getPart(PositionPart.class);
        float deltaY = positionPart.getY() - (float) endMapCoordinates.getY();
        float deltaX = positionPart.getX() - (float) endMapCoordinates.getX();
        return (float) Math.sqrt( ((deltaX * deltaX) + (deltaY * deltaY)));
    }
    private int getDistanceBetweenEntities(Entity entity1, Entity entity2){
        PositionPart positionPart1 = entity1.getPart(PositionPart.class);
        PositionPart positionPart2 = entity2.getPart(PositionPart.class);
        float deltaY = positionPart1.getY() - positionPart2.getY();
        float deltaX = positionPart1.getX() - positionPart2.getX();
        return (int) Math.sqrt( ((deltaX * deltaX) + (deltaY * deltaY)));
    }

    // returning angle in degrees
    private int getAngleBetweenEntities(Entity entity1, Entity entity2) {
        PositionPart positionPart1 = entity1.getPart(PositionPart.class);
        PositionPart positionPart2 = entity2.getPart(PositionPart.class);
        float deltaY = positionPart1.getY() - positionPart2.getY();
        float deltaX = positionPart1.getX() - positionPart2.getX();
        // calculating angle
        float angle = (float) Math.atan2(deltaY, deltaX);
        return (int) Math.toDegrees(angle);
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
        float speed = 0;
        float rotationSpeed = 5;

        Point coordinate = map.tileCoorToMapCoor(xTile, yTile);
        float x = (float) coordinate.x - map.getTileSize() / 2;
        float y = (float) coordinate.y - map.getTileSize() / 2;


        tower.add(new MovingPart(speed, rotationSpeed));
        tower.add(new PositionPart(x, y, 0));
        tower.setRadius(20);
        tower.add(new WeaponPart(200, 5, 10));
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