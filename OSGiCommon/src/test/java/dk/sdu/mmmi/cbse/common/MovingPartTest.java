package dk.sdu.mmmi.cbse.common;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.entityparts.MovingPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.commonmap.IMap;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.anyFloat;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class MovingPartTest {

//    @Mock
//    PositionPart positionPart;

    @Mock
    GameData gameData;

    @Mock
    Entity entity;

    @Mock
    IMap map;


    /* testing movement by checking if movingPart.process changes x,y coordinates*/

    @Ignore //TODO fix test
    @Test
    public void movementTest(){
        MovingPart movingPart = new MovingPart(1, 0);
        movingPart.setIMap(map);


        movingPart.setMoving(true);
        PositionPart positionPart = new PositionPart(12, 10, 90);
        entity.add(positionPart);

        when(entity.getPart(PositionPart.class)).thenReturn(positionPart);

        when(map.getTileSize()).thenReturn(58);

        when(entity.getRadius()).thenReturn(5f);

        when(map.isInsideMap(anyFloat(), anyFloat())).thenReturn(true);

        System.out.println();
        movingPart.process(gameData, entity);
        PositionPart positionPart2 = entity.getPart(PositionPart.class);


        assertTrue(positionPart2.getX()!=12 || positionPart2.getY()!=10);

    }




}
