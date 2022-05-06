package dk.sdu.mmmi.cbse.enemy;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import dk.sdu.mmmi.cbse.common.data.*;
import dk.sdu.mmmi.cbse.common.data.entityparts.LifePart;
import dk.sdu.mmmi.cbse.common.data.entityparts.MovingPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.PathPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import dk.sdu.mmmi.cbse.commonenemy.Enemy;
import dk.sdu.mmmi.cbse.commonmap.IMap;

import java.awt.*;
import java.util.List;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class EnemyControlSystem implements IEntityProcessingService {

    ThreadPoolExecutor executor =
            (ThreadPoolExecutor) Executors.newFixedThreadPool(3);
    private IMap map;
    @Override
    public void process(GameData gameData, World world) {
        List<Attack> currentAttacks = gameData.getCurrentAttacks();


        for (Attack attack : currentAttacks) {
            if (attack.getAttackNumber() > 0) {
                createEnemy(gameData, world);
                attack.setAttackNumber(attack.getAttackNumber() - 1);
                attack.setAttackTimeMs(attack.getAttackTimeMs() + 500);
                gameData.addMoney(1);
            } else {
                gameData.removeAttack(attack);
            }
        }


        List<Entity> enemies = world.getEntities(Enemy.class);

        Collections.shuffle(enemies);

        for (Entity enemy : enemies) {
            MovingPart movingPart = enemy.getPart(MovingPart.class);
            LifePart lifePart = enemy.getPart(LifePart.class);

            movingPart.process(gameData, enemy);

        }


        for (Entity enemy : enemies) {
            executor.submit(() -> {
                PathPart pathPart = enemy.getPart(PathPart.class);
                PositionPart positionPart = enemy.getPart(PositionPart.class);

                if(positionPart.getRadians() == PositionPart.left && positionPart.getX() > pathPart.getxGoal()) return;
                if(positionPart.getRadians() == PositionPart.right && positionPart.getX() < pathPart.getxGoal()) return;
                if(positionPart.getRadians() == PositionPart.down && positionPart.getY() > pathPart.getyGoal()) return;
                if(positionPart.getRadians() == PositionPart.up && positionPart.getY() < pathPart.getyGoal()) return;

                setNewEnemyPathTemp(enemy, gameData);
            });
        }
    }

    private void setNewEnemyPathTemp(Entity enemy, GameData gameData){
        PathPart pathPart = enemy.getPart(PathPart.class);
        PositionPart positionPart = enemy.getPart(PositionPart.class);
        LifePart lifePart = enemy.getPart(LifePart.class);
        //MovingPart movingPart = enemy.getPart(MovingPart.class);

        Point currentTile = pathPart.getCurrentTile();

        String currentTileType = map.getTileType(currentTile.x, currentTile.y);

        if(currentTileType != null && currentTileType.equals("End")){
            lifePart.setLife(0);
            gameData.setLife(gameData.getLife()-1);
            return;
        }

        if(setNewGoal(pathPart, positionPart, currentTile, PositionPart.up)) return;
        if(setNewGoal(pathPart, positionPart, currentTile, PositionPart.down)) return;
        if(setNewGoal(pathPart, positionPart, currentTile, PositionPart.left)) return;
        if(setNewGoal(pathPart, positionPart, currentTile, PositionPart.right)) return;


    }

    private boolean setNewGoal(PathPart pathPart, PositionPart positionPart, Point currentTile, int direction){

        String newTileType;
        String path = "Path";
        Point newTile = new Point(currentTile.x, currentTile.y);

        if(direction == PositionPart.down) {
            newTile.y = newTile.y - 1;
        }else if(direction == PositionPart.up) {
            newTile.y = newTile.y + 1;
        } else if (direction == PositionPart.left) {
            newTile.x = newTile.x - 1;
        } else if (direction == PositionPart.right) {
            newTile.x = newTile.x + 1;
        }

        Point newTileCoor = map.tileCoorToMapCoor(newTile.x, newTile.y);

        newTileType = map.getTileType(newTile.x, newTile.y);

        if (newTileType != null && (newTileType.equals(path) || newTileType.equals("End")) && !pathPart.isExplored(newTile)) {
            positionPart.setRadians(direction);
            pathPart.setyGoal(newTileCoor.y);
            pathPart.setxGoal(newTileCoor.x);
            pathPart.addPosition(newTile);
            return true;
        }

        return false;
    }

    private void createEnemy(GameData gameData, World world){
        Point start = map.getStartTileCoor();

        float speed = 1;
        Point point = map.tileCoorToMapCoor(start.x, start.y);
        float x = point.x;
        float y = point.y;
        int radians = PositionPart.left;

        PathPart path = new PathPart();
        path.setxGoal(500);
        path.setyGoal(525);
        path.addPosition(map.getTileCoordinates(x,y));

        Sprite sprite = new Sprite(world.getTextureHashMap().get(Types.ENEMY));
        sprite.setCenter(sprite.getHeight()/2, sprite.getWidth()/2);
        Entity enemy = new Enemy(sprite, Types.ENEMY);
        enemy.setRadius(27);
        enemy.add(new MovingPart( speed, 0, true));
        enemy.add(new PositionPart(x, y, radians));
        enemy.add(new LifePart(50));
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
