package dk.sdu.mmmi.cbse.tower;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.Types;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;
import dk.sdu.mmmi.cbse.commontower.Tower;

public class TowerPlugin implements IGamePluginService {

    private Entity tower;

    @Override
    public void start(GameData gameData, World world) {
    }

    @Override
    public void stop(GameData gameData, World world) {
        // Remove entities
        for (Entity tower : world.getEntities(Tower.class)){
            world.removeEntity(tower);
        }
    }

    @Override
    public Entity create(GameData gameData, World world) {
        return null;
    }

    @Override
    public Types getType() {
        return Types.TOWER;
    }
}
