package dk.sdu.mmmi.cbse.common.services;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.Types;
import dk.sdu.mmmi.cbse.common.data.World;

public interface IGamePluginService {
    void start(GameData gameData, World world);

    void stop(GameData gameData, World world);

    Entity create(SpriteBatch batch, GameData gameData, World world, Texture texture);

    Types getType();

}
