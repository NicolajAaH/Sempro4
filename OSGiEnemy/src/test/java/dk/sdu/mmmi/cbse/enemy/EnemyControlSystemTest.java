package dk.sdu.mmmi.cbse.enemy;

import com.badlogic.gdx.graphics.Texture;
import dk.sdu.mmmi.cbse.common.data.*;
import dk.sdu.mmmi.cbse.common.data.entityparts.MovingPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.PathPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.commonenemy.Enemy;
import dk.sdu.mmmi.cbse.commonmap.IMap;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyFloat;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class EnemyControlSystemTest {

    @Mock
    GameData gameData;

    @Mock
    World world;

    @Mock
    Entity entity;

    @Mock
    Enemy enemy;

    @Mock
    Attack attack;

    @Mock
    IMap map;

    @Mock
    Texture texture;

    @Mock
    PathPart pathPart;

    @Mock
    PathDirection pathDirection;

    @Mock
    MovingPart movingPart;

    @Test
    public void EnemyLoadAttacksTest(){
        Point point = new Point(10, 10);
        EnemyControlSystem enemyControlSystem = new EnemyControlSystem();
        enemyControlSystem.setIMap(map);
        when(gameData.getCurrentAttacks()).thenReturn(new ArrayList<Attack>(){{add(attack);}});
        when(attack.getAttackNumber()).thenReturn(2);
        when(map.getStartTileCoor()).thenReturn(point);
        when(map.tileCoorToMapCoor(anyFloat(), anyFloat())).thenReturn(point);
        when(map.getTileCenter(any())).thenReturn(point);
        HashMap<Types, Texture> hashMap = new HashMap<Types, Texture>(){{
            put(Types.ENEMY, texture);
        }};
        when(world.getTextureHashMap()).thenReturn(hashMap);
        ArrayList<Point> points = new ArrayList<Point>(){{
            add(point);
            add(new Point(5,5));
        }};
        when(map.getPath()).thenReturn(points);
        enemyControlSystem.process(gameData, world);
        verify(gameData, times(1)).addMoney(2);
    }

    @Test
    public void testEnemyMovement(){
        EnemyControlSystem enemyControlSystem = new EnemyControlSystem();
        enemyControlSystem.setIMap(map);
        when(gameData.getCurrentAttacks()).thenReturn(new ArrayList<>());
        when(world.getEntities(Enemy.class)).thenReturn(new ArrayList<Entity>(){{
            add(enemy);
        }});
        when(enemy.getPart(MovingPart.class)).thenReturn(movingPart);
        enemyControlSystem.process(gameData, world);
        verify(movingPart, times(1)).process(any(), any());
    }

    @Test
    public void testPathToDirectionConversion(){
        EnemyControlSystem enemyControlSystem = new EnemyControlSystem();
        enemyControlSystem.setIMap(map);
        when(map.getTileCenter(any())).thenReturn(new Point(0,0));

        ArrayList<Point> input = new ArrayList<>();

        input.add(new Point(0,0));
        input.add(new Point(1,0));
        input.add(new Point(1,1));
        input.add(new Point(0,1));

        Stack<PathDirection> output = enemyControlSystem.getPathDirectionStack(input);

        assertEquals(3, output.size());
        assertEquals(output.pop().getDirection(), PositionPart.right);
        assertEquals(output.pop().getDirection(),PositionPart.down);
        assertEquals(output.pop().getDirection(),PositionPart.left);
        
    }
}
