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

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class EnemyControlSystem implements IEntityProcessingService {


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

        //TODO: get enemies
        //TODO: calculate new position for enemies

        for (Entity enemy : world.getEntities(Enemy.class)) {
            PathPart pathPart = enemy.getPart(PathPart.class);
            PositionPart positionPart = enemy.getPart(PositionPart.class);
            MovingPart movingPart = enemy.getPart(MovingPart.class);
            LifePart lifePart = enemy.getPart(LifePart.class);


            if (lifePart.getLife() == 0) {
                world.removeEntity(enemy);
                return;
            }
            if(pathPart.getDirection() == Direction.left && positionPart.getX() > pathPart.getxGoal()) return;
            if(pathPart.getDirection() == Direction.right && positionPart.getX() < pathPart.getyGoal()) return;
            if(pathPart.getDirection() == Direction.down && positionPart.getY() > pathPart.getyGoal()) return;
            if(pathPart.getDirection() == Direction.up && positionPart.getY() < pathPart.getyGoal()) return;

            setNewEnemyPath(pathPart, movingPart, world);

        }
    }

    private void setNewEnemyPath(PathPart pathPart, MovingPart movingPart, World world) {

        TiledMapTileLayer layer = (TiledMapTileLayer) world.getMap().getTiledMap().getLayers().get(0);
        int pathX = pathPart.getxGoal();
        int pathY = pathPart.getyGoal();
        int pathXPlus = (int) (pathPart.getxGoal() + layer.getTileWidth());
        int pathXMinus = (int) (pathPart.getxGoal() - layer.getTileWidth());
        int pathYPlus = (int) (pathPart.getyGoal() + layer.getTileWidth());
        int pathYMinus = (int) (pathPart.getyGoal() - layer.getTileWidth());
        String pathTag = "path";
        
        if(pathPart.getDirection() == Direction.left){
            if(world.getMap().getTileType(pathXMinus, pathY).equals(pathTag)){
                pathPart.setxGoal(pathXMinus);
            } else if(world.getMap().getTileType(pathX, pathYMinus).equals(pathTag)){
                pathPart.setyGoal(pathYMinus);
                pathPart.setDirection(Direction.down);
                movingPart.setDown(true);
                movingPart.setLeft(false);
            } else if(world.getMap().getTileType(pathX, pathYPlus).equals(pathTag)){
                pathPart.setyGoal(pathYPlus);
                pathPart.setDirection(Direction.up);
                movingPart.setUp(true);
                movingPart.setLeft(false);
            }
        } else if(pathPart.getDirection() == Direction.right){
            if(world.getMap().getTileType(pathXPlus, pathY).equals(pathTag)){
                pathPart.setxGoal(pathXPlus);
            } else if(world.getMap().getTileType(pathX, pathYPlus).equals(pathTag)){
                pathPart.setyGoal(pathYPlus);
                pathPart.setDirection(Direction.up);
                movingPart.setUp(true);
                movingPart.setRight(false);
            } else if(world.getMap().getTileType(pathX, pathYMinus).equals(pathTag)) {
                pathPart.setyGoal(pathYMinus);
                pathPart.setDirection(Direction.down);
                movingPart.setDown(true);
                movingPart.setRight(false);
            }
        } else if(pathPart.getDirection() == Direction.up){
            if(world.getMap().getTileType(pathX, pathYPlus).equals(pathTag)){
                pathPart.setyGoal(pathYPlus);
            } else if(world.getMap().getTileType(pathXMinus, pathY).equals(pathTag)){
                pathPart.setxGoal(pathXPlus);
                pathPart.setDirection(Direction.right);
                movingPart.setRight(true);
                movingPart.setUp(false);
            } else if(world.getMap().getTileType(pathXPlus, pathY).equals(pathTag)) {
                pathPart.setxGoal(pathXMinus);
                pathPart.setDirection(Direction.left);
                movingPart.setLeft(true);
                movingPart.setUp(false);
            }
        } else if(pathPart.getDirection() == Direction.down){
            if(world.getMap().getTileType(pathX, pathYMinus).equals(pathTag)){
                pathPart.setyGoal(pathYMinus);
            } else if(world.getMap().getTileType(pathXPlus, pathY).equals(pathTag)){
                pathPart.setxGoal(pathXPlus);
                pathPart.setDirection(Direction.right);
                movingPart.setRight(true);
                movingPart.setDown(false);
            } else if(world.getMap().getTileType(pathXMinus, pathY).equals(pathTag)) {
                pathPart.setxGoal(pathXMinus);
                pathPart.setDirection(Direction.left);
                movingPart.setLeft(true);
                movingPart.setDown(false);
            }
        }
    }

    private void createEnemy(GameData gameData, World world){
        ArrayList<TiledMapTileLayer.Cell> tiles = world.getMap().getTilesOfType("start");
        ArrayList<TiledMapTileLayer.Cell> tiles = world.getMap().getTilesOfType("Start");

        if(tiles.isEmpty()) return;
            //throw new RuntimeException("no starting tile for enemy");
        if(tiles.isEmpty()) return;

        TiledMapTile tile = tiles.get(0).getTile();

        TiledMapTileLayer layer = (TiledMapTileLayer) world.getMap().getTiledMap().getLayers().get(0);

        float deacceleration = 10;
        float acceleration = 200;
        float speed = 1;
        float rotationSpeed = 5;
        float x = tile.getOffsetX() + layer.getTileWidth() / 2;
        float y = tile.getOffsetX() + layer.getTileHeight() / 2;
        float radians = 3.1415f / 2;

        Sprite sprite = new Sprite(world.getTextureHashMap().get(Types.ENEMY));
        Entity enemy = new Enemy(sprite, Types.ENEMY);
        enemy.add(new MovingPart(deacceleration, acceleration, speed, rotationSpeed));
        enemy.add(new PositionPart(x, y, radians));
        enemy.add(new LifePart(1));
        enemy.add(new PathPart());
        world.addEntity(enemy);
    }

    @Override
    public void draw(SpriteBatch spriteBatch, World world) {
        for (Entity enemy : world.getEntities(Enemy.class)) {
            enemy.draw(spriteBatch);
        }
    }
}
