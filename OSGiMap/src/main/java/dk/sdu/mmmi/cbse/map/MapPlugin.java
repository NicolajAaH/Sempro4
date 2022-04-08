package dk.sdu.mmmi.cbse.map;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.Types;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;

public class MapPlugin implements IGamePluginService{
    public MapPlugin() {
    }

    @Override
    public void start(GameData gameData, World world) {

    }

    @Override
    public void stop(GameData gameData, World world) {

    }

    @Override
    public Entity create(GameData gameData, World world) {
        return null;
    }

    @Override
    public Types getType() {
        return null;
    }
}
