package dk.sdu.mmmi.cbse.map;

import dk.sdu.mmmi.cbse.commonmap.IMap;
import org.junit.Ignore;
import org.junit.Test;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.Assert.assertEquals;

public class MapTest {

    @Ignore
    @Test
    public void test1() {
        HashMap<Point, String> tileTypes = new HashMap<>();
        tileTypes.put(new Point(0,0),"Grass");
        tileTypes.put(new Point(0,1),"Grass");
        tileTypes.put(new Point(0,2),"End");
        tileTypes.put(new Point(0,3),"Grass");
        tileTypes.put(new Point(1,0),"Grass");
        tileTypes.put(new Point(1,1),"Path");
        tileTypes.put(new Point(1,2),"Path");
        tileTypes.put(new Point(1,3),"Grass");
        tileTypes.put(new Point(2,0),"Grass");
        tileTypes.put(new Point(2,1),"Path");
        tileTypes.put(new Point(2,2),"Path");
        tileTypes.put(new Point(2,3),"Grass");
        tileTypes.put(new Point(3,0),"Grass");
        tileTypes.put(new Point(3,1),"Grass");
        tileTypes.put(new Point(3,2),"Start");
        tileTypes.put(new Point(3,3),"Grass");
        Point startPoint = new Point(3,2);
        Point endPoint = new Point(0,2);

        ArrayList<Point> expectedPath = new ArrayList<>();
        expectedPath.add(new Point(3, 3));
        expectedPath.add(new Point(2, 3));
        expectedPath.add(new Point(1, 3));
        expectedPath.add(new Point(0, 3));

        IMap map = new MockMap(tileTypes, startPoint, endPoint);

        ArrayList<Point> path = map.getPath();

        System.out.println(path);

        assertEquals(expectedPath.size(), path.size());
        for (int x = 0; x < path.size(); x++) {
            assertEquals(expectedPath.get(x), path.get(x));
        }
    }

    @Ignore
    @Test
    public void test2() {
        HashMap<Point, String> tileTypes = new HashMap<>();
        tileTypes.put(new Point(0,0),"Path");
        tileTypes.put(new Point(0,1),"Path");
        tileTypes.put(new Point(0,2),"Path");
        tileTypes.put(new Point(0,3),"Path");
        tileTypes.put(new Point(1,0),"Path");
        tileTypes.put(new Point(1,1),"Grass");
        tileTypes.put(new Point(1,2),"Grass");
        tileTypes.put(new Point(1,3),"Path");
        tileTypes.put(new Point(2,0),"Path");
        tileTypes.put(new Point(2,1),"Grass");
        tileTypes.put(new Point(2,2),"Grass");
        tileTypes.put(new Point(2,3),"Path");
        tileTypes.put(new Point(3,0),"End");
        tileTypes.put(new Point(3,1),"Grass");
        tileTypes.put(new Point(3,2),"Grass");
        tileTypes.put(new Point(3,3),"Start");
        Point startPoint = new Point(3,3);
        Point endPoint = new Point(3,0);

        ArrayList<Point> expectedPath = new ArrayList<>();
        expectedPath.add(new Point(3, 0));
        expectedPath.add(new Point(2, 0));
        expectedPath.add(new Point(1, 0));
        expectedPath.add(new Point(0, 0));
        expectedPath.add(new Point(0, 1));
        expectedPath.add(new Point(0, 2));
        expectedPath.add(new Point(0, 3));
        expectedPath.add(new Point(1, 3));
        expectedPath.add(new Point(2, 3));
        expectedPath.add(new Point(3, 3));

        IMap map = new MockMap(tileTypes, startPoint, endPoint);

        ArrayList<Point> path = map.getPath();

        System.out.println(path);

        assertEquals(expectedPath.size(), path.size());
        for (int x = 0; x < path.size(); x++) {
            assertEquals(expectedPath.get(x), path.get(x));
        }
    }

    @Ignore
    @Test
    public void test3() {
        HashMap<Point, String> tileTypes = new HashMap<>();
        tileTypes.put(new Point(0,0),"Path");
        tileTypes.put(new Point(0,1),"Path");
        tileTypes.put(new Point(0,2),"Path");
        tileTypes.put(new Point(0,3),"Path");
        tileTypes.put(new Point(1,0),"Path");
        tileTypes.put(new Point(1,1),"Grass");
        tileTypes.put(new Point(1,2),"Grass");
        tileTypes.put(new Point(1,3),"Path");
        tileTypes.put(new Point(2,0),"Path");
        tileTypes.put(new Point(2,1),"Path");
        tileTypes.put(new Point(2,2),"Path");
        tileTypes.put(new Point(2,3),"Path");
        tileTypes.put(new Point(3,0),"End");
        tileTypes.put(new Point(3,1),"Grass");
        tileTypes.put(new Point(3,2),"Grass");
        tileTypes.put(new Point(3,3),"Start");
        Point startPoint = new Point(3,3);
        Point endPoint = new Point(3,0);

        ArrayList<Point> expectedPath = new ArrayList<>();
        expectedPath.add(new Point(3, 0));
        expectedPath.add(new Point(2, 0));
        expectedPath.add(new Point(2, 1));
        expectedPath.add(new Point(2, 2));
        expectedPath.add(new Point(2, 3));
        expectedPath.add(new Point(3, 3));

        IMap map = new MockMap(tileTypes, startPoint, endPoint);

        ArrayList<Point> path = map.getPath();

        System.out.println(path);

        assertEquals(expectedPath.size(), path.size());
        for (int x = 0; x < path.size(); x++) {
            assertEquals(expectedPath.get(x), path.get(x));
        }
    }
}
