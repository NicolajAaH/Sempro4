package dk.sdu.mmmi.cbse.map;

import dk.sdu.mmmi.cbse.commonmap.IMap;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.awt.*;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MapTest {

    @Mock
    IMap map;

    @Test
    public void test1() {
        ArrayList<Point> expectedPath = new ArrayList<>();
        expectedPath.add(new Point(0, 2));
        expectedPath.add(new Point(1,2));
        expectedPath.add(new Point(2,2));
        expectedPath.add(new Point(3, 2));

        when(map.getStartTileCoor()).thenReturn(new Point(3,2));
        when(map.getEndTileCoor()).thenReturn(new Point(0,2));
        when(map.getTileType(0,2)).thenReturn("End");
        when(map.getTileType(1,1)).thenReturn("Path");
        when(map.getTileType(1,2)).thenReturn("Path");
        when(map.getTileType(1,3)).thenReturn("Grass");
        when(map.getTileType(2,1)).thenReturn("Path");
        when(map.getTileType(2,2)).thenReturn("Path");
        when(map.getTileType(2,3)).thenReturn("Grass");
        when(map.getTileType(3,1)).thenReturn("Grass");
        when(map.getTileType(3,2)).thenReturn("Start");
        when(map.getTileType(3,3)).thenReturn("Grass");
        when(map.getTileType(4,2)).thenReturn("Grass");

        PathFinder pathFinder = new PathFinder(map);
        ArrayList<Point> path = pathFinder.calculatePath();

        assertEquals(expectedPath.size(), path.size());
        for (int x = 0; x < path.size(); x++) {
            assertEquals(expectedPath.get(x), path.get(x));
        }
    }
}
