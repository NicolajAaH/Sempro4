package dk.sdu.mmmi.cbse.enemy;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
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

    ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(3);
    private IMap map;

    @Override
    public void process(GameData gameData, World world) {
        List<Attack> currentAttacks = gameData.getCurrentAttacks();

        for (Attack attack : currentAttacks) {
            if (attack.getAttackNumber() > 0) {
                createEnemy(world, gameData);
                attack.setAttackNumber(attack.getAttackNumber() - 1);
                attack.setAttackTimeMs(attack.getAttackTimeMs() + 500);
                gameData.addMoney(2);
            } else {
                gameData.removeAttack(attack);
            }
        }

        List<Entity> enemies = world.getEntities(Enemy.class);

        for (Entity enemy : enemies) {
            MovingPart movingPart = enemy.getPart(MovingPart.class);
            movingPart.process(gameData, enemy);
        }

        for (Entity enemy : enemies) {
            executor.submit(() -> {
                PathPart pathPart = enemy.getPart(PathPart.class);
                PositionPart positionPart = enemy.getPart(PositionPart.class);

                if (positionPart.getRadians() == PositionPart.left && positionPart.getX() > pathPart.getGoal().x)
                    return;
                if (positionPart.getRadians() == PositionPart.right && positionPart.getX() < pathPart.getGoal().x)
                    return;
                if (positionPart.getRadians() == PositionPart.down && positionPart.getY() > pathPart.getGoal().y)
                    return;
                if (positionPart.getRadians() == PositionPart.up && positionPart.getY() < pathPart.getGoal().y) return;

                setNewEnemyPath(enemy, gameData, world);
            });
        }
    }

    private void setNewEnemyPath(Entity enemy, GameData gameData, World world) {
        PathPart pathPart = enemy.getPart(PathPart.class);
        PositionPart positionPart = enemy.getPart(PositionPart.class);

        PathDirection newTile = pathPart.getPath().pop();
        pathPart.setGoal(newTile.getGoal());
        positionPart.setRadians(newTile.getDirection());

        // checking if reached end of path, updating life and removing enemy
        if (pathPart.getPath().isEmpty()) {
            gameData.setLife(gameData.getLife() -1);
            world.removeEntity(enemy);
        }
    }


    private Stack<PathDirection> getPathDirectionStack(ArrayList<Point> path) {
        Stack<PathDirection> pathDirections = new Stack<>();
        for (int x = 1; x < path.size(); x++) {
            Point currentTile = path.get(x);
            Point newTile = path.get(x - 1);
            PathDirection direction = new PathDirection(getDirection(currentTile, newTile), map.getTileCenter(newTile));

            pathDirections.add(direction);
        }

        return pathDirections;
    }

    private int getDirection(Point currentTile, Point newTile) {
        if (currentTile.x < newTile.x) return PositionPart.right;
        if (currentTile.x > newTile.x) return PositionPart.left;
        if (currentTile.y < newTile.y) return PositionPart.up;
        return PositionPart.down;
    }

    private void createEnemy(World world, GameData gameData) {
        Point start = map.getStartTileCoor();

        float speed = 1;
        Point point = map.tileCoorToMapCoor(start.x, start.y);
        float x = point.x;
        float y = point.y;
        int radians = PositionPart.left;

        PathPart path = new PathPart(getPathDirectionStack(map.getPath()));

        Sprite sprite = new Sprite(world.getTextureHashMap().get(Types.ENEMY));
        sprite.setCenter(sprite.getHeight() / 2, sprite.getWidth() / 2);
        Entity enemy = new Enemy(sprite, Types.ENEMY);
        enemy.setRadius(24);
        enemy.add(new MovingPart(speed, 0, true));
        enemy.add(new PositionPart(x - map.getTileSize() / 2, y - map.getTileSize() / 2, radians));
        enemy.add(new LifePart(10));
        enemy.add(path);
        setNewEnemyPath(enemy, gameData, world);
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

    public void removeIMap(IMap map) {
        this.map = null;
    }
}
