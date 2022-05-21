package dk.sdu.mmmi.cbse.common;

import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertTrue;



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
