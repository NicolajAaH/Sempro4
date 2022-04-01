package dk.sdu.mmmi.cbse.enemy;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import dk.sdu.mmmi.cbse.common.data.Attack;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.LifePart;
import dk.sdu.mmmi.cbse.common.data.entityparts.MovingPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import dk.sdu.mmmi.cbse.commonenemy.Enemy;

import java.util.ArrayList;
import java.util.Random;

public class EnemyControlSystem implements IEntityProcessingService {


    @Override
    public void process(GameData gameData, World world) {
        //TODO: Mapp attacks to include time passed
        ArrayList<Attack> currentAttacks = gameData.getAttacks();

        for (Attack attack : currentAttacks) {
            //TODO: add enemies corresponding to attack
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

    @Override
    public void draw(SpriteBatch spriteBatch, World world) {
        for (Entity enemy : world.getEntities(Enemy.class)) {
            enemy.draw(spriteBatch);
        }
    }
}
