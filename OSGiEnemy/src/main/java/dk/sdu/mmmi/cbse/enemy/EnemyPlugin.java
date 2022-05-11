package dk.sdu.mmmi.cbse.enemy;

import com.badlogic.gdx.graphics.Texture;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.Types;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;
import dk.sdu.mmmi.cbse.commonenemy.Enemy;

import java.util.HashMap;

public class EnemyPlugin implements IGamePluginService {

    @Override
    public void start(GameData gameData, World world, HashMap<Types, Texture> textures) {

    }

    @Override
    public void stop(GameData gameData, World world) {
        for (Entity enemy : world.getEntities(Enemy.class)){
            world.removeEntity(enemy);
        }
    }

    @Override
    public Types getType() {
        return Types.ENEMY;
    }
}
