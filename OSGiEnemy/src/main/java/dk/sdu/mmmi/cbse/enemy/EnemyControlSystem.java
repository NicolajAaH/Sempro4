package dk.sdu.mmmi.cbse.enemy;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import dk.sdu.mmmi.cbse.common.data.*;
import dk.sdu.mmmi.cbse.common.data.entityparts.LifePart;
import dk.sdu.mmmi.cbse.common.data.entityparts.MovingPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.PathPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import dk.sdu.mmmi.cbse.commonenemy.Enemy;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
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
            PositionPart positionPart = enemy.getPart(PositionPart.class);
            MovingPart movingPart = enemy.getPart(MovingPart.class);
            LifePart lifePart = enemy.getPart(LifePart.class);
            Random rdm = new Random();

            movingPart.setLeft(rdm.nextBoolean());
            movingPart.setRight(rdm.nextBoolean());
            movingPart.setUp(rdm.nextBoolean());
            movingPart.setDown(rdm.nextBoolean());

            movingPart.process(gameData, enemy);
            positionPart.process(gameData, enemy);

            if (lifePart.getLife() == 0) {
                world.removeEntity(enemy);
                break;
            }
        }
    }

    private void createEnemy(GameData gameData, World world){
        ArrayList<TiledMapTileLayer.Cell> tiles = world.getMap().getTilesOfType("Start");

        if(tiles.isEmpty()) return;

        TiledMapTile tile = tiles.get(0).getTile();

        Point point = world.getMap().tileCoorToMapCoor(tile.getOffsetX(), tile.getOffsetY());



        float deacceleration = 10;
        float acceleration = 200;
        float speed = 1;
        float rotationSpeed = 5;
        float x = point.x;
        float y = point.y;
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
