package dk.sdu.mmmi.cbse.commonprojectile;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;

public interface ProjectileSPI {
    public void createProjectile(Entity e, GameData gameData);
}
