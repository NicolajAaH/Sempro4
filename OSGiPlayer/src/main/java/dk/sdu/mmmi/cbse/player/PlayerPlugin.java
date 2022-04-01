package dk.sdu.mmmi.cbse.player;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
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
import dk.sdu.mmmi.cbse.filehandler.OSGiFileHandle;

import java.io.File;
import java.util.Arrays;


public class PlayerPlugin implements IGamePluginService {

    private Entity player;

    public PlayerPlugin() {
    }

    @Override
    public void start(GameData gameData, World world) {

    }

    @Override
    public void stop(GameData gameData, World world) {
        // Remove entities
        world.removeEntity(player);
    }

    @Override
    public Entity create(SpriteBatch batch, GameData gameData, World world, Texture texture) {
        float deacceleration = 10;
        float acceleration = 200;
        float speed = 2;
        float rotationSpeed = 5;
        float x = gameData.getDisplayWidth() / 2;
        float y = gameData.getDisplayHeight() / 2;
        float radians = 3.1415f / 2;

        Sprite sprite = new Sprite(texture);
        Entity player = new Player(sprite); //throws exception nulpointer
        player.add(new MovingPart(deacceleration, acceleration, speed, rotationSpeed));
        player.add(new PositionPart(x, y, radians));
        player.add(new LifePart(1));
        world.addEntity(player);

        return player;
    }

}
