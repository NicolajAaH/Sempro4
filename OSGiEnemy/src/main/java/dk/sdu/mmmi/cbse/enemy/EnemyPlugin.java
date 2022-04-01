package dk.sdu.mmmi.cbse.enemy;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.LifePart;
import dk.sdu.mmmi.cbse.common.data.entityparts.MovingPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;
import dk.sdu.mmmi.cbse.commonplayer.Player;

public class EnemyPlugin implements IGamePluginService {

    @Override
    public void start(GameData gameData, World world) {

    }

    @Override
    public void stop(GameData gameData, World world) {
        world.removeEntity("enemy");
    }

    @Override
    public Entity create(SpriteBatch spriteBatch, GameData gameData, World world, Texture texture) {

        float deacceleration = 10;
        float acceleration = 200;
        float speed = 2;
        float rotationSpeed = 5;
        float x = gameData.getDisplayWidth() / 2;
        float y = gameData.getDisplayHeight() / 2;
        float radians = 3.1415f / 2;

        Sprite sprite = new Sprite(texture);
        Entity enemy = new Player(sprite); //throws exception nulpointer
        enemy.add(new MovingPart(deacceleration, acceleration, speed, rotationSpeed));
        enemy.add(new PositionPart(x, y, radians));
        enemy.add(new LifePart(1));
        world.addEntity(enemy);
      
        return enemy;
    }
}