package dk.sdu.mmmi.cbse.player;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.LifePart;
import dk.sdu.mmmi.cbse.common.data.entityparts.MovingPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;
import dk.sdu.mmmi.cbse.commonplayer.Player;

import java.io.File;
import java.util.Arrays;


public class PlayerPlugin implements IGamePluginService {

    private Entity player;

    public PlayerPlugin() {
    }

    @Override
    public void start(GameData gameData, World world) {

        // Add entities to the world
        player = createPlayer(gameData);
        world.addEntity(player);
    }

    private Entity createPlayer(GameData gameData) {

        float deacceleration = 10;
        float acceleration = 200;
        float maxSpeed = 300;
        float rotationSpeed = 5;
        float x = gameData.getDisplayWidth() / 2;
        float y = gameData.getDisplayHeight() / 2;
        float radians = 3.1415f / 2;

        File file = new File("assets/images/Sprites/player_nogun.png");
        System.out.println(file.exists());
        File file2 = new File("images/Sprites/player_nogun.png");
        System.out.println(file2.exists());
        System.out.println(new Sprite(new Texture("~/assets/images/Sprites/player_nogun.png")));
        Entity player = new Player(new Sprite(new Texture("~/assets/images/Sprites/player_nogun.png"))); //throws exception nulpointer
        player.add(new MovingPart(deacceleration, acceleration, maxSpeed, rotationSpeed));
        player.add(new PositionPart(x, y, radians));
        player.add(new LifePart(1));

        return player;
    }

    @Override
    public void stop(GameData gameData, World world) {
        // Remove entities
        world.removeEntity(player);
    }

}
