package dk.sdu.mmmi.cbse.projectile;

import com.badlogic.gdx.graphics.Texture;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.Types;
import dk.sdu.mmmi.cbse.common.data.World;

import dk.sdu.mmmi.cbse.common.services.IGamePluginService;
import dk.sdu.mmmi.cbse.commonprojectile.Projectile;

import java.util.HashMap;


public class ProjectilePlugin implements IGamePluginService {
    public ProjectilePlugin() {
    }

    @Override
    public void start(GameData gameData, World world, HashMap<Types, Texture> textures) {

    }

    @Override
    public void stop(GameData gameData, World world) {
        // Remove entities
        for (Entity entity : world.getEntities(Projectile.class)) {
            world.removeEntity(entity);
        }
    }

    @Override
    public Types getType() {
        return Types.PROJECTILE;
    }
}
