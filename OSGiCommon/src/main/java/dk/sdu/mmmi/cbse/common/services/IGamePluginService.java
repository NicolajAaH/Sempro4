package dk.sdu.mmmi.cbse.common.services;

import com.badlogic.gdx.graphics.Texture;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.Types;
import dk.sdu.mmmi.cbse.common.data.World;

import java.util.HashMap;

public interface IGamePluginService {
    void start(GameData gameData, World world, HashMap<Types, Texture> textures);

    void stop(GameData gameData, World world);
}
