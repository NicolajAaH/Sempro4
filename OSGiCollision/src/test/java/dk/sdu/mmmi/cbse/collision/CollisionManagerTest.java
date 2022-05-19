package dk.sdu.mmmi.cbse.collision;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.Types;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.LifePart;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Test of F8: A Collision system must be able to detect collisions between player, enemies and projectiles
 */
@ExtendWith(MockitoExtension.class)
class CollisionManagerTest {

    @Mock
    Entity entity1;
    @Mock
    Entity entity2;
    @Mock
    GameData gameData;
    @Mock
    World world;

    CollisionManager collisionManager;


    @BeforeEach
    public void setUp() {
        collisionManager = new CollisionManager();
    }

    /**
     * Test of process method, of class CollisionDetector.
     */
    @Test
    public void testProcess_Collision() {
        List<Entity> lists = new ArrayList<>();
        lists.add(entity1);
        lists.add(entity2);
        PositionPart commonPositionPart = new PositionPart(10, 10, 0);
        LifePart lifePart1 = new LifePart(5);
        LifePart lifePart2 = new LifePart(2);
        when(entity1.getPart(PositionPart.class))
                .thenReturn(commonPositionPart);
        when(entity2.getPart(PositionPart.class))
                .thenReturn(commonPositionPart);
        when(world.getEntities())
                .thenReturn(lists);
        when(entity1.getPart(LifePart.class))
                .thenReturn(lifePart1);
        when(entity2.getPart(LifePart.class))
                .thenReturn(lifePart2);
        when(entity1.getType())
                .thenReturn(Types.ENEMY);
        when(entity2.getType())
                .thenReturn(Types.PLAYER);
        when(entity1.getRadius())
                .thenReturn(5f);
        when(entity2.getRadius())
                .thenReturn(5f);
        collisionManager.process(gameData, world);
        LifePart lifePartEntity1 = entity1.getPart(LifePart.class);
        LifePart lifePartEntity2 = entity2.getPart(LifePart.class);
        assertEquals(4, lifePartEntity1.getLife());
        assertEquals(1, lifePartEntity2.getLife());
        verify(world, atLeast(1)).getEntities();
        verify(entity1, atLeast(2)).getPart(any());
        verify(entity2, atLeast(2)).getPart(any());
    }

    @Test
    public void testProcess_No_Collision() {
        List<Entity> lists = new ArrayList<>();
        lists.add(entity1);
        lists.add(entity2);
        PositionPart entity1PositionPart = new PositionPart(50, 50, 0);
        PositionPart entity2PositionPart = new PositionPart(10, 10, 0);
        LifePart lifePart1 = new LifePart(5);
        LifePart lifePart2 = new LifePart(2);
        when(entity1.getPart(PositionPart.class))
                .thenReturn(entity1PositionPart);
        when(entity2.getPart(PositionPart.class))
                .thenReturn(entity2PositionPart);
        when(world.getEntities())
                .thenReturn(lists);
        when(entity1.getPart(LifePart.class))
                .thenReturn(lifePart1);
        when(entity2.getPart(LifePart.class))
                .thenReturn(lifePart2);
        when(entity1.getType())
                .thenReturn(Types.ENEMY);
        when(entity2.getType())
                .thenReturn(Types.PLAYER);
        when(entity1.getRadius())
                .thenReturn(5f);
        when(entity2.getRadius())
                .thenReturn(5f);
        collisionManager.process(gameData, world);
        LifePart lifePartEntity1 = entity1.getPart(LifePart.class);
        LifePart lifePartEntity2 = entity2.getPart(LifePart.class);
        assertEquals(5, lifePartEntity1.getLife());
        assertEquals(2, lifePartEntity2.getLife());
        verify(world, atLeast(1)).getEntities();
        verify(entity1, atLeast(2)).getPart(any());
        verify(entity2, atLeast(2)).getPart(any());
    }

    @Test
    public void testProcess_Collision_Same_Type() {
        List<Entity> lists = new ArrayList<>();
        lists.add(entity1);
        lists.add(entity2);
        LifePart lifePart1 = new LifePart(5);
        LifePart lifePart2 = new LifePart(2);
        when(world.getEntities())
                .thenReturn(lists);
        when(entity1.getPart(LifePart.class))
                .thenReturn(lifePart1);
        when(entity2.getPart(LifePart.class))
                .thenReturn(lifePart2);
        when(entity1.getType())
                .thenReturn(Types.ENEMY);
        when(entity2.getType())
                .thenReturn(Types.ENEMY);
        collisionManager.process(gameData, world);
        LifePart lifePartEntity1 = entity1.getPart(LifePart.class);
        LifePart lifePartEntity2 = entity2.getPart(LifePart.class);
        assertEquals(5, lifePartEntity1.getLife());
        assertEquals(2, lifePartEntity2.getLife());
        verify(world, atLeast(1)).getEntities();
        verify(entity1, times(1)).getPart(any());
        verify(entity2, times(1)).getPart(any());
    }

    @Test
    public void testProcess_Collision_Tower() {
        List<Entity> lists = new ArrayList<>();
        lists.add(entity1);
        lists.add(entity2);
        LifePart lifePart1 = new LifePart(5);
        LifePart lifePart2 = new LifePart(2);
        when(world.getEntities())
                .thenReturn(lists);
        when(entity1.getPart(LifePart.class))
                .thenReturn(lifePart1);
        when(entity2.getPart(LifePart.class))
                .thenReturn(lifePart2);
        when(entity1.getType())
                .thenReturn(Types.TOWER);
        when(entity2.getType())
                .thenReturn(Types.ENEMY);
        collisionManager.process(gameData, world);
        LifePart lifePartEntity1 = entity1.getPart(LifePart.class);
        LifePart lifePartEntity2 = entity2.getPart(LifePart.class);
        assertEquals(5, lifePartEntity1.getLife());
        assertEquals(2, lifePartEntity2.getLife());
        verify(world, atLeast(1)).getEntities();
        verify(entity1, times(1)).getPart(any());
        verify(entity2, times(1)).getPart(any());
    }

    @Test
    public void testProcess_Collision_Player_Projectile() {
        List<Entity> lists = new ArrayList<>();
        lists.add(entity1);
        lists.add(entity2);
        LifePart lifePart1 = new LifePart(5);
        LifePart lifePart2 = new LifePart(2);
        when(world.getEntities())
                .thenReturn(lists);
        when(entity1.getPart(LifePart.class))
                .thenReturn(lifePart1);
        when(entity2.getPart(LifePart.class))
                .thenReturn(lifePart2);
        when(entity1.getType())
                .thenReturn(Types.PLAYER);
        when(entity2.getType())
                .thenReturn(Types.PROJECTILE);
        collisionManager.process(gameData, world);
        LifePart lifePartEntity1 = entity1.getPart(LifePart.class);
        LifePart lifePartEntity2 = entity2.getPart(LifePart.class);
        assertEquals(5, lifePartEntity1.getLife());
        assertEquals(2, lifePartEntity2.getLife());
        verify(world, atLeast(1)).getEntities();
        verify(entity1, times(1)).getPart(any());
        verify(entity2, times(1)).getPart(any());
    }

    @Test
    public void testProcess_Collision_Dead() {
        List<Entity> lists = new ArrayList<>();
        lists.add(entity1);
        lists.add(entity2);
        PositionPart commonPositionPart = new PositionPart(10, 10, 0);
        LifePart lifePart1 = new LifePart(5);
        LifePart lifePart2 = new LifePart(1);
        when(entity1.getPart(PositionPart.class))
                .thenReturn(commonPositionPart);
        when(entity2.getPart(PositionPart.class))
                .thenReturn(commonPositionPart);
        when(world.getEntities())
                .thenReturn(lists);
        when(entity1.getPart(LifePart.class))
                .thenReturn(lifePart1);
        when(entity2.getPart(LifePart.class))
                .thenReturn(lifePart2);
        when(entity1.getType())
                .thenReturn(Types.ENEMY);
        when(entity2.getType())
                .thenReturn(Types.PLAYER);
        when(entity1.getRadius())
                .thenReturn(5f);
        when(entity2.getRadius())
                .thenReturn(5f);
        collisionManager.process(gameData, world);
        LifePart lifePartEntity1 = entity1.getPart(LifePart.class);
        LifePart lifePartEntity2 = entity2.getPart(LifePart.class);
        assertEquals(4, lifePartEntity1.getLife());
        assertEquals(0, lifePartEntity2.getLife());
        verify(world, times(1)).removeEntity(entity2);
        verify(world, atLeast(1)).getEntities();
        verify(entity1, atLeast(2)).getPart(any());
        verify(entity2, atLeast(2)).getPart(any());
    }
}