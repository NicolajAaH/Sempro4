import com.badlogic.gdx.graphics.Texture;
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
    Entity entityMock;

    @Mock
    PositionPart positionPartMock;

    @Mock
    WeaponPart weaponPartMock;

    @Mock
    World worldMock;

    @Mock
    GameData gameDataMock;

    @Mock
    IMap mapMock;

    @Mock
    Texture textureMock;

    @BeforeEach
    public void setup(){
        projectileControlSystem = new ProjectileControlSystem();


        // injecting mock dependencies
        projectileControlSystem.setIMap(mapMock);
    }



    /**
     * Test of requirement F9.1: A Projectile must be able to be generated
     */
    @Test
    public void testGenerateProjectile() {
        Entity shooter = entityMock;

        // creating mock of shooters Weapon and Position Part
        when(positionPartMock.getX()).thenReturn(100F);
        when(positionPartMock.getY()).thenReturn(100F);
        when(positionPartMock.getAngle()).thenReturn(0);

        // adding mocks to shooter entity
        when(shooter.getPart(PositionPart.class)).thenReturn(positionPartMock);
        when(shooter.getPart(WeaponPart.class)).thenReturn(weaponPartMock);

        // mock for sprite creation
        HashMap<Types, Texture> hashMap = new HashMap<Types, Texture>(){{
            put(Types.PROJECTILE, textureMock);
        }};
        when(worldMock.getTextureHashMap()).thenReturn(hashMap);

        // calling method
        projectileControlSystem.createProjectile(shooter, gameDataMock, worldMock);

        // assert the addEntity has been called on worldMock with an Entity of Projectile class
        verify(worldMock, times(1)).addEntity(any(Projectile.class));
    }


    /**
     * Test of requirement F9.2:
     * A Projectile must be able to move in a straight line with a defined speed
     */

    @Test
    public void testStraightLineAndDefinedSpeed(){
        Entity projectile = entityMock;

        when(mapMock.isInsideMap(anyFloat(),anyFloat())).thenReturn(true);
        when(mapMock.getTileSize()).thenReturn(58);

        // Create a projectile: starting point (10,10), direction 0 degrees, speed: 6
        PositionPart projectilePositionPart = new PositionPart(10,10,0);
        MovingPart projectileMovingPart = new MovingPart(6, true);
        projectileMovingPart.setIMap(mapMock);
        when(projectile.getPart(PositionPart.class)).thenReturn(projectilePositionPart);
        when(projectile.getPart(MovingPart.class)).thenReturn(projectileMovingPart);
        when(projectile.getPart(WeaponPart.class)).thenReturn(weaponPartMock);

        // making world return created mocked projectile
        List<Entity> entityList = new ArrayList<Entity>(){{
            add(projectile);
        }};
        when(worldMock.getEntities(Projectile.class)).thenReturn(entityList);

        // call process method and assert that projectile has moved at it's speed along the x-axis
        projectileControlSystem.process(gameDataMock, worldMock);
        float speed = projectileMovingPart.getSpeed();
        float originX = projectilePositionPart.getOriginX();
        float newX = projectilePositionPart.getX();
        assertEquals(originX+speed, newX);

        // changing direction to 90 degrees and assert if moving at it's speed along the y-axis
        projectilePositionPart.setAngle(90);
        projectileControlSystem.process(gameDataMock, worldMock);
        assertEquals(projectilePositionPart.getOriginY() + projectileMovingPart.getSpeed(), projectilePositionPart.getY());
    }
}