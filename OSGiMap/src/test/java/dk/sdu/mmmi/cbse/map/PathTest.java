package dk.sdu.mmmi.cbse.map;

import java.io.FileNotFoundException;
import java.util.Scanner;
import org.junit.Test;
import java.util.List;
import java.util.ArrayList;
import java.awt.Point;
import org.junit.Assert;
import java.io.File;

public class PathTest
{
    @Test
    public void pathTest() {
        final String path = "4";
        final String start = "5";
        final String end = "2";
        final File mapFile = new File("src/test/resources/tallMap.tmx");
        Assert.assertTrue("Can't find the map file", mapFile.exists());
        final String[][] mapArray = this.createMapArray(mapFile);
        Point startPoint = null;
        Point endPoint = null;
        for (int row = 0; row < mapArray.length; ++row) {
            for (int col = 0; col < mapArray[0].length; ++col) {
                if (mapArray[row][col].equals(start)) {
                    startPoint = new Point(row, col);
                }
                if (mapArray[row][col].equals(end)) {
                    endPoint = new Point(row, col);
                }
            }
        }
        Assert.assertNotNull("The map is missing a start point", (Object)startPoint);
        Assert.assertNotNull("The map is missing an end point", (Object)endPoint);
        final List<Point> unexploredPath = new ArrayList<Point>();
        final List<Point> exploredPath = new ArrayList<Point>();
        unexploredPath.add(startPoint);
        while (!unexploredPath.isEmpty()) {
            final Point currentPoint = unexploredPath.get(0);
            final int x = currentPoint.x;
            final int y = currentPoint.y;
            exploredPath.add(currentPoint);
            for (int dx = (x > 0) ? -1 : 0; dx <= ((x < mapArray.length - 1) ? 1 : 0); ++dx) {
                for (int dy = (y > 0) ? -1 : 0; dy <= ((y < mapArray[0].length - 1) ? 1 : 0); ++dy) {
                    if (dx == 0 ^ dy == 0) {
                        final Point adjacentPoint = new Point(x + dx, y + dy);
                        if (mapArray[adjacentPoint.x][adjacentPoint.y].equals(path) && !exploredPath.contains(adjacentPoint)) {
                            unexploredPath.add(adjacentPoint);
                        }
                        if (mapArray[adjacentPoint.x][adjacentPoint.y].equals(end)) {
                            exploredPath.add(adjacentPoint);
                        }
                    }
                }
            }
            unexploredPath.remove(currentPoint);
        }
        Assert.assertEquals("No path to reach the end point", (Object)endPoint, (Object)exploredPath.get(exploredPath.size() - 1));
    }
    
    private String[][] createMapArray(final File file) {
        final List<String[]> mapLines = new ArrayList<String[]>();
        try (final Scanner scanFile = new Scanner(file)) {
            while (scanFile.hasNextLine()) {
                final String line = scanFile.nextLine();
                if (!line.contains("<") && !line.isEmpty()) {
                    final String[] tiles = line.split(",");
                    mapLines.add(tiles);
                }
            }
        }
        catch (FileNotFoundException exception) {
            exception.printStackTrace();
        }
        return mapLines.toArray(new String[0][0]);
    }
}