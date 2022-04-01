package dk.sdu.mmmi.cbse.player;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.Types;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.LifePart;
import dk.sdu.mmmi.cbse.common.data.entityparts.MovingPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;
import dk.sdu.mmmi.cbse.commonplayer.Player;


public class PlayerPlugin implements IGamePluginService {

    public PlayerPlugin() {
    }

    @Override
    public void start(GameData gameData, World world) {
        //TODO REFACTOR
        Sprite sprite = new Sprite(world.getTextureHashMap().get(Types.PLAYER));
        float deacceleration = 10;
        float acceleration = 200;
        float speed = 2;
        float rotationSpeed = 5;
        float x = gameData.getDisplayWidth() / 2;
        float y = gameData.getDisplayHeight() / 2;
        float radians = 3.1415f / 2;
        Entity player = new Player(sprite, Types.PLAYER); //throws exception nulpointer
        player.add(new MovingPart(deacceleration, acceleration, speed, rotationSpeed));
        player.add(new PositionPart(x, y, radians));
        player.add(new LifePart(1));
        world.addEntity(player);
    }

    @Override
    public void stop(GameData gameData, World world) {
        // Remove entities
        for (Entity entity : world.getEntities(Player.class)) {
            world.removeEntity(entity);
        }
    }

    @Override
    public Entity create(SpriteBatch batch, GameData gameData, World world, Texture texture) { //TODO REFACTOR INTERFACET
        float deacceleration = 10;
        float acceleration = 200;
        float speed = 2;
        float rotationSpeed = 5;
        float x = gameData.getDisplayWidth() / 2;
        float y = gameData.getDisplayHeight() / 2;
        float radians = 3.1415f / 2;

        Sprite sprite = new Sprite(texture);
        Entity player = new Player(sprite, Types.PLAYER); //throws exception nulpointer
        player.add(new MovingPart(deacceleration, acceleration, speed, rotationSpeed));
        player.add(new PositionPart(x, y, radians));
        player.add(new LifePart(1));
        world.addEntity(player);

        return player;
    }

    @Override
    public Types getType() {
        return Types.PLAYER;
    }

}
