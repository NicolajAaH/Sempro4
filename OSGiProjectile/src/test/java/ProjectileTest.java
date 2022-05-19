import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.Types;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.MovingPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.WeaponPart;
import dk.sdu.mmmi.cbse.commonmap.IMap;
import dk.sdu.mmmi.cbse.commonprojectile.Projectile;
import dk.sdu.mmmi.cbse.projectile.ProjectileControlSystem;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class ProjectileTest {

    ProjectileControlSystem projectileControlSystem;

    @Mock
    Entity shooter;

    @Mock
    PositionPart positionPartMock;

    @Mock
    WeaponPart weaponPartMock;

    @Mock
    World world;

    @Mock
    GameData gameData;

    @Mock
    IMap mockMap;

    @Mock
    Texture texture;

    @Mock
    Sprite sprite;

    @Mock
    Projectile projectile;

    @BeforeEach
    public void setup(){
        projectileControlSystem = new ProjectileControlSystem();
        when(mockMap.getTileSize()).thenReturn(58);

        // injecting mock dependencies
        projectileControlSystem.setIMap(mockMap);
    }



    /**
     * Test of requirement F9.1: A Projectile must be able to be generated
     */
    @Test
    public void testGenerateProjectile() {

        // creating mock of shooters Weapon and Position Part
        when(positionPartMock.getX()).thenReturn(100F);
        when(positionPartMock.getY()).thenReturn(100F);
        when(positionPartMock.getRadians()).thenReturn(0);

        // adding mocks to shooter entity
        when(shooter.getPart(PositionPart.class)).thenReturn(positionPartMock);
        when(shooter.getPart(WeaponPart.class)).thenReturn(weaponPartMock);

        //MockedConstruction<Sprite>
        HashMap<Types, Texture> hashMap = new HashMap<Types, Texture>(){{
            put(Types.PROJECTILE, texture);
        }};
        when(world.getTextureHashMap()).thenReturn(hashMap);

        // calling method
        projectileControlSystem.createProjectile(shooter, gameData, world);

        // assert the addEntity has been calld on world with a Entity of projectil.class
        verify(world, times(1)).addEntity(any(Projectile.class));
    }


    /**
     * Test of requirement F9.2:
     * A Projectile must be able to move in a straight line with a defined speed
     */

    @Test
    public void testStraightLineAndDefinedSpeed(){

        when(mockMap.isInsideMap(anyFloat(),anyFloat())).thenReturn(true);
        // Create a projectile: starting point (10,10), direction 0 degrees, speed: 6

        PositionPart projectilePositionPart = new PositionPart(10,10,0);
        MovingPart projectileMovingPart = new MovingPart(6,0,true);
        projectileMovingPart.setIMap(mockMap);
        when(projectile.getPart(PositionPart.class)).thenReturn(projectilePositionPart);
        when(projectile.getPart(MovingPart.class)).thenReturn(projectileMovingPart);
        when(projectile.getPart(WeaponPart.class)).thenReturn(weaponPartMock);


        // making world return created mocked projectile
        List<Entity> entityList = new ArrayList<Entity>(){{
            add(projectile);
        }};
        when(world.getEntities(Projectile.class)).thenReturn(entityList);

        // call process method and assert that projectile has moved at it's speed along the x-axis
        projectileControlSystem.process(gameData, world);
        float speed = projectileMovingPart.getSpeed();
        float originX = projectilePositionPart.getOriginX();
        float newX = projectilePositionPart.getX();
        assertEquals(originX+speed, newX);

        // changing direction to 90 degrees and assert if moving at it's speed along the y-axis
        projectilePositionPart.setRadians(90);
        projectileControlSystem.process(gameData, world);
        assertEquals(projectilePositionPart.getOriginY() + projectileMovingPart.getSpeed(), projectilePositionPart.getY());
    }
}

// TODO: Refactor Weapon and Movingpart - delete unused elements - eg. rotation speed in mp and frequency and damage in waepon part. Refacter all X & Y's to Points!