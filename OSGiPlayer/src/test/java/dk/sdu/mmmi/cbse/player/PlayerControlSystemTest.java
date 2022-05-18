package dk.sdu.mmmi.cbse.player;


import dk.sdu.mmmi.cbse.common.data.*;

import dk.sdu.mmmi.cbse.common.data.entityparts.MovingPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.commonmap.IMap;
import dk.sdu.mmmi.cbse.commontower.TowerSPI;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.awt.*;
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
    @Mock
    GameKeys keys;

    @Mock
    MovingPart movingPart;

    @Mock
    PositionPart positionPart;

    @Mock
    TowerSPI towerSPI;

    @Mock
    IMap map;

    @Mock
    Point point;


    /*
    tests player movement by checking that movingpart.process
    and positionpart.process is called once each, each time the playerControlSystem.process is run
     */
    @Test
    public void testPlayerMovement(){
        PlayerControlSystem playerControlSystem = new PlayerControlSystem();

        when(entity1.getPart(MovingPart.class)).thenReturn(movingPart);
        when(entity1.getPart(PositionPart.class)).thenReturn(positionPart);
        world.addEntity(entity1);

        when(world.getEntities(any())).thenReturn(new ArrayList<Entity>(){{
            add(entity1);
    }});
        when(gameData.getKeys()).thenReturn(keys);

        playerControlSystem.process(gameData, world);

        verify(movingPart,times(1)).process(any(), any());
        verify(positionPart, times(1)).process(any(), any());


    }

    @Test
    public void placeTowerTest(){
        when(entity1.getPart(MovingPart.class)).thenReturn(movingPart);
        when(entity1.getPart(PositionPart.class)).thenReturn(positionPart);
        world.addEntity(entity1);

        when(world.getEntities(any())).thenReturn(new ArrayList<Entity>(){{
            add(entity1);
        }});
        PlayerControlSystem playerControlSystem = new PlayerControlSystem();
        playerControlSystem.setTowerSPI(towerSPI);
        playerControlSystem.setIMap(map);
        when(keys.isDown(anyInt())).thenReturn(true);
        when(gameData.getKeys()).thenReturn(keys);
        when(map.mapCoorToTileCoor(anyFloat(), anyFloat())).thenReturn(point);
        playerControlSystem.process(gameData, world);
        verify(towerSPI, times(1)).createTower(any(), any(), anyInt(), anyInt());
    }


}
