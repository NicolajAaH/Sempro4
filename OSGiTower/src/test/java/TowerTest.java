import com.badlogic.gdx.graphics.Texture;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.Types;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.WeaponPart;
import dk.sdu.mmmi.cbse.commonenemy.Enemy;
import dk.sdu.mmmi.cbse.commonmap.IMap;
import dk.sdu.mmmi.cbse.commonprojectile.ProjectileSPI;
import dk.sdu.mmmi.cbse.commontower.Tower;
import dk.sdu.mmmi.cbse.tower.TowerControlSystem;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import static org.mockito.Mockito.*;

/**
 * Test of requirement F6.1: A Tower must be able to shoot at enemies
 */

@ExtendWith(MockitoExtension.class)
public class TowerTest {
    @Mock
    ProjectileSPI mockProjectileLauncer;

    @Mock
    IMap mockMap;

    @Mock
    GameData gameData;

    @Mock
    World world;

    @Mock
    Tower tower;

    @Mock
    Enemy enemy;

    @Mock
    Random mockRandom;

    @Mock
    PositionPart positionPartMock;

    @Mock
    WeaponPart weaponPartMock;

    @Mock
    Texture texture;

    @Test
    public void testCreateProjectile(){
        TowerControlSystem towerControlSystem = new TowerControlSystem();

        // injecting mock dependencies
        towerControlSystem.setIMap(mockMap);
        towerControlSystem.setProjectileSPI(mockProjectileLauncer);
        towerControlSystem.setR(mockRandom);

        // Mock of world
        List<Entity> towerEntities = new ArrayList<>();
        towerEntities.add(tower);
        when(world.getEntities(Tower.class)).thenReturn(towerEntities);

        List<Entity> enemyEntities = new ArrayList<>();
        enemyEntities.add(enemy);
        when(world.getEntities(Enemy.class)).thenReturn(enemyEntities);

        // creating mock of PositionPart & Weapon Part methods
        when(positionPartMock.getX()).thenReturn(100F);
        when(positionPartMock.getY()).thenReturn(100F);
        when(weaponPartMock.getRange()).thenReturn(30f);

        // adding entityparts mocks to tower & enemy mocks
        when(tower.getPart(PositionPart.class)).thenReturn(positionPartMock);
        when(tower.getPart(WeaponPart.class)).thenReturn(weaponPartMock);
        when(enemy.getPart(PositionPart.class)).thenReturn(positionPartMock);

        // Mocking random variable
        when(mockRandom.nextInt(anyInt())).thenReturn(0);

        // running the code
        towerControlSystem.process(gameData,world);

        // verifyCreateProjectile is called
        verify(mockProjectileLauncer, times(1)).createProjectile(tower,gameData,world);
    }

    @Test
    public void placeTowerTest(){
        TowerControlSystem towerControlSystem = new TowerControlSystem();
        towerControlSystem.setIMap(mockMap);
        when(mockMap.getTileType(anyInt(), anyInt())).thenReturn("Grass");
        HashMap<Types, Texture> hashMap = new HashMap<Types, Texture>(){{
            put(Types.TOWER, texture);
        }};
        when(mockMap.tileCoorToMapCoor(anyFloat(), anyFloat())).thenReturn(new Point(1,1));
        when(world.getTextureHashMap()).thenReturn(hashMap);
        when(mockMap.getTileSize()).thenReturn(58);
        // injecting mock dependencies
        towerControlSystem.setIMap(mockMap);
        towerControlSystem.createTower(gameData, world, 1, 1);
        verify(mockMap, times(1)).changeTileType(1,1, "Tower");
    }
}
