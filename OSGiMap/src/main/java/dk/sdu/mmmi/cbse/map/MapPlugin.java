package dk.sdu.mmmi.cbse.map;

import com.badlogic.gdx.graphics.Texture;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.Types;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;

import java.util.HashMap;

public class MapPlugin implements IGamePluginService{
    public MapPlugin() {
    }

    @Override
    public void start(GameData gameData, World world, HashMap<Types, Texture> textures) {

    }

    @Override
    public void stop(GameData gameData, World world) {

    }

    @Override
    public Types getType() {
        return Types.MAP;
    }
}
