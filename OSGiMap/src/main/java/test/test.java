package test;

import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import dk.sdu.mmmi.cbse.commonmap.IMap;
import dk.sdu.mmmi.cbse.map.MapType;
import org.junit.Test;

import java.awt.*;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class test {

    @Test
    public void test(){
        ArrayList<Point> expectedPath = new ArrayList<>();
        expectedPath.add(new Point(3,3));
        expectedPath.add(new Point(2,3));
        expectedPath.add(new Point(1,3));
        expectedPath.add(new Point(0,3));

        IMap map = new MapType();

        map.setTiledMap(new TmxMapLoader().load(""));
        ArrayList<Point> path = map.getPath();

        assertEquals(expectedPath.size(),path.size());
        for(int x = 0 ; x < path.size() ; x++){
            assertEquals(expectedPath.get(x), path.get(x));
        }
    }
}
