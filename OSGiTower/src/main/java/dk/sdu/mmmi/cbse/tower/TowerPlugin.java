package dk.sdu.mmmi.cbse.tower;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.Types;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;

public class TowerPlugin implements IGamePluginService {

    private Entity tower;

    @Override
    public void start(GameData gameData, World world) {

    }

    @Override
    public void stop(GameData gameData, World world) {
// Remove entities
        world.removeEntity(tower);
    }

    @Override
    public Entity create(SpriteBatch batch, GameData gameData, World world, Texture texture) {
        return null;
    }

    @Override
    public Types getType() {
        return Types.TOWER;
    }
}
