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

        //TODO: get enemies
        //TODO: calculate new position for enemies

        List<Entity> enemies = world.getEntities(Enemy.class);

        Collections.shuffle(enemies);

        for (Entity enemy : enemies) {

            PathPart pathPart = enemy.getPart(PathPart.class);
            PositionPart positionPart = enemy.getPart(PositionPart.class);
            MovingPart movingPart = enemy.getPart(MovingPart.class);
            LifePart lifePart = enemy.getPart(LifePart.class);

            movingPart.process(gameData,enemy);

            if (lifePart.getLife() == 0) {
                world.removeEntity(enemy);
                return;
            }
            if(movingPart.isLeft() && positionPart.getX() > pathPart.getxGoal()) return;
            if(movingPart.isRight() && positionPart.getX() < pathPart.getxGoal()) return;
            if(movingPart.isDown() && positionPart.getY() > pathPart.getyGoal()) return;
            if(movingPart.isUp() && positionPart.getY() < pathPart.getyGoal()) return;

            setNewEnemyPath(pathPart, movingPart, lifePart);

        }
    }

    private void setNewEnemyPath(PathPart pathPart, MovingPart movingPart, LifePart lifePart) {

        TiledMapTileLayer layer = (TiledMapTileLayer) map.getTiledMap().getLayers().get(0);
        int pathX = pathPart.getxGoal();
        int pathY = pathPart.getyGoal();
        int pathXPlus = (int) (pathPart.getxGoal() + layer.getTileWidth() - 10);
        int pathXMinus = (int) (pathPart.getxGoal() - layer.getTileWidth() + 10);
        int pathYPlus = (int) (pathPart.getyGoal() + layer.getTileWidth());
        int pathYMinus = (int) (pathPart.getyGoal() - layer.getTileWidth());

        if(movingPart.isLeft()){
            if(setNewGoal(pathXMinus, pathY, pathPart, movingPart,lifePart,Direction.left)) return;
            if(setNewGoal(pathX, pathYMinus, pathPart, movingPart,lifePart,Direction.down)) return;
            if(setNewGoal(pathX, pathYPlus, pathPart, movingPart,lifePart,Direction.up)) return;
            if(setNewGoal(pathXPlus, pathY,pathPart, movingPart, lifePart, Direction.right)) return;
        }

        if(movingPart.isRight()){
            if(setNewGoal(pathXPlus, pathY, pathPart, movingPart,lifePart,Direction.right)) return;
            if(setNewGoal(pathX, pathYPlus, pathPart, movingPart,lifePart,Direction.up)) return;
            if(setNewGoal(pathX, pathYMinus, pathPart, movingPart,lifePart,Direction.down)) return;
            if(setNewGoal(pathXMinus, pathY,pathPart, movingPart, lifePart, Direction.left)) return;
        }

        if(movingPart.isUp()){
            if(setNewGoal(pathX, pathYPlus, pathPart, movingPart,lifePart,Direction.up)) return;
            if(setNewGoal(pathXMinus, pathY, pathPart, movingPart,lifePart,Direction.right)) return;
            if(setNewGoal(pathXPlus, pathY, pathPart, movingPart,lifePart,Direction.left)) return;
            if(setNewGoal(pathX, pathYMinus,pathPart, movingPart, lifePart, Direction.down)) return;
        }

        if(movingPart.isDown()){
            if(setNewGoal(pathX, pathYMinus, pathPart, movingPart,lifePart,Direction.down)) return;
            if(setNewGoal(pathXPlus, pathY, pathPart, movingPart,lifePart,Direction.right)) return;
            if(setNewGoal(pathXMinus, pathY, pathPart, movingPart,lifePart,Direction.left)) return;
            if(setNewGoal(pathX, pathYPlus,pathPart, movingPart, lifePart, Direction.up)) return;
        }
    }

    private boolean setNewGoal(int x, int y, PathPart pathPart, MovingPart movingPart, LifePart lifePart, Direction direction){

        Point point = map.getTileCoordinates(x,y);
        String tile = map.getTileType(point.x, point.y);

        if(tile.equals("End")) {
            lifePart.setLife(0);
            return false;
        }

        if(!tile.equals("Path")) return false;
        if(pathPart.isExplored(point)) return false;

        pathPart.addPosition(point);
        pathPart.setxGoal(x);
        pathPart.setyGoal(y);

        movingPart.setRight(direction.equals(Direction.right));
        movingPart.setLeft(direction.equals(Direction.left));
        movingPart.setUp(direction.equals(Direction.up));
        movingPart.setDown(direction.equals(Direction.down));
        return true;

    }

    private void createEnemy(GameData gameData, World world){
        ArrayList<TiledMapTileLayer.Cell> tiles = map.getTilesOfType("Start");

        TiledMapTile tile = tiles.get(0).getTile();

        TiledMapTileLayer layer = (TiledMapTileLayer) map.getTiledMap().getLayers().get(0);

        float speed = 2;
        float x = 600;
                //tile.getOffsetX() + layer.getTileWidth() / 2;
        float y = 525;
                //tile.getOffsetX() + layer.getTileHeight() / 2;
        float radians = 3.1415f / 2;

        PathPart path = new PathPart();
        path.setxGoal(500);
        path.setyGoal(525);

        MovingPart movingPart = new MovingPart(0, 0, speed, 0);
        movingPart.setLeft(true);

        Sprite sprite = new Sprite(world.getTextureHashMap().get(Types.ENEMY));
        Entity enemy = new Enemy(sprite, Types.ENEMY);
        enemy.add(movingPart);
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
