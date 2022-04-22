package dk.sdu.mmmi.cbse.collision;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.LifePart;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.services.IPostEntityProcessingService;

import java.util.ArrayList;

public class CollisionManager implements IPostEntityProcessingService {
    @Override
    public void process(GameData gameData, World world) {
        ArrayList<Entity> entities = new ArrayList<>(world.getEntities());

        for (int i = 0; i < entities.size(); i++) {
            for (int o = i + 1; o < entities.size(); o++) {
                // Get the entities
                Entity iEntity = entities.get(i);
                Entity oEntity = entities.get(o);

                // Get the position part for the entities
                PositionPart iPosition = iEntity.getPart(PositionPart.class);
                PositionPart oPosition = oEntity.getPart(PositionPart.class);


                // PRINT POSITION
                System.out.println("\ni"
                        + "\nx: " + iPosition.getX()
                        + "\ny: " + iPosition.getY());

                System.out.println("\no"
                        + "\nx: " + oPosition.getX()
                        + "\ny: " + oPosition.getY());



                // Calculate distance between two entities
                double distance = Math.sqrt(Math.pow((iPosition.getX() - oPosition.getX()), 2) + Math.pow((iPosition.getY() - oPosition.getY()),2));

                if (distance < (iEntity.getRadius() + oEntity.getRadius())) {
                    System.out.println("*** COLLISION *** ");
                    LifePart iLifePart = iEntity.getPart(LifePart.class);
                    LifePart oLifePart = oEntity.getPart(LifePart.class);

                    iLifePart.setIsHit(true);
                    oLifePart.setIsHit(true);
                }

            }

        }

    }
}
