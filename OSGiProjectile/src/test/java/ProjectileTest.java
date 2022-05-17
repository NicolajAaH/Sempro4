import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.WeaponPart;
import dk.sdu.mmmi.cbse.commonmap.IMap;
import dk.sdu.mmmi.cbse.commonprojectile.Projectile;
import dk.sdu.mmmi.cbse.projectile.ProjectileControlSystem;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedConstruction;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;


import static org.mockito.Mockito.*;

/**
 * Test of requirement F9.1: A Projectile must be able to be generated
 */

//@RunWith(PowerMockRunner.class)

@ExtendWith(MockitoExtension.class)

public class ProjectileTest {

    ProjectileControlSystem projectileControlSystem = new ProjectileControlSystem();

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
    Projectile projectile;

    @Mock
    Sprite sprite;

    @Mock
    Texture texture;

    @Test
    public void testGenerateProjectile() throws Exception {

        // injecting mock dependencies
        projectileControlSystem.setIMap(mockMap);

        // creating mock of shooters Weapon and Position Part
        when(positionPartMock.getX()).thenReturn(100F);
        when(positionPartMock.getY()).thenReturn(100F);
        when(positionPartMock.getRadians()).thenReturn(0);
        when(weaponPartMock.getProjectileSpeed()).thenReturn(6F);

        //when(mockMap.getTileSize()).thenReturn(58);

        // adding mocks to shooter entity
        when(shooter.getPart(PositionPart.class)).thenReturn(positionPartMock);
        when(shooter.getPart(WeaponPart.class)).thenReturn(weaponPartMock);

        MockedConstruction<Sprite>
        // calling method
        projectileControlSystem.createProjectile(shooter, gameData, world);

        // assert the world contains a projectile
        verify(world, times(1)).addEntity(any(Projectile.class));
    }
}
