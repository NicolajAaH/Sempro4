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
        // Numbers corresponding to there type on the map
        final String path = "4";
        final String start = "5";
        final String end = "2";

        // Find the file to test and check if it is there
        final File mapFile = new File("src/test/resources/tallMap.tmx");
        Assert.assertTrue("Can't find the map file", mapFile.exists());

        // Create 2d array with the map
        final String[][] mapArray = this.createMapArray(mapFile);

        // Initialize start and end point
        Point startPoint = null;
        Point endPoint = null;

        // Find start and end point in mapArray
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

        // Check that the mapArray has a start and end point
        Assert.assertNotNull("The map is missing a start point", startPoint);
        Assert.assertNotNull("The map is missing an end point", endPoint);

        // Create list to hold unexplored and explored path
        final List<Point> unexploredPath = new ArrayList<>();
        final List<Point> exploredPath = new ArrayList<>();

        // Add start point to unexplored path
        unexploredPath.add(startPoint);

        // While there is still unexplored path, look for adjacent paths
        while (!unexploredPath.isEmpty()) {
            // Initialize current point to the first point in unexplored path
            final Point currentPoint = unexploredPath.get(0); // Breadth First
            final int x = currentPoint.x;
            final int y = currentPoint.y;

            // currentPoint is about to be explored, add it to explored path
            exploredPath.add(currentPoint);

            // Ternary statements to stay inside mapArray boundaries
            for (int dx = (x > 0) ? -1 : 0; dx <= ((x < mapArray.length - 1) ? 1 : 0); ++dx) {
                for (int dy = (y > 0) ? -1 : 0; dy <= ((y < mapArray[0].length - 1) ? 1 : 0); ++dy) {
                    // Makes sure points diagonal to currentPoint is not explored
                    if (dx == 0 ^ dy == 0) {
                        final Point adjacentPoint = new Point(x + dx, y + dy);
                        //Checks if adjacentPoint is a path and that it has not already been explored
                        if (mapArray[adjacentPoint.x][adjacentPoint.y].equals(path) && !exploredPath.contains(adjacentPoint)) {
                            unexploredPath.add(adjacentPoint);
                        }
                        // Checks if the adjacentPoint is the end point
                        if (mapArray[adjacentPoint.x][adjacentPoint.y].equals(end)) {
                            // Adds it directly to exploredPath because there is no reason to explore the end
                            exploredPath.add(adjacentPoint);
                        }
                    }
                }
            }
            // currentPoint has just been explored, remove it from unexplored path
            unexploredPath.remove(currentPoint);
        }
        // Check if the last point in exploredPath is equal to the end point
        Assert.assertEquals("No path to reach the end point", endPoint, exploredPath.get(exploredPath.size() - 1));
    }
    
    private String[][] createMapArray(final File file) {
        // List to hold String array
        final List<String[]> mapLines = new ArrayList<>();
        try (final Scanner scanFile = new Scanner(file)) {
            while (scanFile.hasNextLine()) {
                final String line = scanFile.nextLine();
                // Makes sure not to add meta-data lines (enclosed in <>) or empty lines
                if (!line.contains("<") && !line.isEmpty()) {
                    final String[] tiles = line.split(",");
                    mapLines.add(tiles);
                }
            }
        }
        catch (FileNotFoundException exception) {
            exception.printStackTrace();
        }
        // Creates a 2d array from the mapLines list
        return mapLines.toArray(new String[0][0]);
    }
}