package dk.sdu.mmmi.cbse.enemy;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import dk.sdu.mmmi.cbse.common.data.*;
import dk.sdu.mmmi.cbse.common.data.Enums.Direction;
import dk.sdu.mmmi.cbse.common.data.entityparts.*;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import dk.sdu.mmmi.cbse.commonenemy.Enemy;
import dk.sdu.mmmi.cbse.commonmap.IMap;
import org.lwjgl.Sys;

import java.awt.*;
import java.util.*;
import java.util.List;

public class EnemyControlSystem implements IEntityProcessingService {

    private IMap map;
    @Override
    public void process(GameData gameData, World world) {
        List<Attack> currentAttacks = gameData.getCurrentAttacks();

        for (Attack attack : currentAttacks) {
            if(attack.getAttackNumber() > 0) {
                createEnemy(gameData, world);
                attack.setAttackNumber(attack.getAttackNumber() - 1);
                attack.setAttackTimeMs(attack.getAttackTimeMs() + 500);
            }else {
                gameData.removeAttack(attack);
            }
        }

        List<Entity> enemies = world.getEntities(Enemy.class);

        Collections.shuffle(enemies);

        for (Entity enemy : enemies) {
            MovingPart movingPart = enemy.getPart(MovingPart.class);
            LifePart lifePart = enemy.getPart(LifePart.class);

            movingPart.process(gameData, enemy);

            if (lifePart.getLife() == 0) {
                world.removeEntity(enemy);
                return;
            }
        }

        for (Entity enemy : enemies) {

            PathPart pathPart = enemy.getPart(PathPart.class);
            PositionPart positionPart = enemy.getPart(PositionPart.class);


            if(positionPart.getRadians() == MovingPart.left && positionPart.getX() > pathPart.getxGoal()) return;
            if(positionPart.getRadians() == MovingPart.right && positionPart.getX() < pathPart.getxGoal()) return;
            if(positionPart.getRadians() == MovingPart.down && positionPart.getY() > pathPart.getyGoal()) return;
            if(positionPart.getRadians() == MovingPart.up && positionPart.getY() < pathPart.getyGoal()) return;

            setNewEnemyPath(enemy);

        }
    }

    private void setNewEnemyPath(Entity enemy) {

        PathPart pathPart = enemy.getPart(PathPart.class);
        PositionPart positionPart = enemy.getPart(PositionPart.class);

        TiledMapTileLayer layer = (TiledMapTileLayer) map.getTiledMap().getLayers().get(0);
        int pathX = pathPart.getxGoal();
        int pathY = pathPart.getyGoal();
        int pathXPlus = (int) (positionPart.getX() + layer.getTileWidth());
        int pathXMinus = (int) (positionPart.getX() - layer.getTileWidth());
        int pathYPlus = (int) (positionPart.getY() + layer.getTileWidth());
        int pathYMinus = (int) (positionPart.getY() - layer.getTileWidth());

        if(setNewGoal(pathX, pathYMinus,enemy,MovingPart.down)) return;
        if(setNewGoal(pathXPlus, pathY,enemy,MovingPart.right)) return;
        if(setNewGoal(pathXMinus, pathY,enemy,MovingPart.left)) return;
        if(setNewGoal(pathX, pathYPlus,enemy, MovingPart.up)) return;
    }

    private boolean setNewGoal(int x, int y, Entity enemy, int direction){

        PathPart pathPart = enemy.getPart(PathPart.class);
        PositionPart positionPart = enemy.getPart(PositionPart.class);
        LifePart lifePart = enemy.getPart(LifePart.class);

        Point point = map.getTileCoordinates(x,y);
        String tile = map.getTileType(point.x, point.y);

        if(tile == null || tile.equals("End")) {
            lifePart.setLife(0);
            return false;
        }

        if(!tile.equals("Path")) return false;
        if(pathPart.isExplored(point)) return false;

        pathPart.addPosition(point);
        pathPart.setxGoal(x);
        pathPart.setyGoal(y);

        positionPart.setRadians(direction);

        System.out.println("GOAL: " + x + " " + y + " " + direction + " " + tile);
        return true;

    }

    private void createEnemy(GameData gameData, World world){
        ArrayList<TiledMapTileLayer.Cell> tiles = map.getTilesOfType("Start");

        TiledMapTile tile = tiles.get(0).getTile();

        TiledMapTileLayer layer = (TiledMapTileLayer) map.getTiledMap().getLayers().get(0);

        float speed = 1;
        float x = 600;
                //tile.getOffsetX() + layer.getTileWidth() / 2;
        float y = 525;
                //tile.getOffsetX() + layer.getTileHeight() / 2;
        Integer radians = MovingPart.left;

        PathPart path = new PathPart();
        path.setxGoal(500);
        path.setyGoal(525);
        path.addPosition(map.getTileCoordinates(x,y));

        Sprite sprite = new Sprite(world.getTextureHashMap().get(Types.ENEMY));
        Entity enemy = new Enemy(sprite, Types.ENEMY);
        enemy.add(new MovingPart( speed, 0));
        enemy.add(new PositionPart(x, y, radians));
        enemy.add(new LifePart(1));
        enemy.add(path);
        world.addEntity(enemy);
    }

    @Override
    public void draw(SpriteBatch spriteBatch, World world) {
        for (Entity enemy : world.getEntities(Enemy.class)) {
            enemy.draw(spriteBatch);
        }
    }

    public void setIMap(IMap map) {
        this.map = map;
    }

    public void removeIMap(IMap map){
        this.map = null;
    }
}
