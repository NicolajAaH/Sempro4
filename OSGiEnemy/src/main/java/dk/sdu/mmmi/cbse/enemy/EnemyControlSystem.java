package dk.sdu.mmmi.cbse.enemy;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import dk.sdu.mmmi.cbse.common.data.Attack;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import dk.sdu.mmmi.cbse.commonenemy.Enemy;

import java.util.ArrayList;

public class EnemyControlSystem implements IEntityProcessingService {


    @Override
    public void process(GameData gameData, World world) {
        //TODO: Mapp attacks to include time passed
        ArrayList<Attack> currentAttacks = gameData.getAttacks();

        for(Attack attack : currentAttacks){
            //TODO: add enemies corresponding to attack
        }

        //TODO: get enemies
        //TODO: calculate new position for enemies

    }

    @Override
    public void draw(SpriteBatch spriteBatch, World world) {
        for (Entity enemy : world.getEntities(Enemy.class)) {
            enemy.draw(spriteBatch);
        }
    }
}
