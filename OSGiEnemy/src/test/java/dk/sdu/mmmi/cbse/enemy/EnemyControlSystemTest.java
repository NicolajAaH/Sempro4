package dk.sdu.mmmi.cbse.enemy;

import dk.sdu.mmmi.cbse.common.data.Attack;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.MovingPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.commonenemy.Enemy;
import dk.sdu.mmmi.cbse.commonmap.IMap;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.awt.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyFloat;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;


@ExtendWith(MockitoExtension.class)
public class EnemyControlSystemTest {

    @Mock
    GameData gameData;

    @Mock
    World world;

    @Mock
    List<Attack> currentAttacks;

    @Mock
    Entity entity;

    @Mock
    Enemy enemy;

    @Mock
    Attack attack;

    @Mock
    IMap map;

//    @Mock
//    Point point;

    EnemyControlSystem enemyControlSystem;

    @Before
    public void setUp(){
       // currentAttacks = new ArrayList<Attack>(3);
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void EnemyLoadAttacksTest(){
//        currentAttacks = new ArrayList<Attack>();
//        currentAttacks.add(attack);

        Point point = new Point(10, 10);
        EnemyControlSystem enemyControlSystem = new EnemyControlSystem();
        enemyControlSystem.setIMap(map);
        when(gameData.getCurrentAttacks()).thenReturn(new ArrayList<Attack>(){{add(attack);}});
        when(attack.getAttackNumber()).thenReturn(2);
        //when(map.getStartTileCoor()).thenReturn(point);
        //when(enemyControlSystem.createEnemy(world, gameData))

        enemyControlSystem.process(gameData, world);
        assertTrue(gameData.getMoney()==2);




    }





}
