package dk.sdu.mmmi.cbse.common;

import com.badlogic.gdx.graphics.g2d.Sprite;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.entityparts.LifePart;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class LifePartTest {

    @Test
    public void test(){
        Entity entity = new Entity(new Sprite());
        LifePart lifePart = new LifePart(0);

        assertEquals(0,lifePart.getLife());

        lifePart.setLife(1);

        assertEquals(1,lifePart.getLife());

        entity.add(lifePart);

        assertEquals(1, ((LifePart) entity.getPart(LifePart.class)).getLife());

        ((LifePart) entity.getPart(LifePart.class)).setLife(2);

        assertEquals(2, ((LifePart) entity.getPart(LifePart.class)).getLife());
    }
    
}
