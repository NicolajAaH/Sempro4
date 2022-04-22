package dk.sdu.mmmi.cbse.player;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
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

    }

    @Override
    public void stop(GameData gameData, World world) {
        // Remove entities
        for (Entity entity : world.getEntities(Player.class)) {
            world.removeEntity(entity);
        }
    }

    @Override
    public Entity create(GameData gameData, World world) {
        float deacceleration = 0;
        float acceleration = 0;
        float speed = 3;
        float rotationSpeed = 0;
        float x = gameData.getDisplayWidth() / 2;
        float y = gameData.getDisplayHeight() / 2;
        float radians = 3.1415f / 2;

        Texture texture = world.getTextureHashMap().get(Types.PLAYER);
        Sprite sprite = new Sprite(texture);
        Entity player = new Player(sprite, Types.PLAYER);
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
