package dk.sdu.mmmi.cbse.map;

import dk.sdu.mmmi.cbse.commonmap.IMap;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mock;

import java.awt.*;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MapTest {

    @Ignore
    @Test
    public void test1() {

        IMap map = mock(IMap.class);

        ArrayList<Point> expectedPath = new ArrayList<>();
        expectedPath.add(new Point(3, 3));
        expectedPath.add(new Point(2, 3));
        expectedPath.add(new Point(1, 3));
        expectedPath.add(new Point(0, 3));

        when(map.getStartTileCoor()).thenReturn(new Point(3,2));
        when(map.getEndTileCoor()).thenReturn(new Point(0,2));
        when(map.getTileType(0,0)).thenReturn("Grass");
        when(map.getTileType(0,1)).thenReturn("Grass");
        when(map.getTileType(0,2)).thenReturn("End");
        when(map.getTileType(0,3)).thenReturn("Grass");
        when(map.getTileType(0,0)).thenReturn("Grass");
        when(map.getTileType(0,1)).thenReturn("Path");
        when(map.getTileType(0,2)).thenReturn("Path");
        when(map.getTileType(0,3)).thenReturn("Grass");
        when(map.getTileType(0,0)).thenReturn("Grass");
        when(map.getTileType(0,1)).thenReturn("Path");
        when(map.getTileType(0,2)).thenReturn("Path");
        when(map.getTileType(0,3)).thenReturn("Grass");
        when(map.getTileType(0,0)).thenReturn("Grass");
        when(map.getTileType(0,1)).thenReturn("Grass");
        when(map.getTileType(0,2)).thenReturn("Start");
        when(map.getTileType(0,3)).thenReturn("Grass");

        PathFinder pathFinder = new PathFinder(map);
        ArrayList<Point> path = pathFinder.calculatePath();

        assertEquals(expectedPath.size(), path.size());
        for (int x = 0; x < path.size(); x++) {
            assertEquals(expectedPath.get(x), path.get(x));
        }
    }

    @Ignore
    @Test
    public void test2() {
        IMap map = mock(IMap.class);

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

        when(map.getStartTileCoor()).thenReturn(new Point(3,3));
        when(map.getEndTileCoor()).thenReturn(new Point(3,0));
        when(map.getTileType(0,0)).thenReturn("Path");
        when(map.getTileType(0,1)).thenReturn("Path");
        when(map.getTileType(0,2)).thenReturn("Path");
        when(map.getTileType(0,3)).thenReturn("Path");
        when(map.getTileType(0,0)).thenReturn("Path");
        when(map.getTileType(0,1)).thenReturn("Grass");
        when(map.getTileType(0,2)).thenReturn("Grass");
        when(map.getTileType(0,3)).thenReturn("Path");
        when(map.getTileType(0,0)).thenReturn("Path");
        when(map.getTileType(0,1)).thenReturn("Grass");
        when(map.getTileType(0,2)).thenReturn("Grass");
        when(map.getTileType(0,3)).thenReturn("Path");
        when(map.getTileType(0,0)).thenReturn("End");
        when(map.getTileType(0,1)).thenReturn("Grass");
        when(map.getTileType(0,2)).thenReturn("Grass");
        when(map.getTileType(0,3)).thenReturn("Start");

        PathFinder pathFinder = new PathFinder(map);
        ArrayList<Point> path = pathFinder.calculatePath();

        assertEquals(expectedPath.size(), path.size());
        for (int x = 0; x < path.size(); x++) {
            assertEquals(expectedPath.get(x), path.get(x));
        }
    }

    @Ignore
    @Test
    public void test3() {
        IMap map = mock(IMap.class);

        ArrayList<Point> expectedPath = new ArrayList<>();
        expectedPath.add(new Point(3, 0));
        expectedPath.add(new Point(2, 0));
        expectedPath.add(new Point(2, 1));
        expectedPath.add(new Point(2, 2));
        expectedPath.add(new Point(2, 3));
        expectedPath.add(new Point(3, 3));

        when(map.getStartTileCoor()).thenReturn(new Point(3,3));
        when(map.getEndTileCoor()).thenReturn(new Point(3,0));
        when(map.getTileType(0,0)).thenReturn("Path");
        when(map.getTileType(0,1)).thenReturn("Path");
        when(map.getTileType(0,2)).thenReturn("Path");
        when(map.getTileType(0,3)).thenReturn("Path");
        when(map.getTileType(0,0)).thenReturn("Path");
        when(map.getTileType(0,1)).thenReturn("Grass");
        when(map.getTileType(0,2)).thenReturn("Grass");
        when(map.getTileType(0,3)).thenReturn("Path");
        when(map.getTileType(0,0)).thenReturn("Path");
        when(map.getTileType(0,1)).thenReturn("Path");
        when(map.getTileType(0,2)).thenReturn("Path");
        when(map.getTileType(0,3)).thenReturn("Path");
        when(map.getTileType(0,0)).thenReturn("End");
        when(map.getTileType(0,1)).thenReturn("Grass");
        when(map.getTileType(0,2)).thenReturn("Grass");
        when(map.getTileType(0,3)).thenReturn("Start");

        PathFinder pathFinder = new PathFinder(map);
        ArrayList<Point> path = pathFinder.calculatePath();

        assertEquals(expectedPath.size(), path.size());
        for (int x = 0; x < path.size(); x++) {
            assertEquals(expectedPath.get(x), path.get(x));
        }
    }
}
