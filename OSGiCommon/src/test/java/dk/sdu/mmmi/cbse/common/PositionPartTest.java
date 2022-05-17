package dk.sdu.mmmi.cbse.common;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.entityparts.MovingPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.commonmap.IMap;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.anyFloat;
import static org.mockito.Mockito.when;



@ExtendWith(MockitoExtension.class)
public class PositionPartTest {

    @Test
    public void setPositionTest(){
        int x = 10;
        int y = 10;

        PositionPart positionPart = new PositionPart(x, y, 10);

        positionPart.setPosition(20, 20);

        assertTrue(positionPart.getX() != x && positionPart.getY() != y);


    }



}
