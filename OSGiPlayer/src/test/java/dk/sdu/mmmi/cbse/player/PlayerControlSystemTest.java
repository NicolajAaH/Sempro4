package dk.sdu.mmmi.cbse.player;


import dk.sdu.mmmi.cbse.common.data.*;

import dk.sdu.mmmi.cbse.common.data.entityparts.MovingPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
public class PlayerControlSystemTest {

    @Mock
    Entity entity1;

    @Mock
    GameData gameData;

    @Mock
    World world;
    GameKeys keys;

    @Mock
    MovingPart movingPart;

    @Mock
    PositionPart positionPart;



    /*
    tests player movement by checking that movingpart.process
    and positionpart.process is called once each, each time the playerControlSystem.process is run
     */
    @Test   //gameData.
    public void testPlayerMovement(){
        PlayerControlSystem playerControlSystem = new PlayerControlSystem();
        keys = new GameKeys();

//        MovingPart movingPart = new MovingPart(10, 0);
//        PositionPart positionPart = new PositionPart(10,10, 0);

        when(entity1.getPart(MovingPart.class)).thenReturn(movingPart);
        when(entity1.getPart(PositionPart.class)).thenReturn(positionPart);
        world.addEntity(entity1);


//        System.out.println(movingPart.toString());
//        when(keys.isDown(GameKeys.UP)).thenReturn(true);
//        movingPart.setMoving(true);
//        movingPart.process(gameData, entity1);
//        positionPart.process(gameData, entity1);
//        System.out.println(positionPart.getY());
//        assertTrue(positionPart.getY()>10);
        when(world.getEntities(any())).thenReturn(new ArrayList<Entity>(){{
            add(entity1);
    }});
        when(gameData.getKeys()).thenReturn(keys);
        //when(gameData.getKeys().isDown(any())).thenReturn(false);


        playerControlSystem.process(gameData, world);

        verify(movingPart,times(1)).process(any(), any());
        verify(positionPart, times(1)).process(any(), any());


    }


}
