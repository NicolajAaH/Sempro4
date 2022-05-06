package dk.sdu.mmmi.cbse.collision;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.Types;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.LifePart;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.services.IPostEntityProcessingService;

import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class CollisionManager implements IPostEntityProcessingService {
    @Override
    public void process(GameData gameData, World world) {
        ArrayList<Entity> entities = new ArrayList<>(world.getEntities());

        ThreadPoolExecutor executor =
                (ThreadPoolExecutor) Executors.newFixedThreadPool(3);


        for (int i = 0; i < entities.size(); i++) {
            final int x = i;

                for (int o = x + 1; o < entities.size(); o++) {


                    // Get the entities
                    Entity iEntity = entities.get(x);
                    Entity oEntity = entities.get(o);

                    if (iEntity.getType() == Types.TOWER || oEntity.getType() == Types.TOWER)
                        continue;

                    if (iEntity.getType() == oEntity.getType()) //Should not collide when equal type
                        continue;

                    if ((iEntity.getType() == Types.PLAYER || oEntity.getType() == Types.PLAYER) && (iEntity.getType() == Types.PROJECTILE || oEntity.getType() == Types.PROJECTILE))
                        continue; //Player and projectile should not collide

                    // Get the position part for the entities
                    PositionPart iPosition = iEntity.getPart(PositionPart.class);
                    PositionPart oPosition = oEntity.getPart(PositionPart.class);

                if (distance < (iEntity.getRadius() + oEntity.getRadius())) {

                    LifePart iLifePart = iEntity.getPart(LifePart.class);

                    if(iEntity.getType() == Types.PROJECTILE) world.removeEntity(iEntity);

                    iLifePart.setLife(iLifePart.getLife()-1);
                }


        }

    }
}
