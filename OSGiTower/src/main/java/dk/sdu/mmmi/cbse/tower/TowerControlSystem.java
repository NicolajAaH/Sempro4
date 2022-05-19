package dk.sdu.mmmi.cbse.tower;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
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
import java.util.*;
import java.util.List;

public class TowerControlSystem implements IEntityProcessingService, TowerSPI {
    private ProjectileSPI projectileLauncher;
    private IMap map;
    private Random r = new Random();

    private Tower selectedTower;

    // weight of heuristics
    int weightDistanceToEnd = 6;
    int weightLife = 10;
    int weightDistanceToStart = -1;
    int weightDistanceToTower = -1;

    @Override
    public void process(GameData gameData, World world) {

        // iterating through all towers
        for (Entity tower : world.getEntities(Tower.class)) {
            selectedTower = (Tower) tower;
            PositionPart positionPart = tower.getPart(PositionPart.class);
            WeaponPart weaponPart = tower.getPart(WeaponPart.class);

            List<Entity> enemies = world.getEntities(Enemy.class);

            // checking if there is reachable enemies and placing them in queue (minHeap)
            PriorityQueue<Enemy> reachableEnemies = new PriorityQueue<>(10, new enemyComparator());

            if (enemies != null) {
                for (Entity enemy : enemies) {
                    int distance = getDistanceBetweenEntities(enemy, tower);
                    if (distance < weaponPart.getRange()) {
                        reachableEnemies.add((Enemy) enemy);
                    }
                }
            }
            // if no enemies is within range - rotate tower
            if (reachableEnemies.size() == 0) {
                int shouldRotate = r.nextInt(100);
                if (shouldRotate < 20) {
                    int radians = positionPart.getAngle();
                    radians += 1;
                    if (360 < radians) {
                        radians = 0;
                    }
                    positionPart.setAngle(radians);
                }
            }

            if (reachableEnemies.size() > 0) {
                // Target the enemy with lowest heuristics (top of queue)
                Enemy selectedEnemy = reachableEnemies.peek();

                // use print for adjusting weights
                // printHeuristics(selectedEnemy, selectedTower);

                // create projectile
                if (projectileLauncher != null) {
                    // set angle so tower points towards target
                    positionPart.setAngle((getAngleBetweenEntities(tower, selectedEnemy) + 180) % 360);
                    int shouldShoot = r.nextInt(50);
                    if (shouldShoot < 1) {
                        projectileLauncher.createProjectile(tower, gameData, world);
                    }
                }
            }
        }
    }

    private void printHeuristics(Enemy selectedEnemy, Tower tower) {
        System.out.println("Heuristcs total: " + getHeuristic(selectedEnemy, tower));
        System.out.println(" -- composed of -- ");
        System.out.println("distance to end " + weightDistanceToEnd * getDistanceToEnd(selectedEnemy));
        System.out.println("distance to start" + weightDistanceToStart * getDistanceToStart(selectedEnemy));
        LifePart enemyLifePart = selectedEnemy.getPart(LifePart.class);
        System.out.println("life of enemy" + weightLife * enemyLifePart.getLife());
        System.out.println("distance to tower " + weightDistanceToTower * getDistanceBetweenEntities(selectedEnemy, tower));
        System.out.println();
    }

    class enemyComparator implements Comparator<Enemy> {
        /**
         * Compares two enemies by their heuristics
         *
         * @param enemy1 the first object to be compared.
         * @param enemy2 the second object to be compared.
         * @return negative if enemy1 has lowest heurisitcs, positive if enemy2 has lowest
         */
        @Override
        public int compare(Enemy enemy1, Enemy enemy2) {
            float heuristicEnemy1 = getHeuristic(enemy1, (Tower) selectedTower);
            float heuristicEnemy2 = getHeuristic(enemy2, (Tower) selectedTower);
            return (int) (heuristicEnemy1 - heuristicEnemy2);
        }
    }

    private float getHeuristic(Enemy enemy, Tower tower) {
        // calculate heuristics of an enemy
        LifePart enemyLifePart = enemy.getPart(LifePart.class);
        float enemyHeuristic = 0;
        enemyHeuristic += weightDistanceToEnd * getDistanceToEnd(enemy);
        enemyHeuristic += weightDistanceToStart * getDistanceToStart(enemy);
        enemyHeuristic += weightDistanceToTower * getDistanceBetweenEntities(enemy, tower);
        enemyHeuristic += weightLife * enemyLifePart.getLife();
        return enemyHeuristic;
    }

    // HELPER METHODS FOR HEURISTICS
    private float getDistanceToEnd(Entity enemy) {
        Point endTileCoordinat = map.getEndTileCoor();
        Point endMapCoordinates = map.tileCoorToMapCoor((float) endTileCoordinat.x, (float) endTileCoordinat.y);
        PositionPart positionPart = enemy.getPart(PositionPart.class);
        float deltaY = positionPart.getY() - (float) endMapCoordinates.getY();
        float deltaX = positionPart.getX() - (float) endMapCoordinates.getX();
        return (float) Math.sqrt(((deltaX * deltaX) + (deltaY * deltaY)));
    }

    private float getDistanceToStart(Entity enemy) {
        Point startTileCoordinat = map.getStartTileCoor();
        Point endMapCoordinates = map.tileCoorToMapCoor((float) startTileCoordinat.x, (float) startTileCoordinat.y);
        PositionPart positionPart = enemy.getPart(PositionPart.class);
        float deltaY = positionPart.getY() - (float) endMapCoordinates.getY();
        float deltaX = positionPart.getX() - (float) endMapCoordinates.getX();
        return (float) Math.sqrt(((deltaX * deltaX) + (deltaY * deltaY)));
    }

    private int getDistanceBetweenEntities(Entity entity1, Entity entity2) {
        PositionPart positionPart1 = entity1.getPart(PositionPart.class);
        PositionPart positionPart2 = entity2.getPart(PositionPart.class);
        float deltaY = positionPart1.getY() - positionPart2.getY();
        float deltaX = positionPart1.getX() - positionPart2.getX();
        return (int) Math.sqrt(((deltaX * deltaX) + (deltaY * deltaY)));
    }

    private int getAngleBetweenEntities(Entity entity1, Entity entity2) {
        // returning angle in degrees
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
    public Entity createTower(GameData gameData, World world, int xTile, int yTile) {

        // Checking tileProperties if tower can be created
        if (!map.getTileType(xTile, yTile).equals("Grass")) {
            gameData.setScreenMessage("You can only place \nTowers on grass");
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

        tower.add(new MovingPart(speed));
        tower.add(new PositionPart(x, y, 0));
        tower.setRadius(20);
        tower.add(new WeaponPart(200, 6));
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

    public void removeIMap(IMap map) {
        this.map = null;
    }

    public Random getR() {
        return r;
    }

    public void setR(Random r) {
        this.r = r;
    }
}