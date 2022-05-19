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

import java.util.HashMap;


public class PlayerPlugin implements IGamePluginService {

    public PlayerPlugin() {
    }

    @Override
    public void start(GameData gameData, World world, HashMap<Types, Texture> textures) {
        float speed = 3;
        float rotationSpeed = 0;
        float x = gameData.getDisplayWidth() / 2f - 50;
        float y = gameData.getDisplayHeight() / 2f;
        int radians = 0;
        Texture texture = textures.get(Types.PLAYER);
        Sprite sprite = new Sprite(texture);
        Entity player = new Player(sprite, Types.PLAYER);
        player.add(new MovingPart(speed));
        player.add(new PositionPart(x, y, radians));
        player.add(new LifePart(1));
        player.setRadius(20);
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
    public Types getType() {
        return Types.PLAYER;
    }
}
